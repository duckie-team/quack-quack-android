@file:Suppress("MemberVisibilityCanBePrivate")
@file:NoLiveLiterals

package land.sungbin.duckie.quackquack.playground

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.lang.ref.WeakReference

private val String.Companion.Empty get() = ""

class ToastWrapper(context: Context) {
    private val contextWrapper = WeakReference(context)
    private val _context get() = contextWrapper.get()!!
    private val toastInstance = Toast.makeText(_context, String.Empty, Toast.LENGTH_SHORT)

    operator fun invoke(
        message: String,
        length: Int = Toast.LENGTH_SHORT,
    ) {
        toastInstance.run {
            setText(message)
            duration = length
            show()
        }
    }
}

@Composable
fun rememberToast(): ToastWrapper {
    val context = LocalContext.current.applicationContext
    return remember(context) {
        ToastWrapper(context)
    }
}
