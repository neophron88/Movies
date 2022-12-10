package com.rasulov.database.all_genres_dao.tuples

import androidx.room.Embedded
import androidx.room.Relation
import com.rasulov.database.all_genres_dao.models.GenreEntity
import com.rasulov.database.all_genres_dao.models.GenreSettingsEntity

class GenreWithSettingsTuple(
    @Embedded val genre: GenreEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "genre_id"
    )
    val settings: GenreSettingsEntity?
)