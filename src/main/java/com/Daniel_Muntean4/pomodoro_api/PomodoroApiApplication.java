package com.Daniel_Muntean4.pomodoro_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PomodoroApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(PomodoroApiApplication.class, args);
	}
	@GetMapping("/ok")
	public String ok(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("ok! %s",name);
	}



}
