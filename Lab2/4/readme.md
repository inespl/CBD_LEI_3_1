## Lab 4

Escolher uma driver -> python
https://docs.mongodb.com/drivers/python/

Instalar a driver: `python3 -m pip install pymongo`

### a)

Documentação e exemplos úteis aqui: https://pymongo.readthedocs.io/en/stable/

```
def insert(collection, document):
	try: 
		new_id = collection.insert_one(document)
		print("Inserted:", new_id)

	except Exception as e:
		print("Error:", e)


def update_documents(collection, queryToUpdate, newValues):
    try:
        collection.update_many(queryToUpdate, newValues)
        print("Documents updated\n")

    except Exception as e:
        print("Error: ", e)


def find(collection, query):
	try:
		for x in collection.find(query):
			print(x)

	except Exception as e:
		print("Error:", e)
```

Para testar:
```
sudo systemctl start mongod
python3 lab4.py
```

### b) 
```
def createIndex(collection, index, name):
	try:
		collection.create_index(index,name=name)

	except Exception as e:
		print("Error:", e)

```

### c)
```
def countLocalidades(collection):
	try:
		locDis = len(collection.distinct("localidade"))
		print("Numero de localidades distintas:", locDis)
		return locDis
	except Exception as e:
		print("Error:", e)
		return None


def countRestByLocalidade(collection):
	dic = {}
	query = [ { "$group": { "_id": "$localidade", "Total": { "$sum": 1} } } ]
	print("\nNumero de restaurantes por localidade:")
	try:
		for x in collection.aggregate(query):
			dic[x["_id"]]=x["Total"]
			print(" ->", x["_id"], "-", dic[x["_id"]])
		return dic
	except Exception as e:
		print("Error:", e)
		return None

def countRestByLocalidadeByGastronomia(collection):
	try:
		dic = {}
		query = [ { "$group": { "_id": {"loc": "$localidade", "gas": "$gastronomia"}, "Total": { "$sum": 1} } } ]
		print("\nNumero de restaurantes por localidade e gastronomia:")
	
		for x in collection.aggregate(query):
			key = x["_id"]["loc"] + " | " + x["_id"]["gas"]
			dic[key]=x["Total"]
			print(" ->", key, "-", dic[key])
		return dic

	except Exception as e:
		print("Error:", e)
		return None

def getRestWithNameCloserTo(collection, name):
	try:
		lis = []
		query = {"nome": {"$regex": name}}
		print("\nNome de restaurantes contendo '"+name+"' no nome:")
		for x in collection.find(query):
			lis.append(x["nome"])
			print(" ->", x["nome"])
		return lis

	except Exception as e:
		print("Error:", e)
		return None
```
