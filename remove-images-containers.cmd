@echo off
@REM Remove containers
FOR /F "tokens=*" %%A IN ('docker ps -a --filter "name=image-coffee-utils.*" -q') DO (
    docker rm -f %%A
)

@REM Remove images
FOR /F "tokens=*" %%A IN ('docker images --format "{{.Repository}}:{{.Tag}}" ^| findstr "image-coffee-utils"') DO (
    docker rmi -f %%A
)

@REM Remove volumes
FOR /F "tokens=2 delims= " %%A IN ('docker volume ls ^| findstr "image-coffee-utils"') DO (
    docker volume rm %%A
)
