package ojosama.talkak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@ConfigurationPropertiesScan
public class TalkakApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalkakApplication.class, args);
	}
}
