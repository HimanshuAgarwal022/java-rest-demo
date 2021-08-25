package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.exceptions.UserConstraintViolationException;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;

@RestController
public class Controller {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@GetMapping(path="/add")
	public @ResponseBody String addNewUser (@RequestParam String name) {

		if(name.isBlank() || name.equals("") || name.isEmpty()){
			throw new UserConstraintViolationException();
		}

		User check = userRepository.findByName(name);
		if(check != null){
			//throw new UserAlreadyExistsException(name);
			return " Hello "+check.getName()+"!!! ";
		}
		else{
			User n = new User();
			n.setName(name);
			userRepository.save(n);

			User ans = userRepository.findByName(name);
			return " Hello "+ans.getName()+"!!! ";
		}

	}

	@ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return "{\"message\": \"" + e.getMessage() + "\" }";
    }

	@ExceptionHandler(UserConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserConstraintViolationException(UserConstraintViolationException e) {
        return "{\"message\": \"" + e.getMessage() + "\" }";
    }

	@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(Exception e) {
        return "{\"message\": \"" + e.getMessage() + "\" }";
    }

}
