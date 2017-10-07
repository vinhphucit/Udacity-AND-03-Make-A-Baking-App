package com.phuctran.makeabakingapp.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.phuctran.makeabakingapp.domain.models.Ingredient;
import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.domain.models.Step;

@Database(entities = {Recipe.class, Ingredient.class, Step.class}, version = 1, exportSchema = false)
public abstract class RecipesDatabase extends RoomDatabase {
    public abstract RecipesDao recipesDao();
}
