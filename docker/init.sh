#!/bin/bash
# Aguardar o Kafka estar completamente pronto
echo "Aguardando Kafka iniciar..."
while ! nc -z kafka 9092; do
  sleep 1
  echo "Aguardando Kafka..."
done
echo "Kafka iniciado!"

# Criar o tópico new-orders se não existir
echo "Criando o tópico 'new-orders' se necessário..."
kafka-topics --create --topic new-orders --bootstrap-server kafka:9092 --partitions 1 --replication-factor 1 || echo "Tópico 'new-orders' já existe."

# Enviar uma mensagem JSON para o tópico 'new-orders'
echo "Enviando mensagem JSON para o tópico 'new-orders'..."
echo '{"externalId": "12345", "status": "NEW", "totalValue": 250.00}' | kafka-console-producer --broker-list kafka:9092 --topic new-orders
