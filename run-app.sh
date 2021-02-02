#!/bin/bash
echo "criando imagem da API..."
mvn clean package -DskipTests
docker build -t projeto-rest-app .
echo "subindo API, Banco (Postgres) e PGAdmin..."
docker-compose up -d