package bg.proxiad.demo.championship;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import bg.proxiad.demo.championship.logic.LogicConfig;
import bg.proxiad.demo.championship.model.HibernateConfig;

@Configuration()
@PropertySource("classpath:application.properties")
@Import({ HibernateConfig.class, LogicConfig.class})
@ImportResource("classpath:SecurityConfig.xml")
public class ApplicationConfig {
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
	    return new PropertySourcesPlaceholderConfigurer();
	}
	
	
}
