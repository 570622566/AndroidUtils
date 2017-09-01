package apk98.com.androidutilslib.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import java.text.ParseException


/**
 * Created by laijian on 2017/8/14.
 * 时间控件
 */
object TimeUtils {

    // 格式：年－月－日 小时：分钟：秒
    private val FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss"
    // 格式：年－月－日
    private val FORMAT_YMD = "yyyy-MM-dd"

    /**
     * 字符串转日期类型
     * @param dateStr 日期字符串
     * @param format 转换类型
     */
    fun stringToDate(dateStr: String, format: String = FORMAT_YMDHMS): Date? {

        val formater: SimpleDateFormat = SimpleDateFormat(format, Locale.US)
        return try {
            formater.isLenient = false
            formater.parse(dateStr)
        } catch (e: Exception) {
            null
        }
    }


    /**
     * 日期转字符串
     */
    fun dateToString(date: Date, format: String = FORMAT_YMDHMS): String? {
        val formater = SimpleDateFormat(format, Locale.US)
        return try {
            formater.format(date)
        } catch (e: Exception) {
            null
        }
    }


    /**
     * 获取某年某月的天数

     * @param year
     * *          int
     * *
     * @param month
     * *          int 月份[1-12]
     * *
     * @return int
     */
    fun getDaysOfMonth(year: Int, month: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, 1)
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    /**
     * 获得当前日期

     * @return int
     */
    fun getToday(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.DATE)
    }

    /**
     * 获得当前月份

     * @return int
     */
    fun getToMonth(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.MONTH) + 1
    }

    /**
     * 获得当前年份

     * @return int
     */
    fun getToYear(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.YEAR)
    }

    /**
     * 返回日期的天

     * @param date
     * *          Date
     * *
     * @return int
     */
    fun getDay(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.DATE)
    }

    /**
     * 返回日期的年

     * @param date
     * *          Date
     * *
     * @return int
     */
    fun getYear(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.YEAR)
    }

    /**
     * 返回日期的月份，1-12

     * @param date
     * *          Date
     * *
     * @return int
     */
    fun getMonth(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.MONTH) + 1
    }

    /**
     * 计算两个日期相差的天数，如果date2 > date1 返回正数，否则返回负数

     * @param date1
     * *          Date
     * *
     * @param date2
     * *          Date
     * *
     * @return long
     */
    fun dayDiff(date1: Date, date2: Date): Long {
        return (date2.time - date1.time) / 86400000
    }

    /**
     * 比较两个日期的年差

     * @param befor
     * *
     * @param after
     * *
     * @return
     */
    fun yearDiff(before: String, after: String): Int {
        val beforeDay = stringToDate(before, FORMAT_YMD)
        val afterDay = stringToDate(after, FORMAT_YMD)
        if (beforeDay != null && afterDay != null) {
            return (getYear(afterDay) - getYear(beforeDay))
        } else {
            return -1
        }
    }

    /**
     * 获取一天的开始时间
     */
    fun getFristDayTime(dateFormat: String = FORMAT_YMDHMS, setTime: String): String {
        val format = SimpleDateFormat(dateFormat, Locale.US)
        val c1 = GregorianCalendar()
        try {
            c1.time = format.parse(setTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        c1.set(Calendar.HOUR_OF_DAY, 0)
        c1.set(Calendar.MINUTE, 0)
        c1.set(Calendar.SECOND, 0)
        return format.format(c1.time)
    }


    /**
     * 获取一天的结束时间
     */
    fun getLastDayTime(dateFormat: String = FORMAT_YMDHMS, setTime: String): String {
        val format = SimpleDateFormat(dateFormat, Locale.US)
        val c1 = GregorianCalendar()
        try {
            c1.time = format.parse(setTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        c1.set(Calendar.HOUR_OF_DAY, 23);
        c1.set(Calendar.MINUTE, 59);
        c1.set(Calendar.SECOND, 59);
        return format.format(c1.time)
    }

    /**
     * 比较指定日期与当前日期的差

     * @param befor
     * *
     * @param after
     * *
     * @return
     */
    fun yearDiffCurr(after: String): Int {
        val beforeDay = Date()
        val afterDay = stringToDate(after, FORMAT_YMD)
        return if (afterDay != null) {
            (getYear(beforeDay) - getYear(afterDay))
        } else {
            -1
        }
    }

    /**
     * 获取指定日期的时间差
     */
    fun yearDiffCurr(after: String, before: String): Int {
        val beforeDay = stringToDate(before, FORMAT_YMD)
        val afterDay = stringToDate(after, FORMAT_YMD)
        return if (afterDay != null && beforeDay != null) {
            (getYear(beforeDay) - getYear(afterDay))
        } else {
            -1
        }
    }


    /**
     * 获取每月的第一周
     * @param year
     * *
     * @param month
     * *
     * @return
     * *
     * @author chenyz
     */
    fun getFirstWeekdayOfMonth(year: Int, month: Int): Int {
        val c = Calendar.getInstance()
        c.firstDayOfWeek = Calendar.SATURDAY // 星期天为第一天
        c.set(year, month - 1, 1)
        return c.get(Calendar.DAY_OF_WEEK)
    }

    /**
     * 获取每月的最后一周
     * @param year
     * *
     * @param month
     * *
     * @return
     * *
     * @author chenyz
     */
    fun getLastWeekdayOfMonth(year: Int, month: Int): Int {
        val c = Calendar.getInstance()
        c.firstDayOfWeek = Calendar.SATURDAY // 星期天为第一天
        c.set(year, month - 1, getDaysOfMonth(year, month))
        return c.get(Calendar.DAY_OF_WEEK)
    }

    /**
     * 获取本月第一天

     * @param format
     * *
     * @return
     */
    fun getFirstDayOfMonth(format: String): String {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DATE, 1)
        val firstDayOfMonth = dateToString(cal.time, format)
        return firstDayOfMonth ?: ""
    }

    /**
     * 获取本月最后一天

     * @param format
     * *
     * @return
     */
    fun getLastDayOfMonth(format: String): String {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DATE, 1)
        cal.add(Calendar.MONTH, 1)
        cal.add(Calendar.DATE, -1)
        val lastDayOfMonth = dateToString(cal.time, format)
        return lastDayOfMonth ?: ""
    }

    /**
     * 判断日期是否有效,包括闰年的情况

     * @param date
     * *          YYYY-mm-dd
     * *
     * @return
     */
    fun isDate(date: String): Boolean {
        val reg = StringBuffer(
                "^((\\d{2}(([02468][048])|([13579][26]))-?((((0?")
        reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))")
        reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|")
        reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12")
        reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))")
        reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))")
        reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[")
        reg.append("1-9])|(1[0-9])|(2[0-8]))))))")
        val p = Pattern.compile(reg.toString())
        return p.matcher(date).matches()
    }

    /**
     * 根据生日获取星座

     * @param birth
     * *          YYYY-mm-dd
     * *
     * @return
     */
    fun getAstro(birth: String): String {
        var birth = birth
        if (!isDate(birth)) {
            birth = "2000" + birth
        }
        if (!isDate(birth)) {
            return ""
        }
        val month = Integer.parseInt(birth.substring(birth.indexOf("-") + 1,
                birth.lastIndexOf("-")))
        val day = Integer.parseInt(birth.substring(birth.lastIndexOf("-") + 1))
        val s = "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯"
        val arr = intArrayOf(20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22)
        val start = month * 2 - if (day < arr[month - 1]) 2 else 0
        return s.substring(start, start + 2) + "座"
    }


}