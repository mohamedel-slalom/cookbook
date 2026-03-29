package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import com.example.demo.entity.converter.StringListConverter;

@Entity
@Table(name = "recipes")
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "title", length = 255)
	private String title;

	@Column(name = "description", length = 1000)
	private String description;

	@Column(name = "servings_count")
	private Integer servingsCount;

	@Column(name = "total_cook_time")
	private Integer totalCookTime;

	@Column(name = "ingredients", length = 4000)
	@Convert(converter = StringListConverter.class)
	private List<String> ingredients;

	@Column(name = "steps", length = 4000)
	@Convert(converter = StringListConverter.class)
	private List<String> steps;

	@Column(name = "images", length = 4000)
	@Convert(converter = StringListConverter.class)
	private List<String> images;

	@Column(name = "tags", length = 1000)
	@Convert(converter = StringListConverter.class)
	private List<String> tags;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	void onCreate() {
		LocalDateTime now = LocalDateTime.now();
		createdAt = now;
		updatedAt = now;
	}

	@PreUpdate
	void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getServingsCount() {
		return servingsCount;
	}

	public void setServingsCount(Integer servingsCount) {
		this.servingsCount = servingsCount;
	}

	public Integer getTotalCookTime() {
		return totalCookTime;
	}

	public void setTotalCookTime(Integer totalCookTime) {
		this.totalCookTime = totalCookTime;
	}

	public List<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}

	public List<String> getSteps() {
		return steps;
	}

	public void setSteps(List<String> steps) {
		this.steps = steps;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
