# Use the official .NET SDK image as a build stage
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /src

# Copy the csproj and restore as distinct layers
COPY ["image-coffee-utils-crop.csproj", "./"]
RUN dotnet restore "./image-coffee-utils-crop.csproj"

# Copy the remaining source code and build the application
COPY . .
RUN dotnet build "image-coffee-utils-crop.csproj" -c Release -o /app/build

# Publish the application
RUN dotnet publish "image-coffee-utils-crop.csproj" -c Release -o /app/publish

# Use the official .NET runtime image as a runtime stage
FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS runtime
WORKDIR /app
COPY --from=build /app/publish .

# Add the banner.txt
COPY ["banner.txt", "/app/banner.txt"]

# Set the entry point for the container
ENTRYPOINT ["dotnet", "image-coffee-utils-crop.dll"]
