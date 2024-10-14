# Changelog

## 2024-10-14
### Commit  (Added other implementation for selecting configurations based on different profiles)
- Added other @Profile() annotations inside classes in 'config' package
- Added @Profile annotations to other classes in the 'config' package
### Commit 37d9d09 (Added profiles implementations)
- Added @Profile() annotations in configuration classes, as well as created new configuration files. Implemented a new AuthenticationProvider for password-based authentication when the 'prod' profile is active, and modified the existing AuthenticationProvider to allow access without a valid password for non-'prod' profiles

## 2024-10-10 
### Commit 46f11da (Added implementation of AuthenticationProvider to a single case of validate authentication)
- Added implementations of class AuthenticationProvider, allowing multiple different business logic implementations for authentication validation. 

## 2024-10-04
### Commit 1a22f77 (Added REST API functionality to register (POST) a UserAccount with password)
- Added '/user-accounts/register' endpoint that allows users to register with a password using BCrypt
### Commit b040887 (Renamed 'User' entity to 'UserAccount')
- Updated all references to 'User' field names (in database, service, controller, repository, domain)  to 'UserAccount'
### Commit 13a0457 (Implemented authentication using JPA with database integration)
- Added JPA and mysql-connector-j dependencies to the pom.xml
- Removed hard-coded passwords
- Added datasource configurations to application.properties
- Added an SQL script to generate the users table. Added 2 users as examples
- Added classes annotated with @Service, @RestController, @Repository, and domain classes for User
- Implemented authentication by validating credentials through database queries

## 2024-10-03
### Commit a101380 (Using specific password encoders (BCrypt and MD4 examples))
- Still declaring 2 passwords hard-coded, but now with password encoding using BCrypt and MD4
### Commit ed3a332 (Added passwords for 2 users with no PasswordEncoder)
- 2 passwords hard-coded in @Bean in the @Configuration file. No password encoding  used.

## 2024-10-02
### Commit 4761b85 (Configured inside application.properties the user and password (can configure just 1 user))
- Specified a single username and password in the default configuration file
### Commit 34cb2ae (Started Security. Logged password to the console. Disabled specific user configuration options)
- Default configuration of Spring Security password by random generation logged in the console (example: 3b1a2319-49b6-4d9a-a7b1-7e0dc64c430c)
