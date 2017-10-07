package com.phuctran.makeabakingapp.mvp.presenters;


import com.phuctran.makeabakingapp.mvp.views.RecipeContract;


/**
 * Created by Admin on 9/22/2016.
 */

public class RecipePresenter implements RecipeContract.Presenter {
    private final RecipeContract.View mView;

    public RecipePresenter(RecipeContract.View mView) {
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
