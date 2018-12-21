Sample application for money transfer
=====================

A Java RESTful API for money transfers between 2 accounts. I tried to keep the application simple.

Technologies
===

<b>NanoHTTPD</b> – a tiny web server in Java

[ServiceKit](https://github.com/Pyknic/ServiceKit) for NanoHTTPD - a little bit modified to allow path for web-services

	    A lightweight java-library that use annotations to create web-services from ordinary java methods.

<b>Gson</b> is a Java library that can be used to convert Java Objects into their JSON representation.

<b>JUnit</b> is a unit testing framework for the Java programming language

<b>Apache HTTP Client</b>



How to build the application
===

mvn clean install

How to run
===

mvn exec:java

Application starts a NanoHTTPD server on localhost port 8888. 
An application object called RevolutData keeps the database data. That object is initialized with some sample bank, customer, account and transaction data.

To view the tests just run: mvn test

NOTE
====

You can open the project with NetBeans


TO DO
====

Improve application by using H2 in memory database - for the first version I didn't had the time 

