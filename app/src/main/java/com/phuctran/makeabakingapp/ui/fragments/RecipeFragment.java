package com.phuctran.makeabakingapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.phuctran.makeabakingapp.R;
import com.phuctran.makeabakingapp.domain.models.Ingredient;
import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.domain.models.Step;
import com.phuctran.makeabakingapp.mvp.presenters.RecipePresenter;
import com.phuctran.makeabakingapp.mvp.views.RecipeContract;
import com.phuctran.makeabakingapp.ui.adapters.RecipeIngredientStepAdapter;

import org.parceler.Parcels;

import butterknife.BindView;

/**
 * Created by phuctran on 9/20/17.
 */

public class RecipeFragment extends BaseDetailFragment implements RecipeContract.View, RecipeIngredientStepAdapter.ListItemClickListener {
    private static final String TAG = RecipesFragment.class.getSimpleName();
    public static final String ARGS_RECIPE = "ARGS_RECIPE";

    @BindView(R.id.recycler_view)
    RecyclerView mRvRecipes;

    private RecipeContract.Presenter mPresenter;
    private Recipe mRecipe;
    private RecipeIngredientStepAdapter mRecipeIngredientStepAdapter;

    public static RecipeFragment newInstance(Recipe recipe) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_RECIPE, Parcels.wrap(recipe));

        RecipeFragment fragment = new RecipeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public RecipeFragment() {
        this.mPresenter = new RecipePresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARGS_RECIPE))
            this.mRecipe = Parcels.unwrap(getArguments().getParcelable(ARGS_RECIPE));
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    protected String getSubclassName() {
        return TAG;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recipe;
    }

    @Override
    protected void updateFollowingViewBinding() {
        setActionBarTitle(mRecipe.getName());
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRvRecipes.setLayoutManager(layoutManager);
        mRvRecipes.setHasFixedSize(true);
        mRecipeIngredientStepAdapter = new RecipeIngredientStepAdapter(getContext(), mRecipe.getIngredients(), mRecipe.getSteps(), this);
        mRvRecipes.setAdapter(mRecipeIngredientStepAdapter);
    }

    @Override
    public void onIngradientListItemClick(Ingredient ingredient) {

    }

    @Override
    public void onStepListItemClick(Step step) {
        goToRecipeStepFragment(step);
    }

    @Override
    public void setPresenter(RecipeContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
