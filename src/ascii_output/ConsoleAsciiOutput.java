package ascii_output;

/**
 * Represents an output method that displays a 2D array of characters to the console.
 * This class implements the AsciiOutput interface.
 *
 * @author Dan Nirel
 */
public class ConsoleAsciiOutput implements AsciiOutput {

    /**
     * Constructs a ConsoleAsciiOutput object.
     */
    public ConsoleAsciiOutput() {}

    /**
     * Outputs the provided 2D array of characters to the console.
     * Each row of characters is printed on a separate line.
     *
     * @param chars The 2D array of characters to be outputted.
     */
    @Override
    public void out(char[][] chars) {
        for (int y = 0; y < chars.length; y++) {
            for (int x = 0; x < chars[y].length; x++) {
                System.out.print(chars[y][x] + " ");
            }
            System.out.println();
        }
    }
}
