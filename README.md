# Skiply : A student management and fees collection platform

### Architecture

There are two parts of this repo <br /> 
 1. api-code : which contains APIs nad models that can be used by other dependent service instead of recreating those again <br />
 2. services: which contains microservices <br />

### Services
Application has been split into 4 services that manages separate functionalities <br />
 1. student-service: this take care of student enroll, pending fees, initiating a payment <br />
 2. fee-service: a static service which contains list of applicable fees <br />
 3. payment-service: this service communicate to payment-gateway to collect payment <br />
 4. receipt-generator: this is to generate receipts as we need <br />

### Getting started
1. First we need to generated api-code using below command <br />
 ``` go to api-code folder``` <br />
 ``` Execute mvn clean install ``` <br />
 This will generate all api-code <br />
