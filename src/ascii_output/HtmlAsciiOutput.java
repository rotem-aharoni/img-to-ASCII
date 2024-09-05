package ascii_output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Represents an output method that generates an HTML file to display a 2D array of characters
 * in a web browser.
 * This class implements the AsciiOutput interface.
 *
 * @author Dan Nirel
 */
public class HtmlAsciiOutput implements AsciiOutput {
    /**
     * The baseline spacing used for rendering characters in the HTML output.
     */
    private static final double BASE_LINE_SPACING = 0.8;

    /**
     * The base font size used for rendering characters in the HTML output.
     */
    private static final double BASE_FONT_SIZE = 150.0;

    private final String fontName;
    private final String filename;

    /**
     * Constructs an HtmlAsciiOutput object with the specified filename and font name.
     *
     * @param filename The name of the HTML file to be generated.
     * @param fontName The name of the font to be used for rendering the characters in the HTML file.
     */
    public HtmlAsciiOutput(String filename, String fontName) {
        this.fontName = fontName;
        this.filename = filename;
    }

    /**
     * Outputs the provided 2D array of characters to an HTML file.
     * Each character is represented as text in the HTML file.
     *
     * @param chars The 2D array of characters to be outputted.
     */
    @Override
    public void out(char[][] chars) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(String.format(
                    "<!DOCTYPE html>\n" +
                            "<html>\n" +
                            "<body style=\"" +
                            "\tCOLOR:#000000;" +
                            "\tTEXT-ALIGN:center;" +
                            "\tFONT-SIZE:1px;\">\n" +
                            "<p style=\"" +
                            "\twhite-space:pre;" +
                            "\tFONT-FAMILY:%s;" +
                            "\tFONT-SIZE:%frem;" +
                            "\tLETTER-SPACING:0.15em;" +
                            "\tLINE-HEIGHT:%fem;\">\n",
                    fontName, BASE_FONT_SIZE / chars[0].length, BASE_LINE_SPACING));

            for (int y = 0; y < chars.length; y++) {
                for (int x = 0; x < chars[y].length; x++) {
                    String htmlRep;
                    switch (chars[y][x]) {
                        case '<': htmlRep = "&lt;"; break;
                        case '>': htmlRep = "&gt;"; break;
                        case '&': htmlRep = "&amp;"; break;
                        default:  htmlRep = String.valueOf(chars[y][x]);
                    }
                    writer.write(htmlRep);
                }
                writer.newLine();
            }
            writer.write(
                    "</p>\n" +
                            "</body>\n" +
                            "</html>\n");
        } catch (IOException e) {
            Logger.getGlobal().severe(String.format("Failed to write to \"%s\"", filename));
        }
    }
}
