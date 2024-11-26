# Image Coffee Utils

<div align="center">
  <img src="image-coffee-utils-ui\src\main\webapp\assets\svg\header-logotype.svg" height="200" alt="Banner">
</div>

This web application helps you work with images by allowing you to extract main
colors, crop, resize, adjust dimensions, and more.

## How to Run

### Development

To set up a development environment, you will need Java 21 and Python 3.12
installed on your machine. Then, run the following command:

Windows:

```batch
.\run-dev
```

Linux:

```bash
./run-dev.sh
```

### Production

For production, ensure Docker is installed on your machine. Then, run the
following command:

Windows:

```batch
.\build
```

Linux:

```bash
./build.sh
```

This command will build the Docker images of Spring Microservices (Docker
Compose will build the Python image) and run the application using Docker
Compose.

## User Interface (UI)

The UI is built with JSF and offers a simple web interface where users can
upload images and perform various operations using backend microservices.

## Microservices

The backend is built with Spring Cloud, leveraging Spring Cloud Config, Spring
Cloud Eureka, and Spring Cloud Gateway, and provides the following
microservices:

> [!NOTE]
>
> - The notation `<api-version>` refers to the version of the API. For example,
>   `v1`, always start with `v`.
> - The notation `-F` refers to form data.
> - The notation `<value:default>` refers to the default value of the parameter.

### Colors

This microservice extracts the main colors from an image. It is built with
Python using FastAPI and Scikit-learn.

#### Endpoints

The entry point is `/api/<api-version>/colors`.

- `POST -F image=<image> -F n=<number-of_colors:5>`: Extracts the `n` main
  colors from the image.

### Crop

This microservice crops an image. It is built with C# using ASP.NET Core.

#### Endpoints

The entry point is `/api/<api-version>/crop`.

- `POST -F image=<image> -F x=<x:0> -F y=<y:0> -F width=<width> -F height=<height>`:
  Crops the image.

### Resize

This microservice resizes an image. It is built with Java using Spring Boot.

#### Endpoints

The entry point is `/api/<api-version>/resize`.

- `POST -F image=<image> -F width=<width> -F height=<height>`: Resizes the
  image.

### Adjust Dimensions

This microservice adjusts the dimensions of an image. It is built with Java
using Spring Boot.

#### Endpoints

The entry point is `/api/<api-version>/adjust-dimensions`.

- `POST -F image=<image> -F width=<width> -F height=<height>`: Adjusts the size
  of the image.

### Invert Colors

This microservice inverts the colors of an image. It is built with Java using
Spring Boot.

#### Endpoints

The entry point is `/api/<api-version>/invert-colors`.

- `POST -F image=<image>`: Inverts the colors of the image.
