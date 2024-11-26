@echo off

@REM Run the build script and wait for it to finish, if it fails, exit with the same error code
call ./build.cmd

@REM Check if the build script failed
if %ERRORLEVEL% neq 0 (
    echo Build script failed with error code %ERRORLEVEL%.
    exit /b %ERRORLEVEL%
)

@REM Run the docker compose
docker-compose up

@REM Remove the images and containers
call ./remove-images-containers.cmd
