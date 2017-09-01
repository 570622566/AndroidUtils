package apk98.com.androidutilslib.utils

import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * Created by laijian on 2017/8/4.
 * 日志工具类
 */
object LogUtils {

    private val TOP_LINE = "" +
            "\n^^^^^^^^^^^^^less code,less bug^^^^^^^^^^^^^^\n" +
            "                   _ooOoo_\n" +
            "                  o8888888o\n" +
            "                  88\" . \"88\n" +
            "                  (| -_- |)\n" +
            "                  O\\  =  /O\n" +
            "               ____/`---'\\____\n" +
            "             .'  \\\\|     |//  `.\n" +
            "            /  \\\\|||  :  |||//  \\\n" +
            "           /  _||||| -:- |||||-  \\\n" +
            "           |   | \\\\\\  -  /// |   |\n" +
            "           | \\_|  ''\\---/''  |   |\n" +
            "           \\  .-\\__  `-`  ___/-. /\n" +
            "         ___`. .'  /--.--\\  `. . __\n" +
            "      .\"\" '<  `.___\\_<|>_/___.'  >'\"\".\n" +
            "     | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |\n" +
            "     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /\n" +
            "======`-.____`-.___\\_____/___.-`____.-'======\n" +
            "                   `=---='\n" +
            "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n" +
            "            佛祖保佑       永无BUG\n" +
            "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n"

    private val TOP_BORDER = "╔══════════════════════════════════════════════════════════════════════════════════════════════════════════"
    private val LEFT_BORDER = "║ "
    private val BOTTOM_BORDER = "╚══════════════════════════════════════════════════════════════════════════════════════════════════════════"
    private var debug: Boolean = true//是否打印log
    private var savesd: Boolean = false//是否存log到sd卡
    private val CHUNK_SIZE = 106 //设置字节数
    private var logDir = ""//设置文件存储目录
    private var logSize = 2 * 1024 * 1024L//设置log文件大小 k
    private val execu: ExecutorService = Executors.newFixedThreadPool(1)

    init {
        Log.e("www.hotapk.cn_log", TOP_LINE)
        initLogFile()
    }


    fun v(tag: String = "www.hotapk.cn_log", msg: String) = debug.debugLog(tag, msg, Log.VERBOSE)
    fun d(tag: String = "www.hotapk.cn_log", msg: String) = debug.debugLog(tag, msg, Log.DEBUG)
    fun i(tag: String = "www.hotapk.cn_log", msg: String) = debug.debugLog(tag, msg, Log.INFO)
    fun w(tag: String = "www.hotapk.cn_log", msg: String) = debug.debugLog(tag, msg, Log.WARN)
    fun e(tag: String = "www.hotapk.cn_log", msg: String) = debug.debugLog(tag, msg, Log.ERROR)


    private fun targetStackTraceMSg(): String {
        val targetStackTraceElement = getTargetStackTraceElement()
        if (targetStackTraceElement != null) {
            return "at ${targetStackTraceElement.className}.${targetStackTraceElement.methodName}(${targetStackTraceElement.fileName}:${targetStackTraceElement.lineNumber})"
        } else {
            return ""
        }
    }

    private fun getTargetStackTraceElement(): StackTraceElement? {
        var targetStackTrace: StackTraceElement? = null
        var shouldTrace = false
        val stackTrace = Thread.currentThread().stackTrace
        for (stackTraceElement in stackTrace) {
            val isLogMethod = stackTraceElement.className == LogUtils::class.java.name
            if (shouldTrace && !isLogMethod) {
                targetStackTrace = stackTraceElement
                break
            }
            shouldTrace = isLogMethod
        }
        return targetStackTrace
    }


    private fun initLogFile() {
        logDir = "${FileUtils.getRootDir()}/hotapk.cn"
        FileUtils.mkDir(logDir)
    }

    private fun Boolean.debugLog(tag: String, msg: String, type: Int) {
        if (!this) {
            return
        }
        val newMsg = msgFormat(msg)

        savesd.saveToSd("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date())}\n${targetStackTraceMSg()}", msg)
        when (type) {
            Log.VERBOSE -> Log.v(tag, newMsg)
            Log.DEBUG -> Log.d(tag, newMsg)
            Log.INFO -> Log.i(tag, newMsg)
            Log.WARN -> Log.w(tag, newMsg)
            Log.ERROR -> Log.e(tag, newMsg)
        }

    }

    private fun msgFormat(msg: String): String {
        val bytes: ByteArray = msg.toByteArray()
        val length = bytes.size
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        var newMsg = "$TOP_BORDER\n$LEFT_BORDER\t${sdf.format(Date())}\n$LEFT_BORDER\t${targetStackTraceMSg()}"
        if (length > CHUNK_SIZE) {
            var i = 0
            while (i < length) {
                val count = Math.min(length - i, CHUNK_SIZE)
                val tempStr = String(bytes, i, count)
                newMsg += "\n$LEFT_BORDER\t$tempStr"
                i += CHUNK_SIZE
            }
        } else {
            newMsg += "\n$LEFT_BORDER\t$msg"
        }
        newMsg += "\n$BOTTOM_BORDER"
        return newMsg

    }

    private fun Boolean.saveToSd(tag: String, msg: String) {
        if (!this) {
            return
        }
        execu.submit({
            val data = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
            var files = FileUtils.sortByTime(File(logDir))?.filter { it -> it.name.contains(data) }
            var filepath: String
            if (files != null && files.isNotEmpty()) {
                val length: Long = FileUtils.getLeng(files[0])
                if (length > logSize) {
                    val id = files[0].name.replace("${data}_", "").replace(".log", "").toInt() + 1
                    filepath = "$logDir/${data}_$id.log"
                    FileUtils.creatFile(filepath)
                } else {
                    filepath = files[0].absolutePath
                }
            } else {
                filepath = "$logDir/${data}_1.log"
                FileUtils.creatFile(filepath)
            }
            FileUtils.appendText(File(filepath), "\r\n$tag\n$msg")
        })

    }


    /**
     * 是否打印log输出
     * @param debug
     */
    fun debug(debug: Boolean): LogUtils {
        LogUtils.debug = debug
        return this
    }

    /**
     * 是否保存到sd卡
     * @param savesd
     */
    fun saveSd(savesd: Boolean): LogUtils {
        LogUtils.savesd = savesd
        return this
    }

    /**
     * 设置每个log的文件大小
     * @param logSize 文件大小 byte
     */
    fun logSize(logSize: Long): LogUtils {
        LogUtils.logSize = logSize
        return this

    }

    /**
     * 设置log文件目录
     * @param logDir 文件目录
     */
    fun logDir(logDir: String): LogUtils {
        LogUtils.logDir = logDir
        return this
    }


}