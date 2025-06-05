package at.aau.appdev.currencyconversionapp.domain

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Utility object for logging currency conversion operations to a local file.
 *
 * Provides functionality to:
 * - log conversion events with timestamps
 * - read and clear the log
 * - share the log file via external apps
 */
object ConversionLogger {

    private const val FILE_NAME = "conversion_log.txt"

    /**
     * Appends a timestamped conversion entry to the log file.
     *
     * @param context Context used to access internal storage.
     * @param text The conversion message to log (e.g., "50 EUR → 53.20 USD").
     */
    fun log(context: Context, text: String) {
        val entry = formatLogEntry(text)
        appendToFile(context, FILE_NAME, entry)
    }

    /**
     * Reads the entire log file and returns its contents as a list of strings.
     *
     * @param context Context used to read from internal storage.
     * @return A list of log entries, or an empty list if the file does not exist.
     */
    fun read(context: Context): List<String> {
        return try {
            readFileLines(context, FILE_NAME)
        } catch (e: FileNotFoundException) {
            emptyList()
        }
    }

    /**
     * Clears all contents of the log file.
     *
     * @param context Context used to overwrite the file.
     */
    fun clear(context: Context) {
        overwriteFile(context, FILE_NAME, "")
    }

    /**
     * Returns an [Intent] for sharing the log file via other apps.
     *
     * @param context Context used to get file URI and create the intent.
     * @return A configured [Intent], or `null` if the file does not exist.
     */
    fun share(context: Context): Intent? {
        val file = getLogFile(context)

        if (!file.exists()) return null

        val uri = getFileUri(context, file)
        return createShareIntent(uri)
    }

    /**
     * Returns the current system time as a formatted timestamp string.
     */
    private fun timestamp(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
    }

    /**
     * Formats a log entry with a timestamp prefix and newline suffix.
     *
     * @param text The text to include in the log entry.
     * @return A string formatted as: "YYYY-MM-DD HH:mm:ss - text\n"
     */
    private fun formatLogEntry(text: String): String {
        return "${timestamp()} - $text\n"
    }

    /**
     * Appends content to a file in internal storage.
     *
     * @param context Context used to access file output stream.
     * @param fileName The name of the file to append to.
     * @param content The content to write.
     */
    private fun appendToFile(context: Context, fileName: String, content: String) {
        context.openFileOutput(fileName, Context.MODE_APPEND).use {
            it.write(content.toByteArray())
        }
    }

    /**
     * Overwrites a file with new content.
     *
     * @param context Context used to access file output stream.
     * @param fileName The name of the file to overwrite.
     * @param content The content to write.
     */
    private fun overwriteFile(context: Context, fileName: String, content: String) {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(content.toByteArray())
        }
    }

    /**
     * Reads all lines from a file and returns them as a list of strings.
     *
     * @param context Context used to access file input stream.
     * @param fileName The name of the file to read.
     * @return List of strings representing each line of the file.
     */
    private fun readFileLines(context: Context, fileName: String): List<String> {
        return context.openFileInput(fileName).bufferedReader().readLines()
    }

    /**
     * Returns a [File] object representing the log file in internal storage.
     *
     * @param context Context used to get internal file directory.
     * @return A [File] pointing to the log file.
     */
    private fun getLogFile(context: Context): File {
        return File(context.filesDir, FILE_NAME)
    }

    /**
     * Returns a content URI for a file using [FileProvider].
     *
     * @param context Context used to resolve the authority.
     * @param file The file for which to create a content URI.
     * @return A content [Uri] pointing to the file.
     */
    private fun getFileUri(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    /**
     * Creates an intent to share a file via compatible apps (e.g., Messages, Email).
     *
     * @param uri The content URI of the file to be shared.
     * @return A configured [Intent] for sharing the file.
     */
    private fun createShareIntent(uri: Uri): Intent {
        return Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }
}