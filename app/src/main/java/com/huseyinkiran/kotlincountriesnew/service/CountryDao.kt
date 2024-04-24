package com.huseyinkiran.kotlincountriesnew.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.huseyinkiran.kotlincountriesnew.model.Country

@Dao
interface CountryDao {

    // Data Access Object

    @Insert
    suspend fun insertAll(vararg countries : Country) : List<Long>

    // Insert -> INSERT INTO
    // suspend -> coroutine, pause & resume
    // vararg -> multiple country objects
    // List<Long> -> primary keys

    @Query("SELECT * FROM country")
    suspend fun getAllCountries() : List<Country>

    @Query("select * from country where uuid = :countryId")
    suspend fun getCountry(countryId : Int) : Country

    @Query("delete from country")
    suspend fun deleteAllCountries()

}