# _Hair Salon_

#### _Web application for a hair salon, using Java and PostgreSQL to store and display user data, Sep. 23, 2016_

#### By _**Andrew Merrell**_

## Description

_This web application intakes user data about hair salon clients and stylists to dynamically display data related to the business. It utilizes Java, PostgreSQL, Spark framework, Velocity Template Engine, and Gradle Build Tool._

## Setup/Installation Requirements

#### Initial Setup
* _Visit https://github.com/amcmerrell/hair-salon to view files for this project._
* _Open your terminal application of choice and use "git clone https://github.com/amcmerrell/hair-salon" in the command line._

#### Database Setup
* _Start postgres in your terminal by entering "postgres, and in another tab, run "psql"."_
* _When psql is successfully running, use "CREATE DATABASE hair_salon;"._
* _Next, run "CREATE DATABASE hair_salon;"_
* _Once the table is created, enter "CREATE TABLE stylist (id serial PRIMARY KEY, name varchar, phoneNumber varchar, workDays varchar);"._
* _Finally, create the client table by entering "CREATE TABLE (id serial PRIMARY KEY, name varchar, phoneNumber varchar, stylistId int);"._
* _If you would like to run the tests, you can copy this database by running "CREATE DATABASE hair_salon_test WITH TEMPLATE hair_salon._

#### Running the Application
* _Navigate into the hair-salon folder you created and use "gradle run" in the command line._
* _When that command gets to 75%, open a web browser and enter "localhost:4567."_

## Specifications
_Display a lit of of all hair salon stylists._
* _Example Behavior: Click "All Stylsits"._
* _Example Output: Joey, Allen, Felicia._

_Display details for a stylist, along with their clients._
* _Example Behavior: Click on "Allen"._
* _Example Output: Display page with name, phone number and list of clients._

_Add new stylists to the database by completing a form._
* _Example Behavior: Submit form with name: Allen, phone number: 123-456-7890._
* _Example Output: Display "Allen" in list of stylists._

_Add new clients to a specific stylist._
* _Example Behavior: Submit form with client name: Pete, phone number: 123-456-7890, stylist: Pete._
* _Example Output: Display Pete on Allen's page, when "Allen" is clicked._

_Submit a form to update a stylist's info._
* _Example Behavior: Click on update button on Allen's page and submit form with new phone number (980-765-4321)._
* _Example Output: Display new phone number on Allen's page upon submitting._

_Submit a form to update a client's info._
* _Example Behavior: Click on update button on Pete's page and submit form with new phone number (980-765-4321)._
* _Example Output: Display new phone number on Pete's page upon submitting._

_Delete a stylist by clicking a button._
* _Example Behavior: Click delete button on stylist page._
* _Example Output: Remove stylist from list of all stylists._

_Delete a client by clicking a button._
* _Example Behavior: Click delete button on client page._
* _Example Output: Remove client from stylist page._

## Known Bugs
_There are currently no known bugs for this project. If you discover any issues, you can reach me via the contact details below._

## Support and contact details
_If you have any questions about this project, please contact Andrew at andrew.m.merrell@gmail.com_

## Technologies Used
_Java_
_PostgreSQL
_Spark Framework_
_Velocity Template Engine_

### License

Copyright (c) 2016 **_Andrew Merrell_**

*Licensed under the MIT license.*
