# service-schedule

O Service Schedule App é uma aplicação de agendamento de horários para prestadores de serviços, onde os clientes podem visualizar a disponibilidade dos prestadores e agendar serviços com base nos horários disponíveis.

## Funcionalidades Principais

- Visualização de prestadores de serviços cadastrados
- Agendamento de horários disponíveis para os prestadores
- Gerenciamento de horários e disponibilidade dos prestadores
- Notificação de novos agendamentos via Apache Kafka
- Notificação de cancelamentos realizados via Apache Kafka.

## Tecnologias Utilizadas
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- RESTful APIs
- Postgres
- Apache Kafka

## Pré-requisitos
- Java JDK 8 ou superior instalado
- Maven (ou Gradle) para gerenciamento de dependências
- IDE (Eclipse, IntelliJ IDEA, etc.) para desenvolvimento
- Postgres instalado
- Apache Kafka e Zookeeper

## Como Executar o Projeto
1. Clone este repositório para o seu ambiente local:
```bash
git clone https://github.com/seu-usuario/service-schedule-app.git
```
2. Importe o projeto na sua IDE e aguarde a resolução das dependências.


3. Configure as propriedades do banco de dados no arquivo application.properties ou application.yml

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/service_schedule_db
spring.datasource.username=seu-usuario
spring.datasource.password=sua-senha
```
4. Baixe e execute o Apache Kafka:
   - Faça o download do Apache Kafka em: https://kafka.apache.org/downloads ou via Docker.
   - Extraia os arquivos do Kafka em um diretório de sua escolha.
   - Inicie o servidor Zookeeper:
```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
``` 
  - Em uma nova janela do terminal, inicie o servidor Kafka:
```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
``` 

5. Configure as propriedades do Kafka no arquivo application.properties ou application.yml:
```bash
# Configurações do Kafka
spring.kafka.consumer.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=group_id
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
```

6. Execute a aplicação a partir da classe principal (ServiceScheduleApplication.java).
Acesse a aplicação no navegador ou em uma ferramenta de requisições HTTP para interagir com as APIs.

## Endpoints Disponíveis

**GET /prestadores:** Retorna a lista de todos os prestadores cadastrados.

**GET /prestadores/{id}:** Retorna os detalhes de um prestador específico.

**POST /prestadores:** Cadastra um novo prestador de serviço.

**POST /prestadores/{id}/horarios:** Associa horários ao prestador especificado.

**GET /prestadores/{id}/horarios-disponiveis:** Retorna os horários disponíveis de um prestador.

**POST /prestadores/{idPrestador}/horarios/{idHorario}/agendar:** Agenda um horário para um cliente.

**POST /prestadores/{idPrestador}/horarios/{idHorario}/cancelar-agenda:** Cancela o agendamento de um horário.

## Contribuição
Contribuições são bem-vindas! Se você deseja contribuir com este projeto, sinta-se à vontade para enviar pull requests ou relatar problemas nas issues.

## Autor
Francisco