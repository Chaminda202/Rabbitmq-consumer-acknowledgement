package com.spring.amq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.spring.amq.model.Content;
import com.spring.amq.util.GsonUtil;

//@Service
public class ConsumerService {
	private Logger logger;
		
	public ConsumerService(){
		this.logger = LoggerFactory.getLogger(this.getClass());
	}
	
	@RabbitListener(queues = "${rabbitmq.queue}")
	public void consumeQueue(String meesage){
		logger.info("{} -> {}", "Consumer_service", meesage);
		Content content = GsonUtil.getFromObject(meesage, Content.class);
		logger.info("{} -> {}", "Consumer_service", content.getMobileNumber());
	}
}
