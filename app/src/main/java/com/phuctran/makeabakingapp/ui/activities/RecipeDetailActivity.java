package com.phuctran.makeabakingapp.ui.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
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

    private Recipe mRecipe;

    private boolean mTwoPane;

    @Nullable
    @BindView(R.id.fragmentStepContainer)
    View fragmentViewStepContainer;

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
                BakingPreferences.getInstance().setWidgetRecipeId(mRecipe.getId());
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidgetProvider.class));
                IngredientWidgetProvider.updateAppWidget(this, appWidgetManager, appWidgetIds);
                return true;
            default:
                return false;
        }

    }

    @Override
    protected void updateFollowingViewBinding(Bundle savedInstanceState) {


        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra(ARGS_RECIPE_DETAIL)) {
            mRecipe = Parcels.unwrap(intentThatStartedThisActivity.getParcelableExtra(ARGS_RECIPE_DETAIL));
            if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_RECIPE) == null)
                showFragment(RecipeFragment.newInstance(mRecipe), FRAGMENT_RECIPE);
        }

        if (fragmentViewStepContainer != null) {
            mTwoPane = true;
            goToRecipeStepFragment(mRecipe.getSteps().get(0));
        } else {
            mTwoPane = false;
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_recipe_detail;
    }

    @Override
    public void goToRecipeStepFragment(Step step) {
        if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_RECIPE_STEP) == null) {
            if(!mTwoPane) {
                showFragment(RecipeStepFragment.newInstance(step), FRAGMENT_RECIPE_STEP);
            }else{
                showFragmentInTwoPane(RecipeStepFragment.newInstance(step));
            }
        }
    }

    public void showFragmentInTwoPane(BaseFragment fragment) {
        FragmentTransaction fragmenttransaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmenttransaction.replace(R.id.fragmentStepContainer, fragment);
        fragmenttransaction.commit();
    }
}
