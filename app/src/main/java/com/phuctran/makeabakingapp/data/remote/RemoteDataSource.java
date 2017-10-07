package com.phuctran.makeabakingapp.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.phuctran.makeabakingapp.BuildConfig;
import com.phuctran.makeabakingapp.data.BakingDataSource;
import com.phuctran.makeabakingapp.domain.models.Ingredient;
import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.domain.models.Step;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by phuctran on 9/11/17.
 */

public class RemoteDataSource implements BakingDataSource {

    private static RemoteDataSource INSTANCE;
    private final RetrofitApi mApi;

    public RemoteDataSource() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(60, TimeUnit.SECONDS).
                readTimeout(60, TimeUnit.SECONDS).
                addInterceptor(logging).build();

        Gson customGsonInstance = new GsonBuilder()
                .create();

        Retrofit retrofitApiAdapter = new Retrofit.Builder()
                .baseUrl(BuildConfig.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(customGsonInstance))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        mApi = retrofitApiAdapter.create(RetrofitApi.class);
    }

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Single<List<Recipe>> getRecipes() {
        return mApi.getRecipes();
    }

    @Override
    public Single<Recipe> getRecipe(int recipeId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Single<List<Ingredient>> getRecipeIngredients(int recipeId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Single<List<Step>> getRecipeSteps(int recipeId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveRecipes(List<Recipe> recipeList) {
        throw new UnsupportedOperationException();
    }

}
