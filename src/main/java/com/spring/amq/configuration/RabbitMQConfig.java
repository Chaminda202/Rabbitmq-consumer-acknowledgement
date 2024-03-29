package com.spring.amq.configuration;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.amq.service.MessageListener;

@Configuration
public class RabbitMQConfig {
	private CongifDetails congifDetails;
	
	@Value("${spring.rabbitmq.host}")
	private String host;		
	@Value("${spring.rabbitmq.port}")
	private int port;
	@Value("${spring.rabbitmq.username}")
	private String username;
	@Value("${spring.rabbitmq.password}")
	private String password;
	
	public RabbitMQConfig(CongifDetails congifDetails) {
		this.congifDetails = congifDetails;
	}
	
	@Bean
	public ConnectionFactory connectionFactory() {
	    CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
	    connectionFactory.setUsername(username);
	    connectionFactory.setPassword(password);
	    connectionFactory.setPort(port);
	    return connectionFactory;
	}
	
	@Bean
    Queue queue() {
        return new Queue(congifDetails.getQueue(), false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(congifDetails.getExchange());
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(congifDetails.getRoutingKey());
    }
    
    @Bean
    public MessageConverter messageConverter() {
      return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public SimpleMessageListenerContainer listenerContainer(MessageListener messageListener) {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory());
        listenerContainer.setQueueNames(congifDetails.getQueue());
        listenerContainer.setMessageListener(messageListener);
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return listenerContainer;
    }
}
