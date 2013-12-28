CREATE DATABASE 'test.fdb' user 'SYSDBA' password 'masterkey' DEFAULT CHARACTER SET WIN1251;

CREATE TABLE companies(
	id INTEGER NOT NULL, 
	name VARCHAR(20) NOT NULL, 
	details VARCHAR(256),
	CONSTRAINT pk_companies PRIMARY KEY (id)  
);

CREATE TABLE users(
	login VARCHAR(20) NOT NULL, 
	passwd VARCHAR(20) NOT NULL,
	adminFlag INTEGER DEFAULT 0,
	CONSTRAINT pk_users PRIMARY KEY(login)  
);
			
CREATE TABLE employees(
	id INTEGER NOT NULL, 
	name VARCHAR(20) NOT NULL,
	companyId INTEGER NOT NULL, 
	login VARCHAR(20) NOT NULL,
	CONSTRAINT pk_employees PRIMARY KEY (id),
	CONSTRAINT fk_employees_companies FOREIGN KEY(companyId) REFERENCES companies(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_employees_users FOREIGN KEY(login) REFERENCES users(login) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE projects(
	id INTEGER NOT NULL, 
	name VARCHAR(20) NOT NULL,
	startDate TIMESTAMP NOT NULL,
	status INTEGER NOT NULL,
	CONSTRAINT pk_projects PRIMARY KEY(id)
);
			
CREATE TABLE contracts(
	companyId INTEGER NOT NULL,
	projectId INTEGER NOT NULL,
	activity INTEGER DEFAULT 0,
	CONSTRAINT pk_contracts PRIMARY KEY(companyId, projectId),
	CONSTRAINT fk_contracts_companies FOREIGN KEY(companyId) REFERENCES companies(id) ON DELETE CASCADE ON UPDATE CASCADE,
        CONSTRAINT fk_contracts_projects FOREIGN KEY(projectId) REFERENCES projects(id) ON DELETE CASCADE ON UPDATE CASCADE
);
			
CREATE TABLE projectEmployees(
	employeeId INTEGER NOT NULL,
	projectId INTEGER NOT NULL,
	role INTEGER DEFAULT 0,
	CONSTRAINT pk_project_employees PRIMARY KEY(employeeId, projectId),
	CONSTRAINT fk_pe_projects FOREIGN KEY (projectId) REFERENCES projects(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_pe_employees FOREIGN KEY (employeeId) REFERENCES employees(id) ON DELETE CASCADE ON UPDATE CASCADE
);
			
CREATE TABLE tasks(
	id INTEGER NOT NULL,
	name VARCHAR(20) NOT NULL,
	projectId INTEGER NOT NULL,
	plannedTime INTEGER NOT NULL,
	status INTEGER NOT NULL,
	CONSTRAINT pk_tasks PRIMARY KEY(id),
	CONSTRAINT fk_tasks_projects FOREIGN KEY (projectId) REFERENCES projects(id) ON DELETE CASCADE ON UPDATE CASCADE
);
			
CREATE TABLE jobs(
	id INTEGER NOT NULL,
	employeeId INTEGER NOT NULL,
	taskId INTEGER NOT NULL,
	startDate TIMESTAMP,
	completionDate TIMESTAMP,
	description VARCHAR(256),
	CONSTRAINT pk_jobs PRIMARY KEY(id),
	CONSTRAINT fk_jobs_employees FOREIGN KEY (employeeId) REFERENCES employees(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_jobs_tasks FOREIGN KEY (TaskId) REFERENCES tasks(id) ON DELETE CASCADE ON UPDATE CASCADE
);
			
CREATE TABLE tasksDependency(
	slaveId INTEGER NOT NULL,
	masterId INTEGER NOT NULL,
	CONSTRAINT pk_tasksDependency PRIMARY KEY(slaveId, masterId),
	CONSTRAINT fk_td1_tasks FOREIGN KEY(slaveId) REFERENCES tasks(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_td2_tasks FOREIGN KEY(masterId) REFERENCES tasks(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE journal(
	id INTEGER NOT NULL,
	login VARCHAR(20) NOT NULL,
	passwd VARCHAR(20) NOT NULL,
	entranceDate TIMESTAMP NOT NULL,
    CONSTRAINT pk_journal PRIMARY KEY(id),
    CONSTRAINT fk_journal_users FOREIGN KEY (login) REFERENCES users(login) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tfmask(
	id INTEGER NOT NULL,
	name VARCHAR(20) NOT NULL,	
	CONSTRAINT pk_tfmask PRIMARY KEY(id)
);

CREATE EXCEPTION ERROR_INCOMPATIBLE_TASKS 'Tasks must be from same project';
CREATE EXCEPTION ERROR_CYCLE_DEPENDENCY 'Tasks become cycle dependent';
CREATE EXCEPTION ERROR_NO_CONTRACT 'There is no contract on this project';
CREATE EXCEPTION ERROR_EMPLOYEE_BUSY 'Employee is busy in this time lapse';
CREATE EXCEPTION ERROR_NO_RIGHTS 'You haven`t rights to do this';
CREATE EXCEPTION ERROR_LOGIN_FAIL 'No such login or password';




CREATE GENERATOR companies_id_gen;
SET GENERATOR companies_id_gen TO 0;

CREATE GENERATOR employees_id_gen;
SET GENERATOR employees_id_gen TO 0;

CREATE GENERATOR projects_id_gen;
SET GENERATOR projects_id_gen TO 0;

CREATE GENERATOR tasks_id_gen;
SET GENERATOR tasks_id_gen TO 0;

CREATE GENERATOR jobs_id_gen;
SET GENERATOR jobs_id_gen TO 0;


set term !! ;
CREATE TRIGGER companies_id_trig FOR companies
ACTIVE BEFORE INSERT POSITION 1
AS
BEGIN
if (NEW.id is NULL or NEW.id = 0) then NEW.id = GEN_ID(companies_id_gen, 1);
END!!

CREATE TRIGGER employees_id_trig FOR employees
ACTIVE BEFORE INSERT POSITION 1
AS
BEGIN
if (NEW.id is NULL or NEW.id = 0) then NEW.id = GEN_ID(employees_id_gen, 1);
END!!

CREATE TRIGGER projects_id_trig FOR projects
ACTIVE BEFORE INSERT POSITION 1
AS
BEGIN
if (NEW.id is NULL or NEW.id = 0) then NEW.id = GEN_ID(projects_id_gen, 1);
END!!

CREATE TRIGGER tasks_id_trig FOR tasks
ACTIVE BEFORE INSERT POSITION 1
AS
BEGIN
if (NEW.id is NULL or NEW.id = 0) then NEW.id = GEN_ID(tasks_id_gen, 1);
END!!

CREATE TRIGGER jobs_id_trig FOR jobs
ACTIVE BEFORE INSERT POSITION 1
AS
BEGIN
if (NEW.id is NULL or NEW.id = 0) then NEW.id = GEN_ID(jobs_id_gen, 1);
END!!

set term ; !!

SET TERM ^ ;

CREATE PROCEDURE check_task_dependency 
 ( slave int,  master int ) 
RETURNS 
 ( cycled int )
AS 
 DECLARE VARIABLE slave_id int;
BEGIN
  cycled = 0;
  for select  td.SLAVEID from TASKSDEPENDENCY td where td.MASTERID = :slave
      into slave_id
  do
  begin
    if (slave_id = master) then cycled = 1;
    else execute procedure check_task_dependency (slave_id, master) returning_values cycled;
  end
END^

CREATE PROCEDURE is_admin
RETURNS 
 ( aflag int )
AS 
 DECLARE VARIABLE login VARCHAR(20);
BEGIN
    aflag = 0;
    if (exists(select * from journal)) then
    begin
        select j.login from journal j order by j.id desc into login;
        select u.adminFlag from users u where u.login = :login into aflag;
    end
    else
        aflag = 1;
END^

CREATE PROCEDURE is_manager
 (projectId int)
RETURNS 
 ( mflag int )
AS 
 DECLARE VARIABLE login VARCHAR(20);
BEGIN
    mflag = 0;
    select j.login from journal j order by j.id desc into login;
    select pe.role from employees e join projectemployees pe on e.id = pe.employeeid where e.login = :login into mflag;
END^

CREATE PROCEDURE is_worker
 (projectId int)
RETURNS 
 ( wflag int )
AS 
 DECLARE VARIABLE login VARCHAR(20);
BEGIN
    wflag = 0;
    select j.login from journal j order by j.id desc into login;
    select pe.role from employees e join projectemployees pe on e.id = pe.employeeid where e.login = :login into wflag;
    if (wflag = 0) then wflag = 1;
    else wflag = 0;
END^

CREATE TRIGGER COMPANIES_PROTECTION_TRIGGER for companies
 ACTIVE 
 BEFORE INSERT OR UPDATE OR DELETE
 POSITION 0 
AS 
 DECLARE VARIABLE aflag int;
BEGIN
    execute procedure is_admin returning_values aflag;
    if (aflag = 0) then exception ERROR_NO_RIGHTS;
END^

CREATE TRIGGER USERS_PROTECTION_TRIGGER for users
 ACTIVE 
 BEFORE INSERT OR UPDATE OR DELETE
 POSITION 0 
AS 
 DECLARE VARIABLE aflag int;
BEGIN
    execute procedure is_admin returning_values aflag;
    if (aflag = 0) then exception ERROR_NO_RIGHTS;
END^

CREATE TRIGGER EMPLOYEES_PROTECTION_TRIGGER for employees
 ACTIVE 
 BEFORE INSERT OR UPDATE OR DELETE
 POSITION 0 
AS 
 DECLARE VARIABLE aflag int;
BEGIN
    execute procedure is_admin returning_values aflag;
    if (aflag = 0) then exception ERROR_NO_RIGHTS;
END^

CREATE TRIGGER PROJECTS_PROTECTION_TRIGGER_DEL for projects
 ACTIVE 
 BEFORE INSERT OR DELETE
 POSITION 0 
AS 
 DECLARE VARIABLE aflag int;
BEGIN
    execute procedure is_admin returning_values aflag;
    if (aflag = 0) then exception ERROR_NO_RIGHTS;
END^

CREATE TRIGGER PROJECTS_PROTECTION_TRIGGER for projects
 ACTIVE 
 BEFORE UPDATE 
 POSITION 0 
AS 
 DECLARE VARIABLE aflag int;
 DECLARE VARIABLE mflag int;
BEGIN
    execute procedure is_admin returning_values aflag;
    execute procedure is_manager (new.id) returning_values mflag;
    if (aflag = 0) then
    begin
        if (mflag = 0 or old.id <> new.id or old.startdate <> new.startdate or old.name <> new.name) then
            exception ERROR_NO_RIGHTS;   
    end
END^

CREATE TRIGGER CONTRACTS_PROTECTION_TRIGGER for contracts 
 ACTIVE 
 BEFORE INSERT OR UPDATE OR DELETE
 POSITION 0 
AS 
 DECLARE VARIABLE aflag int;
BEGIN
    execute procedure is_admin returning_values aflag;
    if (aflag = 0) then exception ERROR_NO_RIGHTS;
END^

CREATE TRIGGER PE_PROTECTION_TRIGGER_DEL for projectEmployees 
 ACTIVE 
 BEFORE DELETE
 POSITION 0 
AS 
 DECLARE VARIABLE aflag int;
BEGIN
    execute procedure is_admin returning_values aflag;
    if (aflag = 0) then exception ERROR_NO_RIGHTS;
END^

CREATE TRIGGER PE_PROTECTION_TRIGGER for projectEmployees
 ACTIVE 
 BEFORE INSERT OR UPDATE 
 POSITION 0 
AS 
 DECLARE VARIABLE aflag int;
 DECLARE VARIABLE mflag int;
BEGIN
    execute procedure is_admin returning_values aflag;
    execute procedure is_manager (new.projectid) returning_values mflag;
    if (aflag = 0) then
    begin
        if (mflag = 0 or new.role = 1) then
            exception ERROR_NO_RIGHTS;   
    end
END^

CREATE TRIGGER TASKS_PROTECTION_TRIGGER for tasks
 ACTIVE 
 BEFORE INSERT OR UPDATE OR DELETE
 POSITION 0 
AS 
 DECLARE VARIABLE aflag int;
 DECLARE VARIABLE mflag int;
 DECLARE VARIABLE wflag int;
BEGIN
    execute procedure is_admin returning_values aflag;
    execute procedure is_manager (new.projectid) returning_values mflag;
    execute procedure is_worker (new.projectid) returning_values wflag;
    if (aflag = 0 and mflag = 0) then
    begin
        if (not updating or old.id <> new.id or old.name <> new.name or old.projectid <> new.projectid or old.plannedtime <> new.plannedtime or new.status <> 2) then
            exception ERROR_NO_RIGHTS;
    end   
END^

CREATE TRIGGER JOBS_PROTECTION_TRIGGER for jobs
 ACTIVE 
 BEFORE INSERT OR UPDATE OR DELETE 
 POSITION 0 
AS 
 DECLARE VARIABLE aflag int;
 DECLARE VARIABLE mflag int;
 DECLARE VARIABLE wflag int;
 DECLARE VARIABLE eid int;
 DECLARE VARIABLE pid int;
 DECLARE VARIABLE login VARCHAR(20);
BEGIN
    execute procedure is_admin returning_values aflag;
    select t.projectid from tasks t where t.id = new.TASKID into pid; 
    execute procedure is_manager (pid) returning_values mflag;
    execute procedure is_worker (pid) returning_values wflag;
    if (aflag = 0 and mflag = 0) then
    begin
        select j.login from journal j order by j.id desc into login;
        select e.id from employees e where e.login = :login into eid;
        if (new.employeeid <> eid or (updating and old.employeeid <> eid) ) then 
            exception ERROR_NO_RIGHTS;
    end
END^

CREATE TRIGGER TD_PROTECTION_TRIGGER for tasksDependency
 ACTIVE 
 BEFORE INSERT OR UPDATE OR DELETE 
 POSITION 0 
AS 
 DECLARE VARIABLE aflag int;
 DECLARE VARIABLE mflag int;
 DECLARE VARIABLE pid int;
BEGIN
    execute procedure is_admin returning_values aflag;
    select t.projectid from tasks t where t.id = new.masterid into pid;
    execute procedure is_manager (:pid) returning_values mflag;
    if (aflag = 0 and mflag = 0) then 
        exception ERROR_NO_RIGHTS;
END^

CREATE TRIGGER TASKS_DEPENDENCY_TRIG for TASKSDEPENDENCY 
 ACTIVE 
 BEFORE INSERT OR UPDATE
 POSITION 2
AS 
 DECLARE VARIABLE cycled int;
 DECLARE VARIABLE id1 int;
 DECLARE VARIABLE id2 int;
BEGIN
    select t.projectid from tasks t where t.id = new.slaveid into id1;
    select t.projectid from tasks t where t.id = new.masterid into id2;
    if (id1 <> id2) then exception ERROR_INCOMPATIBLE_TASKS;
    execute procedure CHECK_TASK_DEPENDENCY (new.SLAVEID, new.MASTERID) returning_values cycled; 
    if (cycled = 1) then exception ERROR_CYCLE_DEPENDENCY;
END^

CREATE TRIGGER PROJECT_EMPLOYEES_TRIG FOR PROJECTEMPLOYEES 
 ACTIVE 
 BEFORE INSERT OR UPDATE 
 POSITION 2 
AS 
declare variable tid int;
declare variable activity int;
BEGIN
    activity = 0;
    select em.COMPANYID from EMPLOYEES em where new.EMPLOYEEID = em.ID into tid; 
    select ct.ACTIVITY from CONTRACTS ct where ct.COMPANYID = :tid into activity;
    if (activity = 0) then
     exception ERROR_NO_CONTRACT;  
END^

CREATE TRIGGER JOBS_TRIG FOR JOBS 
 ACTIVE 
 BEFORE INSERT OR UPDATE 
 POSITION 2 
AS 
declare variable jid int;

BEGIN
    select js.id from JOBS js where 
        new.EMPLOYEEID = js.EMPLOYEEID and (
            (new.STARTDATE between js.STARTDATE and js.COMPLETIONDATE) or
            (new.COMPLETIONDATE between js.STARTDATE and js.COMPLETIONDATE)   
        )
        into jid;
    if (jid is not null and jid <> new.ID) then
        exception ERROR_EMPLOYEE_BUSY;
END^

CREATE TRIGGER JOURNAL_TRIG FOR JOURNAL
 ACTIVE 
 BEFORE UPDATE 
 POSITION 0 
AS 
declare variable pass VARCHAR(20);
BEGIN
    select u.passwd from USERS u where new.login = u.login into pass;
    
    if ((pass is null) or new.passwd <> pass) then
        exception ERROR_LOGIN_FAIL;
END^


SET TERM ; ^

