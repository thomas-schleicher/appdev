package at.aau.appdev.currencyconversionapp

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

object ConversionLogger {

    private const val FILE_NAME = "conversion_log.txt"

    fun log(context: Context, text: String) {
        val entry = "${timestamp()} - $text\n"
        context.openFileOutput(FILE_NAME, Context.MODE_APPEND).use {
            it.write(entry.toByteArray())
        }
    }

    fun read(context: Context): List<String> {
        return try {
            context.openFileInput(FILE_NAME).bufferedReader().readLines()
        } catch (e: FileNotFoundException) {
            emptyList()
        }
    }

    fun clear(context: Context) {
        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
            it.write("".toByteArray())
        }
    }

    fun share(context: Context): Intent? {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) return null

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        return Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }

    private fun timestamp(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
    }
}