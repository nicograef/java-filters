public class Psycho extends Filter {
  
  private static final double DEFAULT_DESATURATION = 0.45; // default desaturation factor when not specified in construtor (not used here)
  
  public Psycho(double desaturation) {
    super(desaturation);
  }
  
  public Psycho() {
    super(DEFAULT_DESATURATION);
  }
  
  public int[] compute(int[] input, int width, int height) {
    int[] output = new int[input.length]; // create pixel array for result
   
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < ((width * STEPSIZE) - 2); x += 3) {
        
        int currentIndex = x + (y * (width * STEPSIZE)); // index of current pixel
        
        // read the color values of the pixel
        int red = input[currentIndex];
        int green = input[currentIndex + 1];
        int blue = input[currentIndex + 2];
        
        int average = (int)((red + green + blue) * factor / 3); // compute (modified) average of the three colors
        if (average > 255) average = 255; // limit average to 255

        // if current pixels red value is greater than the average, decrease current value, otherwise increase
        if (red < average) { output[currentIndex] = (int)((red * factor) > 255 ? 255 : (red * factor)); }
        else { output[currentIndex] = (int)(red / factor); }
        
        // if current pixels green value is greater than the average, decrease current value, otherwise increase
        if (green < average) { output[currentIndex + 1] = (int)((green * factor) > 255 ? 255 : (green * factor)); }
        else { output[currentIndex + 1] = (int)(green / factor); }
        
        // if current pixels blue value is greater than the average, decrease current value, otherwise increase
        if (blue < average) { output[currentIndex + 2] = (int)((blue * factor) > 255 ? 255 : (blue * factor)); }
        else { output[currentIndex + 2] = (int)(blue / factor); }
        
      }
    }
    
    return output;
  }
}