package apk98.com.androidutilslib.utils

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


/**
 * Created by laijian on 2017/8/31.
 * 软键盘开关工具类
 */
object KeyBoardUtils {

    /**
     * 打开软键盘
     *
     * @param mEditText
     * @param mContext
     */
    fun openKeybord(mEditText: EditText, mContext: Context) {
        val imm = mContext
                .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText
     * @param mContext
     */
    fun closeKeybord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }


    /**
     * des:隐藏软键盘,这种方式参数为activity
     *
     * @param activity
     */
    fun hideInputForce(activity: Activity?) {
        if (activity == null || activity.currentFocus == null)
            return
        (activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(activity.currentFocus!!
                        .windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 打开键盘
     */
    fun showInput(context: Context, view: View) {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm != null) {
            view.requestFocus()
            imm!!.showSoftInput(view, 0)
        }
    }


}