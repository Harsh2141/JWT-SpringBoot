package com.example.Spring.security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringSecurityController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private jwtUtil jwtUtil;

	@RequestMapping("/")
	public String home() {
		return ("<h1>Welcome</h1>");
	}
	
	@RequestMapping("/authenticate")
	public AuthenticateResponse authenticateUser(@RequestBody AuthenticateRequest request) {

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String jwt = jwtUtil.generateToken(userDetails);

		return new AuthenticateResponse(jwt);
	}

}
