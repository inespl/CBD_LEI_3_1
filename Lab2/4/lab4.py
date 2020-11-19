from pymongo import *
from datetime import datetime 

client = MongoClient()
db = client["cbd"]


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


def createIndex(collection, index, name):
	try:
		collection.create_index(index,name=name)

	except Exception as e:
		print("Error:", e)


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


def  main(collection_name):
	
	collection = db[collection_name]
	print("INSERT\n")
	insert(collection, 
	{
	"address" : {
		"building" : "5555",
		"coord" : [
			-75,
			40
		],
		"rua" : "10 Street",
		"zipcode" : "11107"
	},
	"localidade" : "Costa do Valado",
	"gastronomia" : "Portuguese",
	"grades" : [
		{
			"date" : datetime(2020,10, 10, 10, 0),
			"grade" : "A",
			"score" : 3
		},
		{
			"date" : datetime(2020,10, 9, 11, 0),
			"grade" : "A",
			"score" : 12
		},
		{
			"date" : datetime(2020,10, 8, 12, 0),
			"grade" : "A",
			"score" : 8
		},
		{
			"date" : datetime(2020,10, 7, 13, 0),
			"grade" : "A",
			"score" : 7
		}
	],
	"nome" : "Comida Alentejana",
	"restaurant_id" : "5555555555"
}
)
	print("\nFIND\n")
	find(collection,{"gastronomia": "Portuguese"})

	print("\nUPDATE\n")
	update_documents(collection,{"localidade": "Costa do Valado"},{"$set": {"address.rua": "Rua do Carregueiro"}})

	print("\nFIND\n")
	find(collection,{"gastronomia": "Portuguese"})

	createIndex(collection,"localidade","localidade")
	createIndex(collection,"gastronomia","gastronomia")
	createIndex(collection,[("nome", TEXT)],"nome")
	
	countLocalidades(collection)
	countRestByLocalidade(collection)
	countRestByLocalidadeByGastronomia(collection)
	getRestWithNameCloserTo(collection, "Park")

if  __name__  ==  '__main__':
	main("rest")