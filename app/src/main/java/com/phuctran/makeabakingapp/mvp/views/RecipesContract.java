package com.phuctran.makeabakingapp.mvp.views;

import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.mvp.presenters.BasePresenter;

import java.util.List;

public interface RecipesContract {
    interface View extends LoadDataBaseView<RecipesContract.Presenter> {
        void renderRecipes(List<Recipe> recipes);
    }

    interface Presenter extends BasePresenter {
        void doGetRecipes();
    }
}
