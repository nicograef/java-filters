package manipulator;

public class Contrast extends Filter {

  private static final double DEFAULT_FACTOR = 1.1; // default grid factor when not specified in construtor

  public Contrast(double factor) {
    super(factor);
  }

  public Contrast() {
    super(DEFAULT_FACTOR);
  }

  public int[] compute(int[] input, int width, int height) {
    int[] output = new int[input.length]; // create pixel array for result

    // create int values to compute average colors
    double meanRed = 0;
    double meanGreen = 0;
    double meanBlue = 0;
    int imagesize = width * height;

    // compute average value for each color on whole image
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < ((width * STEPSIZE) - 2); x += STEPSIZE) {
        int currentIndex = x + (y * (width * STEPSIZE)); // index of current pixel
        meanRed += ((double) input[currentIndex]) / imagesize;
        meanGreen += ((double) input[currentIndex + 1]) / imagesize;
        meanBlue += ((double) input[currentIndex + 2]) / imagesize;
      }
    }

    // decrease or increase value for each color on every pixel based on the average
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < ((width * STEPSIZE) - 2); x += STEPSIZE) {

        int currentIndex = x + (y * (width * STEPSIZE)); // index of current pixel

        // get current pixel colors
        int red = input[currentIndex];
        int green = input[currentIndex + 1];
        int blue = input[currentIndex + 2];

        // if current pixels red value is greater than the average, increase current
        // value, otherwise decrease
        if (red > meanRed) {
          output[currentIndex] = (int) ((red * factor) > 255 ? 255 : (red * factor));
        } else {
          output[currentIndex] = (int) (red / factor);
        }

        // if current pixels green value is greater than the average, increase current
        // value, otherwise decrease
        if (green > meanGreen) {
          output[currentIndex + 1] = (int) ((green * factor) > 255 ? 255 : (green * factor));
        } else {
          output[currentIndex + 1] = (int) (green / factor);
        }

        // if current pixels blue value is greater than the average, increase current
        // value, otherwise decrease
        if (blue > meanBlue) {
          output[currentIndex + 2] = (int) ((blue * factor) > 255 ? 255 : (blue * factor));
        } else {
          output[currentIndex + 2] = (int) (blue / factor);
        }
      }
    }

    return output;
  }
}