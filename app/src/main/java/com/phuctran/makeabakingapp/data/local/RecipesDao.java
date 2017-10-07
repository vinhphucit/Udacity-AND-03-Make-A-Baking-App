package com.phuctran.makeabakingapp.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.phuctran.makeabakingapp.domain.models.Ingredient;
import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.domain.models.Step;

import java.util.List;

@Dao
public interface RecipesDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertRecipes(List<Recipe> recipeList);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertRecipe(Recipe recipe);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertIngredients(List<Ingredient> ingredientList);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertSteps(List<Step> stepList);


  @Query("Select * FROM " + DatabaseContract.RECIPES_TABLE_NAME)
  List<Recipe> getAllRecipes();

  @Query("Select * FROM " + DatabaseContract.RECIPES_TABLE_NAME + " WHERE id=:recipeId")
  Recipe getRecipe(int recipeId);

  @Query("Select * FROM " + DatabaseContract.INGREDIENT_TABLE_NAME + " WHERE recipeId=:recipeId")
  List<Ingredient> getAllIngredients(int recipeId);

  @Query("Select * FROM " + DatabaseContract.STEP_TABLE_NAME + " WHERE recipeId=:recipeId")
  List<Step> getAllSteps(int recipeId);

  @Query("DELETE FROM " + DatabaseContract.RECIPES_TABLE_NAME)
  void deleteAll();





}
