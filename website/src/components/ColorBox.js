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