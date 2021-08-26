# Scheduler

[![Scheduler CI](https://github.com/DuDiiC/scheduler/actions/workflows/build_scheduler.yml/badge.svg)](https://github.com/DuDiiC/scheduler/actions)
[![Open Issues](https://img.shields.io/github/issues/dudiic/scheduler)](https://github.com/DuDiiC/scheduler/issues)

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/dashboard?id=DuDiiC_scheduler)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=DuDiiC_scheduler&metric=alert_status)](https://sonarcloud.io/dashboard?id=DuDiiC_scheduler)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=DuDiiC_scheduler&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=DuDiiC_scheduler)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=DuDiiC_scheduler&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=DuDiiC_scheduler)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=DuDiiC_scheduler&metric=security_rating)](https://sonarcloud.io/dashboard?id=DuDiiC_scheduler)

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=DuDiiC_scheduler&metric=bugs)](https://sonarcloud.io/dashboard?id=DuDiiC_scheduler)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=DuDiiC_scheduler&metric=code_smells)](https://sonarcloud.io/dashboard?id=DuDiiC_scheduler)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=DuDiiC_scheduler&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=DuDiiC_scheduler)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=DuDiiC_scheduler&metric=coverage)](https://sonarcloud.io/dashboard?id=DuDiiC_scheduler)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=DuDiiC_scheduler&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=DuDiiC_scheduler)


App for managing the timetable

## Project Roadmap

| STATUS | DETAILS |
| :---: | :--- |
| ✅ | project concept with tech-stack and functionalities |
| ✅ | setup project core with Java 15, Spring Boot, H2 DB, OpenApi doc |
| ✅ | security - users, roles, registration, JWT authorization for endpoints with tests |
| ✅ | build custome exception handling with more details than the usual Spring provides |
| ✅ | schedules entity with simple CRUD operations and tests for them |
| ✅ | dockerazing database for project and migration dev configuration from H2 to PostgreSQL |
| ✅ | setup GitHub actions for continious integration - building and testing app |
| ✅ | setup SonarQube as a static code analysis and add it in pipeline in GitHub actions |
| ▶️ | dockerazing project application into docker image |
| ⏳ | activieties entity with simple CRUD operations and tests for them |
| ⏳ | publish application in prod enviroment, somewhere in cloud (where exactly has not been chosen yet) with continious delivery configuration |
| ⏳ | 🏆🏆🏆 version 1.0 🏆🏆🏆 |
| ⏳ | adding new functionalities related to more extensive business logic than regular CRUD operations |

## Tech-stack



### Run on your machine

The application requires Java version 15.

Currently, the app can be run in two Spring profiles for developers:

#### 1. `dev_h2`

With in-memory H2 database. The database file is created automatically when the application is started for the first time.

#### 2. `dev_postgres`

With Postgres database in Docker container. 
    
In order to run the container, in the `postgres/` catalog, run the command:

    docker-compose up

There is also access to [pgAdmin](https://www.pgadmin.org/) (Open Source administration and development platform for PostgreSQL) at http://localhost:8081/.

pgAdmin credentials:

    email: root@mail.com
    password: password123

database creadentials:

    user: dev
    password: password123
