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

}