package apk98.com.androidutilslib.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText


/**
 * Created by laijian on 2017/8/31.
 */
class NullMenuEditText(context: Context, attrs: AttributeSet) : EditText(context, attrs) {

    internal fun canPaste(): Boolean {
        return false
    }

    internal fun canCut(): Boolean {
        return false
    }

    internal fun canCopy(): Boolean {
        return false
    }

    internal fun canSelectAllText(): Boolean {
        return false
    }

    internal fun canSelectText(): Boolean {
        return false
    }

    internal fun textCanBeSelected(): Boolean {
        return false
    }

    init {
        isLongClickable = false
        setTextIsSelectable(false)
        customSelectionActionModeCallback = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode) {

            }
        }

    }

    override fun onTextContextMenuItem(id: Int): Boolean {
        return true
    }
}
