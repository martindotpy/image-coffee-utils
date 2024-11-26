from .config_client import APP_NAME, IP, PATH, PORT, PROFILES, _config
from .eureka_client import register_eureka


def init_spring_config() -> None:
    """Init the spring config.

    All the arguments are in the `config_client.py` file.
    """
    register_eureka(APP_NAME, _config, IP)


__all__ = ["IP", "PATH", "PORT", "PROFILES", "init_spring_config"]
