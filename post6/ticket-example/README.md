## Description

This is an example application, intended to illustrate barcoding concepts, that
simulates a ticketing system. Users can create a ticket, print a PDF of it and
redeem it.

## Requirements

- Java SDK 1.7+
- Maven 3.0+

## Installation

The example uses the [Tomcat Maven Plugin](https://tomcat.apache.org/maven-plugin-trunk/index.html)
and an embedded [SQLite](http://www.sqlite.org/) data store, so you should be
able to just execute ```mvn tomcat7:run``` from the ticket-example home
directory.

The SQLite data store is copied to the maven target directory and will persist
between application restarts; however, if you run ```mvn clean``` the target
directory will be deleted and you will lose all your precious phoney ticket
data.