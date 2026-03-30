package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		try {
			if (request.getUsername() == null || request.getUsername().isBlank() ||
				request.getPassword() == null || request.getPassword().isBlank()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Username and password are required");
			}

			Optional<User> userOpt = userService.findByUsername(request.getUsername());

			if (userOpt.isEmpty()) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Invalid username or password");
			}

			User user = userOpt.get();

			if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Invalid username or password");
			}

			String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

			return ResponseEntity.ok(new LoginResponse(token));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Login failed: " + e.getMessage());
		}
	}

	public static class LoginRequest {
		private String username;
		private String password;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	public static class LoginResponse {
		private String token;

		public LoginResponse(String token) {
			this.token = token;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}
	}
}
