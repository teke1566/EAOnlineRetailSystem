package cs544.ea.OnlineRetailSystem.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class ApplicationConfiguration {

	@Bean
	ModelMapper modelMapper() {
		// Maps any object to another object of similar structure such as Entity to Response
		return new ModelMapper();
	}
	
	@Bean
	ObjectMapper jsonMapper() {
		//Maps any object to JSON
		return new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .registerModule(new JavaTimeModule());
	}
}
