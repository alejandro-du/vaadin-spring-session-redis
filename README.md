[![Published on Vaadin  Directory](https://img.shields.io/badge/Vaadin%20Directory-published-00b4f0.svg)](https://vaadin.com/directory/component/spring-session-redis)
[![Stars on Vaadin Directory](https://img.shields.io/vaadin-directory/star/spring-session-redis.svg)](https://vaadin.com/directory/component/spring-session-redis)
[![Latest version on vaadin.com/directory](https://img.shields.io/vaadin-directory/v/spring-session-redis.svg)](https://img.shields.io/vaadin-directory/v/spring-session-redis.svg)

# vaadin-spring-session-redis

[spring-session-redis](https://vaadin.com/directory#!addon/spring-session-redis) is a Vaadin Add-on that allows you to replace the standard HttpSesion with Redis in Vaadin applications using Spring Session.

# How to replace the standard HttpSession with Redis in Vaadin applications?

1. [Download, install, and start Redis](http://redis.io/download).
2. Create a new Spring Boot application and add the Spring Session, Spring Data Redis, and Vaadin dependencies. Tip: use [Spring Initializr](http://start.spring.io/).
3. Add the [vaadin-spring-session-redis](https://vaadin.com/directory#!addon/spring-session-redis) dependency.
4. Define a new Spring bean of type [VaadinSessionRewriteFilter](https://github.com/alejandro-du/vaadin-spring-session-redis/blob/master/src/main/java/org/vaadin/spring/session/redis/VaadinSessionRewriteFilter.java) or add the @ServletComponentScan("org.vaadin.spring.session.redis") annotation to a @Configuration class.
5. Implement your Vaadin UIs as usual. Spring Session will transparently use Redis to back the app's HttpSession instances.

Alternatively, and if you are using Tomcat, you can [configure Redis directly in Tomcat](https://dzone.com/articles/setup-redis-session-store) without using Spring Session. You have to manually add the [VaadinSessionRewriteFilter](https://github.com/alejandro-du/vaadin-spring-session-redis/blob/master/src/main/java/org/vaadin/spring/session/redis/VaadinSessionRewriteFilter.java) in your web configuration (web.xml, or extend the class + @WebFilter).

# Why this add-on?
Spring Session with Redis doesn't work out-of-the-box with Vaadin. The issue is that the objects in the session are sent to Redis before the `VaadinSession` instance has been fully configured which causes an old version of the instance to be persisted in Redis. The problem resides in the `RedisOperationsSessionRepository` class which unfortunatelly doesn't have enough extension options to fix the problem with inheritance for example.

In order to overcome this, I found [this project](https://github.com/khauser/microservices4vaadin) that included a "dirty hack" that fixes the issue. The hack requires you to use a [modified version](https://raw.githubusercontent.com/khauser/microservices4vaadin/master/microservices/frontend/src/main/java/org/springframework/session/data/redis/RedisOperationsSessionRepository.java) of the `RedisOperationsSessionRepository` class placed in the same package as the original one.

This "hack", inspired the [spring-session-redis](https://vaadin.com/directory#!addon/spring-session-redis) Vaadin add-on that includes a [filter](https://github.com/alejandro-du/vaadin-spring-session-redis/blob/master/src/main/java/org/vaadin/spring/session/redis/VaadinSessionRewriteFilter.java) that does the trick without having to replace any class from Spring Session.

Thanks to [Karsten Ludwig Hauser](https://github.com/khauser) for the original idea!

# License

This add-on is distributed under [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).
