FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /src

COPY ["image-coffee-utils-crop.csproj", "./"]
RUN dotnet restore "./image-coffee-utils-crop.csproj"

COPY . .
RUN dotnet build "image-coffee-utils-crop.csproj" -c Release -o /app/build

RUN dotnet publish "image-coffee-utils-crop.csproj" -c Release -o /app/publish

FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS runtime
WORKDIR /app
COPY --from=build /app/publish .

COPY ["banner.txt", "/app/banner.txt"]

ENTRYPOINT ["dotnet", "image-coffee-utils-crop.dll"]
