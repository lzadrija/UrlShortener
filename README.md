URL Shortener API
=================

####Overview

REST API for URL shortening

####Version information
Version: 1.0.0

####Contact information
Contact: lucija.zadrija@gmail.com


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

* http://short.com/bnaN5hc

###URL shortening REST service

The shortening API offers:
 
* user registration into the service by opening an account
* long URL registration and shortening 
* registered short URL usage statistic retrieval

where URL registration and usage statistic is available only to authorized users with an account. Basic authentication is used and user's account ID and password need 
to be provided in the `Authorization header`.
Each registered URL can be referenced with a given short URL by anyone. These operations and their detailed descriptions can be found on the application's help page (`/help`)