package image;

import java.awt.*;

/**
 * Represents a sub-image extracted from a larger image.
 * This class inherits from the Image class and adds functionality for calculating and retrieving the
 * brightness of the sub-image.
 */
public class SubImage extends Image {

    /**
     * The weight of the red component in calculating grayscale brightness.
     */
    private static final double RED_WEIGHT = 0.2126;

    /**
     * The weight of the green component in calculating grayscale brightness.
     */
    private static final double GREEN_WEIGHT = 0.7152;

    /**
     * The weight of the blue component in calculating grayscale brightness.
     */
    private static final double BLUE_WEIGHT = 0.0722;

    /**
     * The maximum value of an RGB color component.
     */
    private static final int MAX_RGB_NUM = 255;

    /**
     * The brightness of the sub-image, calculated based on grayscale pixel values.
     */
    private double brightness;

    /**
     * Constructs a SubImage object with the provided pixel array, width, and height.
     * The brightness of the sub-image is automatically calculated upon construction.
     *
     * @param pixelArray The pixel array representing the colors of the sub-image.
     * @param width      The width of the sub-image.
     * @param height     The height of the sub-image.
     */
    public SubImage(Color[][] pixelArray, int width, int height) {
        super(pixelArray, width, height);
        calculateSubImageBrightness();
    }

    /**
     * Retrieves the brightness of the sub-image.
     *
     * @return The brightness value of the sub-image.
     */
    public double getBrightness() {
        return brightness;
    }

    /**
     * Calculates the brightness of the sub-image based on its grayscale pixel values.
     */
    private void calculateSubImageBrightness() {
        double[][] grayPixelArray = createGrayScalePixelArray();
        double sum = 0;
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                double grayPixelScale = calculateGrayPixel(getPixel(i, j));
                grayPixelArray[i][j] = grayPixelScale;
                sum += grayPixelScale;
            }
        }
        brightness = sum / (getHeight() * getWidth() * MAX_RGB_NUM);
    }

    /**
     * Creates a 2D array of grayscale pixel values from the sub-image.
     *
     * @return A 2D array representing the grayscale pixel values of the sub-image.
     */
    private double[][] createGrayScalePixelArray() {
        double[][] grayPixelArray = new double[getHeight()][getWidth()];
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                grayPixelArray[i][j] = calculateGrayPixel(getPixel(i, j));
            }
        }
        return grayPixelArray;
    }

    /**
     * Calculates the grayscale pixel value from the given color.
     *
     * @param color The color from which to calculate the grayscale pixel value.
     * @return The grayscale pixel value calculated from the color.
     */
    private static double calculateGrayPixel(Color color) {
        return color.getRed() * RED_WEIGHT + color.getGreen() * GREEN_WEIGHT + color.getBlue() * BLUE_WEIGHT;
    }
}
