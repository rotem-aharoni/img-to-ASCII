package image_char_matching;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Converts characters into binary "images" represented as 2D arrays of booleans.
 * The class is inspired by and adapted from the AsciiImgCache class from the asciimg project,
 * described in the blog post "ASCII Art Generator in Java" by Dan Nirel and Rachel Behar.
 * The class utilizes Java's AWT and BufferedImage to render characters into images.
 * Font and pixel resolution settings are configurable.
 *
 * @author Dan Nirel, Rachel Behar
 */
public class CharConverter {

    /**
     * X-axis offset factor for character rendering.
     */
    private static final double X_OFFSET_FACTOR = 0.2;

    /**
     * Y-axis offset factor for character rendering.
     */
    private static final double Y_OFFSET_FACTOR = 0.75;

    /**
     * Default font name used for character rendering.
     */
    private static final String FONT_NAME = "Courier New";

    /**
     * Default pixel resolution used for rendering characters into images.
     */
    public static final int DEFAULT_PIXEL_RESOLUTION = 16;

    /**
     * Default constructor for the CharConverter class.
     */
    public CharConverter() {}


    /**
     * Converts a given character to a binary "image" represented as a 2D array of booleans.
     * The character is rendered using the specified font and pixel resolution.
     *
     * @param c The character to convert.
     * @return A 2D boolean array representing the binary image of the character.
     */
    public static boolean[][] convertToBoolArray(char c) {
        BufferedImage img = getBufferedImage(c, FONT_NAME, DEFAULT_PIXEL_RESOLUTION);
        boolean[][] matrix = new boolean[DEFAULT_PIXEL_RESOLUTION][DEFAULT_PIXEL_RESOLUTION];
        for (int y = 0; y < DEFAULT_PIXEL_RESOLUTION; y++) {
            for (int x = 0; x < DEFAULT_PIXEL_RESOLUTION; x++) {
                matrix[y][x] = img.getRGB(x, y) == 0; // Check if the color is black
            }
        }
        return matrix;
    }

    /**
     * Renders the specified character into a BufferedImage with the specified font and pixel resolution.
     *
     * @param c             The character to render.
     * @param fontName      The name of the font to use.
     * @param pixelsPerRow  The pixel resolution per row.
     * @return A BufferedImage containing the rendered character.
     */
    private static BufferedImage getBufferedImage(char c, String fontName, int pixelsPerRow) {
        String charStr = Character.toString(c);
        Font font = new Font(fontName, Font.PLAIN, pixelsPerRow);
        BufferedImage img = new BufferedImage(pixelsPerRow, pixelsPerRow, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.setFont(font);
        int xOffset = (int) Math.round(pixelsPerRow * X_OFFSET_FACTOR);
        int yOffset = (int) Math.round(pixelsPerRow * Y_OFFSET_FACTOR);
        g.drawString(charStr, xOffset, yOffset);
        return img;
    }
}
