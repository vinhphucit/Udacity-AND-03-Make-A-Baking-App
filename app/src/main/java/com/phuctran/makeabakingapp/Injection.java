package com.phuctran.makeabakingapp;

import com.phuctran.makeabakingapp.data.BakingRepository;
import com.phuctran.makeabakingapp.data.local.LocalDataSource;
import com.phuctran.makeabakingapp.data.remote.RemoteDataSource;
import com.phuctran.makeabakingapp.domain.usecases.GetRecipeUseCase;
import com.phuctran.makeabakingapp.domain.usecases.GetRecipesUseCase;

/**
 * Created by phuctran on 10/3/17.
 */

public class Injection {
    public static BakingRepository provideRxRepository() {
        return new BakingRepository(LocalDataSource.getInstance(),
                RemoteDataSource.getInstance());
    }

    public static GetRecipesUseCase provideGetRecipesUseCase() {
        return new GetRecipesUseCase(provideRxRepository());
    }

    public static GetRecipeUseCase provideGetRecipeUseCase() {
        return new GetRecipeUseCase(provideRxRepository());
    }
}
