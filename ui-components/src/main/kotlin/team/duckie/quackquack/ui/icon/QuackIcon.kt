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
public value class QuackIcon private constructor(
    @DrawableRes private val drawableId: Int,
) {
    public companion object {
        @Stable
        public val Share: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_share_24,
        )

        @Stable
        public val ImageEdit: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_image_edit_24,
        )

        @Stable
        public val Sell: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_sell_24,
        )

        @Stable
        public val Bookmark: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_bookmark_24,
        )

        @Stable
        public val ArrowRight: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_arrow_right_24,
        )

        @Stable
        public val Badge: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_badge_24,
        )

        @Stable
        public val Plus: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_plus_24,
        )

        @Stable
        public val ArrowDown: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_arrow_down_24,
        )

        @Stable
        public val NoticeAdd: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_notice_add_24,
        )

        @Stable
        public val Filter: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_filter_24,
        )

        @Stable
        public val MarketPrice: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_marketprice_24,
        )

        @Stable
        public val Camera: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_camera_24,
        )

        @Stable
        public val Search: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_search_24,
        )

        @Stable
        public val ArrowSend: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_arrow_send_24,
        )

        @Stable
        public val ArrowBack: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_arrow_back_24,
        )

        @Stable
        public val Image: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_image_24,
        )

        @Stable
        public val Tag: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_tag_24,
        )

        @Stable
        public val Area: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_area_24,
        )

        @Stable
        public val Place: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_place_24,
        )

        @Stable
        public val Buy: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_buy_24,
        )

        @Stable
        public val More: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_more_24,
        )

        @Stable
        public val Close: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_close_24,
        )

        @Stable
        public val Delete: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_delete_24,
        )

        @Stable
        public val DeleteBg: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_delete_bg_24,
        )

        @Stable
        public val Setting: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_setting_24,
        )

        @Stable
        public val Won: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_won_24,
        )

        @Stable
        public val Comment: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_comment_24,
        )

        @Stable
        public val ImageEditBg: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_image_edit_bg_24,
        )

        @Stable
        public val Profile: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_profile_24,
        )

        @Stable
        public val Heart: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_heart_24,
        )

        @Stable
        public val Feed: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_feed_24,
        )

        @Stable
        public val Dm: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_dm_24,
        )

        @Stable
        public val WhiteHeart: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_heart_24_white,
        )

        @Stable
        public val FilledHeart: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_heart_filled_24,
        )

        @Stable
        public val FilledBookmark: QuackIcon = QuackIcon(
            drawableId = R.drawable.ic_bookmark_24_filled,
        )
    }
}
