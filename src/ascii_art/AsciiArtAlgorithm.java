package ascii_art;

import image.Image;
import image.PaddingImage;
import image.SubImage;
import image_char_matching.SubImgCharMatcher;
import java.util.ArrayList;

/**
 * AsciiArtAlgorithm class is responsible for converting an image into ASCII art representation.
 * It divides the input image into sub images, matches each sub-image to a corresponding character based on
 * brightness, and generates a 2D array of characters representing the ASCII art.
 */
public class AsciiArtAlgorithm {
    private final PaddingImage paddingImage;
    private final int resolution;
    private final SubImgCharMatcher subImgCharMatcher;

    /**
     * Constructs an AsciiArtAlgorithm object with the specified parameters.
     *
     * @param image The input image to be converted into ASCII art.
     * @param resolution The resolution for creating sub-images.
     * @param subImgCharMatcher The character matcher for mapping image brightness to characters.
     */
    public AsciiArtAlgorithm(Image image, int resolution, SubImgCharMatcher subImgCharMatcher) {
        this.subImgCharMatcher = subImgCharMatcher;
        this.resolution = resolution;
        this.paddingImage = new PaddingImage(image);
    }

    /**
     * Runs the ASCII art conversion algorithm and returns the generated ASCII art as a 2D character array.
     *
     * @return A 2D character array representing the ASCII art.
     */
    public char[][] run() {
        // Create sub-images from the padded image
        ArrayList<SubImage> subImages = paddingImage.createSubImages(resolution);

        // Determine the dimension of the charset
        int dimension = paddingImage.getHeight() / subImages.get(0).getHeight();

        // Initialize the charset array
        char[][] charset = new char[dimension][resolution];

        // Match each sub-image brightness to a character and populate the charset array
        for (int i = 0; i < subImages.size(); i++) {
            charset[i / resolution][i % dimension] = subImgCharMatcher.getCharByImageBrightness(
                    subImages.get(i).getBrightness());
        }

        return charset;
    }
}
