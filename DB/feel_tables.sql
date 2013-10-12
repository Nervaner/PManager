CONNECT 'test.fdb' user 'SYSDBA' password 'masterkey';

INSERT INTO users(login, passwd, adminFlag) VALUES ('Admin', 'Admin', 1);
INSERT INTO users(login, passwd, adminFlag) VALUES ('User', 'User', 0);
INSERT INTO users(login, passwd, adminFlag) VALUES ('SomeUser', '1', 0);
INSERT INTO users(login, passwd, adminFlag) VALUES ('Chief', '1', 0);


INSERT INTO companies(name, details) VALUES ('Notec', 'some description');
INSERT INTO companies(name, details) VALUES ('YAC', 'yet another company');

INSERT INTO employees(name, companyId, login) VALUES ('Anonym AA', 1, 'User');
INSERT INTO employees(name, companyId, login) VALUES ('Petrov PP', 1, 'SomeUser');
INSERT INTO employees(name, companyId, login) VALUES ('Boss BB', 1, 'Chief');


INSERT INTO projects(name, startdate, status) VALUES ('project 1', '15.01.2013', 0);


INSERT INTO tasks(name, projectId, plannedtime, status) VALUES ('task1', 1, 20, 0);
INSERT INTO tasks(name, projectId, plannedtime, status) VALUES ('task2', 1, 20, 0);
INSERT INTO tasks(name, projectId, plannedtime, status) VALUES ('task3', 1, 25, 0);
INSERT INTO tasks(name, projectId, plannedtime, status) VALUES ('task4', 1, 8, 0);
INSERT INTO tasks(name, projectId, plannedtime, status) VALUES ('task5', 1, 5, 0);

INSERT INTO jobs (employeeId, taskId, startDate, completionDate, description) VALUES (1, 2, '16.01.2013, 12:41', '16.01.2013, 14:41', 'blabla')

INSERT INTO tasksdependency(slaveId, masterId) VALUES (4, 1);
INSERT INTO tasksdependency(slaveId, masterId) VALUES (5, 4);



INSERT INTO tfmask(id, name) VALUES (0, 'false');
INSERT INTO tfmask(id, name) VALUES (1, 'true');