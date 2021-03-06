# Pulp Friction
This repository contains an application developed to empower discoverability between part-time job workers and freelancers/ business owners as part of a [student entrepreneurship contest.](https://jagreece.org/programs/virtual_business/)

# Data management
Data are being stored using [google Firebase](https://firebase.google.com/) as a database and its available APIs for password security and account management.

# Structural model
The structural model used is the following: MainActivity class is the first activity being called on launch and is responsible for auto-login and registration flows.

Res package contains UI images and XML layouts used by the app.

Objects package contains custom objects that help manage information stored locally and on the database. We can use the objects created from these classes to get the wanted information.

Fragments package contains all fragments used by the app.

CustomListViewAdapters package contains custom list views used by fragments to display information about users and job publications as a list.
