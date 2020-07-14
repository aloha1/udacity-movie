package com.yunwen.movieapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_table")
public class TmdbMovie implements Parcelable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private Integer id;
    @ColumnInfo(name = "posterPath")
    private String posterPath;
    @ColumnInfo(name = "adult")
    private boolean adult;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "releaseDate")
    private String releaseDate;
    //private List<Integer> genreIds;
    @ColumnInfo(name = "originalTitle")
    private String originalTitle;
    @ColumnInfo(name = "originalLanguage")
    private String originalLanguage;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "backdropPath")
    private String backdropPath;
    @ColumnInfo(name = "popularity")
    private Double popularity;
    @ColumnInfo(name = "voteCount")
    private Integer voteCount;
    @ColumnInfo(name = "video")
    private Boolean video;
    @ColumnInfo(name = "voteAverage")
    private Double voteAverage;
    @ColumnInfo(name = "isPopular")
    private Boolean isPopular;
    @ColumnInfo(name = "isTopRated")
    private Boolean isTopRated;
    @ColumnInfo(name = "isFavorite")
    private Boolean isFavorite;

    public TmdbMovie(@NonNull Integer id, String posterPath, boolean adult, String overview,
                     String releaseDate, String originalTitle,
                     String originalLanguage, String title, String backdropPath, Double popularity,
                     Integer voteCount, Boolean video, Double voteAverage, Boolean isPopular,
                     Boolean isTopRated, Boolean isFavorite) {
        this.id = id;
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
        this.isPopular = isPopular;
        this.isTopRated = isTopRated;
        this.isFavorite = isFavorite;
    }

    @NonNull
    public Integer getId() {
        return this.id;
    }

    public String getPosterPath() {
        return this.posterPath;
    }

    public Boolean getAdult(){return this.adult;}

    public String getOverview(){return this.overview;}

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public String getOriginalTitle() {
        return this.originalTitle;
    }

    public String getOriginalLanguage(){return  this.originalLanguage;}

    public String getTitle() {
        return this.title;
    }

    public String getBackdropPath() {
        return this.backdropPath;
    }

    public Double getPopularity() {
        return this.popularity;
    }

    public Integer getVoteCount() {
        return this.voteCount;
    }

    public Boolean getVideo() {
        return this.video;
    }

    public Double getVoteAverage() {
        return this.voteAverage;
    }

    public Boolean getIsPopular() {
        return this.isPopular;
    }

    public Boolean getIsTopRated() {
        return this.isTopRated;
    }

    public Boolean getIsFavorite() {
        return this.isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeValue(this.id==null?0:this.id);
        dest.writeValue(this.id);
        dest.writeString(this.posterPath);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.originalTitle);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.title);
        dest.writeString(this.backdropPath);
        dest.writeValue(this.popularity);
        dest.writeValue(this.voteCount);
        dest.writeValue(this.video);
        dest.writeValue(this.voteAverage);
        dest.writeByte((byte) (this.isPopular ? 1 : 0));
        dest.writeByte((byte) (this.isTopRated ? 1 : 0));
        dest.writeByte((byte) (this.isFavorite ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected TmdbMovie(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.posterPath = in.readString();
        this.adult = in.readByte() != 0;
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.originalTitle = in.readString();
        this.originalLanguage = in.readString();
        this.title = in.readString();
        this.backdropPath = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.voteCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.isPopular =  in.readByte() != 0;
        this.isTopRated = in.readByte() != 0;
        this.isFavorite = in.readByte() != 0;
    }

    public static final Parcelable.Creator<TmdbMovie> CREATOR = new Parcelable.Creator<TmdbMovie>() {
        @Override
        public TmdbMovie createFromParcel(Parcel source) {
            return new TmdbMovie(source);
        }

        @Override
        public TmdbMovie[] newArray(int size) {
            return new TmdbMovie[size];
        }
    };

}
