package com.firstapp.movie_app.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movies", indices = [Index(value = ["id"], unique = true)])
@Parcelize
data class Movies (
    @PrimaryKey(autoGenerate = true)
    val movieId: Int,
    val id: Int,
    val title: String,
    val release_date: String,
    val poster_path: String,
    val overview: String
):Parcelable