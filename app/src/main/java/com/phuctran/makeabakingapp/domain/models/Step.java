package com.phuctran.makeabakingapp.domain.models;

import android.arch.persistence.room.ColumnInfo;
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
@Entity(tableName = DatabaseContract.STEP_TABLE_NAME,
        foreignKeys =
        @ForeignKey
                (entity = Recipe.class,
                        parentColumns = "id",
                        childColumns = "recipeId",
                        onDelete = CASCADE),
        indices = @Index("recipeId"))
@org.parceler.Parcel(org.parceler.Parcel.Serialization.BEAN)
public class Step {
    private int recipeId;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    //This is the database id
    private int _id;

    @SerializedName("id")
    //The webservice so-called "id"
    private Integer index;

    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("videoURL")
    @Expose
    private String videoURL;

    @SerializedName("thumbnailURL")
    @Expose
    private String thumbnailURL;


    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }


    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
//rtmp://live-api-a.facebook.com:80/rtmp/
//131799267565397?ds=1&s_l=1&a=ATje7huziL6m2B-e