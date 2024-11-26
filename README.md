# Image Coffee Utils

<div align="center">
  <img src="image-coffee-utils-ui\src\main\webapp\assets\svg\header-logotype.svg" height="200" alt="Banner">
</div>

This web application helps you work with images by allowing you to extract main
colors, crop, resize, adjust dimensions, and more.

## How to Run

### Development

To set up a development environment, you will need Java 21, Python 3.12 and .NET
8.0 installed on your machine. Then, run the following command:

Windows:

```batch
.\run-dev
```

Linux:

```bash
./run-dev.sh
```

Alternatively, you can use the Visual Studio Code devcontainer for an easier
setup.

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
Compose will build the Python and C# image) and run the application using Docker
Compose.

## User Interface (UI)

The UI is built with JSF and offers a simple web interface where users can
upload images and perform various operations using backend microservices.

## Microservices

The backend uses Spring Cloud to deliver scalable services, including:

| Service               | Technology       | Endpoint Example                       | OpenAPI Docs                                                                                               |
| --------------------- | ---------------- | -------------------------------------- | ---------------------------------------------------------------------------------------------------------- |
| **Colors**            | Python (FastAPI) | `/api/<api-version>/colors`            | [`api/v0/colors/docs`](https://image-coffee-utils.cupscoffee.xyz/api/v0/colors/docs)                       |
| **Crop**              | C# (ASP.NET)     | `/api/<api-version>/crop`              | [`api/v0/crop/docs`](https://image-coffee-utils.cupscoffee.xyz/api/v0/crop/docs)                           |
| **Resize**            | Java (Spring)    | `/api/<api-version>/resize`            | [`api/v0/resize/docs`](https://image-coffee-utils.cupscoffee.xyz/api/v0/resize/docs)                       |
| **Adjust Dimensions** | Java (Spring)    | `/api/<api-version>/adjust-dimensions` | [`api/v0/adjust-dimensions/docs`](https://image-coffee-utils.cupscoffee.xyz/api/v0/adjust-dimensions/docs) |
| **Invert Colors**     | Java (Spring)    | `/api/<api-version>/invert-colors`     | [`api/v0/invert-colors/docs`](https://image-coffee-utils.cupscoffee.xyz/api/v0/invert-colors/docs)         |

Each service processes images based on specific operations such as resizing,
cropping, or color inversion.

### Endpoint Parameters

> [!NOTE]
>
> - The notation `<api-version>` refers to the version of the API. For example,
>   `v1`, always start with `v`.
> - The notation `-F` refers to form data.
> - The notation `<value:default>` refers to the default value of the parameter.

#### Colors

- **Endpoint:** `/api/<api-version>/colors`
- **Method:** `POST`
- **Parameters:**

  - `-F image=<image>`: The image file to process.
  - `-F n=<number_of_colors:5>`: Number of colors to extract (default is 5).

  _Extracts the `n` main colors from the image._

#### Crop

- **Endpoint:** `/api/<api-version>/crop`
- **Method:** `POST`
- **Parameters:**

  - `-F image=<image>`: The image file to crop.
  - `-F x=<x:0>`: X coordinate for the top-left corner of the crop.
  - `-F y=<y:0>`: Y coordinate for the top-left corner of the crop.
  - `-F width=<width>`: The width of the cropped area.
  - `-F height=<height>`: The height of the cropped area.

  _Crops the image based on the specified coordinates and dimensions._

#### Resize

- **Endpoint:** `/api/<api-version>/resize`
- **Method:** `POST`
- **Parameters:**

  - `-F image=<image>`: The image file to resize.
  - `-F width=<width>`: The new width of the image.
  - `-F height=<height>`: The new height of the image.

  _Resizes the image to the specified dimensions._

#### Adjust Dimensions

- **Endpoint:** `/api/<api-version>/adjust-dimensions`
- **Method:** `POST`
- **Parameters:**

  - `-F image=<image>`: The image file to adjust.
  - `-F width=<width>`: The new width of the image.
  - `-F height=<height>`: The new height of the image.

  _Adjusts the size of the image to the specified width and height._

#### Invert Colors

- **Endpoint:** `/api/<api-version>/invert-colors`
- **Method:** `POST`
- **Parameters:**

  - `-F image=<image>`: The image file whose colors will be inverted.

  _Inverts the colors of the provided image._
