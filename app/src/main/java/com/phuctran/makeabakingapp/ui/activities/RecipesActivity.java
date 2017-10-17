package com.phuctran.makeabakingapp.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.widget.Toolbar;

import com.phuctran.makeabakingapp.R;
import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.ui.fragments.RecipesFragment;

import butterknife.BindView;

/**
 * Created by phuctran on 9/20/17.
 */

public class RecipesActivity extends BaseActivity {
    private static final String FRAGMENT_RECIPES = "FRAGMENT_RECIPES";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void updateFollowingViewBinding(Bundle savedInstanceState) {
        setupActionBar();
        if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_RECIPES) == null)
            showFragment(RecipesFragment.newInstance(), FRAGMENT_RECIPES);

    }

    private void setupActionBar() {

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        setActionBarTitle(R.string.app_name);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_recipes;
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        return ((RecipesFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_RECIPES)).getIdlingResource();
    }
}
