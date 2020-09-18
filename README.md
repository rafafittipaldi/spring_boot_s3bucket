### Spring Boot com S3 Bucket (Amazon S3/Ninja S3)

#### Requisitos
* JDK 8
* Conta na Amazon S3 ou Ninja S3 instalado

API Rest que realiza Upload/Delete/Download em um determinado Bucket  

#### application.yml (Arquivo de propriedades)  

Alterar as propriedades desejadas:  

* accessKey: ***{accessKey}***  
* secretKey: ***{secretKey}***  
* bucketName: ***{bucketName}***  

### Amazon S3  
Para usar o Bean do Amazon S3, mudar para **@Qualifier(value = "amazonS3Client")** ou excluir da classe **BucketServiceImpl.java**, pois o da Amazon está configurado como **@Primary**;  

### Ninja S3
* *Endpoint padrão: http://localhost:9444/s3*  

Para usar o Bean do Ninja S3, mudar para **@Qualifier(value = "ninjaS3Client")** da classe **BucketServiceImpl.java**;  

### Build Docker (Dockerfile)  

docker build -t **{REPOSITORY}**/buckets3:1.0 .  

### Run Docker  

docker run -d -p 0.0.0.0:**{PORTA_DESEJADA}**:8181 **{REPOSITORY}**/buckets3:1.0  

