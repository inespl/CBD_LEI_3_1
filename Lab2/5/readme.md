## Ex 5

### Setup
Dataset: Profile de https://github.com/ozlerhakan/mongodb-json-files

Importá-lo para o mongo:
```
sudo systemctl start mongod
mongoimport --db cbd --collection profiles --drop --file profile.json
mongo
```

Ex de um documento:
```
{
	"_id" : ObjectId("552786262cec76ed95fd61d8"),
	"client" : "127.0.0.1",
	"keyUpdates" : 0,
	"lockStats" : {
		"timeAcquiringMicros" : {
			"r" : 0,
			"w" : 4
		},
		"timeLockedMicros" : {
			"r" : 0,
			"w" : 82
		}
	},
	"millis" : 0,
	"ns" : "school2.student_grades",
	"numYield" : 0,
	"op" : "insert",
	"ts" : ISODate("2012-11-20T20:02:43.974Z"),
	"user" : ""
}

```

### Queries - Find

#1-Find all books with op "command"
> db.profiles.find({op:"command"}, {"_id":0, "client":1, "op":1}).pretty()

#2-Find all profiles that have "student" in ns
> db.profiles.find({ns:/student/}).pretty()

#3-Find all profiles that have a numYield higher than 500
> db.profiles.find({numYield: {$gt:500}}, {"_id":0, "client":1, "numYield":1}).pretty()

#4-Find all profiles that have ns as "school2.students" and ts is ISODate("2012-11-20T20:09:34.038Z")
> db.profiles.find({ns:"school2.students", ts: ISODate("2012-11-20T20:09:34.038Z")}).pretty()

#5-Find all profiles that have timeAcquiringMicros.r between ]0,20] and timeLockedMicros.r higher than 43
> db.profiles.find ({ "lockStats.timeAcquiringMicros.r": { $gt: 0, $lte: 20 }, "lockStats.timeLockedMicros.r": { $gt: 43 } }, { _id:0, lockStats:1 } )

#6-Find all profiles that ne is different than "school2.student_grades", client is "127.0.0.1" and millis are lower than 200
> db.profiles.find({ns:{$ne:"school2.student_grades"}, client:"127.0.0.1", millis: {$lt:200}}).pretty()

---

### Queries - Aggregate
aggretate ($group-, $project-, $unwind, $match-, etc);sort-
#1-Show all ns that exist with the number of profiles 
> db.profiles.aggregate( [ { $group: { _id: "$ns", Total: { $sum: 1} } } ] )

#2-#1 with Total higher than 50
> db.profiles.aggregate( [ { $group: { _id: "$ns", Total: { $sum: 1} } }, { $match: { Total: { $gt: 50 } } } ] )

#3-#1 sorted by Total (high to low)
> db.profiles.aggregate( [ { $group: { _id: "$ns", Total: { $sum: 1} } }, { $sort:{'Total':-1} } ] )

#4-#1 but the first 2 documents only
> db.profiles.aggregate( [ { $group: { _id: "$ns", Total: { $sum: 1} } }, { $limit : 2 } ] )

#5-#1 but skip the first 5 documents
> db.profiles.aggregate( [ { $group: { _id: "$ns", Total: { $sum: 1} } }, { $skip : 5 } ] )

#6-Show all without lockStats
> db.profiles.aggregate([ { $unset: "lockStats" } ])



