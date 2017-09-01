package apk98.com.androidutilslib.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

/**
 * Created by laijian on 2017/8/14.
 * SharedPreferences 数据保存
 */
object SharedPreferencesUtil {

    fun putBase(context: Context, tableName: String = "apk98_table", keyName: String, value: Any): Boolean {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(tableName, Activity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        when (value) {
            is Int -> editor.putInt(keyName, value)
            is Boolean -> editor.putBoolean(keyName, value)
            is Float -> editor.putFloat(keyName, value)
            is String -> editor.putString(keyName, value)
            is Long -> editor.putLong(keyName, value)
            else -> throw IllegalArgumentException("SharedPreferences can,t be save this type")
        }
        return editor.commit()
    }

    fun putStringSet(context: Context, tableName: String = "apk98_table", keyName: String, value: Set<String>): Boolean {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(tableName, Activity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putStringSet(keyName, value)
        return editor.commit()
    }

    fun getInt(context: Context, tableName: String = "apk98_table", keyName: String, defaultValue: Int = -1): Int {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(tableName, Activity.MODE_PRIVATE)
        return sharedPreferences.getInt(keyName, defaultValue)
    }

    fun getBoolean(context: Context, tableName: String = "apk98_table", keyName: String, defaultValue: Boolean = false): Boolean {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(tableName, Activity.MODE_PRIVATE)
        return sharedPreferences.getBoolean(keyName, defaultValue)
    }

    fun getFloat(context: Context, tableName: String = "apk98_table", keyName: String, defaultValue: Float = -1F): Float {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(tableName, Activity.MODE_PRIVATE)
        return sharedPreferences.getFloat(keyName, defaultValue)
    }

    fun getString(context: Context, tableName: String = "apk98_table", keyName: String, defaultValue: String? = null): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(tableName, Activity.MODE_PRIVATE)
        return sharedPreferences.getString(keyName, defaultValue)
    }

    fun getLong(context: Context, tableName: String = "apk98_table", keyName: String, defaultValue: Long = -1L): Long {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(tableName, Activity.MODE_PRIVATE)
        return sharedPreferences.getLong(keyName, defaultValue)
    }

    fun getStringSet(context: Context, tableName: String = "apk98_table", keyName: String, defaultValue: Set<String>? = null): Set<String>? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(tableName, Activity.MODE_PRIVATE)
        return sharedPreferences.getStringSet(keyName, defaultValue)
    }

    fun removeKeyName(context: Context, tableName: String = "apk98_table", keyName: String): Boolean {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(tableName, Activity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove(keyName)
        return editor.commit()
    }

    fun clearTableName(context: Context, tableName: String = "apk98_table"): Boolean {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(tableName, Activity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        return editor.commit()
    }

}