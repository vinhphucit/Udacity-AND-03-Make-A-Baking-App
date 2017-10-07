package com.phuctran.makeabakingapp.mvp.presenters;


import com.phuctran.makeabakingapp.data.remote.RemoteDataSource;
import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.domain.usecases.GetRecipesUseCase;
import com.phuctran.makeabakingapp.mvp.views.RecipesContract;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Admin on 9/22/2016.
 */

public class RecipesPresenter implements RecipesContract.Presenter {
    private final RecipesContract.View mView;
    private final GetRecipesUseCase mGetRecipesUseCase;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public RecipesPresenter(RecipesContract.View mView, GetRecipesUseCase getRecipesUseCase) {
        this.mView = mView;
        this.mGetRecipesUseCase = getRecipesUseCase;
        mView.setPresenter(this);
    }


    @Override
    public void doGetRecipes() {
        mView.showLoading();
        compositeDisposable.add(mGetRecipesUseCase.getRecipeList().subscribeWith(new DisposableSingleObserver<List<Recipe>>() {
            @Override
            public void onSuccess(@NonNull List<Recipe> recipes) {
                mView.hideLoading();
                mView.renderRecipes(recipes);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.hideLoading();
            }
        }));
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
