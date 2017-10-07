package com.phuctran.makeabakingapp.domain.usecases;

import com.phuctran.makeabakingapp.BakingPreferences;
import com.phuctran.makeabakingapp.data.BakingRepository;
import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.utils.RxUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class GetRecipesUseCase {
    private BakingRepository mRepository;

    public GetRecipesUseCase(BakingRepository repository) {
        this.mRepository = repository;
    }

    public Single<List<Recipe>> getRecipeList() {
        return mRepository.getRecipes().doOnSuccess(recipes -> {
            if (!BakingPreferences.getInstance().isUpToDate()) {

                mRepository.saveRecipes(recipes);
                BakingPreferences.getInstance().updateUpToDateStatus(true);
            }
        }).compose(RxUtils.applySchedulersSingle());

    }
}
