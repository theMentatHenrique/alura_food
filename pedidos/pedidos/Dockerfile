
# Use uma imagem base do Maven
FROM maven:latest AS builder

# Copie os arquivos do aplicativo para o diretório de trabalho
COPY . /usr/src/app

# Define o diretório de trabalho como /usr/src/app
WORKDIR /usr/src/app

# Compila o aplicativo
RUN mvn clean install

# Use uma imagem menor do OpenJDK para a execução
FROM openjdk:17

# Copie o JAR compilado do estágio de construção para o diretório /app no contêiner
COPY --from=builder /usr/src/app/target/pedidos-1.0.jar /app/pedidos-1.0.jar

# Define o diretório de trabalho como /app
WORKDIR /app

# Comando para executar o JAR
CMD ["java", "-jar", "pedidos-1.0.jar"]