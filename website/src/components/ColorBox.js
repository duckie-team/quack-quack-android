/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

import React from 'react';

export default function ColorBox({width, height, color}) {
  const style = {
    width: width,
    height: height,
    'background-color': color,
    display: "inline-block",
  };

  return (
    <div style={style}/>
  );
}
