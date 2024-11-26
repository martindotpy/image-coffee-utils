import os
from multiprocessing import freeze_support

import uvicorn
from fastapi import FastAPI
from routes import dependencies, routes
from spring_cloud import IP, PATH, PORT, PROFILES, init_spring_config

app: FastAPI = FastAPI(
    title="Image Coffee Utils Colors API",
    description="API for extracting the most common colors from an image.",
    docs_url=f"{PATH}/docs",
    version="0.0.1",
    openapi_tags=[
        {"name": "colors", "description": "Colors operations with images"},
    ],
    openapi_url=f"{PATH}/openapi.json",
    redoc_url=f"{PATH}/redoc",
    dependencies=dependencies,
)


@app.get("/info")
def info() -> dict:
    """Return the status of the service.

    Returns:
        dict: The status of the service.

    """
    return {
        "status": "UP",
    }


for router in routes:
    app.include_router(router, prefix=f"{PATH}")


if __name__ == "__main__":
    if os.name == "nt":
        freeze_support()

    with open("./banner.txt", encoding="utf-8") as banner:
        print(banner.read())

    init_spring_config()

    uvicorn.run(
        "app:app",
        port=PORT,
        host=IP,
        reload="dev" in PROFILES,
        server_header=False,
    )
