# linked-line-common-log
Atix Labs Technical Challenge

There are two options to test the service

OPTION 1

The simplest way to test is by configuring a POST request in Postman, Insomnia or similar with the below URL
https://linked-line-app.herokuapp.com/log-lines

And adding a request body as follows:

{
    "message":"Hola"
}


![imagen](https://user-images.githubusercontent.com/20845506/164093561-e8f8a477-04e1-4223-aaad-3b1c8c122e7d.png)


Image that ilustrates a POST request configured to make a call to the endpoint



OPTION 2
The second option is cloning this repository into your local machine and run it locally.

It is neede to create a folder named 'demo' in C:
C:\demo

Also is needed to add the following VM argument in order for the IDE to take into account the application-local.yml

-Dspring.profiles.active=local

![imagen](https://user-images.githubusercontent.com/20845506/164092459-5fc142da-3211-4b9b-8831-69fd5f47333d.png)

This is an example on how the local profile could be set up in Eclipse IDE

Finally there should be configured a POST request pointing to local as explained for OPTION 1

![imagen](https://user-images.githubusercontent.com/20845506/164092873-238409f2-169b-4e16-9d02-62fec51055d9.png)

Image related to POST request configured to point to local environment



How to verify the log lines are linked as requested?

For the OPTION 1, you can make a request copy the response body into a notepad file, then make another request and so on. By this way you will be able to identify that each line (block) is related to the previous one by the "previousHash" field.

For the OPTION 2, you can do the same as described in OPTION 1 and additionally check the generated file in C:\demo\challenge.csv

NOTE: An endpoint to retrieve all lines in file is in progress, but so far the mentioned options to verify the results are available
