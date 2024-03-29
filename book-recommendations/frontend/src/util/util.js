/**
 * HSL - a model to define colors, with hue, saturation, and lightness
 * pastel color - a color with low to medium saturation (low - medium intensity) and high lightness.
 * @param colorsArray the colors array (this array will be modified and populated from this function)
 * @param itemsLength number of colors that should be produced
 * @param saturationRange min and max values of saturation
 * @param lightnessRange min and max values of lightness
 */
export function generatePastelColors(colorsArray, itemsLength,
  saturationRange = {min: 30, max: 70}, lightnessRange = {min: 80, max: 100}) {
  for (let i = colorsArray.length; i < itemsLength; i++) {
    let hue = Math.floor(Math.random() * (360 - (190 - 167 + 1) + 1)) + 0;
    if (hue >= 167 && hue <= 190) {
      hue += 190 - 167 + 1;
    }
    const saturation = Math.floor(
      Math.random() * (saturationRange.max - saturationRange.min + 1)
      + saturationRange.min);
    const lightness = Math.floor(
      Math.random() * (lightnessRange.max - lightnessRange.min + 1)
      + lightnessRange.min);

    const color = `hsl(${hue}, ${saturation}%, ${lightness}%)`;
    colorsArray.includes(color) ? i-- : colorsArray.push(color);
  }
}
