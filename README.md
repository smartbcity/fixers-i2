# I2

---

# Description

I2 is a set of features built for Keycloak:

- Builds a Keycloak image embedding SmartB’s default theme.
- Adds an overlay to Keycloak base SDK.
- Initiates a Keycloak instance [I2-init](#I2-init)
- Configurates a Keycloak instance [I2-config](#I2-config)
- Allows to interact with a Keycloak instance [I2-gateway](#I2-gateway)
- Contains a Spring Security Config to work properly with authentication issued by Keycloak (only for Spring boot based app)

# Getting Started

## <a id="I2-init"></a> I2-init

I2-init is an application used to initialize a realm on your Keycloak instance.

On this realm, it can create/configure:

- A client with restricted rights (this client will be most likely used by i2-gateway)
- An admin user that can manage the realm
- SMTP server

**A random password is generated and printed for both client and user admin.**

TODO features:

- Creates a realm role “admin” and assign to the admin user
- Adds the “memberOf” claim to the jwt
- Creates base roles (read_user, write_user ...)

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
      - i2_init_admin-user_email=${INIT_ADMIN_EMAIL}
      - i2_init_admin-user_firstname=${INIT_ADMIN_FIRSTNAME}
      - i2_init_admin-user_lastname=${INIT_ADMIN_FIRSTNAME}
```

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

## <a id="I2-config"></a> I2-config

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

## <a id="I2-gateway"></a> I2-gateway

See more [here](https://gitlab.smartb.city/framework/connect/im).