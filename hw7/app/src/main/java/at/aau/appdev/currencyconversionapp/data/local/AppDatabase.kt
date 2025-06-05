package at.aau.appdev.currencyconversionapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import at.aau.appdev.currencyconversionapp.data.model.CurrencyRate

/**
 * Defines the Room database with CurrencyRate as the only entity.
 * Version 1 = initial schema version, exportSchema = false disables schema export to a folder.
 */
@Database(entities = [CurrencyRate::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Abstract method to access the DAO (Data Access Object) for currency rates.
     */
    abstract fun currencyRateDao(): CurrencyRateDAO

    companion object {
        // Volatile ensures that changes to this variable are visible to all threads.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Returns the singleton instance of the database.
         * If it doesn't exist yet, it will be created using Room's databaseBuilder.
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = createDatabase(context)
                INSTANCE = instance
                instance
            }
        }

        /**
         * Creates a new Room database instance.
         *
         * @param context The application context used to access the filesystem.
         * @return A new instance of [AppDatabase].
         */
        private fun createDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "currency_converter_db"
            ).build()
        }
    }

}