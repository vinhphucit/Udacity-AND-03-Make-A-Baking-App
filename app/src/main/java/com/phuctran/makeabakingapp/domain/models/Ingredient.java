package com.phuctran.makeabakingapp.domain.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.phuctran.makeabakingapp.data.local.DatabaseContract;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by phuctran on 9/20/17.
 */
@Entity(tableName = DatabaseContract.INGREDIENT_TABLE_NAME,
        foreignKeys =
        @ForeignKey
                (entity = Recipe.class,
                        parentColumns = "id",
                        childColumns = "recipeId",
                        onDelete = CASCADE),
        indices = @Index("recipeId"))
@org.parceler.Parcel(org.parceler.Parcel.Serialization.BEAN)
public class Ingredient {
    private int recipeId;

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("quantity")
    @Expose
    private Double quantity;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("ingredient")
    @Expose
    private String ingredient;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
