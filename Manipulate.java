import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class Manipulate {

  public static void main(String[] args) throws IOException {

    /* BEGIN LOADING IMAGE TO BUFFEREDIMAGE AND THE INTO INTEGER ARRAY */
    // String inputFile = args[0];
    String inputFile = System.console().readLine("Image file: ").toString();
    String filename = inputFile.split("[.]")[0];
    String datatype = inputFile.split("[.]")[1];
    BufferedImage img = ImageIO.read(new File(inputFile));

    final int width = img.getWidth();
    final int height = img.getHeight();
    final int[] pixels = img.getRaster().getPixels(0, 0, width, height, (int[]) null);
    /* END LOADING IMAGE TO BUFFEREDIMAGE AND THE INTO INTEGER ARRAY */

    // Initialize all the possible filters
    Filter blackAndWhite = new BlackAndWhite(1.35);
    Filter contrast = new Contrast(1.22);
    Filter gaussBlur = new GaussBlur(19);
    Filter meanBlur = new MeanBlur();
    Filter psycho = new Psycho(0.5);
    Filter desaturate = new Desaturate(1.0000);
    Filter overdriveColors = new OverdriveColors(1.05);

    /*
     * This is where the magic happens! Every line represents one filter in action.
     * You can comment and uncomment in your style.
     */
    int[] res = pixels;
    res = blackAndWhite.compute(res, width, height);
    res = contrast.compute(res, width, height);
    res = gaussBlur.compute(res, width, height);
    res = meanBlur.compute(res, width, height);
    res = psycho.compute(res, width, height);
    res = desaturate.compute(res, width, height);
    res = overdriveColors.compute(res, width, height);

    byte[] result = new byte[res.length];
    for (int i = 0; i < result.length; ++i) {
      result[i] = (byte) res[i];
    }

    /* BEGIN SAVING INTEGER ARRAY TO BUFFEREDIMAGE AND THE INTO IMAGE FILE */
    File outputfile = new File(filename + "_manipulated." + datatype);
    BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    WritableRaster wr = out.getRaster();
    wr.setDataElements(0, 0, width, height, result);
    ImageIO.write(out, datatype, outputfile);
    /* END SAVING INTEGER ARRAY TO BUFFEREDIMAGE AND THE INTO IMAGE FILE */
  }
}