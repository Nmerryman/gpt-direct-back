package dev.hydris.gptdirect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


@SpringBootApplication
@RestController 
public class GptdirectApplication {

	public static void main(String[] args) {
		SpringApplication.run(GptdirectApplication.class, args);
	}

    // @GetMapping("/hello")
    // public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
    //     return String.format("Hello %s!", name);
    // }
    
    @PostMapping("/api/ping")
    public PingMessage ping(@RequestBody PingMessage msg) {
        return msg;
    }


}
