Order Service - Gerenciamento de Pedidos
📌 Descrição

O Order Service é uma aplicação construída com Java Spring Boot para gerenciar pedidos de forma escalável e eficiente. A aplicação recebe pedidos de um sistema externo (Produto Externo A), processa e calcula os valores, armazenando no banco de dados. Após a validação e processamento, os pedidos são disponibilizados para outro sistema externo (Produto Externo B).

A aplicação também implementa:
✅ Evitação de pedidos duplicados
✅ Concorrência segura com Lock Otimista
✅ Escalabilidade com Kafka Partitions
✅ Monitoramento com Logs e Prometheus
✅ Testes unitários e de integração
🛠 Tecnologias Utilizadas
Tecnologia	Versão
Java	17
Spring Boot	3.x
Spring Data JPA	3.x
Spring Kafka	3.x
PostgreSQL	15.x
Apache Kafka	3.x
Testcontainers	1.18.x
JUnit 5	5.x
Prometheus	2.x
🚀 Instalação e Execução
1️⃣ Clonar o Repositório

git clone https://github.com/seu-usuario/order-service.git
cd order-service

2️⃣ Configurar o Banco de Dados

A aplicação usa PostgreSQL. Para rodar via Docker, execute:

docker run --name postgres-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=orderdb -p 5432:5432 -d postgres:15

Caso prefira rodar localmente, ajuste as configurações em application.yml:

spring:
datasource:
url: jdbc:postgresql://localhost:5432/orderdb
username: admin
password: admin

3️⃣ Subir o Apache Kafka

Se não tiver um ambiente Kafka configurado, execute com Docker:

docker-compose -f kafka-docker-compose.yml up -d

Ou crie um tópico manualmente:

kafka-topics.sh --bootstrap-server localhost:9092 --create --topic new-orders --partitions 3 --replication-factor 1

4️⃣ Rodar a Aplicação

mvn spring-boot:run

Ou empacote o JAR e execute:

mvn clean package
java -jar target/order-service.jar

🧪 Rodando os Testes
Testes Unitários e de Integração

mvn test

Rodando Testcontainers (Banco PostgreSQL isolado para testes)

mvn verify

📡 Monitoramento e Logs
1️⃣ Acessar Logs Estruturados

tail -f logs/order-service.log

2️⃣ Health Check da Aplicação

curl http://localhost:8080/actuator/health

3️⃣ Exposição de Métricas para Prometheus

curl http://localhost:8080/actuator/prometheus

4️⃣ Rodar Prometheus para Monitoramento

Se já tem o Prometheus instalado, configure o arquivo prometheus.yml com:

global:
scrape_interval: 5s

scrape_configs:
- job_name: 'order-service'
  metrics_path: '/actuator/prometheus'
  static_configs:
    - targets: ['localhost:8080']

Depois inicie o Prometheus:

prometheus --config.file=prometheus.yml

5️⃣ Rodar Grafana para Dashboard

docker run -d -p 3000:3000 grafana/grafana

Acesse http://localhost:3000 (usuário: admin senha: admin).