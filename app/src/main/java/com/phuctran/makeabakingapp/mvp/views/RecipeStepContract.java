package com.phuctran.makeabakingapp.mvp.views;

import com.phuctran.makeabakingapp.mvp.presenters.BasePresenter;

public interface RecipeStepContract {
    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
    }
}
