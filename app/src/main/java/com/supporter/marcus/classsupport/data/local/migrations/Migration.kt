package com.supporter.marcus.classsupport.data.local.migrations

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration


val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE `donations` (`id` TEXT NOT NULL, "
                + "`name` TEXT NOT NULL, " + " `date` INTEGER NOT NULL, " + " `amount` FLOAT NOT NULL, PRIMARY KEY(`id`))")
    }
}
val MIGRATION_2_3: Migration = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE `donations` (`id` INTEGER NOT NULL, "
                + "`name` TEXT NOT NULL, " + " `date` DATE NOT NULL, " + " `amount` FLOAT NOT NULL, PRIMARY KEY(`id`))")
    }
}