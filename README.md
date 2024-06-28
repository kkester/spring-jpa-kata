# Spring JPA Code Kata

The intent of this Kata is to practice working with relation database design.  This exercise entails defining various types of database constraints such as primary keys, compound primary keys, uniques constraints, and foreign key constraints.  In addition, the exercise will involve defining various types of relations between entities such as `One to One`, `One to Many`, and `Many to Many`.

The project comes with a set of Spring Boot tests and the minimal code needed for them to compile.  The goal of the exercise is to implement the code needed to get the tests to pass.  The project comes with a `compose.yaml` file that will spin up a PostGRES docker container.  This container must be running in order for the tests to pass.  When starting the kata, it is recommended to start with the `ProductTest` and then move on to the other tests once that one is green.

### Prerequisites 

This project requires the following tools to be installed.
* Java 21
* Docker

### The Domain Model

This project contains four domains: Catalog, Product, Inventory, and Category.  Catalog, Product, and Category are independent entities and can exist on their own.  Inventory requires the existence of a product for which the inventory data pertains.  Catalogs contain a collection or products and products can belong to multiple catalogs.  A category contains multiple products, but a product can only belong to one category.  Each product contains a sku which must be unique across all products and may have an optional inventory status associated with it. 

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.3.1/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.3.1/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#using.devtools)
* [Docker Compose Support](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#features.docker-compose)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

### Docker Compose support
This project contains a Docker Compose file named `compose.yaml`.
In this file, the following services have been defined:

* postgres: [`postgres:latest`](https://hub.docker.com/_/postgres)

Please review the tags of the used images and set them to the same as you're running in production.

