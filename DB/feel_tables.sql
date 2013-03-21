CONNECT 'test.fdb' user 'SYSDBA' password 'masterkey';

INSERT INTO users(login, passwd, adminFlag) VALUES ('Admin', 'Admin', 1);
INSERT INTO users(login, passwd, adminFlag) VALUES ('User', 'User', 0);

INSERT INTO companies(name, details) VALUES ('Notec', 'some description');

INSERT INTO employees(name, companyId, login) VALUES ('Anonym AA', 1, 'User');

INSERT INTO projects(name, startdate, status) VALUES ('project 1', '15.01.2013', 0);


INSERT INTO tasks(name, projectId, plannedtime, status) VALUES ('task1', 1, 20, 0);
INSERT INTO tasks(name, projectId, plannedtime, status) VALUES ('task2', 1, 20, 0);
INSERT INTO tasks(name, projectId, plannedtime, status) VALUES ('task3', 1, 20, 0);
