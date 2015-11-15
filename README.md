URL Shortener API
=================

REST API for URL shortening

##Description
<p></p> 

URL Shortener is a REST service used for representing long URL-s with a possibly small set of characters.

###URL shortening

URL shortening is a procedure based on assigning a unique number, a _hash_, to each URL that is to be shortened. This _hash_ is essentially a `key` that is used to retrieve
 a long URL assigned to it. For this to be possible, each hash is encoded using a set URL permissible characters. This encoded hash can be prefixed with server address or 
application domain and used as a _short_ URL itself. So each time a short URL is referenced, it is used as a key to find and redirect to a long URL mapped to it.

This service uses a number of registered long URL-s as a hash. A hash is encoded using a set of case sensitive alphanumeric characters and digits, a total of 62 characters. 
Encoding procedure is basically a decimal number to base-62 conversion, where base-62 uses sixty two distinct symbols from these range of characters: `a-z`, `A-Z`, `0-9`.

####Example

Long URL to shorten:

* https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X#authorization-authorizationscope

Hash:

* `68719476736` (two to the power of thirty six)

Base-62 conversion:

* `68719476736` is represented as `bnaN5hc` (with seven characters) in base-62

The result short URL:

* http://my-short-domain.com/bnaN5hc

###URL shortening REST service

The shortening API offers:
 
* user registration into the service by opening an account
* long URL registration and shortening 
* registered short URL usage statistic retrieval

where URL registration and usage statistic is available only to authorized users with an account. Basic authentication is used and user's account ID and password need 
to be provided in the `Authorization header`.
Each registered URL can be referenced with a given short URL by anyone. 

###Installation & setup
<p></p>

This application is built upon:

1. [Java Development Kit (JDK) 8][1]  
2. [Apache Maven][2] build automation tool 
3. [Spring][3] framework and
4. [H2][4] in-memory database

Application is packaged as a web application archive (`war` file) and it can be deployed on any web or application server. 

However, [Jetty Maven plugin][5] included in project's `pom.xml` file can be used for running [Jetty][6] web container in Maven life-cycle. 
To run this service using Jetty Maven plugin, Apache Maven and JDK 8 need to be downloaded and installed.

###Usage
<p></p>

To run Jetty web container using Jetty Maven plugin in terminal, from the project's root folder, use:   
```mvn package jetty:run-war```     
or to start the service in [scattered][7] mode (for testing/development purposes):   
```mvn jetty:run```     
This will start one instance of Jetty web container and the shortening service will be available at the URL: `http://localhost:8080/`

To change the port, Jetty Maven plugin can be invoked using the `jetty.port` option, for example: `-Djetty.port=8090` to change the port to `8090`.     
This can be useful if standard `8080` port is already taken or if you want to run multiple Jetty instances in parallel.

To stop the Jetty web container, use: `stop.key` and `stop.port` configured in the project's `pom.xml` file:    
```mvn -Dstop.key=STOP -Dstop.port=9999 jetty:stop```   

For detailed description of the entire service, and how to use it, visit `Help page` which is available at `/help` 

####Example

* Opening an account

`/account`

| TYPE          | VALUE            |
| ------------- | -----------------|
| HTTP method   | POST             |
| JSON request  | { "accountId":"Merry" } |
| JSON response | { description: "Your account is opened" success: true password: "PlT8tduA" } |

* Long URL registration and shortening

`/register`

| TYPE          | VALUE            |
| ------------- | -----------------|
| HTTP method   | POST             |
| Header        | Authorization: Basic TWVycnk6UGxUOHRkdUE= |
| JSON request  | { "url":"https://www7.pearsonvue.com/testtaker/signin/SignInPage.htm?clientCode=ORACLE" } |
| JSON response | { shortUrl: "http://localhost:8181/a" } |

* registered short URL usage statistic retrieval

`/statistic/Merry` 

| TYPE          | VALUE            |
| ------------- | -----------------|
| HTTP method   | GET             |
| Header        | Authorization: Basic TWVycnk6UGxUOHRkdUE= |
| JSON response | { https://www7.pearsonvue.com/testtaker/signin/SignInPage.htm?clientCode=ORACLE: 5 } |

[1]: http://www.oracle.com/technetwork/java/javase/downloads/       "Java download"
[2]: http://maven.apache.org/download.cgi       "Maven download"
[3]: https://spring.io/        "Spring"
[4]: http://www.h2database.com/html/main.html       "H2 database engine"
[5]: http://mvnrepository.com/artifact/org.eclipse.jetty/jetty-maven-plugin     "Jetty Maven plugin repository"
[6]: http://www.eclipse.org/jetty/     "Jetty web server"
[7]: http://www.benoitschweblin.com/2013/03/run-jetty-in-maven-life-cycle.html "Jetty plugin configuration"


