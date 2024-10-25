"""Spring Cloud Config Client for Python."""

from .config_client import HOST, IP, PORT, PROFILES
from .spring_config import init_spring_config

__all__ = ["HOST", "IP", "PORT", "PROFILES", "init_spring_config"]
