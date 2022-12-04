package com.rasulov.database.all_categories_dao.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre_settings_table")
class GenreSettingsEntity(
    @ColumnInfo(name = "genre_id") @PrimaryKey val genreId: Int,
    @ColumnInfo(name = "sort_by") val sortBy: Int
)