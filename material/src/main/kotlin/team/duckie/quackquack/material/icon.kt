/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

/* ktlint-disable argument-list-wrapping */

@file:Suppress("unused")

package team.duckie.quackquack.material

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

/**
 * 덕키에서 사용할 아이콘을 정의합니다.
 *
 * @param drawableId 아이콘 drawable 리소스 아이디
 */
@Immutable
@JvmInline
// TODO(1): 리소스 정리 필요 (불필요한 아이콘까지 포함됨으로 예상됨)
// TODO(1): @Stable 명시 필요 (명시적인 안정성을 선호함)
// TODO(1): XML -> Compose SVG Path
public value class QuackIcon(@DrawableRes public val drawableId: Int) {
    public companion object {
        public val TextLogo: QuackIcon = QuackIcon(R.drawable.quack_duckie_text_logo)

        public val Check: QuackIcon = QuackIcon(R.drawable.quack_ic_check_24)

        public val Share: QuackIcon = QuackIcon(R.drawable.quack_ic_share_24)

        public val ImageEdit: QuackIcon = QuackIcon(R.drawable.quack_ic_image_edit_24)

        public val Sell: QuackIcon = QuackIcon(R.drawable.quack_ic_sell_24)

        public val Bookmark: QuackIcon = QuackIcon(R.drawable.quack_ic_bookmark_24)

        public val ArrowRight: QuackIcon = QuackIcon(R.drawable.quack_ic_arrow_right_24)

        public val Badge: QuackIcon = QuackIcon(R.drawable.quack_ic_badge_24)

        public val Plus: QuackIcon = QuackIcon(R.drawable.quack_ic_plus_24)

        public val ArrowDown: QuackIcon = QuackIcon(R.drawable.quack_ic_arrow_down_24)

        public val NoticeAdd: QuackIcon = QuackIcon(R.drawable.quack_ic_notice_add_24)

        public val Filter: QuackIcon = QuackIcon(R.drawable.quack_ic_filter_24)

        public val MarketPrice: QuackIcon = QuackIcon(R.drawable.quack_ic_marketprice_24)

        public val Camera: QuackIcon = QuackIcon(R.drawable.quack_ic_camera_24)

        public val Search: QuackIcon = QuackIcon(R.drawable.quack_ic_search_24)

        public val ArrowSend: QuackIcon = QuackIcon(R.drawable.quack_ic_arrow_send_24)

        public val ArrowBack: QuackIcon = QuackIcon(R.drawable.quack_ic_arrow_back_24)

        public val Image: QuackIcon = QuackIcon(R.drawable.quack_ic_image_24)

        public val Tag: QuackIcon = QuackIcon(R.drawable.quack_ic_tag_24)

        public val Area: QuackIcon = QuackIcon(R.drawable.quack_ic_area_24)

        public val Place: QuackIcon = QuackIcon(R.drawable.quack_ic_place_24)

        public val Buy: QuackIcon = QuackIcon(R.drawable.quack_ic_buy_24)

        public val More: QuackIcon = QuackIcon(R.drawable.quack_ic_more_24)

        public val Close: QuackIcon = QuackIcon(R.drawable.quack_ic_close_24)

        public val Delete: QuackIcon = QuackIcon(R.drawable.quack_ic_delete_24)

        public val DeleteBg: QuackIcon = QuackIcon(R.drawable.quack_ic_delete_bg_24)

        public val Setting: QuackIcon = QuackIcon(R.drawable.quack_ic_setting_24)

        public val Won: QuackIcon = QuackIcon(R.drawable.quack_ic_won_24)

        public val Comment: QuackIcon = QuackIcon(R.drawable.quack_ic_comment_24)

        public val ImageEditBg: QuackIcon = QuackIcon(R.drawable.quack_ic_image_edit_bg_24)

        public val Profile: QuackIcon = QuackIcon(R.drawable.quack_ic_profile_24)

        public val Heart: QuackIcon = QuackIcon(R.drawable.quack_ic_heart_24)

        public val Feed: QuackIcon = QuackIcon(R.drawable.quack_ic_feed_24)

        public val Dm: QuackIcon = QuackIcon(R.drawable.quack_ic_dm_24)

        public val WhiteHeart: QuackIcon = QuackIcon(R.drawable.quack_ic_heart_24_white)

        public val FilledHeart: QuackIcon = QuackIcon(R.drawable.quack_ic_heart_filled_24)

        public val FilledBookmark: QuackIcon = QuackIcon(R.drawable.quack_ic_bookmark_24_filled)

        public val FilledProfile: QuackIcon = QuackIcon(R.drawable.quack_ic_profile_24_filled)

        public val Gallery: QuackIcon = QuackIcon(R.drawable.quack_ic_gallery_24)
    }
}
