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
| :heavy_check_mark:        | 2 UI screens | dylan, gerbe                                                                                                                                              |
| :heavy_check_mark: | Secure API request | We are calling the open-meteo.com api to fetch the temperature and the windspeed to display it on the workout overview screen.                            |
| :hourglass:        | API request with IDOR | rene                                                                                                                                                      |
| :heavy_check_mark: | Connection to room database | the workouts are added in our ZoomiDatabase, every time you create a new workout using the "Add workout" button it gets the current weather of that time. |
| :heavy_check_mark:                | Secure storage |  Giel                                                                                                                                                         |
|                    |  |                                                                                                                                                           | 
|        | **Security** |                                                                                                                                                           | 
| :heavy_check_mark:                | Unsafe storage | gerbe                                                                                                                                                     |
| :x:                | Malware | dylan                                                                                                                                                     |
| :heavy_check_mark:                | Frida functionality | gerbe                                                                                                                                                     |
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
We're going to bypass the isRoot() function to bypass the Root detection

**1. Beforehand**

a. We have to have a rooted device.
  > Root the created AVD and check with the app `rootchecker` (apk available on Leho) if root was successful.
  > 
  > - `git clone https://gitlab.com/newbit/rootAVD`
  > - `cd rootAVD`
  > - `rootAVD.bat ListAllAVDs`
  > - run the one which ends with `ramdisk.img`
  >   - `rootAVD.bat system-images\android-36\google_apis\x86_64\ramdisk.img`
  > - reopen emulator, run rootcheck, select "Forever", it's done.
  > 
  > - `adb install <path_to_app_apk>` => to install apps 

b. We have to install frida server on both the emulator & our host.
  > Download frida-server from this site:
  > 
  > https://github.com/frida/frida/releases
  > 
  > For this case install version `17.5.1` zip file
  >
  > Push the unzipped file to /sdcard/frida and rename it to frida-server
  >
  > then copy it to /data/local/tmp
  > 
  > from the host run `adb shell '/data/local/tmp/frida-server &'`
  > 
  >  to run it on te background on the device.

c. We now have to install frida to our host.
  > In your cmd create a venv environment to install the right python modules

  > then activate the venv environment and run the folowing commands: `pip3 install frida-tools`
  
  > when that is installed you can test if it works with the command: `frida-ps -U` 
  if this gives output frida is installed on your host and you can move on to the next step

  **2. We have to look for interesting functions on the apk.**

  > I've done this for you and I found the isRoot() function inside of the loginScreen.kt file.
  > 
  > Let's first watch what the function does using the following command
  >
  > run `frida-trace -U -j '*!isRoot' -N com.group1.zoomi` from your host
  >
  > It should show the line 'Started tracing x functions.'
  > 
  > When trying to log in it shows that the isRoot function returns 'true' which causes us to not being able to login.

**3. We have to write a script that bypasses the isRoot() function.**
  > I've done this for you:
```javascript
Java.perform(function () {
  var LoginScreenKt = Java.use("com.group1.zoomi.ui.login.LoginScreenKt");

  LoginScreenKt.isRoot.implementation = function (context) {
    console.log("[*] isRoot() got called!");
    return false;
  };
});
```
  > Save this code to a file called `isRoot.js`

**4. Let's bypass the root detection of our app.**
  > run `frida -U -f com.group1.zoomi -l <path_to_isRoot.js_file>`
  >
  > try to login using the credentials and you'll notice the root check is bypassed using frida.

Small demo video:
[Click here to view](ReadmeImages/root-check-bypass-demo.mp4)

### ![](ReadmeImages/Root.png) Root
Implementation of the detecting root and block functionality.

** 1. First we have to import the dependency Rootbeer.**
  > This dependency is added via this import: `import com.scottyab.rootbeer.RootBeer`
  > This dependency does a lot of checks to see if the device is rooted or not, like:
  - Checks if there are apps present that manage root
  - Checks for apps that could hide root
  - Checks for dangerous properties(ro.debuggable and ro.secure) that indicate if it is a genuine android device or not.
  - ...

** 2. Then we create a function that checks if root is present and returns true or false depending on the outcome.**
  > Function:
  ```kotlin
  private fun isRoot(context: Context): Boolean {
    val rootBeer = RootBeer(context)
    return rootBeer.isRooted
}
  ```

** 3. Then add it so the function executes if the login button is clicked.**
  > When root is detected it displays the error "cant't use rooted device" and it does not let you log in.
  > When there is no root detected it checks if the credentials that are entered are correct or not.
  > When both are correct it let's you log in.
  > Code:
  ```kotlin
  Button(
            onClick = {
                if (isRoot(context)) {
                    errorMessage = "Can't use rooted device!"
                } else {
                    if (validateCredentials(username, password)) {
                        onLoginSuccess()
                    } else {
                        errorMessage = "Invalid credentials"
                    }
                }
            }
  ```

## Link to Panopto video
https://

## Repositories
- Code
  - https://gitlab.ti.howest.be/ti/2025-2026/s3/mobilesecurity/students/group-01/zoomi/
- APK
  - [Link to repository]