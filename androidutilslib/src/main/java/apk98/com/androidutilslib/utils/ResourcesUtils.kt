package apk98.com.androidutilslib.utils

import android.content.Context

/**
 * Created by laijian on 2017/8/31.
 * 获取资源id
 */
object ResourcesUtils {
    fun getIdResources(context: Context, idName: String): Int = context.getResourcesId(idName, "id")

    fun getDrawableResources(context: Context, drawableName: String): Int = context.getResourcesId(drawableName, "drawable")

    fun getColorResources(context: Context, colorName: String): Int = context.getResourcesId(colorName, "color")

    fun getStringResources(context: Context, stringName: String): Int = context.getResourcesId(stringName, "string")

    fun getLayoutResources(context: Context, layoutName: String): Int = context.getResourcesId(layoutName, "layout")

    fun getAttrResources(context: Context, attrName: String): Int = context.getResourcesId(attrName, "attr")

    fun getStyleResources(context: Context, styleName: String): Int = context.getResourcesId(styleName, "style")


    private fun Context.getResourcesId(resourcesName: String, defType: String): Int = this.resources.getIdentifier(resourcesName, defType, this.packageName)


    /**
     * context.getResources().getIdentifier 无法获取到 styleable 的数据
     *
     * @param name
     * @return
     * @paramcontext
     */

    fun getStyleable(context: Context, name: String): Int {

        return (getResourceId(context, name, "styleable") as Int).toInt()

    }

    /**
     * 获取 styleable 的 ID 号数组
     *
     * @param name
     * @return
     * @paramcontext
     */
    fun getStyleableArray(context: Context, name: String): IntArray {
        return getResourceId(context, name, "styleable") as IntArray
    }

    /**
     * 对于 context.getResources().getIdentifier 无法获取的数据 , 或者数组
     *
     *
     * 资源反射值
     *
     * @param name
     * @param type
     * @return
     * @paramcontext
     */

    private fun getResourceId(context: Context, name: String, type: String): Any? {

        val className = context.packageName + ".R"

        try {

            val cls = Class.forName(className)

            for (childClass in cls.classes) {

                val simple = childClass.simpleName

                if (simple == type) {

                    for (field in childClass.fields) {

                        val fieldName = field.name
                        if (fieldName == name) {
                            println(fieldName)
                            return field.get(null)

                        }

                    }

                }

            }

        } catch (e: Exception) {

            e.printStackTrace()

        }

        return null

    }
}