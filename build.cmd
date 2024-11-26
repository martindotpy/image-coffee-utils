@echo off
setlocal enabledelayedexpansion

echo Building the project

call ./mvnw clean install spring-boot:build-image -DskipTests

endlocal