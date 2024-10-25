import os
from socket import gethostbyname, gethostname

from config.spring import ConfigClient

config_server_url = os.getenv(
    "SPRING_CLOUD_CONFIG_URI", "http://localhost:8888"
)
_config_client: ConfigClient = ConfigClient(
    app_name="msvc-colors", address=config_server_url
)
_config_client.get_config()

_config: dict = _config_client.config

PORT: int = _config["server"]["port"]
PROFILES: str = _config.get("spring", {}).get("profiles", {}).get("active", "")
HOST: str = gethostname()
IP: str = gethostbyname(gethostname())

__all__ = ["PORT", "PROFILES"]
