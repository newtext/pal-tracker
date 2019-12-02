package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    String a_welcome_message;

    public WelcomeController(){};

    public WelcomeController(String a_welcome_message) {
        this.a_welcome_message = a_welcome_message;
    }

    @GetMapping("/")
    public String sayHello(@Value("${welcome.message}") String Hello) {
        return Hello;
    }

    public String sayHello() {
        return a_welcome_message;
    }
}
