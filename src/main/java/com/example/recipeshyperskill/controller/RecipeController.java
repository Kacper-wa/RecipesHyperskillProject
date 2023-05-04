package com.example.recipeshyperskill.controller;

import com.example.recipeshyperskill.model.dto.RecipeDto;
import com.example.recipeshyperskill.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRecipes() {
        return new ResponseEntity<>(recipeService.getAll() == null ? "No recipes found!" : recipeService.getAll(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable Long id) {
        return recipeService.get(id);
    }

    @PostMapping("/new")
    public ResponseEntity<?> postRecipe(
            @Valid
            @RequestBody RecipeDto recipeDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return recipeService.post(recipeDto, userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(
            @Valid
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return recipeService.delete(id, userDetails.getUsername());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(
            @Valid
            @RequestBody RecipeDto recipeDto,
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return recipeService.update(id, recipeDto, userDetails.getUsername());
    }

    @GetMapping("/search/")
    public ResponseEntity<?> searchRecipe(
            @Valid
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String name) {
        return recipeService.search(category, name);
    }

}