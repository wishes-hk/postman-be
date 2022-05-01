# postman-be
Subscribe to the message queue and deliver the mail to intended users

### Use this module as a client
#### configure the params for the email
```
Map<String, Object> mailInfo = Map.of(
    "recipient", "abc@gmail.com",
    "subject", "Testing",
    "content", "Greetings, ${name}. Your username is <b>${username}. Have a nice day..!",
    "params", Map.of("name", "Amy Chu", "username", "amyChu494")
);
```
#### include Jackson library in your project and convert the map into json string
```
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>
```
```
ObjectMapper mapper = new ObjectMapper();
String mailInJson = mapper.writeValueAsString(mailInfo);
```
#### configure rabbitmq setting
In your application.yml, add
```
spring:
  rabbitmq:
    host: {MQ_HOST}
    port: {MQ_PORT}
    username: {MQ_USERNAME}
    password: {MQ_PASSWORD}
```
```
@Autowired
private RabbitTemplate rabbitTemplate;
rabbitTemplate.convertAndSend("mailing-exchange", "notification.mailing", mailInJson);
```
