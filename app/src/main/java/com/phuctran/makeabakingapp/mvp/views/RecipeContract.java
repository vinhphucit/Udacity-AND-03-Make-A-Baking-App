package com.phuctran.makeabakingapp.mvp.views;

import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.mvp.presenters.BasePresenter;

public interface RecipeContract {
    interface View extends BaseView<Presenter> {
        void showRecipe(Recipe recipe);
    }

    interface Presenter extends BasePresenter {
        void getRecipe(int mRecipeId);
    }
}
