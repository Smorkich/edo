package config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static model.constant.Constant.*;

@EnableRabbit
@Configuration
public class RabbitConfig {


    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Bean(name = "SimpleRabbitListener")
    public SimpleRabbitListenerContainerFactory
    rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new
                SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    @Bean(name = "connection")
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(host, port);
    }

    @Bean(name = "amqpAdmin")
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean(name = "rabbitTemplate")
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean(name = "nomenclature")
    public Queue nomenclatureQueue() {
        Queue queue = new Queue(REST_TO_SERVICE_NOMENCLATURE_QUEUE);
        amqpAdmin().declareQueue(queue);
        return queue;
    }

    @Bean(name = "approval")
    public Queue approvalQueue() {
        Queue queue = new Queue(REST_TO_SERVICE_APPROVAL_QUEUE);
        amqpAdmin().declareQueue(queue);
        return queue;
    }

    @Bean(name = "appeal")
    public Queue appealQueue() {
        Queue queue = new Queue(REST_TO_SERVICE_APPEAL_QUEUE);
        amqpAdmin().declareQueue(queue);
        return queue;
    }

    @Bean(name = "fileRecognition")
    public Queue fileRecognitionQueue() {
        Queue queue = new Queue(FILE_RECOGNITION_START);
        amqpAdmin().declareQueue(queue);
        return queue;
    }

    @Bean(name = "deadlineChange")
    public Queue deadlineChangeQueue() {
        Queue queue = new Queue(DEADLINE_CHANGE_QUEUE);
        amqpAdmin().declareQueue(queue);
        return queue;
    }
}