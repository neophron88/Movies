package com.rasulov.database.all_categories_dao.tuples

import androidx.room.Embedded
import androidx.room.Relation
import com.rasulov.database.all_categories_dao.models.GenreEntity
import com.rasulov.database.all_categories_dao.models.GenreSettingsEntity

class GenreWithSettingsTuple(
    @Embedded val genre: GenreEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "genre_id"
    )
    val settings: GenreSettingsEntity?
)