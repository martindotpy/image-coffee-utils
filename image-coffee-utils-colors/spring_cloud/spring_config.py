from .config_client import HOST, IP, _config
from .eureka_client import register_eureka


def init_spring_config() -> None:
    """Init the spring config.

    All the arguments are in the `config_client.py` file.
    """
    register_eureka(_config, IP, HOST)


__all__ = ["init_spring_config"]
