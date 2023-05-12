package ewha.gsd.midubang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MidubangApplication {

	public static void main(String[] args) {
		SpringApplication.run(MidubangApplication.class, args);
	}

}
