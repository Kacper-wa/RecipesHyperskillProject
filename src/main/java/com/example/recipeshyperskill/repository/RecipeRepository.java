package com.example.recipeshyperskill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import com.example.recipeshyperskill.model.entity.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("select r from Recipe r where upper(r.category) = upper(?1)")
    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(@NonNull String category);

    @Query("select r from Recipe r where upper(r.name) = upper(?1)")
    List<Recipe> findAllByNameIgnoreCaseContainingOrderByDateDesc(@NonNull String name);

}
