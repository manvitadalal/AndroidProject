# Red Teapot Dating : Profile Order Experiment

### Introduction
Th Product team wants to experiment with the order of information in a user's profile, to see if first impressions are
important. They are looking to answer questions like: Should the profile photo be first? What if we placed the about me text
higher? Is the school important for a College aged demographic?
The task requires displaying a single profile at a time with the profile fields in the order defined by the configuration. There
should also be a button that allows you to navigate to the next user.

### Network APIs available
There will be two API end points that your project will need to request.
http://hinge-ue1-dev-cli-android-homework.s3-website-us-east-1.amazonaws.com/
GET /users - This will return a list of users and their profile information. Each user will have an id , name , and gender .
Other fields will be optional.
```
{
"users": [
{
"id": 1,
"name": "Jim",
"gender": "m",
"about": "*Looks at Camera*",
"photo": "https://upload.wikimedia.org/wikipedia/en/7/7e/Jim-halpert.jpg"
},
{
"id": 2,
"name": "Pam",
"gender": "f",
"photo": "https://upload.wikimedia.org/wikipedia/en/6/67/Pam_Beesley.jpg",
"school": "Pratt Institute"
},
{
"id": 3,
"name": "Michael",
"photo": "https://upload.wikimedia.org/wikipedia/en/d/dc/MichaelScott.png",
"gender": "m",
"about": "Scarn, Michael Scarn. Shaken not stirred.",
"school": "School of Life"
},
{
"id": 4,
"name": "Dwight",
"gender": "m",
"photo": "https://upload.wikimedia.org/wikipedia/en/c/cd/Dwight_Schrute.jpg"
},
{
"id": 5,
"name": "Andy",
"photo": "https://upload.wikimedia.org/wikipedia/en/8/84/Andy_Bernard_photoshot.jpg",
"gender": "m",
"school": "Cornell University",
"about": "I was on American Idol",
"hobbies": [
"Singing",
"Sailing"
]
},
{
"id": 6,
"name": "Kelly",
"gender": "f",
"about": "ARE YOU READING THIS RYAN?!?"
}
]
}
```
GET /config - This details which order the profile fields should be displayed in

```
{
"profile": ["name", "photo", "gender", "about", "school", "hobbies"]
}
```

### Non-Goals
1. Match user functionality would be out of scope
2. Back button for navigating to the previous user will not be available but user can navigate back using gestures
3. Gesture support wouldn't be available for Next
4. Accessibility improvements are out of scope
5. Performing analysis on the profile configuration on the mobile device is out of scope. These would be done using Firebase Analytics.

### Design
We will use MVVM architecture and combine it with the Repository architecture to better manage the Model components. We will not be decoupling further using Interfaces to keep the solution straight forward for the current needs but that would be a better approach for scalability. We will use Fragments and

The visual design can change in the future so we will currently just match the following design. In the future it would be ideal to extract the code for Textviews and Imageviews. This improvement will be made after the final designs are shared by the UX team.
<img width="242" alt="Screen Shot 2023-02-08 at 11 47 57 AM" src="https://user-images.githubusercontent.com/29209463/217596880-91a9f585-0507-41d0-bfa1-ba41b4ab01a0.png">

A screen recording of the implementation is available at the below link.
https://drive.google.com/file/d/1OSRxNuLbfcNHmKEBtvDfQWxzXcu4y8rN/view?usp=share_link

#### System Architecture
The architecture will look like below 
<img width="678" alt="Screen Shot 2023-02-08 at 11 40 36 AM" src="https://user-images.githubusercontent.com/29209463/217594525-f4759f3e-a6ff-46b6-8221-06464cd95584.png">

The code is divided into 2 main packages:
1. repository (represents the model using the Repository architecture)
2. ui (consists of view and viewmodel)


#### Data Model
The repository package is divided into
1. api (network operations using Retrofit)
2. db (database operations to save the user list using RoomDb)
3. model (Models available for response objects to config and user endpoint as well as the User object which is saved in the database and displayed)
4. repo (Repository classes which are the entry points to the data models from the UI)


#### Business Logic
Hashmap is used to setup the visuals for each user fragment based on the config received from backend. This code can be seen in the UserFragment.
Time spent on each user fragment is calculated and then logged on Firebase analytics along with the config at the time so further analysis can be performed by the Product team.


#### Risks
Config is currently not stored in a shared preference or local database, thus there is a risk of the default config showing up instead of the one available from the server. This config is logged on Firebase which reduces the risk of inaccurary while performing analytics to take informed decisions.
