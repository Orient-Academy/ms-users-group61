package az.edu.orient.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.queue.user}")
    private String queueName;

    @Bean
    Queue createQueue() {
        return new Queue(queueName);
    }
    @Bean
    public Jackson2JsonMessageConverter jsonConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

}
