/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.ui.icon

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import team.duckie.quackquack.ui.R

/**
 * 덕키에서 사용할 아이콘을 정의합니다. 추상화를 위해 아이콘 리소스를 바로 사용하는게 아닌
 * 이 클래스를 통해 사용해야 합니다.
 *
 * copy 를 통한 값 변경은 덕키 스타일 가이드의 아이콘 사전 정의가 깨짐으로
 * copy 생성을 방지하기 위해 data class 가 아닌 class 가 사용됐습니다.
 *
 * @param drawableId 아이콘 drawable 리소스 아이디
 */
@Immutable
@JvmInline
value class QuackIcon private constructor(
    @DrawableRes val drawableId: Int,
) {
    companion object {
        @Stable
        val Share = QuackIcon(
            drawableId = R.drawable.ic_share_24,
        )

        @Stable
        val ImageEdit = QuackIcon(
            drawableId = R.drawable.ic_image_edit_24,
        )

        @Stable
        val Sell = QuackIcon(
            drawableId = R.drawable.ic_sell_24,
        )

        @Stable
        val Bookmark = QuackIcon(
            drawableId = R.drawable.ic_bookmark_24,
        )

        @Stable
        val ArrowRight = QuackIcon(
            drawableId = R.drawable.ic_arrow_right_24,
        )

        @Stable
        val Badge = QuackIcon(
            drawableId = R.drawable.ic_badge_24,
        )

        @Stable
        val Plus = QuackIcon(
            drawableId = R.drawable.ic_plus_24,
        )

        @Stable
        val ArrowDown = QuackIcon(
            drawableId = R.drawable.ic_arrow_down_24,
        )

        @Stable
        val NoticeAdd = QuackIcon(
            drawableId = R.drawable.ic_notice_add_24,
        )

        @Stable
        val Filter = QuackIcon(
            drawableId = R.drawable.ic_filter_24,
        )

        @Stable
        val MarketPrice = QuackIcon(
            drawableId = R.drawable.ic_marketprice_24,
        )

        @Stable
        val Camera = QuackIcon(
            drawableId = R.drawable.ic_camera_24,
        )

        @Stable
        val Search = QuackIcon(
            drawableId = R.drawable.ic_search_24,
        )

        @Stable
        val ArrowSend = QuackIcon(
            drawableId = R.drawable.ic_arrow_send_24,
        )

        @Stable
        val ArrowBack = QuackIcon(
            drawableId = R.drawable.ic_arrow_back_24,
        )

        @Stable
        val Image = QuackIcon(
            drawableId = R.drawable.ic_image_24,
        )

        @Stable
        val Tag = QuackIcon(
            drawableId = R.drawable.ic_tag_24,
        )

        @Stable
        val Area = QuackIcon(
            drawableId = R.drawable.ic_area_24,
        )

        @Stable
        val Place = QuackIcon(
            drawableId = R.drawable.ic_place_24,
        )

        @Stable
        val Buy = QuackIcon(
            drawableId = R.drawable.ic_buy_24,
        )

        @Stable
        val More = QuackIcon(
            drawableId = R.drawable.ic_more_24,
        )

        @Stable
        val Close = QuackIcon(
            drawableId = R.drawable.ic_close_24,
        )

        @Stable
        val Delete = QuackIcon(
            drawableId = R.drawable.ic_delete_24,
        )

        @Stable
        val DeleteBg = QuackIcon(
            drawableId = R.drawable.ic_delete_bg_16,
        )

        @Stable
        val Setting = QuackIcon(
            drawableId = R.drawable.ic_setting_24,
        )

        @Stable
        val Won = QuackIcon(
            drawableId = R.drawable.ic_won_24,
        )

        @Stable
        val Comment = QuackIcon(
            drawableId = R.drawable.ic_comment_24,
        )

        @Stable
        val ImageEditBg = QuackIcon(
            drawableId = R.drawable.ic_image_edit_bg_24,
        )

        @Stable
        val Profile = QuackIcon(
            drawableId = R.drawable.ic_profile_24,
        )

        @Stable
        val Heart = QuackIcon(
            drawableId = R.drawable.ic_heart_24,
        )

        @Stable
        val Feed = QuackIcon(
            drawableId = R.drawable.ic_feed_24,
        )

        @Stable
        val Dm = QuackIcon(
            drawableId = R.drawable.ic_dm_24,
        )

        @Stable
        val Checked = QuackIcon(
            drawableId = R.drawable.ic_checked_round_28
        )

        @Stable
        val UnChecked = QuackIcon(
            drawableId = R.drawable.ic_un_checked_round_28
        )
    }
}
