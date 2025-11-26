import android.content.Context

// SessionManager.kt
object SessionManager {
    private const val PREF_NAME = "user_session"
    private const val KEY_KEEP_LOGGED_IN = "keep_logged_in"

    fun setKeepLoggedIn(context: Context, keepLoggedIn: Boolean) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_KEEP_LOGGED_IN, keepLoggedIn).apply()
    }

    fun getKeepLoggedIn(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_KEEP_LOGGED_IN, false)
    }
}