# restful-spring #

Presentation and Code Samples for 2014 presentation on Spring RESTful Web Services.

Presented by Frank P Moley III

Twitter: @fpmoles

Web: www.frankmoley.com

## Structure ##

### piiDataServices ###
maven war structured project for the Java Programming Language. Contains all source code for the demo project

### Presentation ###
This directory contains the PDF version of the presentation


## Requirements ##
* MongoDB
* Maven 3
* Java 1.7
* Tomcat 7


## Project Background ##
This project concept was dual serving. First I needed a real life project to use for demos for the presentation. I am a firm believer in demo software being conceptually real.
This software is also inline with my Master's Project for the University of Kansas MSIT program, where I am working on a system to help protect enterprise collection, storage,
and distribution of customer Personal Identifiable Information (PII) data.

This system will provide data access services of PII data stored in an NoSQL data store. This project for this presentation does not perform the full design of the ultimate
project for the MSIT program, as it is only being designed to provide the service framework. The data access is provided to replicate a real system for conceptual vision only.

## Branch Explanations

### 3.2.x-example
This example uses Spring MVC to generate ReSTful web services from 3.2 line of Spring-MVC.

### 4.0.x-example
This example uses Spring MVC to generate ReSTful web services from the 4.0 line of Spring-MVC. Of main note are the changes to the controller class,
changing from @Controller to @RestController and the modification of the Jackson engine to support Spring 4.0. Most of the code is the exact same, however,
which is good for the developer doing an upgrade.


