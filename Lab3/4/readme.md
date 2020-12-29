**Inês Leite - 92928**
*CBD, LEI 3º ano*   
---

# Cassandra

## Ex 4

**Sites úteis:**
https://www.guru99.com/cassandra-collections-tutorial-set-list-map.html
https://docs.datastax.com/en/cql-oss/3.3/cql/cql_using/useInsertList.html
https://docs.datastax.com/en/cql-oss/3.3/cql/cql_using/useInsertSet.html
https://docs.datastax.com/en/cql-oss/3.3/cql/cql_using/useInsertMap.html
https://docs.datastax.com/en/cql-oss/3.3/cql/cql_using/useSecondaryIndex.html


### Keyspace
```
CREATE KEYSPACE uni WITH replication = {'class': 'SimpleStrategy', 'replication_factor':2};
use uni;
```

### Tabelas
```
CREATE TABLE students_per_course (nmec int, name text, course text, year int, classes list<text>, PRIMARY KEY (course, year, nmec)) WITH CLUSTERING ORDER BY (year ASC, nmec ASC);
CREATE TABLE students_per_nmec (nmec int, name text, course text, year int, classes list<text>, PRIMARY KEY (nmec, course)) WITH CLUSTERING ORDER BY (course ASC);
CREATE TABLE course (code int, name text, professors set<text>, PRIMARY KEY (code));
CREATE TABLE classes (name text, courses set<text>, PRIMARY KEY (name));
CREATE TABLE prof (nmec int, name text, classes map<text,text>, PRIMARY KEY (name, nmec)) WITH CLUSTERING ORDER BY (nmec ASC);
CREATE TABLE dept (id int, number int, name text, courses set<text>, PRIMARY KEY (name, number, id));
```

