public class BlackAndWhite extends Filter {
  
  private static final double DEFAULT_BRIGHTNESS = 1; // default brightness factor when not specified in construtor (not used here)
  
  public BlackAndWhite(double brightness) {
    super(brightness);
  }
  
  public BlackAndWhite() {
    super(DEFAULT_BRIGHTNESS);
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

        // set each color value to the average -> black and white image
        output[currentIndex] = average; // red
        output[currentIndex + 1] = average; // green
        output[currentIndex + 2] = average; // blue
        
      }
    }
    
    return output;
  }
}