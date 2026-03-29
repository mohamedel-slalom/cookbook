package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Recipe;
import com.example.demo.service.RecipeService;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

	@Autowired
	private RecipeService recipeService;

	@GetMapping
	public ResponseEntity<List<Recipe>> getAllRecipes() {
		try {
			List<Recipe> recipes = recipeService.getAllRecipes();
			return new ResponseEntity<>(recipes, HttpStatus.OK);
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") Integer id) {
		try {
			Optional<Recipe> recipeData = recipeService.getRecipeById(id);
			if (recipeData.isPresent()) {
				return new ResponseEntity<>(recipeData.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("/createRecipe")
	public ResponseEntity<?> createRecipe(@RequestBody Recipe recipe) {
		try {
			Recipe savedRecipe = recipeService.createRecipe(recipe);
			return new ResponseEntity<>(savedRecipe, HttpStatus.CREATED);
		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception exception) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/patchRecipe/{id}")
	public ResponseEntity<?> patchRecipe(@PathVariable("id") Integer id, @RequestBody Recipe recipe) {
		try {
			Recipe updatedRecipe = recipeService.patchRecipe(id, recipe);
			return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception exception) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteRecipe/{id}")
	public ResponseEntity<?> deleteRecipe(@PathVariable("id") Integer id) {
		try {
			recipeService.deleteRecipe(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception exception) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
