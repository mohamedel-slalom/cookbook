package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Recipe;
import com.example.demo.repository.RecipeRepository;

@Service
@Transactional
public class RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;

	public List<Recipe> getAllRecipes() {
		return recipeRepository.findAll();
	}

	public Optional<Recipe> getRecipeById(Integer id) {
		return recipeRepository.findById(id);
	}

	public Recipe createRecipe(Recipe recipe) {
		validateRecipe(recipe);

		if (recipe.getCreatedAt() == null) {
			recipe.setCreatedAt(LocalDateTime.now());
		}
		recipe.setUpdatedAt(LocalDateTime.now());
		recipe.setId(null);

		return recipeRepository.save(recipe);
	}

	public Recipe patchRecipe(Integer id, Recipe patchRecipe) {
		Optional<Recipe> existingRecipeOpt = recipeRepository.findById(id);

		if (existingRecipeOpt.isEmpty()) {
			throw new IllegalArgumentException("Recipe not found with ID: " + id);
		}

		Recipe existingRecipe = existingRecipeOpt.get();

		if (patchRecipe.getTitle() != null) {
			existingRecipe.setTitle(patchRecipe.getTitle());
		}
		if (patchRecipe.getDescription() != null) {
			existingRecipe.setDescription(patchRecipe.getDescription());
		}
		if (patchRecipe.getServingsCount() != null) {
			existingRecipe.setServingsCount(patchRecipe.getServingsCount());
		}
		if (patchRecipe.getTotalCookTime() != null) {
			existingRecipe.setTotalCookTime(patchRecipe.getTotalCookTime());
		}
		if (patchRecipe.getIngredients() != null && !patchRecipe.getIngredients().isEmpty()) {
			existingRecipe.setIngredients(patchRecipe.getIngredients());
		}
		if (patchRecipe.getSteps() != null && !patchRecipe.getSteps().isEmpty()) {
			existingRecipe.setSteps(patchRecipe.getSteps());
		}
		if (patchRecipe.getImages() != null) {
			existingRecipe.setImages(patchRecipe.getImages());
		}
		if (patchRecipe.getTags() != null) {
			existingRecipe.setTags(patchRecipe.getTags());
		}

		validateRecipe(existingRecipe);
		existingRecipe.setUpdatedAt(LocalDateTime.now());
		return recipeRepository.save(existingRecipe);
	}

	public void deleteRecipe(Integer id) {
		if (!recipeRepository.existsById(id)) {
			throw new IllegalArgumentException("Recipe not found with ID: " + id);
		}
		recipeRepository.deleteById(id);
	}

	private void validateRecipe(Recipe recipe) {
		if (recipe == null) {
			throw new IllegalArgumentException("Recipe data cannot be null");
		}
		if (recipe.getTitle() == null || recipe.getTitle().isBlank()) {
			throw new IllegalArgumentException("Recipe title is required");
		}
		if (recipe.getServingsCount() == null || recipe.getServingsCount() <= 0) {
			throw new IllegalArgumentException("ServingsCount must be greater than 0");
		}
		if (recipe.getTotalCookTime() == null || recipe.getTotalCookTime() < 0) {
			throw new IllegalArgumentException("TotalCookTime must be 0 or greater");
		}
		if (recipe.getIngredients() == null || recipe.getIngredients().isEmpty()) {
			throw new IllegalArgumentException("Ingredients are required");
		}
		if (recipe.getSteps() == null || recipe.getSteps().isEmpty()) {
			throw new IllegalArgumentException("Steps are required");
		}
		if (recipe.getImages() == null) {
			recipe.setImages(List.of());
		}
		if (recipe.getTags() == null) {
			recipe.setTags(List.of());
		}
	}
}
