import os
from multiprocessing import freeze_support

import uvicorn
from fastapi import FastAPI
from routes import dependencies, routes
from spring_cloud import IP, PORT, PROFILES, init_spring_config

app: FastAPI = FastAPI(
    title="Image Coffee Utils Colors",
    description="API to extract the most common colors from an image.",
    docs_url="/api/v0/colors/docs",
    version="0.0.1",
    openapi_tags=[
        {"name": "colors", "description": "Operations related to colors"}
    ],
    openapi_url="/api/v0/colors/openapi.json",
    redoc_url="/api/v0/colors/redoc",
    dependencies=dependencies,
)

for router in routes:
    app.include_router(router, prefix="/api/v0/colors")


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
    )
