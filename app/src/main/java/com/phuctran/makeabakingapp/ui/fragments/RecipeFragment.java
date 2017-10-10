package com.phuctran.makeabakingapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.phuctran.makeabakingapp.Injection;
import com.phuctran.makeabakingapp.R;
import com.phuctran.makeabakingapp.domain.models.Recipe;
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
    public static final String ARGS_RECIPE_ID = "ARGS_RECIPE_ID";

    @BindView(R.id.recycler_view)
    RecyclerView mRvRecipes;

    private RecipeContract.Presenter mPresenter;
    private int mRecipeId;
    private RecipeIngredientStepAdapter mRecipeIngredientStepAdapter;

    public static RecipeFragment newInstance(int recipeId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS_RECIPE_ID, recipeId);

        RecipeFragment fragment = new RecipeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public RecipeFragment() {
        this.mPresenter = new RecipePresenter(this, Injection.provideGetRecipeUseCase());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARGS_RECIPE_ID))
            this.mRecipeId = getArguments().getInt(ARGS_RECIPE_ID);
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

        setupRecyclerView();
        mPresenter.getRecipe(mRecipeId);
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRvRecipes.setLayoutManager(layoutManager);
        mRvRecipes.setHasFixedSize(true);

    }


    @Override
    public void onStepListItemClick(int stepOrder) {
        goToRecipeStepFragment(stepOrder);
    }

    @Override
    public void setPresenter(RecipeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showRecipe(Recipe mRecipe) {
        setActionBarTitle(mRecipe.getName());
        mRecipeIngredientStepAdapter = new RecipeIngredientStepAdapter(getContext(), mRecipe, this);
        mRvRecipes.setAdapter(mRecipeIngredientStepAdapter);
    }
}
