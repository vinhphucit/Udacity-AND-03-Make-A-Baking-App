package com.phuctran.makeabakingapp.data.remote;

import com.phuctran.makeabakingapp.domain.models.Recipe;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by phuctran on 9/20/17.
 */

public interface RetrofitApi {
    @GET("baking.json")
    Single<List<Recipe>> getRecipes();
}
