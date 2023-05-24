package cs544.ea.OnlineRetailSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class JmsConfig {

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        // Set the message converter, if necessary
        // template.setMessageConverter(messageConverter());
        return template;
    }
}