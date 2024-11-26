import os

from py_eureka_client import eureka_client


def register_eureka(app_name: str, config: dict, ip: str) -> None:
    """Register the service in Eureka server.

    Args:
        app_name (str): Name of the service.
        config (dict): Configuration of the service. All the configuration is\
            in the application.yml file provided by the Spring Cloud Config\
            Server.
        ip (str): IP of the host.

    """
    eureka_instance_hostname: str = config["eureka"]["instance"]["hostname"]
    eureka_server: str = config["eureka"]["client"]["serviceUrl"]["defaultZone"]

    if eureka_instance_hostname.startswith("$"):
        eureka_instance_hostname = eureka_instance_hostname[2:-1]
        eureka_server_values: list = eureka_instance_hostname.split(":")

        for value in eureka_server_values[:-1]:
            if eureka_instance_hostname := os.getenv(value, ""):
                break

        if not eureka_instance_hostname:
            eureka_instance_hostname = eureka_server_values[-1]

    eureka_server = eureka_server.replace(
        "${eureka.instance.hostname}", eureka_instance_hostname
    )

    eureka_client.init(
        eureka_server=eureka_server,
        app_name=app_name,
        instance_port=config["server"]["port"],
        instance_host=ip,
        instance_id=f"{ip}:{app_name}:{config['server']['port']}",
    )
