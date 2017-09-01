package apk98.com.androidutils

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import apk98.com.androidutilslib.utils.KeyBoardUI
import apk98.com.androidutilslib.utils.LogUtils
import apk98.com.androidutilslib.utils.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.namee.permissiongen.PermissionGen
import kr.co.namee.permissiongen.PermissionSuccess


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PermissionGen.needPermission(this, 200, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE))
        showlog_bt.setOnClickListener({
            var msg = ""
            var i = 0
            val num = 100
            while (i < num) {
                msg += i
                i++
            }
            LogUtils.saveSd(true).logSize(50 * 1024L).e(msg = msg)
        })

        keyboard_bt.setOnClickListener({
            val intent = Intent(this, KeyBoardActivity::class.java)
            startActivity(intent)
        })
    }


    @PermissionSuccess(requestCode = 200)
    fun openCamera() {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }


}
