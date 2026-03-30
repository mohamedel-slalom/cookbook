package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.example.demo.security.JwtUtil;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecipeControllerIntegrationTest {

	@LocalServerPort
	private int port;

	private final HttpClient httpClient = HttpClient.newHttpClient();

	@Autowired
	private JwtUtil jwtUtil;

	private String adminToken;

	@BeforeEach
	void setUp() {
		adminToken = jwtUtil.generateToken("admin", "ADMIN");
	}

	@Test
	void shouldSupportBasicRecipeApiFlow() throws Exception {
		HttpResponse<String> listResponse = sendRequest("GET", "/api/recipes", null, adminToken);
		assertEquals(200, listResponse.statusCode());
		assertTrue(listResponse.body().contains("Blueberry Pancakes"));

		String createPayload = """
			{
			  "title": "Integration Test Recipe",
			  "description": "Integration recipe",
			  "time": "22 min",
			  "servingsCount": 3,
			  "ingredients": ["1 cup rice", "2 cups water"],
			  "steps": ["Boil water", "Cook rice"],
			  "images": ["https://example.com/rice.jpg"],
			  "tags": ["test", "quick"]
			}
			""";

		HttpResponse<String> createResponse = sendRequest("POST", "/api/recipes/createRecipe", createPayload, adminToken);
		assertEquals(201, createResponse.statusCode());

		int recipeId = extractId(createResponse.body());
		assertTrue(recipeId > 0);

		HttpResponse<String> getByIdResponse = sendRequest("GET", "/api/recipes/" + recipeId, null, adminToken);
		assertEquals(200, getByIdResponse.statusCode());
		assertTrue(getByIdResponse.body().contains("Integration Test Recipe"));

		String patchPayload = """
			{
			  "title": "Integration Recipe Updated",
			  "time": "25 min"
			}
			""";
		HttpResponse<String> patchResponse = sendRequest("PATCH", "/api/recipes/patchRecipe/" + recipeId, patchPayload, adminToken);
		assertEquals(200, patchResponse.statusCode());
		assertTrue(patchResponse.body().contains("Integration Recipe Updated"));

		HttpResponse<String> deleteResponse = sendRequest("DELETE", "/api/recipes/deleteRecipe/" + recipeId, null, adminToken);
		assertEquals(200, deleteResponse.statusCode());

		HttpResponse<String> afterDeleteResponse = sendRequest("GET", "/api/recipes/" + recipeId, null, adminToken);
		assertEquals(404, afterDeleteResponse.statusCode());
	}

	private HttpResponse<String> sendRequest(String method, String path, String body, String token)
			throws IOException, InterruptedException {
		HttpRequest.BodyPublisher publisher = body == null
			? HttpRequest.BodyPublishers.noBody()
			: HttpRequest.BodyPublishers.ofString(body);

		HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
			.uri(URI.create(baseUrl() + path))
			.header("Content-Type", "application/json");

		if (token != null) {
			requestBuilder.header("Authorization", "Bearer " + token);
		}

		HttpRequest request = requestBuilder
			.method(method, publisher)
			.build();

		return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	}

	private int extractId(String body) {
		int keyIndex = body.indexOf("\"id\":");
		assertTrue(keyIndex >= 0);

		int valueStart = keyIndex + 5;
		while (valueStart < body.length() && !Character.isDigit(body.charAt(valueStart))) {
			valueStart++;
		}

		int valueEnd = valueStart;
		while (valueEnd < body.length() && Character.isDigit(body.charAt(valueEnd))) {
			valueEnd++;
		}

		assertNotNull(body.substring(valueStart, valueEnd));
		return Integer.parseInt(body.substring(valueStart, valueEnd));
	}

	private String baseUrl() {
		return "http://localhost:" + port;
	}
}
