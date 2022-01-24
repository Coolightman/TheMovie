package com.coolightman.themovie.data.database

import androidx.room.TypeConverter
import com.coolightman.themovie.data.database.dbModel.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun listCountriesToJson(countries: List<CountryDbModel>): String {
        return Gson().toJson(countries)
    }

    @TypeConverter
    fun jsonToListCountries(json: String): List<CountryDbModel> {
        val itemType = object : TypeToken<List<CountryDbModel>>() {}.type
        return Gson().fromJson(json, itemType)
    }

    @TypeConverter
    fun listFactsToJson(facts: List<FactDbModel>): String {
        return Gson().toJson(facts)
    }

    @TypeConverter
    fun jsonToListFacts(json: String): List<FactDbModel> {
        val itemType = object : TypeToken<List<FactDbModel>>() {}.type
        return Gson().fromJson(json, itemType)
    }

    @TypeConverter
    fun listFramesToJson(frames: List<FrameDbModel>): String {
        return Gson().toJson(frames)
    }

    @TypeConverter
    fun jsonToListFrames(json: String): List<FrameDbModel> {
        val itemType = object : TypeToken<List<FrameDbModel>>() {}.type
        return Gson().fromJson(json, itemType)
    }

    @TypeConverter
    fun listGenresToJson(genres: List<GenreDbModel>): String {
        return Gson().toJson(genres)
    }

    @TypeConverter
    fun jsonToListGenres(json: String): List<GenreDbModel> {
        val itemType = object : TypeToken<List<GenreDbModel>>() {}.type
        return Gson().fromJson(json, itemType)
    }

    @TypeConverter
    fun listVideosToJson(videos: List<VideoDbModel>): String {
        return Gson().toJson(videos)
    }

    @TypeConverter
    fun jsonToListVideos(json: String): List<VideoDbModel> {
        val itemType = object : TypeToken<List<VideoDbModel>>() {}.type
        return Gson().fromJson(json, itemType)
    }

    @TypeConverter
    fun listShortMoviesToJson(movies: List<ShortMovieDbModel>): String {
        return Gson().toJson(movies)
    }

    @TypeConverter
    fun jsonToListShortMovies(json: String): List<ShortMovieDbModel> {
        val itemType = object : TypeToken<List<ShortMovieDbModel>>() {}.type
        return Gson().fromJson(json, itemType)
    }

    @TypeConverter
    fun listReviewsToJson(facts: List<ReviewDbModel>): String {
        return Gson().toJson(facts)
    }

    @TypeConverter
    fun jsonToListReviews(json: String): List<ReviewDbModel> {
        val itemType = object : TypeToken<List<ReviewDbModel>>() {}.type
        return Gson().fromJson(json, itemType)
    }

    @TypeConverter
    fun listStringsToJson(strings: List<String>): String {
        return Gson().toJson(strings)
    }

    @TypeConverter
    fun jsonToListStrings(json: String): List<String> {
        val itemType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, itemType)
    }

    @TypeConverter
    fun listPersonFilmsToJson(personFilms: List<PersonFilmDbModel>): String {
        return Gson().toJson(personFilms)
    }

    @TypeConverter
    fun jsonToListPersonFilms(json: String): List<PersonFilmDbModel> {
        val itemType = object : TypeToken<List<PersonFilmDbModel>>() {}.type
        return Gson().fromJson(json, itemType)
    }

}