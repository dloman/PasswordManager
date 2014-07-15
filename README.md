Password Manager
==================

This is an Android application to manage my passwords
##Usage
This app is very simple. To use enter your password and the name of the site.
Then click the "Generate Password" button to generate your password.
The application automatically places your generated password into your copy buffer.

##Screen shot of the application:
![Picking Colors is Chill](/ScreenShots/Password.png "App Screenshot")

##What it does
Unlike a lot of password manager applications this application uses no connection to the internet
and doesn't store your password.
The application takes your secret password and concatenates it with the name of the site
and then runs thats string through MD5 hash algorithm.
The app then takes the first 10 characters of the MD5 hash and places that into your copy buffer.


