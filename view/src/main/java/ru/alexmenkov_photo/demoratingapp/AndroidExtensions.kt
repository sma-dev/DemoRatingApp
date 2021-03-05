package ru.alexmenkov_photo.demoratingapp

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.view.animation.Transformation
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

fun localePriceToString(price: BigDecimal): String =
    NumberFormat.getCurrencyInstance(Locale("ru", "RU")/*Locale.getDefault()*/)
        .apply { roundingMode = RoundingMode.DOWN }
        .format(price)


inline fun <reified T : Any?> Fragment.argument(key: String): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        requireArguments().get(key) as T
    }
}

fun expand(
    v: View,
    height: Int,
    dpPerMillis: Double = 1.0,
    interpolator: Interpolator = LinearInterpolator()
) {
    v.measure(LinearLayout.LayoutParams.MATCH_PARENT, height)
    val targetHeight = v.measuredHeight
    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    v.layoutParams.height = 1
    v.visibility = View.VISIBLE
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            val newHeight = (targetHeight * interpolatedTime).toInt()
            v.layoutParams.height =
                when {
                    interpolatedTime == 1f -> WindowManager.LayoutParams.WRAP_CONTENT
                    newHeight != 0 -> newHeight
                    else -> 1
                }
            v.requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    // 1dp/ms
    a.duration =
        (targetHeight / (v.context.resources.displayMetrics.density *
                if (dpPerMillis <= 0) 1.0 else dpPerMillis)).toLong()
    a.interpolator = interpolator
    v.startAnimation(a)
}

fun collapse(
    v: View,
    dpPerMillis: Double = 1.0,
    interpolator: Interpolator = LinearInterpolator(),
    endAction: () -> Unit
) {
    val initialHeight: Int = v.measuredHeight
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            if (interpolatedTime == 1f) {
                v.visibility = View.GONE
                endAction.invoke()
            } else {
                v.layoutParams.height =
                    initialHeight - (initialHeight * interpolatedTime).toInt()
                v.requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    // 1dp/ms
    a.duration =
        (initialHeight / (v.context.resources.displayMetrics.density *
                if (dpPerMillis <= 0) 1.0 else dpPerMillis)).toLong()
    a.interpolator = interpolator
    v.startAnimation(a)
}


inline fun <reified T> T.logD(s: String) {
    val tag = T::class.java.simpleName
    Log.d(tag, s)
}

fun Fragment.toast(s: String) {
    Toast.makeText(requireContext(), s, Toast.LENGTH_LONG).show()
}

fun getScreenWidth(context: Context): Int = context.resources.displayMetrics.widthPixels

fun getPercentHeight(context: Context, percent: Float) =
    context.resources.displayMetrics.heightPixels * percent / 100

fun getPixelDensity(context: Context): Float = context.resources.displayMetrics.density

fun Int.toPx(context: Context): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    context.resources.displayMetrics
)

fun Float.toDp(context: Context) = this / context.resources.displayMetrics.density


fun getItemInGroup(
    context: Context,
    dpItemWidth: Int,
    minItemInGroup: Int,
    dpGapWidth: Int,
    dpBorderWidth: Int
): Int {
    val pxContentWidth = getScreenWidth(context) - (dpBorderWidth * 2).toPx(context)
    val pxGapWidth = dpGapWidth.toPx(context)
    val pxItemWidth = dpItemWidth.toPx(context)
    val itemInGroup = ((pxContentWidth - pxGapWidth) / pxItemWidth).toInt()
    return if (itemInGroup < minItemInGroup) minItemInGroup else itemInGroup
}

fun getOptimalItemPxWidth(
    context: Context,
    itemInGroup: Int,
    dpGapWidth: Int,
    dpBorderWidth: Int
): Int {
    val pxContentWidth = getScreenWidth(context) - (dpBorderWidth * 2).toPx(context)
    val pxGapWidth = dpGapWidth.toPx(context)
    return ((pxContentWidth - (pxGapWidth * (itemInGroup - 1))) / itemInGroup).toInt()
}