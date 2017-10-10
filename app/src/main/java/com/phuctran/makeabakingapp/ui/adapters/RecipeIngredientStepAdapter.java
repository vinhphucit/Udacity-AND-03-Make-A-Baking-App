package com.phuctran.makeabakingapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phuctran.makeabakingapp.R;
import com.phuctran.makeabakingapp.domain.models.Ingredient;
import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.phuctran.makeabakingapp.domain.models.Step;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by phuctran on 9/20/17.
 */

public class RecipeIngredientStepAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private Recipe mRecipe;
    final private RecipeIngredientStepAdapter.ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onStepListItemClick(int stepOrder);
    }

    public RecipeIngredientStepAdapter(Context context, Recipe recipe, RecipeIngredientStepAdapter.ListItemClickListener listener) {
        this.mContext = context;
        this.mRecipe = recipe;
        this.mOnClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            Context context = parent.getContext();
            int layoutIdForListItem = R.layout.item_list_ingredient;
            LayoutInflater inflater = LayoutInflater.from(context);
            boolean shouldAttachToParentImmediatelly = false;
            View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediatelly);
            IngradientViewHolder viewHolder = new IngradientViewHolder(view);
            return viewHolder;
        } else {
            Context context = parent.getContext();
            int layoutIdForListItem = R.layout.item_list_step;
            LayoutInflater inflater = LayoutInflater.from(context);
            boolean shouldAttachToParentImmediatelly = false;
            View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediatelly);
            StepViewHolder viewHolder = new StepViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < mRecipe.getIngredients().size()) {
            ((IngradientViewHolder) holder).bind(position);
        } else {
            ((StepViewHolder) holder).bind(position);
        }

    }

    @Override
    public int getItemCount() {
        return mRecipe.getIngredients().size() + mRecipe.getSteps().size();
    }


    @Override
    public int getItemViewType(int position) {
        if (position < mRecipe.getIngredients().size()) {
            return 0;
        }
        return 1;
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_step_image)
        ImageView recipe_step_image;
        @BindView(R.id.recipe_step_title)
        TextView recipe_step_title;

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            final Step recipe = mRecipe.getSteps().get(position - mRecipe.getIngredients().size());

            if (recipe.getThumbnailURL().isEmpty()) {
                recipe_step_image.setImageResource(R.drawable.placeholder);
            } else {
                Picasso.with(mContext).load(recipe.getThumbnailURL()).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(recipe_step_image);
            }

            recipe_step_title.setText(recipe.getShortDescription());
        }

        @Override
        public void onClick(View view) {
            int clickPosition = getAdapterPosition();
            mOnClickListener.onStepListItemClick(clickPosition - mRecipe.getIngredients().size());
        }
    }

    class IngradientViewHolder extends RecyclerView.ViewHolder  {
        @BindView(R.id.recipe_ingredient_name)
        TextView recipe_ingredient_name;
        @BindView(R.id.recipe_ingredient_quantity)
        TextView recipe_ingredient_quantity;

        public IngradientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {
            final Ingredient recipe = mRecipe.getIngredients().get(position);

            recipe_ingredient_name.setText(recipe.getIngredient());
            recipe_ingredient_quantity.setText(String.valueOf(recipe.getQuantity()));
        }

    }
}
