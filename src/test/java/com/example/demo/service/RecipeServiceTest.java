package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entity.Recipe;
import com.example.demo.repository.RecipeRepository;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

	@Mock
	private RecipeRepository recipeRepository;

	@InjectMocks
	private RecipeService recipeService;

	@Test
	void createRecipeShouldSaveValidRecipe() {
		Recipe recipe = buildValidRecipe();

		when(recipeRepository.save(any(Recipe.class))).thenAnswer(invocation -> {
			Recipe saved = invocation.getArgument(0);
			saved.setId(99);
			return saved;
		});

		Recipe savedRecipe = recipeService.createRecipe(recipe);

		assertNotNull(savedRecipe);
		assertEquals(99, savedRecipe.getId());
		assertNotNull(savedRecipe.getCreatedAt());
		assertNotNull(savedRecipe.getUpdatedAt());
		verify(recipeRepository).save(any(Recipe.class));
	}

	@Test
	void createRecipeShouldThrowWhenTitleMissing() {
		Recipe recipe = buildValidRecipe();
		recipe.setTitle("   ");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
			() -> recipeService.createRecipe(recipe));

		assertEquals("Recipe title is required", exception.getMessage());
		verify(recipeRepository, never()).save(any(Recipe.class));
	}

	@Test
	void patchRecipeShouldThrowWhenRecipeNotFound() {
		Recipe patchRecipe = new Recipe();
		patchRecipe.setTitle("Updated");

		when(recipeRepository.findById(100)).thenReturn(Optional.empty());

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
			() -> recipeService.patchRecipe(100, patchRecipe));

		assertEquals("Recipe not found with ID: 100", exception.getMessage());
	}

	@Test
	void deleteRecipeShouldDeleteWhenFound() {
		when(recipeRepository.existsById(1)).thenReturn(true);

		recipeService.deleteRecipe(1);

		verify(recipeRepository).deleteById(1);
	}

	@Test
	void deleteRecipeShouldThrowWhenNotFound() {
		when(recipeRepository.existsById(2)).thenReturn(false);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
			() -> recipeService.deleteRecipe(2));

		assertEquals("Recipe not found with ID: 2", exception.getMessage());
	}

	private Recipe buildValidRecipe() {
		Recipe recipe = new Recipe();
		recipe.setTitle("Tomato Pasta");
		recipe.setDescription("Simple dinner");
		recipe.setServingsCount(2);
		recipe.setTotalCookTime(30);
		recipe.setIngredients(List.of("200g pasta", "1 cup tomato sauce"));
		recipe.setSteps(List.of("Boil pasta", "Add sauce"));
		recipe.setImages(List.of("https://example.com/pasta.jpg"));
		recipe.setTags(List.of("quick", "dinner"));
		return recipe;
	}
}
