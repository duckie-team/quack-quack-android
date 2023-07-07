/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalCoilApi::class)
@file:Suppress("OVERRIDE_DEPRECATION")

package team.duckie.quackquack.ui.plugin.image.common

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.TypedValue
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.decode.DataSource
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Size
import coil.test.FakeImageLoaderEngine
import kotlin.math.roundToInt
import team.duckie.quackquack.ui.plugin.QuackPluginLocal
import team.duckie.quackquack.ui.plugin.image.QuackImagePlugin

private class TextDrawable(private val res: Resources, private val text: String) : Drawable() {
  private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    color = Color.parseColor("#FFA500") // orange
    textAlign = Paint.Align.CENTER
    textSize = TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_SP,
      13f,
      res.displayMetrics,
    )
  }

  override fun draw(canvas: Canvas) {
    val x = intrinsicWidth / 2f
    val y = intrinsicHeight / 2 - (paint.descent() + paint.ascent()) / 2
    canvas.drawText(text, x, y, paint)
  }

  override fun getOpacity() = paint.alpha

  override fun setAlpha(alpha: Int) {
    paint.alpha = alpha
  }

  override fun setColorFilter(colorFilter: ColorFilter?) {
    paint.colorFilter = colorFilter
  }

  override fun getIntrinsicWidth() = (paint.measureText(text, 0, text.length) + .5).roundToInt()

  override fun getIntrinsicHeight() = paint.getFontMetricsInt(null)

  fun setFontSize(fontSize: Float) {
    paint.textSize =
      TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        fontSize,
        res.displayMetrics,
      )
  }
}

private fun imageResultOf(drawable: Drawable, request: ImageRequest) =
  SuccessResult(
    drawable = drawable,
    request = request,
    dataSource = DataSource.MEMORY,
  )

class QuackImageCoilBuilderIntercepter(
  private val map: MutableMap<String, Any?> = mutableMapOf(),
) : QuackImagePlugin.CoilImageLoader {
  override fun ImageLoader.Builder.quackBuild(
    context: Context,
    src: Any?,
    contentDescription: String?,
    quackPluginLocal: QuackPluginLocal?,
  ): ImageLoader.Builder =
    components {
      val testEngine =
        FakeImageLoaderEngine
          .Builder()
          .default { chain ->
            val textDrawable =
              TextDrawable(res = context.resources, text = src.toString()).apply {
                (quackPluginLocal?.value as? Float)?.let { fontSize ->
                  setFontSize(fontSize)
                }
              }
            imageResultOf(
              drawable = textDrawable,
              request = chain
                .withSize(
                  Size(
                    width = textDrawable.intrinsicWidth,
                    height = textDrawable.intrinsicHeight,
                  ),
                )
                .request,
            )
          }
          .build()
      add(testEngine)
    }
      .also {
        map["context"] = context
        map["src"] = src
        map["contentDescription"] = contentDescription
        map["quackPluginLocal"] = quackPluginLocal
      }
}
