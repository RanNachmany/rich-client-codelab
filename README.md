Rich client codelab
================

Romain Guy demoed at Google I/O 2014 a "material witness" app - a sample application for the new Material Design APIs introduced in the Android L SDK.
In this code lab we will take a subset of the original app and backport it to ICS. We will implement 

* Custom theme colors
* Dynamic palette
* Activity transitions


How to use this code lab
===========================

The project can be opened in Android Studio 0.8.0 or later. It contains a single module
called **app** in the `app/` folder.

Branches: 
* Stage_0 - initial branch containing Master-Detail app. 
* Stage_1 - Adding color tint to images. 
* Stage_2 - Using rounded corner images.
* Stage_3 - Implementing shared element activity transition. 
* Stage_4 - Using Pallate library to dynamically extract colors from content. 


Source code license
===================

This project is subject to the [Apache License, Version 2.0](http://apache.org/licenses/LICENSE-2.0.html).

Library licenses
================
__android-support-v7-palette__ is subject to the [Apache License, Version 2.0](http://apache.org/licenses/LICENSE-2.0.html).
