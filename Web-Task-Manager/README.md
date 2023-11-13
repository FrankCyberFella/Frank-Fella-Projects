# Task Manager / Todo List Project

### Description

This project demonstrates a full-stack application using Java, Spring Boot 2 MVC Spring JDBC, PostgreSQL, REST API calls using Axios, Vue.js framework, HTML, CSS, Javascript et al.

It is written to be used in a training presentation to review/introduce these technologies to new entry-level developer students.

It is intended to be reviewed interactively by an instructor with comments being added to the code and refactoring as it is being discussed/reviewed to document and reinforce what is covered.

Database access expects tables used to be stored in a database called: ***taskmanger***

Repo contents:
- **database** folder - SQL to create relational PostgrSQL table used and populate with initial data.
- **taskmanager-server** - Spring Boot 2 MVC REST API server
  - **controller** - MVC API controller
  - **datasource/model** - Components related to application data used by server
    - **exception** - Custom Exceptions
    - **taskmanager** - JDBC Components for accessing data source
  - **generalpurposeutilities** - Support code
      - **LogHttpRequest** - Components to log information about HTTP request received 
- **taskmanager-vue-front-end** - Vue.js client front-end
  - **public** - Components available through browser
  - **src** - Vue application code
    - **assets** - images displayed in Vue components
    - **components** - Vue web page(s)
    - **services** - services containing API calls to server
    - **store** - Vuex data store
- **Task Manager Demo.mp4** - Demo of project
  