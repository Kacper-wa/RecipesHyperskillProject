package com.example.recipeshyperskill.service;

import com.example.recipeshyperskill.exception.RecipeNotFoundException;
import com.example.recipeshyperskill.model.dto.RecipeDto;
import com.example.recipeshyperskill.model.entity.Recipe;
import com.example.recipeshyperskill.model.mapper.RecipeMapper;
import com.example.recipeshyperskill.repository.RecipeRepository;
import com.example.recipeshyperskill.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final RecipeMapper recipeMapper;

    private static final Logger logger = LoggerFactory.getLogger(RecipeService.class);


    public ResponseEntity<?> get(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(
                () -> new RecipeNotFoundException("No recipe found with id: " + id)
        );
        logger.info("Recipe found with id: {}", id);
        return new ResponseEntity<>(recipeMapper.toDto(recipe), HttpStatus.OK);
    }

    public List<RecipeDto> getAll() {
        List<Recipe> recipes = recipeRepository.findAll();
        if (recipes.isEmpty()) {
            logger.info("No recipes found!");
            return null;
        }
        logger.info("Recipes fetched successfully!");
        return recipeMapper.toDtoList(recipes);
    }

    @Transactional
    public ResponseEntity<?> post(RecipeDto recipeDto, String email) {
        Recipe recipe = recipeMapper.toEntity(recipeDto);
        recipe.setUser(userRepository.findByEmailIgnoreCase(email).get());
        recipeRepository.save(recipe);
        logger.info("Recipe created with id: {}", recipe.getId());
        return new ResponseEntity<>(Map.of(
                "id", recipe.getId()
                //"message", "Recipe successfully created!"
        ), HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> delete(Long id, String email) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(
                () -> new RecipeNotFoundException("No recipe found with id: " + id)
        );
        if (!recipe.getUser().getEmail().equalsIgnoreCase(email)) {
            logger.warn("User with email: {} attempted to delete a recipe with id: {} which does not belong to this user", email, id);
            return new ResponseEntity<>(Map.of(
                    "message", "You do not have permission to delete this recipe!"),
                    HttpStatus.FORBIDDEN);
        }
        recipeRepository.delete(recipe);
        logger.info("Recipe with id: {} deleted successfully!", id);
        return new ResponseEntity<>(Map.of(
                "message", "The recipe has been deleted!"),
                HttpStatus.NO_CONTENT);
    }

    @Transactional
    public ResponseEntity<?> update(Long id, RecipeDto recipeDto, String email) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(
                () -> new RecipeNotFoundException("No recipe found with id: " + id)
        );

        if (!recipe.getUser().getEmail().equals(email)) {
            logger.warn("User with email {} attempted to edit a recipe with id {} but did not have permission", email, id);
            return new ResponseEntity<>(Map.of(
                    "message", "You do not have permission to edit this recipe!"),
                    HttpStatus.FORBIDDEN);
        }
        recipe = recipeMapper.partialUpdate(recipeDto, recipe);
        recipeRepository.save(recipe);
        logger.info("Recipe with id: {} updated successfully!", id);
        return new ResponseEntity<>(recipeMapper.toDto(recipe),
                HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> search(String category, String name) {
        List<Recipe> recipes;
        if (category == null && name == null) {
            logger.warn("No search parameters provided!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (category != null) {
            recipes = recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
            logger.info("Recipes fetched by category: {}", category);
        } else {
            recipes = recipeRepository.findAllByNameIgnoreCaseContainingOrderByDateDesc(name);
            logger.info("Recipes fetched by name: {}", name);
        }
        return new ResponseEntity<>(recipeMapper.toDtoList(recipes), HttpStatus.OK);
    }

}