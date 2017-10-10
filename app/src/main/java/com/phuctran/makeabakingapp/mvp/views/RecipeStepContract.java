package com.phuctran.makeabakingapp.mvp.views;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.phuctran.makeabakingapp.domain.models.Step;
import com.phuctran.makeabakingapp.mvp.presenters.BasePresenter;

public interface RecipeStepContract {
    interface View extends BaseView<Presenter> {
        void showStep(Step step);
        void setNextVisibility(boolean isVisible);
        void setBackVisibility(boolean isVisible);

    }

    interface Presenter extends BasePresenter {
        void getRecipe(int mRecipeId);
        void showStepAt(int order);
        void setCurrentStep(int setCurrentStep);
    }
}
