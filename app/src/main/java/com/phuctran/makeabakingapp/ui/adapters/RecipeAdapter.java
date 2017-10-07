package com.phuctran.makeabakingapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phuctran.makeabakingapp.R;
import com.phuctran.makeabakingapp.domain.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by phuctran on 9/20/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> mRecipeItems;
    final private ListItemClickListener mOnClickListener;
    private Context mContext;


    public interface ListItemClickListener {
        void onListItemClick(Recipe movieModel);
    }

    public RecipeAdapter(Context context, List<Recipe> mRecipeItems, ListItemClickListener listener) {
        this.mContext = context;
        this.mRecipeItems = mRecipeItems;
        this.mOnClickListener = listener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.item_list_recipe;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediatelly = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediatelly);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mRecipeItems.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_title)
        TextView recipe_title;
        @BindView(R.id.recipe_image)
        ImageView recipe_image;
        @BindView(R.id.recipe_servings)
        TextView recipe_servings;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            final Recipe recipe = mRecipeItems.get(position);

            recipe_servings.setText(recipe.getServings() + "");
            recipe_title.setText(recipe.getName());
            if (recipe.getImage().isEmpty()) {
                recipe_image.setImageResource(R.drawable.placeholder);
            }else{
                Picasso.with(mContext).load(recipe.getImage()).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(recipe_image);
            }


        }

        @Override
        public void onClick(View view) {
            int clickPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(mRecipeItems.get(clickPosition));
        }
    }
}
