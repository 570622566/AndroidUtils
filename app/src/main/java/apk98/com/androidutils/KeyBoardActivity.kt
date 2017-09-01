package apk98.com.androidutils

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import apk98.com.androidutilslib.utils.KeyBoardUI

class KeyBoardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key_board)
        KeyBoardUI.buildKeyBoardUI(this)
    }
}
