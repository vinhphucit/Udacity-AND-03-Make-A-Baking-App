package com.phuctran.makeabakingapp.mvp.presenters;


import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.domain.usecases.GetRecipeUseCase;
import com.phuctran.makeabakingapp.mvp.views.RecipeStepContract;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;


/**
 * Created by Admin on 9/22/2016.
 */

public class RecipeStepPresenter implements RecipeStepContract.Presenter {
    private final RecipeStepContract.View mView;
    private final GetRecipeUseCase mGetRecipeUseCase;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Recipe mRecipse;
    private int mCurrentStep = -1;

    public RecipeStepPresenter(RecipeStepContract.View mView, GetRecipeUseCase getRecipeUseCase) {
        this.mView = mView;
        this.mGetRecipeUseCase = getRecipeUseCase;
        mView.setPresenter(this);
    }


    @Override
    public void getRecipe(int mRecipeId) {
        compositeDisposable.add(mGetRecipeUseCase.getRecipe(mRecipeId).subscribeWith(new DisposableSingleObserver<Recipe>() {
            @Override
            public void onSuccess(@NonNull Recipe recipe) {
                RecipeStepPresenter.this.mRecipse = recipe;
                mView.showStep(recipe.getSteps().get(mCurrentStep));
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        }));
    }

    @Override
    public void showStepAt(int pos) {
        setCurrentStep(pos);
        mView.setBackVisibility(pos != 0);
        mView.setNextVisibility(pos != mRecipse.getSteps().size() - 1);
        mView.showStep(mRecipse.getSteps().get(pos));
    }

    public void setCurrentStep(int setCurrentStep) {
        mCurrentStep = setCurrentStep;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
