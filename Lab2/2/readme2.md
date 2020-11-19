## Ex 2
$ mongoimport --db cbd --collection rest --drop --file restaurants.json
-> cria a coleção rest e poe lá os documentos do json
$ mongo
> use cbd
> db.rest.count()
3772

**util**
https://stackoverflow.com/questions/25589113/how-to-select-a-single-field-for-all-documents-in-a-mongodb-collection

find()
count()
$ne -> not equal


