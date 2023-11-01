--
-- This SQL will refresh the apireview database
--
-- Verify you are in correct database connection and database when you run this
--
-- Be sure the apireview database exists before you run this
-- if it doesn't: 
--    1. Open a Windows command prompt (NOT GitBash!)
--    2. Issue the command: createdb -U postgres apireview
--       Note: You will be prompted for a password which will not display when you type
--             The password should be: postgres1  
--
-- If you are using pgAdmin, you may also create the apireview database from there

Begin transaction;  -- mark the start of the logical unit of work 
                    -- so it can be saved or undone as a unit (see end of this SQL)
--
-- Remove any existing objects from the database that this SQL creates
--
drop Table if exists employee         cascade;
drop Table if exists department       cascade;
drop Table if exists project          cascade;
drop Table if exists project_employee cascade;

drop sequence if exists seq_employee_id;
drop sequence if exists seq_department_id;
drop sequence if exists seq_project_id;

--
-- Define objects required for the employee table
--
CREATE SEQUENCE seq_employee_id;

CREATE TABLE employee (
	employee_id integer NOT NULL DEFAULT nextval('seq_employee_id'),
	department_id integer,
	first_name varchar(20) NOT NULL,
	last_name varchar(30) NOT NULL,
	birth_date date NOT NULL,
	gender char(1) NOT NULL,
	hire_date date NOT NULL,
	CONSTRAINT pk_employee_employee_id PRIMARY KEY (employee_id),
	CONSTRAINT ck_gender CHECK (gender IN ('M', 'F'))
);
--
-- Define objects required for the department table
--
CREATE SEQUENCE seq_department_id;

CREATE TABLE department (
	department_id integer NOT NULL DEFAULT nextval('seq_department_id'),
	name varchar(40) UNIQUE NOT NULL,
	CONSTRAINT pk_department_department_id PRIMARY KEY (department_id)
);
--
-- Define objects required for the project table
--
CREATE SEQUENCE seq_project_id;

CREATE TABLE project (
	project_id integer NOT NULL DEFAULT nextval('seq_project_id'),
	name varchar(40) UNIQUE NOT NULL,
	from_date date,
	to_date date,
	CONSTRAINT pk_project_project_id PRIMARY KEY (project_id)
);
--
-- Define objects required for the project_employee table
--
CREATE TABLE project_employee (
	project_id integer NOT NULL,
	employee_id integer NOT NULL,
	CONSTRAINT pk_project_employee_project_project_id_employee_id PRIMARY KEY (project_id, employee_id)
);
--
-- populate all the tables
--
INSERT INTO department (name) VALUES ('Department of Redundancy Department');
INSERT INTO department (name) VALUES ('Network Administration');
INSERT INTO department (name) VALUES ('Research and Development');
INSERT INTO department (name) VALUES ('Store Support');

INSERT INTO project (name, from_date, to_date) VALUES ('Project X', '1961-03-01', '2002-08-31');
INSERT INTO project (name) VALUES ('Forelorn Cupcake');
INSERT INTO project (name, from_date) VALUES ('The Never-ending Project', '2010-09-01');
INSERT INTO project (name, to_date) VALUES ('Absolutely Done By', '2020-06-30');
INSERT INTO project (name, from_date, to_date) VALUES ('Royal Shakespeare', '2015-10-15', '2016-03-14');
INSERT INTO project (name, from_date, to_date) VALUES ('Plan 9', '2014-10-01', '2020-12-31');

INSERT INTO employee (first_name, last_name, birth_date, gender, hire_date)
VALUES ('Fredrick', 'Keppard', '1953-07-15', 'M', '2001-04-01');
INSERT INTO employee (department_id, first_name, last_name, birth_date, gender, hire_date)
VALUES (1, 'Flo', 'Henderson', '1990-12-28', 'F', '2011-08-01');
INSERT INTO employee (department_id, first_name, last_name, birth_date, gender, hire_date)
VALUES (2, 'Franklin', 'Trumbauer', '1980-07-14', 'M', '1998-09-01');
INSERT INTO employee (department_id, first_name, last_name, birth_date, gender, hire_date)
VALUES (2, 'Delora', 'Coty', '1976-07-04', 'F', '2009-03-01');
INSERT INTO employee (department_id, first_name, last_name, birth_date, gender, hire_date)
VALUES (2, 'Sid', 'Goodman', '1972-06-04', 'F', '1998-09-01');
INSERT INTO employee (department_id, first_name, last_name, birth_date, gender, hire_date)
VALUES (3, 'Mary Lou', 'Wolinski', '1983-04-08', 'F', '2012-04-01');
INSERT INTO employee (department_id, first_name, last_name, birth_date, gender, hire_date)
VALUES (3, 'Jammie', 'Mohl', '1987-11-11', 'M', '2013-02-16');
INSERT INTO employee (department_id, first_name, last_name, birth_date, gender, hire_date)
VALUES (3, 'Neville', 'Zellers', '1983-04-04', 'M', '2013-07-01');
INSERT INTO employee (department_id, first_name, last_name, birth_date, gender, hire_date)
VALUES (3, 'Meg', 'Buskirk', '1987-05-13', 'F', '2007-11-01');
INSERT INTO employee (department_id, first_name, last_name, birth_date, gender, hire_date)
VALUES (3, 'Matthew', 'Duford', '1968-04-05', 'M', '2016-02-16');
INSERT INTO employee (department_id, first_name, last_name, birth_date, gender, hire_date)
VALUES (4, 'Jarred', 'Lukach', '1981-09-25', 'M', '2003-05-01');
INSERT INTO employee (department_id, first_name, last_name, birth_date, gender, hire_date)
VALUES (4, 'Gabreila', 'Christie', '1980-03-17', 'F', '1999-08-01');

INSERT INTO project_employee (project_id, employee_id) VALUES (1, 3);
INSERT INTO project_employee (project_id, employee_id) VALUES (1, 5);
INSERT INTO project_employee (project_id, employee_id) VALUES (3, 1);
INSERT INTO project_employee (project_id, employee_id) VALUES (3, 5);
INSERT INTO project_employee (project_id, employee_id) VALUES (3, 7);
INSERT INTO project_employee (project_id, employee_id) VALUES (4, 2);
INSERT INTO project_employee (project_id, employee_id) VALUES (4, 6);
INSERT INTO project_employee (project_id, employee_id) VALUES (5, 8);
INSERT INTO project_employee (project_id, employee_id) VALUES (5, 9);
INSERT INTO project_employee (project_id, employee_id) VALUES (6, 5);
INSERT INTO project_employee (project_id, employee_id) VALUES (6, 10);
INSERT INTO project_employee (project_id, employee_id) VALUES (6, 11);
--
-- Add the Foreign Key dependencies 
-- Done after loading tables so order of loading tables doesn't matter 
--
ALTER TABLE employee ADD FOREIGN KEY (department_id) REFERENCES department(department_id);
ALTER TABLE project_employee ADD FOREIGN KEY (project_id) REFERENCES project(project_id);
ALTER TABLE project_employee ADD FOREIGN KEY (employee_id) REFERENCES employee(employee_id);
--
-- Commit (save) or Rollback (undo) the work done in the transaction
--
Commit;  -- Save the changes and end the unit of work/transaction