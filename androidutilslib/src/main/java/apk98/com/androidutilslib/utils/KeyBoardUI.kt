package apk98.com.androidutilslib.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Rect
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import apk98.com.androidutilslib.widget.NullMenuEditText


/**
 * Created by laijian on 2017/8/31.
 * 软键盘上显示EditText
 */
class KeyBoardUI private constructor(private val activity: Activity) : KeyBoardHeightUtils.KeyBoardHeightListener {

    private lateinit var edtextView: EditText //activity的输入框
    private lateinit var mDialog: Dialog
    private lateinit var popuEdtext: NullMenuEditText //popu的输入框
    private var screenWeight = 0//屏幕宽度
    private var keyBoardHeightUtils: KeyBoardHeightUtils? = null

    init {
        getScreen()
        initDialog()
        keyBoardHeightUtils = KeyBoardHeightUtils(activity, this)
    }

    private fun getScreen() {
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        screenWeight = dm.widthPixels
    }

    private fun initDialog() {
        val inflater = activity.baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popuView = inflater.inflate(ResourcesUtils.getLayoutResources(activity, "popuwindow"), null)
        val populay: RelativeLayout = popuView.findViewById(ResourcesUtils.getIdResources(activity, "popu_lay"))
        popuEdtext = popuView.findViewById(ResourcesUtils.getIdResources(activity, "ed_text"))
        mDialog = Dialog(activity, ResourcesUtils.getStyleResources(activity, "dialog"))
        mDialog.setContentView(popuView)
        populay.setOnClickListener({
            KeyBoardUtils.closeKeybord(popuEdtext, activity)
            mDialog.dismiss()

        })

    }

    private fun checkViewVisiable(): Boolean {
        val localRect = Rect()
        return edtextView.getLocalVisibleRect(localRect)
    }

    private fun onEdChange() {

        var hintStr = ""
        var text = ""

        if (!TextUtils.isEmpty(edtextView.text)) {
            text = edtextView.text.toString()
        }
        if (!TextUtils.isEmpty(edtextView.hint)) {
            hintStr = edtextView.hint.toString()
        }
        popuEdtext.findFocus()
        popuEdtext.inputType = edtextView.inputType
        popuEdtext.hint = hintStr
        popuEdtext.setText(text)
        popuEdtext.setSelection(text.length)
        popuEdtext.maxEms = edtextView.maxEms
        popuEdtext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                edtextView.setText(s)
                edtextView.setSelection(s.length)
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
        popuEdtext.setOnEditorActionListener(edLis@ { _, actionId, _ ->
            if (actionId == 0) {
                KeyBoardUtils.closeKeybord(popuEdtext, activity)
                mDialog.dismiss()
                return@edLis true
            }
            false
        })
    }

    override fun showKeyBoard(height: Int, contentView: View?) {
        if (contentView != null) {
            val childView = contentView.findFocus()
            if (childView != null) {
                if (childView is EditText) {
                    edtextView = childView
                    if (!checkViewVisiable()) {
                        mDialog.show()
                        val dialogWindow = mDialog.window
                        val p = dialogWindow.attributes // 获取对话框当前的参数值
                        p.height = height // 高度设置为屏幕的0.6，根据实际情况调整
                        p.width = screenWeight // 宽度设置为屏幕的0.65，根据实际情况调整
                        dialogWindow.attributes = p
                        dialogWindow.setWindowAnimations(ResourcesUtils.getStyleResources(activity, "PopupAnimation"))
                        onEdChange()
                        KeyBoardUtils.openKeybord(edtextView!!, activity)
                    }
                }
            }
        }
    }

    override fun hideKeyBoard(height: Int, contentView: View?) {
        mDialog.dismiss()
    }


    companion object {
        fun buildKeyBoardUI(activity: Activity): KeyBoardUI = KeyBoardUI(activity)

    }

    fun removeKeyboardHeightListener() {
        keyBoardHeightUtils?.removeKeyboardHeightListener()
    }
}