#### Insert
```
insert into students_per_course(nmec, name, course, year, classes) values(92928, 'ines','LEI', 3, ['CBD', 'IA', 'IES', 'SIO', 'TPW']);
insert into students_per_course(nmec, name, course, year, classes) values(103222, 'miguel','MIEM', 1, ['ACE', 'ALGA', 'CI', 'EF', 'EQF']);
insert into students_per_course(nmec, name, course, year, classes) values(98459, 'joana','LEI', 2, ['SO', 'MPEI', 'AED', 'RS', 'SM']);
insert into students_per_course(nmec, name, course, year, classes) values(103222, 'pedro','LEI', 1, ['FP', 'ITW', 'CI', 'MAS', 'ALGA']);
insert into students_per_course(nmec, name, course, year, classes) values(93456, 'gonçalo','MIEET', 3, ['CE', 'MPE', 'SC', 'DE', 'PEE']);
insert into students_per_course(nmec, name, course, year, classes) values(99008, 'beatriz','MIEET', 2, ['MN', 'ACI', 'CIII', 'CE', 'CTE']);
insert into students_per_course(nmec, name, course, year, classes) values(105666, 'rita','MIEET', 1, ['ISD', 'LEI', 'IP', 'ALGA', 'CI']);
insert into students_per_course(nmec, name, course, year, classes) values(88666, 'clara','MIEET', 4, ['EP', 'SSD', 'SI', 'ED', 'PI']);
insert into students_per_course(nmec, name, course, year, classes) values(83222, 'maria','MIEM', 5, ['PCM', 'AEP', 'ESR', 'CNC', 'C']);
insert into students_per_course(nmec, name, course, year, classes) values(94956, 'joao','MIEM', 3, ['MF', 'ME', 'TMI', 'TA', 'AI']);
insert into students_per_course(nmec, name, course, year, classes) values(80999, 'jose','MIEM', 6, ['PCM', 'AEP']);
insert into students_per_course(nmec, name, course, year, classes) values(99999, 'laura','MIEM', 2, ['MNE', 'EM', 'CIII', 'MS', 'DEM']);

insert into students_per_nmec(nmec, name, course, year, classes) values(92928, 'ines','LEI', 3, ['CBD', 'IA', 'IES', 'SIO', 'TPW']);
insert into students_per_nmec(nmec, name, course, year, classes) values(103222, 'miguel','MIEM', 1, ['ACE', 'ALGA', 'CI', 'EF', 'EQF']);
insert into students_per_nmec(nmec, name, course, year, classes) values(98459, 'joana','LEI', 2, ['SO', 'MPEI', 'AED', 'RS', 'SM']);
insert into students_per_nmec(nmec, name, course, year, classes) values(103222, 'pedro','LEI', 1, ['FP', 'ITW', 'CI', 'MAS', 'ALGA']);
insert into students_per_nmec(nmec, name, course, year, classes) values(93456, 'gonçalo','MIEET', 3, ['CE', 'MPE', 'SC', 'DE', 'PEE']);
insert into students_per_nmec(nmec, name, course, year, classes) values(99008, 'beatriz','MIEET', 2, ['MN', 'ACI', 'CIII', 'CE', 'CTE']);
insert into students_per_nmec(nmec, name, course, year, classes) values(105666, 'rita','MIEET', 1, ['ISD', 'LEI', 'IP', 'ALGA', 'CI']);
insert into students_per_nmec(nmec, name, course, year, classes) values(88666, 'clara','MIEET', 4, ['EP', 'SSD', 'SI', 'ED', 'PI']);
insert into students_per_nmec(nmec, name, course, year, classes) values(83222, 'maria','MIEM', 5, ['PCM', 'AEP', 'ESR', 'CNC', 'C']);
insert into students_per_nmec(nmec, name, course, year, classes) values(94956, 'joao','MIEM', 3, ['MF', 'ME', 'TMI', 'TA', 'AI']);
insert into students_per_nmec(nmec, name, course, year, classes) values(80999, 'jose','MIEM', 6, ['PCM', 'AEP']);
insert into students_per_nmec(nmec, name, course, year, classes) values(99999, 'laura','MIEM', 2, ['MNE', 'EM', 'CIII', 'MS', 'DEM']);

insert into classes(name, courses) values('CBD', {'LEI', 'MIECT'});
insert into classes(name, courses) values('ALGA', {'LEI', 'MIECT', 'MIEM', 'MIEET'});
insert into classes(name, courses) values('CI',  {'LEI', 'MIECT', 'MIEM', 'MIEET'});
insert into classes(name, courses) values('IA', {'LEI', 'MIECT'});
insert into classes(name, courses) values('IES', {'LEI'});
insert into classes(name, courses) values('ACE', {'MIEM'});
insert into classes(name, courses) values('IP', {'MIEET'});
insert into classes(name, courses) values('CII', {'LEI', 'MIECT', 'MIEM', 'MIEET'});
insert into classes(name, courses) values('CIII', {'MIECT', 'MIEM', 'MIEET'});
insert into classes(name, courses) values('TPW', {'LEI'});
insert into classes(name, courses) values('AI', {'MIEM'});


INSERT INTO course (code, name, professors) values(8888, 'LEI', {'M.O.', 'J.M.', 'C.D.'});
INSERT INTO course (code, name, professors) values(9999, 'MIEM', {'T.O.', 'H.M.', 'S.C.'});
INSERT INTO course (code, name, professors) values(1111, 'MIECT', {'R.S.', 'O.P.', 'M.M.', 'M.O.', 'C.D.'});
INSERT INTO course (code, name, professors) values(4444, 'MIEET', {'G.D.', 'U.E.', 'E.R.'});
INSERT INTO course (code, name, professors) values(5555, 'LRE', {'E.F.', 'I.J.', 'N.B.'});
INSERT INTO course (code, name, professors) values(2222, 'LT', {'F.F.', 'O.P.', 'Z.S.', 'E.F.', 'I.J.'});
INSERT INTO course (code, name, professors) values(7777, 'MIEF', {'S.R.', 'G.D.', 'T.O', 'H.M'});
INSERT INTO course (code, name, professors) values(6666, 'LF', {'J.O.', 'A.A.'});
INSERT INTO course (code, name, professors) values(3333, 'LCBM', {'M.F.', 'L.L.', 'L.A.'});
INSERT INTO course (code, name, professors) values(0001, 'LMOG', {'I.L.', 'S.S.', 'H.S.', 'D.H.'});
INSERT INTO course (code, name, professors) values(7878, 'MIEB', {'S.R.', 'T.O.', 'P.S.', 'G.D.'});
INSERT INTO course (code, name, professors) values(3344, 'MEI', {'R.R.', 'D.S.'});


INSERT INTO prof (nmec, name, classes) values(12456, 'M.O.', {'LEI': 'TPW', 'MIECT':'SD'});
INSERT INTO prof (nmec, name, classes) values(30446, 'C.D.', {'LEI': 'SO', 'MIECT':'SdO'});
INSERT INTO prof (nmec, name, classes) values(18975, 'J.M.', {'LEI': 'RS', 'MIEET':'SR'});
INSERT INTO prof (nmec, name, classes) values(8975, 'H.M.', {'MIEM': 'ACE', 'MIEF':'MG'});
INSERT INTO prof (nmec, name, classes) values(3456, 'S.C.', {'MIEM': 'CI'});
INSERT INTO prof (nmec, name, classes) values(8362, 'E.F.', {'LRE': 'AI', 'LT':'AI'});
INSERT INTO prof (nmec, name, classes) values(10452, 'I.J.', {'LRE': 'RI', 'LT':'RI'});
INSERT INTO prof (nmec, name, classes) values(345, 'N.B.', {'LRE': 'FI', 'LT':'FI'});
INSERT INTO prof (nmec, name, classes) values(58369, 'S.R.', {'MIEF':'SM', 'MIEB':'MS'});
INSERT INTO prof (nmec, name, classes) values(23453, 'G.D.', {'MIEF':'O', 'MIEB':'OS'});
INSERT INTO prof (nmec, name, classes) values(4325, 'T.O.', {'MIEF':'E', 'MIEB':'E'});
INSERT INTO prof (nmec, name, classes) values(242, 'T.F.', {'LF':'MD'});


INSERT INTO dept (id, number, name, courses) values(1, 4, 'DETI', {'LEI', 'MEI', 'MIEET', 'MIECT'});
INSERT INTO dept (id, number, name, courses) values(2, 8, 'DBIO', {'LB', 'LBG', 'MBA', 'MBMA'});
INSERT INTO dept (id, number, name, courses) values(3, 8, 'DBIO', {'MBMC', 'MEA'});
INSERT INTO dept (id, number, name, courses) values(4, 13, 'DFIS', {'LF', 'LMOG', 'MIEF', 'MIEC'}); 
INSERT INTO dept (id, number, name, courses) values(5, 13, 'DFIS', {'MIEB', 'MCMA', 'MF', 'MMDB', 'MTIM'});
INSERT INTO dept (id, number, name, courses) values(6, 5, 'DEP', {'LEB', 'LP', 'MIEET', 'MIECT'});
INSERT INTO dept (id, number, name, courses) values(7, 9, 'DEMAC', {'MIEM'});
INSERT INTO dept (id, number, name, courses) values(8, 28, 'DECIVIL', {'LRP', 'MIEC'});
INSERT INTO dept (id, number, name, courses) values(9, 7, 'DAO', {'MIEA', 'MSES', 'MIEET', 'MIECT'});
INSERT INTO dept (id, number, name, courses) values(10, 2, 'DLC', {'LRE', 'LLC', 'LEE', 'LT'});
INSERT INTO dept (id, number, name, courses) values(11, 11, 'DMAT', {'LM', 'MEM', 'MM'});
INSERT INTO dept (id, number, name, courses) values(12, 22, 'DEM', {'MIEM', 'MEI', 'MIEET', 'MIECT'});
INSERT INTO dept (id, number, name, courses) values(13, 15, 'DQ', {'BioQ', 'BioT', 'Q'});

SELECT * FROM students_per_course;
SELECT * FROM students_per_nmec;
SELECT * FROM classes;
SELECT * FROM course;
SELECT * FROM prof;
SELECT * FROM dept;
```

