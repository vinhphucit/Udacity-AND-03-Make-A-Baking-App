package com.phuctran.makeabakingapp.mvp.presenters;


import com.phuctran.makeabakingapp.mvp.views.RecipeContract;
import com.phuctran.makeabakingapp.mvp.views.RecipeStepContract;


/**
 * Created by Admin on 9/22/2016.
 */

public class RecipeStepPresenter implements RecipeStepContract.Presenter {
    private final RecipeStepContract.View mView;

    public RecipeStepPresenter(RecipeStepContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
