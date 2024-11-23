@echo off
set PREFIX=image-coffee-utils-

if not exist logs mkdir logs

rem Run the config server
echo Running the config server: %PREFIX%config
start cmd /c "call ./mvnw -f %PREFIX%config clean install spring-boot:run -q -DskipTests"

timeout /t 10 /nobreak

rem Run the eureka server
echo Running the eureka server: %PREFIX%eureka
start cmd /c "call ./mvnw -f %PREFIX%eureka clean install spring-boot:run -q -DskipTests"

timeout /t 5 /nobreak

rem Run the gateway server
echo Running the gateway server: %PREFIX%gateway
start cmd /c "call ./mvnw -f %PREFIX%gateway clean install spring-boot:run -q -DskipTests"

timeout /t 10 /nobreak

rem Run the ui service
echo Running the ui service: %PREFIX%ui
start cmd /c "call ./mvnw -f %PREFIX%ui clean install spring-boot:run -q -DskipTests"

rem Run the colors service
echo Running the colors service: %PREFIX%colors
start cmd /c "cd %PREFIX%colors && call poetry install && call poetry run python app.py"