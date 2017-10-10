package com.phuctran.makeabakingapp.mvp.presenters;


import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.domain.usecases.GetRecipeUseCase;
import com.phuctran.makeabakingapp.domain.usecases.GetRecipesUseCase;
import com.phuctran.makeabakingapp.mvp.views.RecipeContract;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;


/**
 * Created by Admin on 9/22/2016.
 */

public class RecipePresenter implements RecipeContract.Presenter {
    private final RecipeContract.View mView;
    private final GetRecipeUseCase mGetRecipeUseCase;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public RecipePresenter(RecipeContract.View mView, GetRecipeUseCase mGetRecipeUseCase) {
        this.mView = mView;
        this.mGetRecipeUseCase = mGetRecipeUseCase;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getRecipe(int mRecipeId) {
        compositeDisposable.add(mGetRecipeUseCase.getRecipe(mRecipeId).subscribeWith(new DisposableSingleObserver<Recipe>() {
            @Override
            public void onSuccess(@NonNull Recipe recipe) {
                mView.showRecipe(recipe);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        }));
    }
}
