package apk98.com.androidutilslib.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout


/**
 * Created by laijian on 2017/8/31.
 * 监听软键盘高度
 */
class KeyBoardHeightUtils constructor(activity: Activity, private val keyBoardHeightListener: KeyBoardHeightListener) {
    private val mChildOfContent: View//activity 的布局View
    private var usableHeightPrevious: Int = 0//activity的View的可视高度
    private val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener { possiblyResizeChildOfContent() }

    init {
        val content = activity.findViewById<View>(android.R.id.content) as FrameLayout
        mChildOfContent = content.getChildAt(0)
        mChildOfContent.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
        content.setOnClickListener({
            KeyBoardUtils.hideInputForce(activity)
        })
    }

    private fun possiblyResizeChildOfContent() {
        val usableHeightNow = computeUsableHeight()
        if (usableHeightNow != usableHeightPrevious) {
            val usableHeightSansKeyboard = mChildOfContent.rootView.height
            val heightDifference = usableHeightSansKeyboard - usableHeightNow
            if (heightDifference > usableHeightSansKeyboard / 4) {
                // keyboard probably just became visible
                keyBoardHeightListener.showKeyBoard(usableHeightSansKeyboard - heightDifference, mChildOfContent)
            } else {
                // keyboard probably just became hidden
                keyBoardHeightListener.hideKeyBoard(usableHeightSansKeyboard, mChildOfContent)

            }
            mChildOfContent.requestLayout()
            usableHeightPrevious = usableHeightNow
        }
    }

    private fun computeUsableHeight(): Int {
        val r = Rect()
        mChildOfContent.getWindowVisibleDisplayFrame(r)
        return r.bottom - r.top
    }

    interface KeyBoardHeightListener {
        fun showKeyBoard(height: Int, contentView: View?)
        fun hideKeyBoard(height: Int, contentView: View?)

    }

    @SuppressLint("ObsoleteSdkInt")
    fun removeKeyboardHeightListener() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            mChildOfContent.viewTreeObserver.removeGlobalOnLayoutListener(globalLayoutListener)
        } else {
            mChildOfContent.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
        }
    }


}
