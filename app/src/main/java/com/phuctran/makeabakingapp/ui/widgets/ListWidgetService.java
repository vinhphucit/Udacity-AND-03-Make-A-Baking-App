package com.phuctran.makeabakingapp.ui.widgets;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.phuctran.makeabakingapp.R;
import com.phuctran.makeabakingapp.domain.models.Ingredient;
import com.phuctran.makeabakingapp.domain.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import static com.phuctran.makeabakingapp.ui.widgets.IngredientWidgetProvider.mChoosedIngredients;


public class ListWidgetService extends RemoteViewsService {
    public static final String KEY_RECIPE = "KEY_RECIPE";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewFactory(getApplicationContext(), intent);
    }

    class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
        private final String TAG = ListRemoteViewFactory.class.getSimpleName();

        private final Context mContext;
        private final Intent intent;
        private List<Ingredient> ingredients = new ArrayList<>();

        public ListRemoteViewFactory(Context context, Intent intent) {
            Log.d(TAG, "constructor");
            this.mContext = context;
            this.intent = intent;
        }

        @Override
        public void onCreate() {
            Log.d(TAG, "onCreate");
            Recipe recipe = new Gson().fromJson(intent.getStringExtra(KEY_RECIPE), Recipe.class);
            ingredients = recipe.getIngredients();
        }

        @Override
        public void onDataSetChanged() {
            this.ingredients = mChoosedIngredients;
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public int getCount() {
            return ingredients.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            Ingredient ingredient = ingredients.get(position);

            RemoteViews views =
                    new RemoteViews(mContext.getPackageName(), R.layout.ingredient_item_widget);

            views.setTextViewText(R.id.tv_amount, String.valueOf(ingredient.getQuantity()) + " " + ingredient.getMeasure());
            views.setTextViewText(R.id.item_name, ingredient.getIngredient());

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}