# User Service

User service provides you the apis about user and role, and its relationship.

* [Todo](#todo)
* [Library](#library)
* [API](#api)
* [Thanks](#thanks)

# Todo

* When one role deleted, then we should delete all of user-role relations too.
* Improve concurrent solution, currently we just use `synchronized` to handle it.
* Consider the max item count problem, how about we create 2^32 items of users or roles.

# Library 

We just use Standard JDK 11 API in source code. And in test code part, we use below plugins:

* junit-jupiter-engine
* mockito-junit-jupiter
* mockito-core

And in build phase, besides the standard maven plugins, we use below additional plugins to help:

* maven-surefire-plugin
* jacoco-maven-plugin
* maven-checkstyle-plugin


# API

## create user

```
POST /users 
Content-Type: application/json

{
    "username": "lzwjava",
    "password": "123"
}

>

{
    "id": 1
}
```

## login 

```
POST /login
Content-Type: application/json
{
    "username": "lzwjava",
    "password": "123"
}

> 

{
    "token": "U1DV5DhkI4Jj7KC30Z/2853QIXL9g4eHb4TckLCGydQ=",
    "tokenExpired": 7200
}
```

## logout

```
POST /logout HTTP/1.1
Host: localhost:8080
Authorization: Bearer 8r+t5TQlbq7yI+0l81zso01eK46dK2KzFjG7USfH2Fg=
Content-Type: application/json
Content-Length: 2

{}

>

{
    "status": 200,
    "error": "",
    "message": "OK"
}
```

## create role 

```
POST /roles HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 23

{
    "name": "admin"
}

>

{
    "id": 1
}
```

## delete role 

```
DELETE /roles/1 HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 2

{}

>

{
    "status": 200,
    "error": "",
    "message": "OK"
}
```

## delete user

```
DELETE /users/1 HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 53

{
    "username": "lzwjava1",
    "password": "123"
}

>

{
    "status": 200,
    "error": "",
    "message": "OK"
}
```

## check user role 

```
GET /users/roles?roleId=1 HTTP/1.1
Host: localhost:8080
Authorization: Bearer Mq1h02llQ1AMHyVgMTDgk6N6iuxczEXr0z1mPM3uJu4=

>

{
    "check": false
}
```

## add user role 

```
POST /users/roles HTTP/1.1
Host: localhost:8080
Authorization: Bearer Mq1h02llQ1AMHyVgMTDgk6N6iuxczEXr0z1mPM3uJu4=
Content-Type: application/json
Content-Length: 36

{
    "userId": 2,
    "roleId": 1
}

>

{
    "id": 2
}
```

## get user role list 

```
GET /users/roles HTTP/1.1
Host: localhost:8080
Authorization: Bearer rcCv9ivUzfNwk368u0fI1P823rz1Q3Q0Mzu52wvJ1RQ=

>
[
    {
        "id": 1,
        "name": "admin"
    }
]
```

# Thanks

I really enjoy this project though it is little. As we better just use standard JDK, so we need to create a lot of utility ourselves. I am happiest when I finally get our simple JSON parser and serializer work. 

It let me think about my journey of programming. Thanks to all colleagues, wiki maintainers, open source authors, book authors, tech bloggers, Stackoverflow authors in this journey. 

Let's end with my recently favourite quote.

> The difference between a good student and a great one is that a good student is concerned more about the outcome 
> while a great one is fascinated by the process of learning.
