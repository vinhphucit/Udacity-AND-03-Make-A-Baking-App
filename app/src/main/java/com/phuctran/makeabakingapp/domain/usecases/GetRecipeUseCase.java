package com.phuctran.makeabakingapp.domain.usecases;

import com.phuctran.makeabakingapp.BakingPreferences;
import com.phuctran.makeabakingapp.data.BakingRepository;
import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.utils.RxUtils;

import java.util.List;

import io.reactivex.Single;

public class GetRecipeUseCase {
    private BakingRepository mRepository;

    public GetRecipeUseCase(BakingRepository repository) {
        this.mRepository = repository;
    }

    public Single<Recipe> getRecipe(int recipeId) {
        return mRepository.getRecipe(recipeId).doOnSuccess(recipes -> {
        }).compose(RxUtils.applySchedulersSingle());

    }
}
