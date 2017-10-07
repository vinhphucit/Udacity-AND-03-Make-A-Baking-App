package com.phuctran.makeabakingapp.data;

import com.phuctran.makeabakingapp.BakingPreferences;
import com.phuctran.makeabakingapp.domain.models.Ingredient;
import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.domain.models.Step;

import java.util.List;

import io.reactivex.Single;

public class BakingRepository implements BakingDataSource {

    BakingDataSource mLocalDataSource;
    BakingDataSource mRemoteDataSource;

    public BakingRepository(BakingDataSource mLocalDataSource,
                            BakingDataSource mRemoteDataSource) {
        this.mLocalDataSource = mLocalDataSource;
        this.mRemoteDataSource = mRemoteDataSource;
    }

    @Override
    public Single<List<Recipe>> getRecipes() {

        if (BakingPreferences.getInstance().isUpToDate()) {
            return mLocalDataSource.getRecipes();
        } else {
            return mRemoteDataSource.getRecipes();
        }
    }

    @Override
    public Single<Recipe> getRecipe(int recipeId) {
        return mLocalDataSource.getRecipe(recipeId);
    }

    @Override
    public Single<List<Ingredient>> getRecipeIngredients(int recipeId) {
        return mLocalDataSource.getRecipeIngredients(recipeId);
    }

    @Override
    public Single<List<Step>> getRecipeSteps(int recipeId) {
        return mLocalDataSource.getRecipeSteps(recipeId);
    }

    @Override
    public void saveRecipes(List<Recipe> recipeList) {
        mLocalDataSource.saveRecipes(recipeList);
    }
}
