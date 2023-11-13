Begin transaction;

drop table if exists task;

create table task
(taskId      serial   primary key,
 dueDate     date     not null,
 description text     not null,
 iscomplete  boolean  default false
);



Insert into task
(dueDate, description, iscomplete)
Values('11/14/2023','Complete Task Manager App', false)
;	   
Insert into task
(dueDate, description, iscomplete)
Values('2023-12-24','Finish Holiday Shopping', false)
;	
Insert into task
(dueDate, description, iscomplete)
Values('2023-10-28','Attend Jax and Rustom Wedding', true)
;	
Insert into task
(dueDate, description, iscomplete)
Values('2024-03-19','Plan Birthday Party', false)
;	
Insert into task
(dueDate, description, iscomplete)
Values('2024-01-15','Plan trip to Gary and Kristen Wedding', false)
;	
Insert into task
(dueDate, description, iscomplete)
Values('2024-10-02','25th Anniversary Dinner', false)
;	
Insert into task
(dueDate, description, iscomplete)
Values('2023-11-11','Plan Thanksgiving Dinner', true)
;	
commit;

