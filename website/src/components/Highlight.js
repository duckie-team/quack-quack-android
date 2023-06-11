/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

import React from 'react';

export default function Highlight({children, color}) {
  function guessBestFontColor(background) {
    const r = parseInt(background.substring(1, 3), 16);
    const g = parseInt(background.substring(3, 5), 16);
    const b = parseInt(background.substring(5, 7), 16);

    const brightness = (r * 299 + g * 587 + b * 114) / 1000;

    return brightness > 128 ? "#000000" : "#FFFFFF";
  }

  return (
    <span style={{
      backgroundColor: color,
      borderRadius: '2px',
      color: guessBestFontColor(color),
      padding: '0.2rem',
    }}>
        {children}
        </span>
  );
}
