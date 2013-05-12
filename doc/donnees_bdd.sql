use devstarter;

delete from user;
delete from enumeration;

INSERT INTO enumeration(name) Values('account');

INSERT INTO enumeration(name,type_id) 
Values('normal',(SELECT id_enumeration from enumeration e where e.name='account'));

INSERT INTO enumeration(name,type_id) 
Values('facebook',(SELECT id_enumeration from enumeration e where e.name='account'));

INSERT INTO enumeration(name,type_id) 
Values('twitter',(SELECT id_enumeration from enumeration e where e.name='account'));

INSERT INTO enumeration(name,type_id) 
Values('google',(SELECT id_enumeration from enumeration e where e.name='account'));

INSERT INTO enumeration(name,type_id) 
Values('admin',(SELECT id_enumeration from enumeration e where e.name='account'));

INSERT INTO enumeration(name) Values('job');

INSERT INTO enumeration(name,type_id) 
Values('No job',(SELECT id_enumeration from enumeration e where e.name='job'));

INSERT INTO enumeration(name,type_id) 
Values('Developper',(SELECT id_enumeration from enumeration e where e.name='job'));

INSERT INTO enumeration(name,type_id) 
Values('Graphist',(SELECT id_enumeration from enumeration e where e.name='job'));