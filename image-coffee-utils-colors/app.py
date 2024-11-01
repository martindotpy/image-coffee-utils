import os
from multiprocessing import freeze_support

import uvicorn
from dotenv import load_dotenv
from fastapi import FastAPI
from routes import routes
from spring_cloud import IP, PORT, PROFILES, init_spring_config

load_dotenv()


app: FastAPI = FastAPI(
    docs_url="/api/v0/colors/docs",
    openapi_url="/api/v0/colors/openapi.json",
    redoc_url="/api/v0/colors/redoc",
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
