@echo off
REM Remove containers
for /f "tokens=*" %%i in ('docker ps -a --filter "name=image-coffee-utils.*" -q') do docker rm -f %%i

REM Remove images
for /f "tokens=*" %%i in ('docker images --format "{{.Repository}}:{{.Tag}}" ^| findstr "image-coffee-utils"') do docker rmi -f %%i
