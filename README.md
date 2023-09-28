# Learning Spring Boot

The purpose of this project is for me to learn Spring Boot.

## Description

The point of origin for this Project was that I wanted to create a REST API for a different Project I am
planning to work on. I started with Node.js which was easy enough to get into, I felt that this wasn't for me.

I found Spring Boot and thought that this was more up my alley regardless of the added complexity.

However, I quickly noticed a recurring trend while trying to "get into" Spring Boot which was that many of the Guides
and Examples that I found online were either outdated or were just not working as they were explained or depicted
in those Online Examples.

This was quite frustrating to work see because it really hinders your way of understanding and working with the whatever
you want to actually learn.

## The Goal

The goal of this Project for me is to simply learn Spring boot. In addition, I would like to share this Project
so that maybe someone else can benefit from those things as well and have an easier time to get started.

From my first experiences, the following list should cover all the goals I have or want to achieve:

* Create a REST API with [Spring Boot](https://spring.io/projects/spring-boot)
* use [JPA](https://jakarta.ee/specifications/persistence/) to manage and connect to a Database
* have different environments for "normal" operation and running tests
* use a different Database between environments 
  * use an in-memory [H2 Database](https://www.h2database.com/html/main.html) to run tests
  * use a [PostgreSQL DB](https://www.postgresql.org/) for anything else
* use a Test-Drive-Development (TDD) process to first create tests and then implement the functionality based on the test
* use the mocking framework [Mockito](https://site.mockito.org/) in combination with [JUnit5](https://junit.org/junit5/) to write those tests
* use [AssertJ](https://assertj.github.io/doc/) to handle the Assertions in the Tests
* use [Maven](https://maven.apache.org/) to handle all dependencies and as build tool