INSERT INTO recipes (
	id,
	title,
	description,
	is_favorite,
	servings_count,
	total_cook_time,
	ingredients,
	steps,
	images,
	tags,
	created_at,
	updated_at
) VALUES (
	1,
	'Classic Pancakes',
	'Simple pancakes for breakfast',
	TRUE,
	4,
	20,
	'2 cups flour||2 eggs||1.5 cups milk',
	'Mix the flour, eggs, and milk.||Pour onto a hot pan.||Cook until golden on both sides.',
	'https://example.com/pancakes.jpg',
	'breakfast||easy',
	CURRENT_TIMESTAMP,
	CURRENT_TIMESTAMP
);
