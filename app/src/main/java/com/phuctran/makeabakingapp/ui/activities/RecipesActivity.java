package com.phuctran.makeabakingapp.ui.activities;

import android.os.Bundle;

import com.phuctran.makeabakingapp.R;
import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.ui.fragments.RecipesFragment;

/**
 * Created by phuctran on 9/20/17.
 */

public class RecipesActivity extends BaseActivity {
    private static final String FRAGMENT_RECIPES = "FRAGMENT_RECIPES";

    @Override
    protected void updateFollowingViewBinding(Bundle savedInstanceState) {
        if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_RECIPES) == null)
            showFragment(RecipesFragment.newInstance(), FRAGMENT_RECIPES);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_recipes;
    }
}
