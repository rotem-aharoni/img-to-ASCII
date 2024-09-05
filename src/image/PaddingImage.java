package image;

import java.awt.*;
import java.util.ArrayList;

/**
 * Represents an image padded with white pixels to achieve a power of two dimensions.
 * This class provides functionality to pad an input image with white pixels until both its width and height
 * are powers of two. This is commonly required for certain image processing algorithms, such as Fourier
 * transforms, which operate more efficiently on images with dimensions that are powers of two.
 * Additionally, the class offers methods to create sub-images from the padded image. These sub-images
 * can be useful for dividing the image into smaller parts for parallel processing or other operations.
 */

public class PaddingImage {

    /**
     * The color value representing white in RGB format.
     */
    private static final int WHITE_COLOR = 255;

    /**
     * The original image before padding.
     */
    private final Image image;

    /**
     * The height of the padded image.
     */
    private int height;
    private int width;

    /**
     * The list of sub-images created from the padded image.
     */
    private final ArrayList<SubImage> subImages;

    /**
     * Constructs a PaddingImage object by padding the input image with white pixels.
     *
     * @param image The original image to be padded.
     */
    public PaddingImage(Image image) {
        this.image = imagePadding(image);
        this.subImages = new ArrayList<>();
    }

    /**
     * Pads the input image with white pixels to achieve a power of two dimensions.
     *
     * @param image The original image to be padded.
     * @return The padded image.
     */
    private Image imagePadding(Image image) {
        height = updateNumberToPowerOfTwo(image.getHeight());
        width = updateNumberToPowerOfTwo(image.getWidth());
        int paddingHeight = (height - image.getHeight()) / 2;
        int paddingWidth = (width - image.getWidth()) / 2;

        Color[][] pixelArray = new Color[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i < paddingHeight || i >= (height - paddingHeight) || j < paddingWidth ||
                        j >= (width - paddingWidth)) {
                    pixelArray[i][j] = new Color(WHITE_COLOR, WHITE_COLOR, WHITE_COLOR);
                } else {
                    pixelArray[i][j] = image.getPixel(i - paddingHeight, j - paddingWidth);
                }
            }
        }
        return new Image(pixelArray, width, height);
    }

    /**
     * Updates a given number to the nearest power of two.
     *
     * @param number The input number to be updated.
     * @return The updated number, now a power of two.
     */
    private int updateNumberToPowerOfTwo(int number) {
        if (!(number != 0 && (number & (number - 1)) == 0)) {
            return (int) Math.pow(2, Math.ceil(Math.log(number) / Math.log(2)));
        }
        return number;
    }

    /**
     * Retrieves the height of the padded image.
     *
     * @return The height of the padded image.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Retrieves the width of the padded image.
     *
     * @return The width of the padded image.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Creates sub-images from the padded image for further processing.
     *
     * @param resolution The resolution of each sub-image.
     * @return The list of sub-images created from the padded image.
     */
    public ArrayList<SubImage> createSubImages(int resolution) {
        int dimension = image.getWidth() / resolution;

        for (int i = 0; i < image.getHeight(); i += dimension) {
            for (int j = 0; j < image.getWidth(); j += dimension) {
                Color[][] pixelArray = new Color[dimension][dimension];
                for (int x = 0; x < dimension; x++) {
                    for (int y = 0; y < dimension; y++) {
                        pixelArray[x][y] = image.getPixel(x + i, y + j);
                    }
                }
                subImages.add(new SubImage(pixelArray, dimension, dimension));
            }
        }
        return subImages;
    }
}
