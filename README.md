## Realm Client

 * keycloak-master-client
Master realm to create other realm
   
 * keycloak-realm-client

Realm use by a service to create users, roles, applications

## S2 Realm

 * build.gradle.yml

```
dependencies {
    implementation("city.smartb.i2:realm-f2-create:${Versions.i2}")
}
```

 * RealmCreateFunction
```
    realmCreateFunction: RealmCreateFunction
```


## S2 User

### RealmCreateCommand

 * Gradle
```
dependencies {
    implementation("city.smartb.i2:user-f2-create:${Versions.i2}")
}
```

 * Inject
```
    realmCreateCommand: RealmCreateCommand
```

### UserResetPasswordFunction

* Gradle
```
dependencies {
    implementation("city.smartb.i2:user-f2-reset-password:${Versions.i2}")
}
```

* Inject
```
    realmCreateCommand: RealmCreateCommand
```