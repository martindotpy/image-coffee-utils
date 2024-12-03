@echo off

rem Define the java microservices
set elements=image-coffee-utils-config image-coffee-utils-eureka image-coffee-utils-gateway image-coffee-utils-ui image-coffee-utils-resize image-coffee-utils-invert-colors

call ./mvnw clean install package -DskipTests

for %%e in (%elements%) do (
    call ./mvnw -f %%e spring-boot:build-image -DskipTests
)
