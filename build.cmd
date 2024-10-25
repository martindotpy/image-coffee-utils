@echo off
setlocal enabledelayedexpansion

rem Define the java microservices
set elements=image-coffee-utils-config image-coffee-utils-eureka image-coffee-utils-gateway image-coffee-utils-ui

for %%e in (%elements%) do (
    start cmd /c "mvn -f %%e clean install spring-boot:build-image -DskipTests"
)

endlocal