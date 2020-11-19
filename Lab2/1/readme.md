**Inês Leite - 92928**
*CBD, LEI 3º ano*   
---

# MongoDB

## Ex 1

**Installation**
https://docs.mongodb.com/manual/tutorial/install-mongodb-on-ubuntu/
Caso haja erro: https://askubuntu.com/questions/823288/mongodb-loads-but-breaks-returning-status-14
Comandos importantes:
```
sudo systemctl start mongod
sudo systemctl daemon-reload
sudo systemctl status mongod
sudo systemctl stop mongod
sudo systemctl restart mongod
mongo
```

### Tutorial - Tutorials Point
https://www.tutorialspoint.com/mongodb/

**MongoDB:** MongoDB is a cross-platform, document oriented database that provides, high performance, high availability, and easy scalability. MongoDB works on concept of collection and document.

**Database:** physical container for collections. Each database gets its own set of files on the file system. A single MongoDB server typically has multiple databases.
**Collection:** group of MongoDB documents. It is the equivalent of an RDBMS table. A collection exists within a single database. Collections do not enforce a schema. Documents within a collection can have different fields. Typically, all documents in a collection are of similar or related purpose.
**Document:** set of key-value pairs. Documents have dynamic schema. Dynamic schema means that documents in the same collection do not need to have the same set of fields or structure, and common fields in a collection's documents may hold different types of data.

`RDBMS 	     MongoDB`
  Database 	- 	Database
  Table 	- 	Collection
  Tuple/Row 	- 	Document
  column 	-	Field
  Table Join 	-	Embedded Documents
  Primary Key 	-	Primary Key (Default key _id provided by MongoDB itself)

`Database Server and Client`
mysqld/Oracle 	- 	mongod
mysql/sqlplus 	-	mongo

**Nota:** id is a 12 bytes hexadecimal number which assures the uniqueness of every document. You can provide _id while inserting the document. If you don’t provide then MongoDB provides a unique id for every document. _id: ObjectId(4 bytes timestamp, 3 bytes machine id, 2 bytes process id, 3 bytes incrementer)
---
`Vantagens de MongoDB vs RDBMS`
https://www.tutorialspoint.com/mongodb/mongodb_advantages.htm

No mongo pode-se escrever `>db.help()` e aparece uma lista de comandos
`db.stats()` dá-nos informações sobre a bd


`Embedded Data Model vs Normalized Data Model`
**Embedded DM:** temos todos os dados relacionados num único documento.
**Normalized DM:** referimos os sub documentos do documento original usando referências
**Exemplo:** https://www.tutorialspoint.com/mongodb/mongodb_data_modeling.htm

**Considerations while designing Schema in MongoDB**
- Design your schema according to user requirements.
- Combine objects into one document if you will use them together. Otherwise separate them (but make sure there should not be need of joins).
- Duplicate the data (but limited) because disk space is cheap as compare to compute time.
- Do joins while write, not on read.
- Optimize your schema for most frequent use cases.
- Do complex aggregation in the schema.
**Ver Exemplo:** https://www.tutorialspoint.com/mongodb/mongodb_data_modeling.htm - mostra uma diferença entre schemas de rdbms e mongoDB
---
**Criar DBs**
**Nota:** In MongoDB default database is test. If you didn't create any database, then collections will be stored in test database.
```
use DATABASE_NAME
```
Este comando cria e muda para essa db

Para verificar em que db estamos
```
db
```
Para verificar a minha database list
```
> show dbs
admin   0.000GB
config  0.000GB
local   0.000GB
```
Meti uma db chamada de 'mydb', ela nao aparece na lista, para aparecer devemos inserir lá pelo menos um documento
```
> db.movie.insertOne({"name":"ines"})
WriteResult({ "nInserted" : 1 })
> show dbs
admin   0.000GB
config  0.000GB
local   0.000GB
mybd    0.000GB
```
Neste caso, na db **mydb** é inserida uma coleção 'movie' que tem um atributo "name" com o valor "ines" -- VER ISTO

- db.collection.insert() - Creates a new document in a collection.
- db.collection.insertOne() - Inserts a new document in a collection.
- db.collection.insertMany() - Inserts several new document in a collection.
**Eliminar DBs**
```
> db.dropDatabase()
{ "dropped" : "mybd", "ok" : 1 }
```
**Criar Coleções**
Pode ser com o insert, mas o mongoDB tem mesmo um método para isto
```
db.createCollection(name, options)
```
Name -> nome da coleção (Type: String)
Options -> (Opcional) especificar opções sobre tamanho de memória e indexing (Type: Document)

Para ver as opçoes possíveis: https://www.tutorialspoint.com/mongodb/mongodb_create_collection.htm
Nota: autoIndexId já não existe nas versões mais recentes

Ex:
```
> db.createCollection("myCol")
{ "ok" : 1 }
> show collections
myCol

> db.createCollection("mycollection", { capped : true, size : 6142800, max : 10000 } )
{ "ok" : 1 }
```
**ELiminar coleções**
```
db.COLLECTION_NAME.drop()
```

```
> db.products.insert( { item: "envelopes", qty: 100, type: "clasp" } )
WriteResult({ "nInserted" : 1 })
> db.products.save( { item: "envelopes", qty: 100, type: "clasp" } )
WriteResult({ "nInserted" : 1 })
> db.products.find()
{ "_id" : ObjectId("5f980cee66623c847032de5d"), "item" : "card", "qty" : 15 }
{ "_id" : ObjectId("5f980d4e66623c847032de5e"), "item" : "envelopes", "qty" : 100, "type" : "clasp" }
{ "_id" : ObjectId("5f980e77e3c61e9261e89bc5"), "item" : "envelopes", "qty" : 100, "type" : "clasp" }
{ "_id" : ObjectId("5f980e80e3c61e9261e89bc6"), "item" : "envelopes", "qty" : 100, "type" : "clasp" }
> db.products.findOne({item: "envelopes"})
{
	"_id" : ObjectId("5f980d4e66623c847032de5e"),
	"item" : "envelopes",
	"qty" : 100,
	"type" : "clasp"
}

```

--> **[!]** insert é com ':' nao '='


##### COMANDOS MONGODB
https://docs.mongodb.com/manual/reference/method/js-collection/
##### MONGODB DATATYPES
https://www.tutorialspoint.com/mongodb/mongodb_datatype.htm


