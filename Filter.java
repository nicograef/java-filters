public abstract class Filter {
  
  protected final int STEPSIZE = 3; // because pixel array has 3 cells for one pixel (RGB)

  //protected int[] input; // input pixel array
  //protected int[] output; // output pixel array
  //protected int width; // width of the image
  //protected int height; // height of the image
  //protected int ifactor; // filter factor of the individual filter
  protected double factor; // filter factor of the individual filter
  // private int lastIndex; // index of the very last pixel
   
  public Filter (double factor) {
    //this.ifactor = (int)factor;
    this.factor = factor;
  }
  
  /**
    Here the filter is running over the input array.
    Returns the output pixel array.
  */
  public abstract int[] compute(int[] input, int width, int height);
  
  public void setFactor(double factor) {
    //this.ifactor = (int)factor;
    this.factor = factor;
  }
  
  /**
    Returns an int array of the calculated indices for the current gridsize.
  */
  protected int[] getIndices(int x, int y, int width, int height) {
    int gridsize = (int)factor;
    int distance = gridsize / 2; // rows/columns from the center of the grid to any side
    int currentIndex = x + (y * (width * STEPSIZE)); // current index in pixel array (or image)
    int lastIndex = ((width * height) * STEPSIZE) - 1;
    int[] indices = new int[gridsize * gridsize];
    
    for (int i = 0; i < (gridsize * gridsize); ++i) {
      int zx = i % gridsize; // x position in grid
      int zy = i / gridsize; // y position in grid
      indices[i] = (x - ((distance - zx) * STEPSIZE)) + ((y - (distance - zy)) * (width * STEPSIZE)); // compute index
      if (indices[i] < 0 || indices[i] > lastIndex) { indices[i] = currentIndex; } // check if index is in image bounds
    }
    
    return indices; // return computed indices as array
  }
}