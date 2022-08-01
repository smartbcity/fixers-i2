# I2

---

# Description

I2 is a set of features built for Keycloak:

- Builds a Keycloak image embedding SmartB’s default theme.
- Adds an overlay to Keycloak base SDK.
- Initiates a Keycloak instance [I2-init](#i2-init)
- Configurates a Keycloak instance [I2-config](#i2-config)
- Allows to interact with a Keycloak instance [IM](#im)
- Contains a Spring Security Config to work properly with authentication issued by Keycloak (only for Spring boot based app)

# Getting Started

## Spring Security Config

### Base configuration

```i2-spring-boot-starter-auth``` package provides a default configuration for Spring Security.
Multi tenancy is enabled by default and allows JWT from multiple issuers.
To do so, you have to add in your ```application.yml``` a list of trusted issuers:
```
i2:
  issuers:
    -
      uri: https://localhost:8080/auth/realms/test
    -
      uri: https://localhost:8080/auth/realms/test2
```

#### Maven dependency
```kotlin
implementation("city.smartb.i2:i2-spring-boot-starter-auth:${Versions.i2}")
```

### Keycloak configuration provider
```i2-spring-boot-starter-auth-keycloak``` extends ```i2-spring-boot-starter-auth``` by adding an endpoint 
to retrieve Keycloak configuration. 
As this endpoint should be used before having a valid JWT, the endpoint does not need any pre-authentication.
Only a name has to be given, in order to retrieve the wanted Keycloak configuration.

Applications importing this package must define the following configuration:
```
i2:
  issuers:
    -
      uri: http://localhost:8080/auth/realms/test
      authUrl: http://localhost:8080/auth
      realm: test
      clientId: i2-web
      name: test
    -
      uri: http://localhost:8080/auth/realms/test2
      authUrl: http://localhost:8080/auth
      realm: test2
      clientId: i2-web
      name: test2
```

#### Maven dependency
```kotlin
implementation("city.smartb.i2:i2-spring-boot-starter-auth-keycloak:${Versions.i2}")
```

## I2-init

I2-init is an application used to initialize a realm on your Keycloak instance.

On this realm, it can create/configure:

- A client with restricted rights (this client will be most likely used by im-gateway)
- An admin user that can manage the realm
- SMTP server
- Roles used by IM

**A random password is generated and printed for both client and user admin.**

### Getting started

I2-init is designed to be run as a docker container:

```yaml
version: "3.3"

services:
  i2-init:
    image: smartbcity/i2-init:${VERSION}
    network_mode: host
    environment:
      - i2_keycloak_auth-server-url=${KEYCLOAK_URL}
      - i2_keycloak_username=${KEYCLOAK_USERNAME}
      - i2_keycloak_password=${KEYCLOAK_PASSWORD}
      - i2_init_realm_name=${INIT_REALM}
      - i2_init_admin-user_username=${INIT_ADMIN_USERNAME}
      - i2_init_admin-user_password=${INIT_ADMIN_PASSWORD}
      - i2_init_admin-user_email=${INIT_ADMIN_EMAIL}
      - i2_init_admin-user_firstname=${INIT_ADMIN_FIRSTNAME}
      - i2_init_admin-user_lastname=${INIT_ADMIN_FIRSTNAME}
      - i2.init.admin-client=${INIT_ADMIN_CLIENT-ID}
      - i2.init.admin-client-secret=${INIT_ADMIN_CLIENT-SECRET}
```
`i2_init_admin-user_password` and `i2.init.admin-client-secret` are optional. 
UUID password will be generated if absent

### Configuration

Properties used to authenticate to the Keycloak instance.

Properties prefix: `i2.keycloak`

| Property | Description | Example | Default |
| --- | --- | --- | --- |
| auth-server-url | URL to the Keycloak instance | https://auth.smart-b.io/auth | http://localhost:8080/auth |
| realm | The master realm to authenticate to (should always be “master”) | master | master |
| username | The username used to authenticate with | admin | admin |
| password | The password used to authenticate with | admin | admin |
| client-id | The client ID to authenticate to (should always be “admin-cli”) | admin-cli | admin-cli |

Properties used to set names of entities to create.

Property prefix: `i2.init`

| Property | Description | Example | Default |
| --- | --- | --- | --- |
| realm | Name of the realm | development | test |
| admin-client | Name of the client | i2-api | i2-api |

Properties used to configure the SMTP server. ([Official documentation](https://www.keycloak.org/docs/latest/server_admin/#:~:text=Configuring%20email%20for%20a%20realm,-Edit%20this%20section&text=To%20enable%20Keycloak%20to%20send,with%20your%20SMTP%20server%20settings.&text=Click%20Realm%20Settings%20in%20the,Click%20the%20Email%20tab.&text=Fill%20in%20the%20fields%20and%20toggle%20the%20switches%20as%20needed.))

Properties prefix: `i2.init.smtp`

| Property | Description | Example | Default |
| --- | --- | --- | --- |
| host | URL to SMTP server | smtp.gmail.com | me-email |
| port | Port of the SMTP server | 587 | 1025 |
| from | The address used for the From SMTP-Header for the emails sent | admin@smartb.city | noreply@smartb.city |
| ssl | Use SSL encryption. | true | false |
| starttls | Use StartTLS encryption | true | false |
| auth | Set this switch to ON if your SMTP server requires authentication. When prompted, supply the Username and Password. | true | false |

Properties used to create an admin user.

Properties prefix: `i2.init.admin-user`

| Property | Description | Example | Default |
| --- | --- | --- | --- |
| username | Username of the user | jojo | admin |
| email | Email of the user | jojolasticot@gmail.com | admin@admin.com |
| firstname | Firstname of the user | John | admin |
| lastname | Lastname of the user | Deuf | admin |

#### Base roles

I2-init creates base roles used by IM, which includes:
- super_admin
- im_read_user
- im_write_user
- im_write_organization
- im_read_organization
- im_read_role
- im_write_role

The ```super_admin``` role is composed with all the others roles.

#### User Admin

The user admin is created with the ```super_admin``` role.

## I2-config

I2-config is an application used to create entities on your Keycloak instance.

It allows you to create:

- Clients
- Users
- Roles and composite roles

### Getting started

I2-config is designed to be used as a docker container:

```yaml
version: "3.3"

services:
  i2-config:
    image: smartbcity/i2-config:${VERSION}
    network_mode: host
    environment:
      - i2_keycloak_auth-server-url=${KEYCLOAK_URL}
      - i2_keycloak_realm=${KEYCLOAK_REALM}
      - i2_keycloak_client-id=${I2_CLIENT_ID}
      - i2_keycloak_client-secret=${I2_CLIENT_SECRET}
      - i2_json=/tmp/payload.json
    volumes:
      - /path/to/your/payload/payload.json:/tmp/payload.json
```

### Configuration

Properties used to authenticate to the Keycloak instance.

Properties prefix: `i2.keycloak`

| Property | Description | Example | Default |
| --- | --- | --- | --- |
| auth-server-url | URL to the Keycloak server | https://auth.smart-b.io/auth | http://localhost:8080/auth |
| realm | The realm where data will be created | development | test |
| client-id | Client ID to authenticate | smartclient | i2-api |
| client-secret | Client Secret to authenticate | smartsecret | xxx |

Data created are stored in a json file (pay attention to your mounted volume on the docker-compose file).

```json
/**
* Fields marked with a "*" are MANDATORY.
*/

{
  "webClient"*: {
    "clientId"*: "plateform-web",
    "webUrl"*: "https://json.com",
    "localhostUrl"*: "http://localhost:3000"
  },
  "appClient"*: {
    "clientId"*: "plateform-app"
  },
  "users"*: [
    {
      "username": "user1",
      "email": "user1@email.com",
      "password": "user1user",
      "firstname": "user1",
      "lastname": "user1",
      "role": "admin"
    },
    {
      "username": "user2",
      "email": "user2@email.com",
      "password": "user2user",
      "firstname": "user2",
      "lastname": "user2",
      "role": "admin"
    }
  ],
  "roles"*: [
    "admin",
    "role1",
    "role2",
    "role3"
  ],
  "roleComposites"*: {
    "admin": ["role1"],
    "role1": ["role2", "role3"]
  }
}
```

## IM

See more [here](https://docs.smartb.city/im).