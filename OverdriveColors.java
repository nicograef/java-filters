public class OverdriveColors extends Filter {
  
  private static final double DEFAULT_OVERDRIVE = 1.05; // default desaturation factor when not specified in construtor (not used here)
  private static final int THRESHOLD = 50;
  private static final int DISTANCE = 15;
  
  public OverdriveColors(double overdrive) {
    super(overdrive);
  }
  
  public OverdriveColors() {
    super(DEFAULT_OVERDRIVE);
  }
  
  public int[] compute(int[] input, int width, int height) {
    int[] output = new int[input.length]; // create pixel array for output
   
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < ((width * STEPSIZE) - 2); x += STEPSIZE) {
        
        int currentIndex = x + (y * (width * STEPSIZE)); // index of current pixel
        
        // read the color values of the pixel
        int red = input[currentIndex];
        int green = input[currentIndex + 1];
        int blue = input[currentIndex + 2];
        
        if (red > THRESHOLD && red > (green + DISTANCE) && red > (blue + DISTANCE)) {
          output[currentIndex] = (int)((red * factor) > 255 ? 255 : (red * factor));
          output[currentIndex + 1] = (int)(green / factor);
          output[currentIndex + 2] = (int)(blue / factor);
        } else if (green > THRESHOLD && green > (red + DISTANCE) && green > (blue + DISTANCE)) {
          output[currentIndex] = (int)(red / factor);
          output[currentIndex + 1] = (int)((green * factor) > 255 ? 255 : (green * factor));
          output[currentIndex + 2] = (int)(blue / factor);
        } else if (blue > THRESHOLD && blue > (red + DISTANCE) && blue > (green + DISTANCE)) {
          output[currentIndex] = (int)(red / factor);
          output[currentIndex + 1] = (int)(green / factor);
          output[currentIndex + 2] = (int)((blue * factor) > 255 ? 255 : (blue * factor));
        } else {
          output[currentIndex] = red;
          output[currentIndex + 1] = green;
          output[currentIndex + 2] = blue;
        }
      }
    }
    
    return output;
  }
}