package br.com.igti.config;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Component
public class LanceServiceConfig {
    private static final String dateTimeFormat = "dd/MM/yyyy HH:mm";

	@Bean
	public RestTemplate webClient() {
		return new RestTemplate();
	}
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.igti.api"))
				.paths(PathSelectors.any())				
				.build()
				.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Lances REST API")
				.description("API de envio de Lances para lotes de leilões ativos. O lance só será válido se o participante estiver "
						+ "habilitado para o leilão até antes da sua abertura. O leilão precisa estar ativo no momento do envio.")
				.version("1.0.0")
		        .license("Apache License Version 2.0")
		        .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
				.build();
	}

	@Bean
	public ModelMapper modelMapper() {
	    ModelMapper mapper = new ModelMapper();
	    mapper.addConverter(new AbstractConverter<String, Date>() {
	    	@Override
	    	protected Date convert(String source) {
	    		SimpleDateFormat df = new SimpleDateFormat(dateTimeFormat);
	    		try {
					return df.parse(source);
				} catch (ParseException e) {
					return null;
				}
	    	}
		});
	    mapper.addConverter(new AbstractConverter<Date, String>() {
	    	@Override
	    	protected String convert(Date source) {	    		
	    		return new SimpleDateFormat(dateTimeFormat).format(source);
	    	}
		});
	    return mapper;
	}

    @Bean
    public JsonDeserializer<Date> jsonCustomizer() {
        return new JsonDeserializer<Date>() {
        	@Override
        	public Date deserialize(JsonParser p, DeserializationContext ctxt)
        			throws IOException, JsonProcessingException {
        		SimpleDateFormat format = new SimpleDateFormat(dateTimeFormat);
        	    String date = p.getText();
        	    try {
        	        return format.parse(date);
        	    } catch (ParseException e) {
        	        throw new RuntimeException(e);
        	    }
        	}
        };
    }
}
