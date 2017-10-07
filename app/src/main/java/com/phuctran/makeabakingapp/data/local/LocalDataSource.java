package com.phuctran.makeabakingapp.data.local;

import android.arch.persistence.room.Room;

import com.phuctran.makeabakingapp.BakingApplication;
import com.phuctran.makeabakingapp.data.BakingDataSource;
import com.phuctran.makeabakingapp.domain.models.Ingredient;
import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.domain.models.Step;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by phuctran on 10/3/17.
 */

public class LocalDataSource implements BakingDataSource {
    private RecipesDao mRecipesDao;
    private static LocalDataSource INSTANCE = null;

    public LocalDataSource() {
        mRecipesDao = Room.databaseBuilder(BakingApplication.getContext(), RecipesDatabase.class, DatabaseContract.DATABASE_NAME).build().recipesDao();
    }

    public static LocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource();
        }
        return INSTANCE;
    }

    private Observable<Recipe> getSubdata(Recipe recipeOnly) {
        return Observable.just(recipeOnly).map(recipe -> {
            recipe.setIngredients(mRecipesDao.getAllIngredients(recipe.getId()));
            recipe.setSteps(mRecipesDao.getAllSteps(recipe.getId()));
            return recipe;
        });
    }

    @Override
    public Single<List<Recipe>> getRecipes() {
        return Observable.fromCallable(() -> mRecipesDao.getAllRecipes())
                .flatMap(Observable::fromIterable)
                .flatMap(this::getSubdata)
                .toList();
    }

    @Override
    public Single<Recipe> getRecipe(int recipeId) {
        return Observable.fromCallable(() -> mRecipesDao.getRecipe(recipeId))
                .flatMap(this::getSubdata)
                .firstOrError();
    }

    @Override
    public Single<List<Ingredient>> getRecipeIngredients(int recipeId) {
        return Single.fromCallable(() -> mRecipesDao.getAllIngredients(recipeId));
    }

    @Override
    public Single<List<Step>> getRecipeSteps(int recipeId) {
        return Single.fromCallable(() -> mRecipesDao.getAllSteps(recipeId));
    }

    @Override
    public void saveRecipes(List<Recipe> recipeList) {
        mRecipesDao.deleteAll();

        mRecipesDao.insertRecipes(recipeList);

        for (Recipe recipe : recipeList) {
            for (int i = 0; i < recipe.getIngredients().size(); i++) {
                recipe.getIngredients().get(i).setRecipeId(recipe.getId());
            }
            for (int i = 0; i < recipe.getSteps().size(); i++) {
                recipe.getSteps().get(i).setRecipeId(recipe.getId());
            }

            mRecipesDao.insertIngredients(recipe.getIngredients());
            mRecipesDao.insertSteps(recipe.getSteps());
        }
    }
}
