/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalCoilApi::class)

package team.duckie.quackquack.ui.plugin.image

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Paint.Align
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.decode.DataSource
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Size
import coil.test.FakeImageLoaderEngine
import com.github.takahirom.roborazzi.captureRoboImage
import io.kotest.matchers.maps.shouldMatchExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeSameInstanceAs
import kotlin.math.roundToInt
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.material.theme.QuackTheme
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.plugin.QuackPluginLocal
import team.duckie.quackquack.ui.plugin.quackPluginLocal
import team.duckie.quackquack.ui.plugin.rememberQuackPlugins

@Suppress("OVERRIDE_DEPRECATION")
private class TextDrawable(private val res: Resources, private val text: String) : Drawable() {
  private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    color = Color.parseColor("#FFA500") // orange
    textAlign = Align.CENTER
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
    paint.textSize = TypedValue.applyDimension(
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

private class QuackImageCoilBuilderIntercepter(
  private val map: MutableMap<String, Any?> = mutableMapOf(),
) : QuackImagePlugin.CoilImageLoader {
  override fun ImageLoader.Builder.builder(
    context: Context,
    src: Any?,
    contentDescription: String?,
    quackPluginLocal: QuackPluginLocal?,
  ): ImageLoader.Builder =
    components {
      val testEngine = FakeImageLoaderEngine
        .Builder()
        .default { chain ->
          val textDrawable = TextDrawable(context.resources, src.toString()).apply {
            (quackPluginLocal?.value as? Float)?.let { fontSize ->
              setFontSize(fontSize)
            }
          }
          imageResultOf(
            drawable = textDrawable,
            request = chain
              .withSize(Size(textDrawable.intrinsicWidth, textDrawable.intrinsicHeight))
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

@RunWith(AndroidJUnit4::class)
class QuackImagePluginTest {
  @get:Rule
  val compose = createAndroidComposeRule<ComponentActivity>()

  @Test
  fun `QuackImage plugin snapshot`() {
    captureRoboImage("src/test/snapshots/QuackImagePlugin/orange-string.png") {
      QuackTheme(
        plugins = rememberQuackPlugins {
          +QuackImageCoilBuilderIntercepter()
        },
      ) {
        QuackImage(
          modifier = Modifier.quackPluginLocal(30f),
          src = "Hello, World!",
          contentDescription = "orange string",
        )
      }
    }
  }

  @Test
  fun `QuackImage plugin intercept test`() {
    val map = mutableMapOf<String, Any?>()

    var context: Context? = null
    val src = "Hello, World!"
    val contentDescription = "orange string"
    val fontSize = 30f

    compose.setContent {
      context = LocalContext.current

      QuackTheme(
        plugins = rememberQuackPlugins {
          +QuackImageCoilBuilderIntercepter(map)
        },
      ) {
        QuackImage(
          modifier = Modifier.quackPluginLocal(fontSize),
          src = src,
          contentDescription = contentDescription,
        )
      }
    }

    map.shouldMatchExactly(
      "context" to { it shouldBeSameInstanceAs context },
      "src" to { it shouldBe src },
      "contentDescription" to { it shouldBe contentDescription },
      "quackPluginLocal" to {
        it.shouldBeInstanceOf<QuackPluginLocal>().value shouldBe fontSize
      },
    )
  }
}
