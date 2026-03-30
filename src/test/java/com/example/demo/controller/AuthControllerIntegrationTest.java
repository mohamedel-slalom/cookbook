package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerIntegrationTest {

	@LocalServerPort
	private int port;

	private final HttpClient httpClient = HttpClient.newHttpClient();

	@Test
	void loginShouldReturnJwtForSeededAdmin() throws Exception {
		String payload = """
			{
			  "username": "admin",
			  "password": "admin123"
			}
			""";

		HttpResponse<String> response = sendRequest("POST", "/api/auth/login", payload);

		assertEquals(200, response.statusCode());
		assertTrue(response.body().contains("token"));
	}

	private HttpResponse<String> sendRequest(String method, String path, String body)
			throws IOException, InterruptedException {
		HttpRequest.BodyPublisher publisher = body == null
			? HttpRequest.BodyPublishers.noBody()
			: HttpRequest.BodyPublishers.ofString(body);

		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(baseUrl() + path))
			.header("Content-Type", "application/json")
			.method(method, publisher)
			.build();

		return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	}

	private String baseUrl() {
		return "http://localhost:" + port;
	}
}