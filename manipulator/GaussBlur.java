package manipulator;

public class GaussBlur extends Filter {

  private static final double DEFAULT_GRIDSIZE = 3; // default grid size when not specified in constructor

  public GaussBlur(double gridsize) {
    super(gridsize);
  }

  public GaussBlur() {
    super(DEFAULT_GRIDSIZE);
  }

  public int[] compute(int[] input, int width, int height) {
    int[] output = new int[input.length]; // create pixel array for result

    int[] coefficients = getCoefficients();
    int divisor = getDivisor(coefficients);

    // iterate over pixel array (or image)
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < (width * STEPSIZE); ++x) {

        int currentIndex = x + (y * (width * STEPSIZE)); // index of current pixel
        int sum = 0;
        int[] indices = getIndices(x, y, width, height); // get the indices of the current grid and position
        for (int i = 0; i < indices.length; ++i) {
          sum += coefficients[i] * input[indices[i]];
        } // sum up the pixel values
        output[currentIndex] = (sum / divisor); // mean the pixel values to the current pixel

      }
    }

    return output; // return blurred pixel array
  }

  /**
   * Returns an int array of the calculated coefficients for the current gridsize.
   */
  private int[] getCoefficients() {
    int gridsize = (int) factor;
    int distance = gridsize / 2; // rows/columns from the center of the grid to any side
    int[] coefficients = new int[gridsize * gridsize];

    for (int i = 0; i < (gridsize * gridsize); ++i) {
      int zx = i % gridsize; // x position in grid
      int zy = i / gridsize; // y position in grid
      int n = Math.abs(distance - zx) + Math.abs(distance - zy);
      coefficients[i] = (int) Math.pow(2, (gridsize - 1) - n); // compute index
    }

    return coefficients; // return computed coefficients as array
  }

  private int getDivisor(int[] coefficients) {
    int divisor = 0;
    for (int i = 0; i < coefficients.length; ++i) {
      divisor += coefficients[i];
    }
    return divisor;
  }
}