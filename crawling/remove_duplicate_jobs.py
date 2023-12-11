from pymongo import MongoClient
from collections import defaultdict


# MongoDB related
MONGO_URI = "mongodb+srv://JobHunting:jobhunting@cluster0.fwskbmd.mongodb.net/JobHuntingTest"
PORT = 8005
DB = "JobHuntingTest"
COLLECTION = "jobs"


# connect to MongoDB
client = MongoClient(f"{MONGO_URI}:{PORT}")
db = client[DB]
collection = db[COLLECTION]


seen_documents = defaultdict(list)

for document in collection.find():
    unique_key = tuple((k, v) for k, v in document.items() if k != "_id" and k != "time")
    seen_documents[unique_key].append(document["_id"])


for duplicate_ids in seen_documents.values():
    if len(duplicate_ids) > 1:
        keep_id = duplicate_ids[0]
        delete_ids = duplicate_ids[1:]
        collection.delete_many({"_id": {"$in": delete_ids}})
        print("delete")
