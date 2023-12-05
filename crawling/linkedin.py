import requests
import time
from bs4 import BeautifulSoup
from pymongo import MongoClient


# MongoDB related
MONGO_URI = "mongodb+srv://JobHunting:jobhunting@cluster0.fwskbmd.mongodb.net/JobHuntingTest"
PORT = 8005
DB = "JobHunting"
COLLECTION = "jobs"

headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36"
}

url = "https://www.linkedin.com/jobs-guest/jobs/api/seeMoreJobPostings/search?keywords=Software%20Engineer&location=Taiwan"

job_ids = []
job_infos = []


for pages in range(10):
    target_url = url + f"&start={pages}"
    res = requests.get(target_url, headers=headers)
    soup = BeautifulSoup(res.text, "html.parser")
    jobs_on_this_page = soup.find_all("li")
    for i in range(len(jobs_on_this_page)):
        tmp = jobs_on_this_page[i].find("div",{"class":"base-card"})
        if tmp:
            job_id = tmp.get('data-entity-urn').split(":")[3]
            job_ids.append(job_id)


for job_id in job_ids:
    target_url = f"https://www.linkedin.com/jobs-guest/jobs/api/jobPosting/{job_id}"
    res = requests.get(target_url, headers=headers)
    soup = BeautifulSoup(res.text, "html.parser")
    job_info = {}

    try:
        job_info["company"] = soup.find("div",{"class":"top-card-layout__card"}).find("a").find("img").get('alt')
    except:
        job_info["company"] = None

    try:
        job_info["job-title"] = soup.find("div",{"class":"top-card-layout__entity-info"}).find("a").text.strip()
    except:
        job_info["job-title"] = None

    try:
        job_info["level"] = soup.find("ul",{"class":"description__job-criteria-list"}).find("li").text.replace("Seniority level","").strip()
    except:
        job_info["level"] = None

    job_info["time"] = time.time()
    job_infos.append(job_info)

print(job_infos)


# connect to MongoDB
client = MongoClient(f"{MONGO_URI}:{PORT}")
db = client[DB]
collection = db[COLLECTION]
collection.insert_many(job_infos)
