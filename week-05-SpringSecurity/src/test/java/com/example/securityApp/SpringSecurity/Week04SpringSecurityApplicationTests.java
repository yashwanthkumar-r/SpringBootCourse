package com.example.securityApp.SpringSecurity;

import com.example.securityApp.SpringSecurity.entities.Users;
import com.example.securityApp.SpringSecurity.services.JWTServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Week04SpringSecurityApplicationTests {

	@Autowired
	private JWTServices jwtServices;

	@Test
	void contextLoads() {
		Users user = new Users(4L,"Yash@gmail.com","yash@123","yash");

		String token = jwtServices.generateToken(user);

		System.out.println("Jwt Token: " + token);

		Long id= jwtServices.getUserFromToken(token);

		System.out.println("User id:" +id);
	}

}
