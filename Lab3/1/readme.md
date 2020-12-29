**Inês Leite - 92928**
*CBD, LEI 3º ano*   
---

# Cassandra

## Ex 1


https://cassandra.apache.org/download/

sudo update-alternatives --config java
 -> lista as versoes existentes
  mudar para versao 8

Erro depois de executar `cqlsh`
 
Connection error: ('Unable to connect to any servers', {'127.0.0.1': error(111, "Tried connecting to [('127.0.0.1', 9042)]. Last error: Connection refused")})


Resposta 5 de https://stackoverflow.com/questions/29121904/cassandra-cqlsh-connection-refused
```
sudo service cassandra start
cqlsh
```

https://www.tutorialspoint.com/cassandra/

**What is replication factor?**
The Replication Factor (RF) is equivalent to the number of nodes where data (rows and partitions) are replicated. Data is replicated to multiple (RF=N) nodes. An RF of one means there is only one copy of a row in a cluster, and there is no way to recover the data if the node is compromised or goes down.

Estou a seguir exemplos do ppt
```
cqlsh> CREATE KEYSPACE Excelsior
   ... WITH replication = {'class': 'SimpleStrategy', 'replication_factor':3};

cqlsh> CREATE KEYSPACE Excalibur WITH replication = {'class': 'NetworkTopologyStrategy', 'DC 1':1, 'DC2': 3} AND durable_writes = false;cqlsh> 

cqlsh> use excelsior ;
cqlsh:excelsior> ALTER KEYSPACE excelsior WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 4};

cqlsh:excelsior> CREATE TABLE groups (
             ... groupname text,
             ... username text,
             ... email text,
             ... age int,
             ... PRIMARY KEY (groupname, username)
             ... )
             ... ;

cqlsh:excelsior> ALTER TABLE groups ADD nickname text;
cqlsh:excelsior> ALTER TABLE groups DROP email;

cqlsh:excelsior> INSERT INTO groups 
             ... (groupname, username, age, nickname)
             ... VALUES ('jjll', 'jll', 10, 'jl');
cqlsh:excelsior> INSERT INTO groups  (groupname, username, age, nickname) VALUES ('maria', 'ml', 23, 'mm');
cqlsh:excelsior> INSERT INTO groups  (groupname, username, age, nickname) VALUES ('joao', 'joaoL', 40, 'johnny');
cqlsh:excelsior> INSERT INTO groups  (groupname, username, age, nickname) VALUES ('luisa', 'luisaP', 24, 'lita');
cqlsh:excelsior> SELECT * FROM groups  ;

 groupname | username | age | nickname
-----------+----------+-----+----------
     luisa |   luisaP |  24 |     lita
      jjll |      jll |  10 |       jl
     maria |       ml |  23 |       mm
      joao |    joaoL |  40 |   johnny

(4 rows)

cqlsh:excelsior> SELECT groupname, age FROM groups ;

 groupname | age
-----------+-----
     luisa |  24
      jjll |  10
     maria |  23
      joao |  40

(4 rows)

cqlsh:excelsior> SELECT groupname, age FROM groups WHERE age<25 ALLOW FILTERING ;

 groupname | age
-----------+-----
     luisa |  24
      jjll |  10
     maria |  23

(3 rows)

cqlsh:excelsior> SELECT groupname, age FROM groups WHERE age<25 AND age>20 ALLOW FILTERING ;

 groupname | age
-----------+-----
     luisa |  24
     maria |  23

(2 rows)

cqlsh:excelsior> SELECT COUNT (*) AS user_count FROM groups;

 user_count
------------
          4

(1 rows)

Warnings :
Aggregation query used without partition key

cqlsh:excelsior> UPDATE groups SET nickname = 'jj' WHERE groupname = 'joao' AND username = 'joaoL';
cqlsh:excelsior> SELECT * FROM groups;

 groupname | username | age | nickname
-----------+----------+-----+----------
     luisa |   luisaP |  24 |     lita
      jjll |      jll |  10 |       jl
     maria |       ml |  23 |       mm
      joao |    joaoL |  40 |       jj

(4 rows)

cqlsh:excelsior> INSERT INTO groups  (groupname, username, age, nickname) VALUES ('la', 'laP', 24, 'la') USING TTL 172800;
cqlsh:excelsior> SELECT * FROM groups  ;

 groupname | username | age | nickname
-----------+----------+-----+----------
     luisa |   luisaP |  24 |     lita
      jjll |      jll |  10 |       jl
     maria |       ml |  23 |       mm
      joao |    joaoL |  40 |       jj
        la |      laP |  24 |       la

(5 rows)

cqlsh:excelsior> SELECT TTL(age) FROM groups WHERE groupname ='la' AND username= 'laP'  ;

 ttl(age)
----------
   172670

(1 rows)
cqlsh:excelsior> SELECT TTL(age) FROM groups WHERE groupname ='la' AND username= 'laP'  ;

 ttl(age)
----------
   172664

(1 rows)

cqlsh:excelsior> SELECT * FROM groups  ;

 groupname | username | age | nickname
-----------+----------+-----+----------
     luisa |   luisaP |  24 |     lita
     maria |       ml |  23 |       mm
      joao |    joaoL |  40 |       jj
        la |      laP |  24 |       la

(4 rows)


```



