# Mobile Security project -  documentation

## Groupmember
1. Giel Van Poppel
2. René Ruts
3. Gerbe Arys
4. Dylan Van Goethem

## Project summary
We have made a workout tracker application. In the app you can add workouts, review them later on, and so on

## Requirements
### ℹ️ Legend
- :heavy_check_mark: = Implemented
- :x: = Not implemented
- :hourglass: = Work in progress

 
| Status             |Description| Details                                                                                                                                                   |
|--------------------|---|-----------------------------------------------------------------------------------------------------------------------------------------------------------|
| :hourglass:        | **Application** |                                                                                                                                                           | 
| :hourglass:        | 2 UI screens | dylan, gerbe                                                                                                                                              |
| :heavy_check_mark: | Secure API request | We are calling the open-meteo.com api to fetch the temperature and the windspeed to display it on the workout overview screen.                            |
| :hourglass:        | API request with IDOR | rene                                                                                                                                                      |
| :heavy_check_mark: | Connection to room database | the workouts are added in our ZoomiDatabase, every time you create a new workout using the "Add workout" button it gets the current weather of that time. |
| :x:                | Secure storage |                                                                                                                                                           |
|                    |  |                                                                                                                                                           | 
|        | **Security** |                                                                                                                                                           | 
| :hourglass:                | Unsafe storage | gerbe                                                                                                                                                     |
| :x:                | Malware | dylan                                                                                                                                                     |
| :x:                | Frida functionality | gerbe                                                                                                                                                     |
| :heavy_check_mark:                | Detect root and block functionality | giel                                                                                                                                                      |


## Overview app
Describe the implementation of the following topics.

### ![](ReadmeImages/Screenshot.png) Screenshots
Give screenshots for every screen in the application. Give each screen an unique name.

### ![](ReadmeImages/API.png) Secure API request
Request to server x retrieving JSON in the following format displayed in screen x.

### ![](ReadmeImages/API.png) API request with IDOR
Request to server x retrieving JSON in the following format displayed in screen x.

### ![](ReadmeImages/Database.png) Room database
Type of data stored in the database used in screen x and displayed in screen y.

### ![](ReadmeImages/Database.png) Secure storage
Type of data stored used in screen x and displayed in screen y.

### ![](ReadmeImages/Database.png) Unsecure storage
Type of data stored used in screen x and displayed in screen y.

### ![](ReadmeImages/Notifications.png) Malware
Implementation of malware.

### ![](ReadmeImages/Frida.png) Frida
Detail implementation of Frida

### ![](ReadmeImages/Root.png) Root
Implementation of the detecting root and block functionality.

## Link to Panopto video
https://

## Repositories
- Code
  - https://gitlab.ti.howest.be/ti/2025-2026/s3/mobilesecurity/students/group-01/zoomi/-/tree/cfea683722fcef257f5ed87706f8312ac0ab370c/
- APK
  - [Link to repository]