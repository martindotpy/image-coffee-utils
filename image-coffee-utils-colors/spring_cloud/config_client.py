import os
from socket import gethostbyname, gethostname
from typing import Final

from config.spring import ConfigClient

APP_NAME: Final[str] = "msvc-colors"

config_server_url = os.getenv("SPRING_CLOUD_CONFIG_URI", "http://localhost:8888")
_config_client: ConfigClient = ConfigClient(
    app_name=APP_NAME, address=config_server_url
)
_config_client.get_config()

_config: dict = _config_client.config

PORT: Final[int] = _config["server"]["port"]
PROFILES: Final[str] = (
    _config.get("spring", {})
    .get("profiles", {})
    .get(
        "active",
        "",
    )
)
PATH: Final[str] = _config["server"]["servlet"]["context-path"]
IP: Final[str] = gethostbyname(gethostname())

__all__ = ["APP_NAME", "IP", "PATH", "PORT", "PROFILES"]
