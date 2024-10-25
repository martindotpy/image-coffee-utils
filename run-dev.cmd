@echo off
set PREFIX=image-coffee-utils-

@REM Run the config server
echo "Running the config server: %PREFIX%config"
start cmd /c "mvn -f %PREFIX%config/pom.xml clean install spring-boot:run -q -DskipTests"

timeout /t 6

@REM Run the eureka server
echo "Running the eureka server: %PREFIX%eureka"
start cmd /c "mvn -f %PREFIX%eureka/pom.xml clean install spring-boot:run -q -DskipTests"

timeout /t 6

@REM Run the gateway server
echo "Running the gateway server: %PREFIX%gateway"
start cmd /c "mvn -f %PREFIX%gateway/pom.xml clean install spring-boot:run -q -DskipTests""

timeout /t 6

@REM Run the ui service
echo "Running the ui service: %PREFIX%ui"
start cmd /c "mvn -f %PREFIX%ui/pom.xml clean install spring-boot:run -q -DskipTests"

@REM Run the colors service
echo "Running the colors service: %PREFIX%colors"
start cmd /c "cd %PREFIX%colors && poetry install && poetry run python app.py"
