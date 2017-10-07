package com.phuctran.makeabakingapp.ui.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.phuctran.makeabakingapp.BakingPreferences;
import com.phuctran.makeabakingapp.R;
import com.phuctran.makeabakingapp.data.local.DatabaseContract;
import com.phuctran.makeabakingapp.data.local.LocalDataSource;
import com.phuctran.makeabakingapp.data.local.RecipesDatabase;
import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.utils.RxUtils;

import org.parceler.Parcels;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;
import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidgetProvider extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int[] appWidgetIds) {
        int recipeId = BakingPreferences.getInstance().getWidgetRecipeId();
        if (recipeId == -1) return;

        LocalDataSource.getInstance().getRecipe(recipeId).compose(RxUtils.applySchedulersSingle())
                .subscribeWith(new DisposableSingleObserver<Recipe>() {
                    @Override
                    public void onSuccess(@NonNull Recipe recipe) {
                        RemoteViews views = getListRemoteView(context, recipe);
                        views.setTextViewText(R.id.appwidget_text, recipe.getName());
                        appWidgetManager.updateAppWidget(appWidgetIds, views);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.e(e);
                    }
                });


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        updateAppWidget(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static RemoteViews getListRemoteView(Context context, Recipe recipe) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_app_widget);
        Intent intent = new Intent(context, ListWidgetService.class);

        intent.putExtra(ListWidgetService.KEY_RECIPE, new Gson().toJson(recipe));


        views.setRemoteAdapter(R.id.appwidget_list, intent);

        return views;
    }
}