### Indíces Secundários
```
CREATE INDEX student_year ON students_per_nmec(year);
CREATE INDEX student_course ON students_per_nmec(course);
```

### Update and Delete
```
DELETE professors FROM course WHERE code=3344;
UPDATE course SET professors = {'F.O.','D.S.','M.G'} WHERE code = 3344;
UPDATE dept SET courses = courses - {'Q'} WHERE name = 'DQ' AND number=15 AND id=13;

UPDATE students_per_nmec SET classes[2] = 'ACE' WHERE nmec = 99999 AND course='MIEM';
DELETE classes[4] FROM students_per_nmec WHERE nmec = 99999 AND course='MIEM';

UPDATE prof SET classes = classes + {'MIEC':'E'} WHERE name='T.O.' AND nmec = 4325;
UPDATE prof SET classes['MIEF']='SdM' WHERE name ='S.R.' AND nmec = 58369;
DELETE classes FROM prof WHERE name='T.F.' AND nmec=242;
DELETE classes['LT'] FROM prof WHERE name='N.B.' AND nmec=345;
```

### Queries
```
SELECT * FROM students_per_course WHERE course='MIEM';
SELECT * FROM students_per_course WHERE course='MIEM' AND year=2;
SELECT * FROM students_per_course WHERE course='MIEM' LIMIT 3;
SELECT * FROM students_per_nmec WHERE nmec=99999;
SELECT * FROM classes WHERE name='CII';
SELECT * FROM dept where name='DFIS';
```

SELECT: https://docs.datastax.com/en/cql-oss/3.3/cql/cql_reference/cqlSelect.html

SELECT * | select_expression | DISTINCT partition 
FROM [keyspace_name.] table_name 
[WHERE partition_value
   [AND clustering_filters 
   [AND static_filters]]] 
[ORDER BY PK_column_name ASC|DESC] 
[LIMIT N]
[ALLOW FILTERING]

https://stackoverflow.com/questions/35708118/where-and-order-by-clauses-in-cassandra-cql

https://stackoverflow.com/questions/24949676/difference-between-partition-key-composite-key-and-clustering-key-in-cassandra


