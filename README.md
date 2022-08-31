# Graphium File Sharing 

## Installation:
To run this application, you need to ensure that you have Java, Gradle and MySQL installed on your computer. 

First ensure that you have cloned the project from the git repository, found here:
https://github.com/MSH-01/Graphium.git
This can be done through the command line using
```sh
git clone https://github.com/MSH-01/Graphium.git
```

Once you have downloaded the repository, run the ‘build_db.sql’ file on your MySQL localhost instance. This can be done using MySQL Workbench or it can be done using the command line using:
```sh
$ mysql -h "localhost" -u "root" -p "comsc" "graphium" < "build_db.sql"
```


Once this is complete, you can begin to build the application. Open the directory of the main ‘Graphium’ folder in your terminal and run the command:
```sh
'gradle build' 
```
this will begin the build of the application. 

Once complete, there will be a new folder in your directory named ‘build’. Inside this folder with be another folder named ‘libs’ which will contain two files: ‘graphium-0.0.1-SNAPSHOT-plain.jar’ and ‘graphium-0.0.1-SNAPSHOT.jar’.
Open this directory in your terminal and use command:
```sh
java -jar graphium-0.0.1-SNAPSHOT.jar
```
Make sure you run the jar file with name 'graphium-0.0.1-SNAPSHOT.jar' **NOT** ‘graphium-0.0.1-SNAPSHOT-plain.jar’

This will launch the application on ‘http://127.0.0.1:8080/’. From here you will be redirected to the login page where you can create a new account or login with known credentials. 



