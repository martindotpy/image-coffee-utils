FROM python:3.12-slim

WORKDIR /app

ENV SPRING_CLOUD_CONFIG_URI=http://image-coffee-utils-config:8888

COPY . /app

RUN pip install --no-cache-dir -r requirements.txt

CMD ["python", "app.py"]
