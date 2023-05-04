package com.example.recipeshyperskill.model.dto;

import com.example.recipeshyperskill.model.entity.Recipe;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link Recipe} entity
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RecipeDto implements Serializable {

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @UpdateTimestamp
    private String date;

    @NotBlank
    private String description;

    @NotEmpty
    private List<String> ingredients;

    @NotEmpty
    private List<String> directions;
}