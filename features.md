# Keycloak features

## Requirements
Users:
- `Keyclaok Admin`: in the default realm `Master` --> credentials used to connect to the GUI
- `Realm Admin`: client created within the custom realm
- `Realm User`: user created within the custom realm
  
## Realm

### Create
*User: `Keyclaok Admin`*

```
i2-s2/realm/realm-f2-create
```

### Get all

```
TODO FXR-65 - As a Keyclaok Admin, I want to get Page of realm 
```

## Client
### Create first client of a realm
*User: `keyclaok Admin`*
*User: `Realm Admin`*

```
i2-s2/client/client-f2-create
```

### Update
*User: `Realm Admin`*

```
TODO What is updated?  FXR-66 - [Client] As Realm Admin, I want to update a client
```


### Generate new secret
*User: `Realm Admin`*
```
TODO https://smartbcity.atlassian.net/browse/FXR-84
i2-s2/client/client-f2-generate-secret
```

### Get all realm client
*User: `Realm Admin`*
```
TODO FXR-67 - As a realm admin, I want to get page of client ha
```

### Get all realm clients by id
*User: `Realm Admin`*
```
TODO FXR-68 - As a realm admin, I want to get client by id
```

## Role
Client roles

### Create 
*User: `Realm Admin`*

```
i2-s2/client/client-f2-create
```

### Add role
*User: `Realm Admin`*

```
TODO FXR-69 - As Realm Admin, I want to add role
What is the differents betweem create and add?
```

### Add Composites
*User: `Realm Admin`*

```
TODO FXR-70 - As a Keyclaok Admin, I want to add Composites
```

### Get all role

```TODO
FXR-71 - As a Keyclaok Admin, I want to get a page of roles
```
### Get role by ?

```TODO
FXR-72 - As a Realm Admin, I want to get a role by id/key 
```

## Realm roles
```
TODO What is Realm ROLES
```

### Create 
*User: `plateform-api`*

```
TODO FXR-73 - As a Realm Admin, I want to create Realm role 
```

### Add role 
*User: `plateform-api`*

```
TODO FXR-74 - As a Realm Admin, I want to create add (asign) role to Realm role 
```

### Add Composites
*User: `plateform-api`*

```
TODO FXR-75 - As a Realm Admin, I want to create add (asign) composite to Realm role
```


## User
### Create
*User: `plateform-api`*

```
i2-s2/user/user-f2-create
```

### Update
*User: `plateform-api`*

```
TODO FXR-77 - As a Realm Admin, I want to update a user 
```


### Disable
*User: `plateform-api`*

```
TODO FXR-78 - As a Realm Admin, I want to disable a user 
```

### Delete
*User: `plateform-api`*

```
FXR-79 - As a Realm Admin, I want to delete a user
```


### Grant role
*User: `plateform-api`*

```
i2-s2/user/user-f2-roles-grant
```


### Revoke role
*User: `plateform-api`*

```
i2-s2/user/user-f2-roles-revoke
```

### Execute actions email
*User: `plateform-api`*

```
TODO FXR-80 - [User] As a Realm Admin, I want to execute action email on a user 
```


### Reset password
*User: `plateform-api`*

```
i2-s2/user/user-f2-roles-revoke
```

### Get list of user
*User: `plateform-api`*

```
TODO FXR-81 - [User] As a Realm Admin, I want to get a page of users h
```

### Get user by id
*User: `plateform-api`*

```
TODO FXR-82 - [User] As a Realm Admin, I want to get a user by id or email 
```

### GET user by email
*User: `plateform-api`*

```
TODO FXR-82 - [User] As a Realm Admin, I want to get a user by id or email 
```


## Init

### As an keycloak admin I want init:
  * A realm
  * A client for reaml
  * Role with Permission
  * User

```
TODO FXR-83 - [Iint] As a Keyclaok Admin, I want to init a realm , client, role, user 
```