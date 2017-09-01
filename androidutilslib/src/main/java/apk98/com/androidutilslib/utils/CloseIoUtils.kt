package apk98.com.androidutilslib.utils

import java.io.Closeable
import java.io.IOException

/**
 * Created by laijian on 2017/8/10.
 * 关闭io 工具类
 */
object CloseIoUtils {

    /**
     * 关闭IO

     * @param closeables closeables
     */
    fun closeIO(vararg closeables: Closeable) {
        if (closeables == null) return
        closeables
                .filterNotNull()
                .forEach {
                    try {
                        it!!.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
    }

}