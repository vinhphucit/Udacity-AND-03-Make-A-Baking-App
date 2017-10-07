package com.phuctran.makeabakingapp.ui.fragments;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.phuctran.makeabakingapp.Injection;
import com.phuctran.makeabakingapp.R;
import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.mvp.presenters.RecipesPresenter;
import com.phuctran.makeabakingapp.mvp.views.RecipesContract;
import com.phuctran.makeabakingapp.ui.activities.RecipeDetailActivity;
import com.phuctran.makeabakingapp.ui.adapters.RecipeAdapter;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;


/**
 * Created by phuctran on 9/20/17.
 */

public class RecipesFragment extends BaseFragment implements RecipesContract.View, RecipeAdapter.ListItemClickListener {
    private static final String TAG = RecipesFragment.class.getSimpleName();

    private RecipesContract.Presenter mPresenter;

    public static RecipesFragment newInstance() {
        return new RecipesFragment();
    }

    @BindView(R.id.recycler_view)
    RecyclerView mRvRecipes;
    private int mSpanCount = 1;
    private RecipeAdapter mMovieAdapter;
    private List<Recipe> mRecipes;

    public RecipesFragment() {
        this.mPresenter = new RecipesPresenter(this, Injection.provideGetRecipesUseCase());
    }

    @Override
    protected String getSubclassName() {
        return TAG;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recipes;
    }

    @Override
    protected void updateFollowingViewBinding() {
        setupRecyclerView();
        setActionBarTitle(R.string.baking_time);
        mPresenter.doGetRecipes();
    }

    @Override
    public void setPresenter(RecipesContract.Presenter presenter) {
        mPresenter = presenter;
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
    public void renderRecipes(List<Recipe> recipes) {
        this.mRecipes = recipes;
        mMovieAdapter = new RecipeAdapter(getContext(), mRecipes,
                this);
        mRvRecipes.setAdapter(mMovieAdapter);
    }

    private void setupRecyclerView() {
        mSpanCount = getContext().getResources().getInteger(R.integer.span);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), mSpanCount);
        mRvRecipes.setLayoutManager(layoutManager);
        mRvRecipes.setHasFixedSize(true);
    }

    @Override
    public void onListItemClick(Recipe movieModel) {
        Intent startChildActivityIntent = new Intent(getContext(), RecipeDetailActivity.class);
        startChildActivityIntent.putExtra(RecipeDetailActivity.ARGS_RECIPE_DETAIL, Parcels.wrap(movieModel));
        startActivity(startChildActivityIntent);
    }
}
