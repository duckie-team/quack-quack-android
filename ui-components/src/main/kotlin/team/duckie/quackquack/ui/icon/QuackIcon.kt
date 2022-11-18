/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.ui.icon

import androidx.annotation.DrawableRes
import team.duckie.quackquack.ui.R

/**
 * 덕키에서 사용할 아이콘을 정의합니다.
 * 추상화를 위해 아이콘 리소스를 바로 사용하는게 아닌 이 클래스를 통해 사용해야 합니다.
 *
 * copy 를 통한 값 변경은 덕키 스타일 가이드의 아이콘 사전 정의가 깨짐으로
 * copy 생성을 방지하기 위해 data class 가 아닌 class 가 사용됐습니다.
 *
 * @param drawableId 아이콘 drawable 리소스 아이디
 */
@JvmInline
public value class QuackIcon private constructor(
    @DrawableRes public val drawableId: Int,
) {
    public companion object {
        public val TextLogo: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_duckie_text_logo,
        )

        public val Share: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_share_24,
        )

        public val ImageEdit: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_image_edit_24,
        )

        public val Sell: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_sell_24,
        )

        public val Bookmark: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_bookmark_24,
        )

        public val ArrowRight: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_arrow_right_24,
        )

        public val Badge: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_badge_24,
        )

        public val Plus: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_plus_24,
        )

        public val ArrowDown: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_arrow_down_24,
        )

        public val NoticeAdd: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_notice_add_24,
        )

        public val Filter: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_filter_24,
        )

        public val MarketPrice: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_marketprice_24,
        )

        public val Camera: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_camera_24,
        )

        public val Search: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_search_24,
        )

        public val ArrowSend: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_arrow_send_24,
        )

        public val ArrowBack: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_arrow_back_24,
        )

        public val Image: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_image_24,
        )

        public val Tag: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_tag_24,
        )

        public val Area: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_area_24,
        )

        public val Place: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_place_24,
        )

        public val Buy: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_buy_24,
        )

        public val More: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_more_24,
        )

        public val Close: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_close_24,
        )

        public val Delete: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_delete_24,
        )

        public val DeleteBg: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_delete_bg_24,
        )

        public val Setting: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_setting_24,
        )

        public val Won: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_won_24,
        )

        public val Comment: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_comment_24,
        )

        public val ImageEditBg: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_image_edit_bg_24,
        )

        public val Profile: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_profile_24,
        )

        public val Heart: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_heart_24,
        )

        public val Feed: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_feed_24,
        )

        public val Dm: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_dm_24,
        )

        public val WhiteHeart: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_heart_24_white,
        )

        public val FilledHeart: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_heart_filled_24,
        )

        public val FilledBookmark: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_bookmark_24_filled,
        )

        public val FilledProfile: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_profile_24_filled,
        )

        public val Gallery: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_gallery_24,
        )

        internal val BottomNavHome: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_home_bottom_nav_24,
        )

        internal val BottomNavHomeSelected: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_home_bottom_nav_selected_24,
        )

        internal val BottomNavSearch: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_search_bottom_nav_24,
        )

        internal val BottomNavSearchSelected: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_search_bottom_nav_selected_24,
        )

        internal val BottomNavNotice: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_notice_bottom_nav_24,
        )

        internal val BottomNavNoticeSelected: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_notice_bottom_nav_selected_24,
        )

        internal val BottomNavMessage: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_message_bottom_nav_24,
        )

        internal val BottomNavMessageSelected: QuackIcon = QuackIcon(
            drawableId = R.drawable.quack_ic_message_bottom_nav_selected_24,
        )
    }
}
