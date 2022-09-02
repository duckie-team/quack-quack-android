/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [ToastWrapper.kt] created by Ji Sungbin on 22. 8. 18. 오전 2:14
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("MemberVisibilityCanBePrivate")
@file:NoLiveLiterals

package team.duckie.quackquack.playground.util

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.lang.ref.WeakReference

class ToastWrapper(context: Context) {
    private val contextWrapper = WeakReference(context)
    private val _context get() = contextWrapper.get()!!

    // TODO: 토스트 폰트 변경 (가능?)
    // 시도해 본 방법: https://stackoverflow.com/questions/2940465/change-toast-font 하지만 작동 X
    private val toastInstance = Toast.makeText(_context, "", Toast.LENGTH_SHORT)

    operator fun invoke(
        message: String,
        length: Int = Toast.LENGTH_SHORT,
    ) = toastInstance.run {
        setText(message)
        duration = length
        show()
    }
}

@Composable
fun rememberToast(): ToastWrapper {
    val context = LocalContext.current.applicationContext
    return remember(
        key1 = context,
    ) {
        ToastWrapper(
            context = context,
        )
    }
}
