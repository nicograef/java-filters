package manipulator;

public class MeanBlur extends Filter {

  private static final double DEFAULT_GRIDSIZE = 3; // default grid size when not specified in constructor

  public MeanBlur(double gridsize) {
    super(gridsize);
  }

  public MeanBlur() {
    super(DEFAULT_GRIDSIZE);
  }

  public int[] compute(int[] input, int width, int height) {
    int[] output = new int[input.length]; // create pixel array for result

    // iterate over pixel array (or image)
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < (width * STEPSIZE); ++x) {

        int currentIndex = x + (y * (width * STEPSIZE)); // index of current pixel
        int sum = 0;
        int[] indices = getIndices(x, y, width, height); // get the indices of the current grid and position
        for (int i = 0; i < indices.length; ++i) {
          sum += input[indices[i]];
        } // sum up the pixel values
        output[currentIndex] = (sum / indices.length); // mean the pixel values to the current pixel

      }
    }

    return output; // return blurred pixel array
  }
}