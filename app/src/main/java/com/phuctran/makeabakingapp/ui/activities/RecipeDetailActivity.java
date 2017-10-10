package com.phuctran.makeabakingapp.ui.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.phuctran.makeabakingapp.BakingPreferences;
import com.phuctran.makeabakingapp.R;
import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.domain.models.Step;
import com.phuctran.makeabakingapp.ui.fragments.BaseDetailFragment;
import com.phuctran.makeabakingapp.ui.fragments.BaseFragment;
import com.phuctran.makeabakingapp.ui.fragments.RecipeFragment;
import com.phuctran.makeabakingapp.ui.fragments.RecipeStepFragment;
import com.phuctran.makeabakingapp.ui.widgets.IngredientWidgetProvider;

import org.parceler.Parcels;

import butterknife.BindView;

public class RecipeDetailActivity extends BaseActivity implements BaseDetailFragment.BaseFragmentResponder {
    private static final String FRAGMENT_RECIPE = "FRAGMENT_RECIPE";
    private static final String FRAGMENT_RECIPE_STEP = "FRAGMENT_RECIPE_STEP";
    public static final String ARGS_RECIPE_DETAIL = "ARGS_RECIPE_DETAIL";

    private int mRecipeId;

    private boolean mTwoPane;

    @Nullable
    @BindView(R.id.fragmentStepContainer)
    View fragmentViewStepContainer;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuItemThatWasSelected = item.getItemId();
        switch (menuItemThatWasSelected) {
            case R.id.action_add_to_widget:
                BakingPreferences.getInstance().setWidgetRecipeId(mRecipeId);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidgetProvider.class));
                IngredientWidgetProvider.updateAppWidget(this, appWidgetManager, appWidgetIds);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return true;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void updateFollowingViewBinding(Bundle savedInstanceState) {

        setupActionBar();
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra(ARGS_RECIPE_DETAIL)) {
            mRecipeId = intentThatStartedThisActivity.getIntExtra(ARGS_RECIPE_DETAIL, -1);
            Fragment recipeFragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_RECIPE);
            if (recipeFragment == null) {
                showFragment(RecipeFragment.newInstance(mRecipeId), FRAGMENT_RECIPE);
            }
        }

        if (fragmentViewStepContainer != null) {
            mTwoPane = true;
            if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_RECIPE_STEP) == null)
                goToRecipeStepFragment(0);
        } else {
            mTwoPane = false;
        }
    }

    private void setupActionBar() {

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_recipe_detail;
    }

    @Override
    public void goToRecipeStepFragment(int stepOrder) {
        Fragment recipeStepFragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_RECIPE_STEP);
        if (recipeStepFragment == null) {
            if (!mTwoPane) {
                showFragment(RecipeStepFragment.newInstance(mRecipeId, stepOrder), FRAGMENT_RECIPE_STEP);
            } else {
                showFragmentInTwoPane(RecipeStepFragment.newInstance(mRecipeId, stepOrder));
            }
        } else {
            ((RecipeStepFragment) recipeStepFragment).goToStep(stepOrder);
        }
    }

    public void showFragmentInTwoPane(BaseFragment fragment) {
        FragmentTransaction fragmenttransaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmenttransaction.replace(R.id.fragmentStepContainer, fragment, FRAGMENT_RECIPE_STEP);
        fragmenttransaction.commit();
    }
}
