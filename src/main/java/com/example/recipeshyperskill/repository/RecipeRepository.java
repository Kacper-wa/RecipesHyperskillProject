package com.example.recipeshyperskill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import com.example.recipeshyperskill.model.entity.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("select r from Recipe r where upper(r.name) like upper(concat('%', ?1, '%'))")
    List<Recipe> findAllByNameIgnoreCaseContainingOrderByDateDesc(String name);

    @Query("""
            select r from Recipe r
            where upper(r.name) like upper(concat('%', ?1, '%')) and upper(r.category) like upper(concat('%', ?2, '%'))""")
    List<Recipe> findAllByNameAndCategoryIgnoreCaseContainingOrderByDateDesc(String name, String category);

    @Query("select r from Recipe r where upper(r.category) like upper(concat('%', ?1, '%'))")
    List<Recipe> findAllByCategoryIgnoreCaseContainingOrderByDateDesc(String category);

}
