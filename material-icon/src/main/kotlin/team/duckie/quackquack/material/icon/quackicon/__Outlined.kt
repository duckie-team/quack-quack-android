/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material.icon.quackicon

import kotlin.collections.List as ____KtList
import androidx.compose.ui.graphics.vector.ImageVector
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.outlined.Area
import team.duckie.quackquack.material.icon.quackicon.outlined.ArrowBack
import team.duckie.quackquack.material.icon.quackicon.outlined.ArrowDown
import team.duckie.quackquack.material.icon.quackicon.outlined.ArrowRight
import team.duckie.quackquack.material.icon.quackicon.outlined.ArrowSend
import team.duckie.quackquack.material.icon.quackicon.outlined.Badge
import team.duckie.quackquack.material.icon.quackicon.outlined.Bookmark
import team.duckie.quackquack.material.icon.quackicon.outlined.Buy
import team.duckie.quackquack.material.icon.quackicon.outlined.Camera
import team.duckie.quackquack.material.icon.quackicon.outlined.Check
import team.duckie.quackquack.material.icon.quackicon.outlined.Close
import team.duckie.quackquack.material.icon.quackicon.outlined.Comment
import team.duckie.quackquack.material.icon.quackicon.outlined.Create
import team.duckie.quackquack.material.icon.quackicon.outlined.Delete
import team.duckie.quackquack.material.icon.quackicon.outlined.DmNew
import team.duckie.quackquack.material.icon.quackicon.outlined.Explore
import team.duckie.quackquack.material.icon.quackicon.outlined.Filter
import team.duckie.quackquack.material.icon.quackicon.outlined.Flag
import team.duckie.quackquack.material.icon.quackicon.outlined.Gif
import team.duckie.quackquack.material.icon.quackicon.outlined.Heart
import team.duckie.quackquack.material.icon.quackicon.outlined.Home
import team.duckie.quackquack.material.icon.quackicon.outlined.Home2
import team.duckie.quackquack.material.icon.quackicon.outlined.Image
import team.duckie.quackquack.material.icon.quackicon.outlined.ImageEdit
import team.duckie.quackquack.material.icon.quackicon.outlined.Marketprice
import team.duckie.quackquack.material.icon.quackicon.outlined.Message
import team.duckie.quackquack.material.icon.quackicon.outlined.More
import team.duckie.quackquack.material.icon.quackicon.outlined.Notice
import team.duckie.quackquack.material.icon.quackicon.outlined.NoticeAdd
import team.duckie.quackquack.material.icon.quackicon.outlined.Pager
import team.duckie.quackquack.material.icon.quackicon.outlined.Place
import team.duckie.quackquack.material.icon.quackicon.outlined.Plus
import team.duckie.quackquack.material.icon.quackicon.outlined.Profile
import team.duckie.quackquack.material.icon.quackicon.outlined.Ranking
import team.duckie.quackquack.material.icon.quackicon.outlined.Search
import team.duckie.quackquack.material.icon.quackicon.outlined.Search2
import team.duckie.quackquack.material.icon.quackicon.outlined.Sell
import team.duckie.quackquack.material.icon.quackicon.outlined.Setting
import team.duckie.quackquack.material.icon.quackicon.outlined.Share
import team.duckie.quackquack.material.icon.quackicon.outlined.Tag
import team.duckie.quackquack.material.icon.quackicon.outlined.Test
import team.duckie.quackquack.material.icon.quackicon.outlined.Won
import team.duckie.quackquack.material.icon.quackicon.outlined.WriteFeed

public object OutlinedGroup

public val QuackIcon.Outlined: OutlinedGroup
  get() = OutlinedGroup

private var __AllIcons: ____KtList<ImageVector>? = null

public val OutlinedGroup.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons = listOf(
      Search, More, Explore, Gif, Check, Heart, DmNew, Buy, Ranking, Marketprice,
      Home, Bookmark, Notice, ArrowRight, WriteFeed, Close, Create, Flag, NoticeAdd, Search2,
      Message, Place, Home2, Image, ArrowDown, Pager, Comment, Delete, Filter, Camera, ArrowSend,
      ArrowBack, Sell, Profile, ImageEdit, Badge, Tag, Area, Plus, Setting, Won, Test, Share
    )
    return __AllIcons!!
  }
