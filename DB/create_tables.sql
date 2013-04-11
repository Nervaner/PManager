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
	CONSTRAINT fk_employees_companies FOREIGN KEY(companyId) REFERENCES companies(id) ON DELETE CASCADE ON UPDATE CASCADE
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
	id INTEGER NOT NULL, 
	companyId INTEGER NOT NULL,
	projectId INTEGER NOT NULL,
	activity INTEGER DEFAULT 0,
	CONSTRAINT pk_contracts PRIMARY KEY(id),
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
	employeeId INTEGER NOT NULL,
	taskId INTEGER NOT NULL,
	startDate TIMESTAMP,
	completionDate TIMESTAMP,
	description VARCHAR(256),
	CONSTRAINT pk_jobs PRIMARY KEY(employeeId, taskId),
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

CREATE TABLE tfmask(
	id INTEGER NOT NULL,
	name VARCHAR(20) NOT NULL,	
	CONSTRAINT pk_tfmask PRIMARY KEY(id)
);

CREATE EXCEPTION ERROR_CYCLE_DEPENDENCY 'task become cycle dependent';
CREATE EXCEPTION ERROR_NO_CONTRACT 'There is no contract on this project';





CREATE GENERATOR companies_id_gen;
SET GENERATOR companies_id_gen TO 0;

CREATE GENERATOR employees_id_gen;
SET GENERATOR employees_id_gen TO 0;

CREATE GENERATOR projects_id_gen;
SET GENERATOR projects_id_gen TO 0;

CREATE GENERATOR contracts_id_gen;
SET GENERATOR contracts_id_gen TO 0;

CREATE GENERATOR tasks_id_gen;
SET GENERATOR tasks_id_gen TO 0;


set term !! ;
CREATE TRIGGER companies_id_trig FOR companies
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
if (NEW.id is NULL or NEW.id = 0) then NEW.id = GEN_ID(companies_id_gen, 1);
END!!

CREATE TRIGGER employees_id_trig FOR employees
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
if (NEW.id is NULL or NEW.id = 0) then NEW.id = GEN_ID(employees_id_gen, 1);
END!!

CREATE TRIGGER projects_id_trig FOR projects
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
if (NEW.id is NULL or NEW.id = 0) then NEW.id = GEN_ID(projects_id_gen, 1);
END!!

CREATE TRIGGER contracts_id_trig FOR contracts
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
if (NEW.id is NULL or NEW.id = 0) then NEW.id = GEN_ID(contracts_id_gen, 1);
END!!

CREATE TRIGGER tasks_id_trig FOR tasks
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
if (NEW.id is NULL or NEW.id = 0) then NEW.id = GEN_ID(tasks_id_gen, 1);
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

SET TERM ; ^

SET TERM ^ ;

CREATE TRIGGER TASKS_DEPENDENCY_TRIG for TASKSDEPENDENCY 
 ACTIVE 
 BEFORE INSERT OR UPDATE
 POSITION 0
AS 
 DECLARE VARIABLE cycled int;
BEGIN 
    execute procedure CHECK_TASK_DEPENDENCY (new.SLAVEID, new.MASTERID) returning_values cycled; 
    if (cycled = 1) then exception ERROR_CYCLE_DEPENDENCY;
END^

SET TERM ; ^

SET TERM ^ ;

CREATE TRIGGER PROJECT_EMPLOYEES_TRIG FOR PROJECTEMPLOYEES 
 ACTIVE 
 BEFORE INSERT OR UPDATE 
 POSITION 0 
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

SET TERM ; ^


