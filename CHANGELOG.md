# Changelog

## 2025-01-14
### Commit  (Add Authorities to UserAccount)
- Add authorities-based access control for endpoints requiring user login

## 2024-11-06
### Commit 9ff9679 (Add SecurityContextHolder logic)
- Add configuration and use examples of SecurityContextHolder functionalities.

## 2024-11-02
### Commit 12c2409 (Add Logout Functionality)
- Add logout functionality using the `/logout` endpoint to properly terminate the session of a previously logged-in account. Session cookies are cleared upon logout, enforcing a secure logout process.

## 2024-10-22
### Commit 44c1cb9 (Add handlers to manage login validation success and failure scenarios)
- Add CustomAuthenticationSuccessHandler and CustomAuthenticationFailureHandler, implementing the respective handlers to manage login scenarios

## 2024-10-21
### Commit 819aad1 (Add access point to login from frontend)
- Added a listener on the /backendLoginEndpoint to handle login requests from the React frontend application. See README.md for details of frontend React App available on GitHub.

## 2024-10-17
### Commit 0fee001 (Add Authentication Event Listeners)
- Add listeners for login success and failure events. Log detailed information on successful and failed login attempts, including the username and failure reason.

## 2024-10-16
### Commit f6c07aa (Add limit number of sessions per user)
- Add a limit on the number of active sessions for logged-in users

## 2024-10-15
### Commit 761bdb4 (Add Session timeout. Specify a load endpoint to redirect in this scenario)
- Add timeout configuration based on idle time limit. Redirect the user to a specific page when attempting to access the system after session expiration.
### Commit 994103f (Add global custom response for access denied errors (403 Forbidden))
- Add handling for AccessDeniedException using http.exceptionHandling()). Ensure a user-friendly response is returned when access is denied, enhancing the user experience for 403 Forbidden errors	

## 2024-10-14
### Commit 9c83c17 (Add global custom response for authentication errors (401 Unauthorized))
- Add handling for AuthenticationException using http.exceptionHandling())
### Commit c1fbb0a (Add handling for AuthenticationException)
- Add custom response for authentication errors
### Commit 37b020f (Added bean configuration to manage allowing or denying HTTP and HTTPS requests)
- Added HTTP request filter to bean configuration to the 'dev' profile. Added a commented HTTPS request filter for the 'prod' profile
### Commit bee08a2 (Added other implementation for selecting configurations based on different profiles)
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
