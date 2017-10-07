package com.phuctran.makeabakingapp.data;

import com.phuctran.makeabakingapp.domain.models.Ingredient;
import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.domain.models.Step;

import java.util.List;

import io.reactivex.Single;

public interface BakingDataSource {

    Single<List<Recipe>> getRecipes();

    Single<Recipe> getRecipe(int recipeId);

    Single<List<Ingredient>> getRecipeIngredients(int recipeId);

    Single<List<Step>> getRecipeSteps(int recipeId);

    void saveRecipes(List<Recipe> recipeList);

}
