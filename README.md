# Scheduler

App for managing the timetable

---

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
