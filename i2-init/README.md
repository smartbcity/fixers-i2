This app can be used to initialize a Keycloak.

It creates at least a realm and two clients.

Additional configurations can be provided to create users and roles.

A minimalist docker-compose example is given.


## Init Keycloak Domain

* Config Realm Admin
  
=> Created Realm Domain
    * name
    * CLient 
      * I2-Api <= Admin -> create user, groups, roles, client


## Config Keycloak Domain
* Config Client I2-Api 
  
* Config Realm Domain
      * Api <= Verify Auth
      * Web <= Auth
    * User
    * Roles
    * Group
    * SMTP


