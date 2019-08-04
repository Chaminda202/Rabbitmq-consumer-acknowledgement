package com.spring.amq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.spring.amq.configuration.CongifDetails;
import com.spring.amq.model.Content;
import com.spring.amq.util.GsonUtil;

@Service
public class ProducerService {
	private Logger logger;
	private RabbitTemplate rabbitTemplate;
	private CongifDetails congifDetails;
	
	public ProducerService(RabbitTemplate rabbitTemplate, CongifDetails congifDetails) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.rabbitTemplate = rabbitTemplate;
		this.congifDetails = congifDetails;
	}
	
	public void produceMessage(Content content){
		logger.info("{} -> {}", "Produce_Message_Service", content.getMobileNumber());
		String message = GsonUtil.getToString(content, Content.class);
		rabbitTemplate.convertAndSend(congifDetails.getExchange(), congifDetails.getRoutingKey(), message);
		logger.info("{} -> {}", "Produce_Message_Service", "SUCCESS");
	}
	
}
