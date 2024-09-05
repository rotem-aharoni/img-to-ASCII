package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import errors.*;
import image.Image;
import image.PaddingImage;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.lang.module.ResolutionException;
import java.util.TreeSet;

/**
 * The Shell class provides a command-line interface for interacting with the ASCII Art generation system.
 * Users can input various commands to modify settings such as the character pool, resolution, image file,
 * output method, and run the ASCII art generation algorithm.
 * The available commands include:
 * "chars": Displays the current character pool.
 * "add": Adds characters to the character pool.
 * "remove": Removes characters from the character pool.
 * "res": Changes the resolution of the ASCII art.
 * "asciiArt": Runs the ASCII art generation algorithm.
 * "output": Changes the output method for displaying ASCII art.
 * "image": Changes the input image file for ASCII art generation.
 * The Shell class also handles various exceptions for incorrect inputs or operations.
 * It provides feedback messages for errors such as incorrect commands, invalid format, or file loading
 * issues.
 */
public class Shell {

    /**
     * The default resolution for ASCII art generation.
     */
    private final static int DEFAULT_RESOLUTION = 128;

    /**
     * The default path for the input image file.
     */
    private final static String DEFAULT_IMAGE_PATH = "cat.jpeg";

    /**
     * The default output method for displaying ASCII art.
     */
    private final static AsciiOutput DEFAULT_OUTPUT = new ConsoleAsciiOutput();

    /**
     * The default character set used in ASCII art generation.
     */
    private final static char[] DEFAULT_CHARSET = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * The command to exit the program.
     */
    private final static String END_PROGRAM = "exit";

    /**
     * The line start indicator for user input.
     */
    private final static String LINE_START = ">>> ";

    /**
     * Command keyword for displaying the character pool.
     */
    private final static String CHARACTER_POOL = "chars";

    /**
     * Command keyword for adding characters to the character pool.
     */
    private final static String ADD_TO_CHARACTER_POOL = "add";

    /**
     * Command keyword for removing characters from the character pool.
     */
    private final static String REMOVE_FROM_CHARACTER_POOL = "remove";

    /**
     * Command keyword for changing the resolution of the ASCII art.
     */
    private final static String CHANGE_RESOLUTION = "res";

    /**
     * Command keyword for running the ASCII art generation algorithm.
     */
    private final static String RUN_ALGORITHM = "asciiArt";

    /**
     * Command keyword for changing the output method of ASCII art display.
     */
    private final static String CHANGE_OUTPUT = "output";

    /**
     * Command keyword for changing the input image file for ASCII art generation.
     */
    private final static String CHANGE_IMAGE = "image";

    /**
     * Output method for displaying ASCII art in the console.
     */
    private final static String CONSOLE_OUTPUT = "console";

    /**
     * Output method for generating HTML output of ASCII art.
     */
    private final static String HTML_OUTPUT = "html";

    /**
     * Default filename for HTML output.
     */
    private final static String HTML_FILENAME = "out.html";

    /**
     * Default font for HTML output.
     */
    private final static String HTML_FONT = "Courier New";

    /**
     * Command keyword for including all characters in the character pool.
     */
    private final static String ALL_CHARS_COMMAND = "all";

    /**
     * Command keyword for including space character in the character pool.
     */
    private final static String SPACE_CHAR_COMMAND = "space";

    /**
     * Command keyword for increasing the resolution of ASCII art.
     */
    private final static String RESOLUTION_UP_COMMAND = "up";

    /**
     * Command keyword for decreasing the resolution of ASCII art.
     */
    private final static String RESOLUTION_DOWN_COMMAND = "down";

    /**
     * Pattern to match for specifying a range of characters.
     */
    private final static String PATTERN_FOR_RANGE = ".-.";

    /**
     * Error message for failure to add characters to the pool due to incorrect format.
     */
    private final static String ADD_ERROR_MSG = "Did not add due to incorrect format.";

    /**
     * Error message for failure to remove characters from the pool due to incorrect format.
     */
    private final static String REMOVE_ERROR_MSG = "Did not remove due to incorrect format.";

    /**
     * Error message for failure to change output method due to incorrect format.
     */
    private final static String OUTPUT_ERROR_MSG = "Did not change output method due to incorrect format.";

    /**
     * Error message for failure to change resolution due to incorrect format.
     */
    private final static String RESOLUTION_ERROR_MSG = "Did not change resolution due to incorrect format.";

    /**
     * Error message for failure to execute due to incorrect command.
     */
    private final static String GENERAL_ERROR_MSG = "Did not execute due to incorrect command.";

    /**
     * Error message for failure to execute due to problem with image file.
     */
    private final static String IMAGE_ERROR_MSG = "Did not execute due to problem with image file.";

    /**
     * Error message for failure to execute due to empty charset.
     */
    private final static String ALGORITHM_ERROR_MSG = "Did not execute. Charset is empty.";

    /**
     * Message indicating the resolution has been changed.
     */
    private final static String CHANGE_RES_MSG = "Resolution set to %d.";

    /**
     * Error message for failure to change resolution due to exceeding boundaries.
     */
    private final static String RES_BOUNDARIES_ERROR_MSG = "Did not change resolution due to exceeding " +
            "boundaries.";

    /**
     * The ASCII value of the first printable character in the ASCII character set.
     */
    private static final int START_ASCII = 32;

    /**
     * The ASCII value of the last printable character in the ASCII character set.
     */
    private static final int END_ASCII = 126;

    private AsciiOutput asciiOutput;
    private Image image;
    private PaddingImage padding;
    private int resolution;
    private final SubImgCharMatcher subImgCharMatcher;
    private final TreeSet<Character> charset;

    /**
     * Constructs a new Shell instance with default settings.
     * Initializes the character set, resolution, character matcher, output method, and loads the default
     * image.
     */
    public Shell() {
        charset = new TreeSet<>();
        for (char c : DEFAULT_CHARSET) {
            charset.add(c);
        }
        resolution = DEFAULT_RESOLUTION;
        subImgCharMatcher = new SubImgCharMatcher(DEFAULT_CHARSET);
        asciiOutput = DEFAULT_OUTPUT;
        try {
            String[] input = {CHANGE_IMAGE, DEFAULT_IMAGE_PATH};
            changeImageFile(input);
        } catch (ImageException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Starts the interactive shell for user interaction.
     * Reads user input, processes commands, and executes corresponding operations.
     */
    public void run() {
        System.out.print(LINE_START);
        String[] line = KeyboardInput.readLine().split(" ");
        while (!line[0].equals(END_PROGRAM)) {
            try {
                switch (line[0]) {
                    case CHARACTER_POOL:
                        if (line.length != 1) {
                            throw new GeneralException(GENERAL_ERROR_MSG);
                        }
                        showPoolChar();
                        break;
                    case ADD_TO_CHARACTER_POOL:
                        addChar(line);
                        break;
                    case REMOVE_FROM_CHARACTER_POOL:
                        removeChar(line);
                        break;
                    case CHANGE_RESOLUTION:
                        changeResolution(line);
                        break;
                    case CHANGE_IMAGE:
                        changeImageFile(line);
                        break;
                    case CHANGE_OUTPUT:
                        changeOutput(line);
                        break;
                    case RUN_ALGORITHM:
                        if (line.length != 1) {
                            throw new GeneralException(GENERAL_ERROR_MSG);
                        }
                        runAlgorithm();
                        break;
                    default:
                        throw new GeneralException(GENERAL_ERROR_MSG);
                }
                System.out.print(LINE_START);
                line = KeyboardInput.readLine().split(" ");
            } catch (AddException | OutputException | RemoveException | ResolutionException | GeneralException
                     | ImageException | AlgorithmException e) {
                System.out.println(e.getMessage());
                System.out.print(LINE_START);
                line = KeyboardInput.readLine().split(" ");
            }
        }
    }

    /**
     * Displays the current character pool.
     */
    private void showPoolChar() {
        for (char c: charset) {
            System.out.printf("%c ", c);
        }
        System.out.println();
    }

    /**
     * Generates an array of characters based on the input criteria.
     *
     * @param input the user input indicating which characters to generate
     * @return an array of characters based on the input criteria
     * @throws NullPointerException if the input format is incorrect
     */
    private char[] charsToUpdate(String input) {
        char[] charsToUpdateArray;
        int startAscii = START_ASCII;
        if (input.equals(ALL_CHARS_COMMAND)) {
            charsToUpdateArray = new char[END_ASCII - startAscii + 1];
            for (int i = 0; i < END_ASCII - startAscii + 1; i++) {
                charsToUpdateArray[i] = (char) (startAscii + i);
            }
            return charsToUpdateArray;
        } else if (input.equals(SPACE_CHAR_COMMAND)) {
            charsToUpdateArray = new char[1];
            charsToUpdateArray[0] = ' ';
            return charsToUpdateArray;
        } else if (input.length() == 1) {
            charsToUpdateArray = new char[1];
            charsToUpdateArray[0] = input.toCharArray()[0];
            return charsToUpdateArray;
        } else if (input.matches(PATTERN_FOR_RANGE)) {
            char[] charArrayRange = input.toCharArray();
            return charsInRange(charArrayRange[0], charArrayRange[2]);
        } else {
            throw new NullPointerException();
        }
    }


    /**
     * Updates the character set based on the user input.
     *
     * @param input the user input indicating which characters to update
     * @throws AddException if the input format is incorrect for adding characters
     */
    private void addChar(String[] input) throws AddException {
        if (input.length != 2) {
            throw new AddException(ADD_ERROR_MSG);
        }
        try {
            char[] charsToAdd = charsToUpdate(input[1]);
            for (char c: charsToAdd) {
                subImgCharMatcher.addChar(c);
                charset.add(c);
            }
        }
        catch (NullPointerException e) {
            throw new AddException(ADD_ERROR_MSG);
        }
    }

    /**
     * Removes characters from the character set based on the user input.
     *
     * @param input the user input indicating which characters to remove
     * @throws RemoveException if the input format is incorrect for removing characters
     */
    private void removeChar(String[] input) throws RemoveException {
        if (input.length != 2) {
            throw new RemoveException(REMOVE_ERROR_MSG);
        }
        try {
            char[] charsToRemove = charsToUpdate(input[1]);
            for (char c: charsToRemove) {
                subImgCharMatcher.removeChar(c);
                charset.remove(c);
            }
        }
        catch (NullPointerException e) {
            throw new RemoveException(REMOVE_ERROR_MSG);
        }
    }

    /**
     * Generates an array of characters within a specified range.
     *
     * @param a the start character of the range
     * @param b the end character of the range
     * @return an array of characters within the specified range
     */
    private char[] charsInRange(char a, char b) {
        char start;
        char end;
        if (a < b) {
            start = a;
            end = b;
        } else {
            start = b;
            end = a;
        }

        char[] charsInRange = new char[end-start+1];
        for (char i = 0; i < end-start+1; i++) {
            charsInRange[i] = (char)(start + i);
        }
        return charsInRange;
    }

    /**
     * Changes the resolution of the ASCII art.
     *
     * @param input the user input indicating the change in resolution
     * @throws ResolutionException if the input format is incorrect or if the new resolution exceeds
     *         boundaries
     */
    private void changeResolution(String[] input) throws ResolutionException {
        if (input.length != 2) {
            throw new ResolutionException(RESOLUTION_ERROR_MSG);
        }
        int newResolution;
        if (input[1].equals(RESOLUTION_UP_COMMAND)) {
            newResolution = resolution * 2;
        } else if (input[1].equals(RESOLUTION_DOWN_COMMAND)) {
            newResolution = resolution / 2;
        } else {
            throw new ResolutionException(RESOLUTION_ERROR_MSG);
        }

        int maxWidthHeight = Math.max(1, padding.getWidth() / padding.getHeight());
        if (newResolution <= padding.getWidth() && newResolution >= maxWidthHeight) {
            resolution = newResolution;
        } else {
            throw new ResolutionException(RES_BOUNDARIES_ERROR_MSG);
        }
        System.out.println(CHANGE_RES_MSG.replace("%d" , String.valueOf (resolution) ));
    }

    /**
     * Changes the input image file for ASCII art generation.
     *
     * @param input the input fot the new image (include the name of the new input image)
     * @throws ImageException if there is a problem with loading the image file
     */
    private void changeImageFile(String[] input) throws ImageException {
        if (input.length != 2) {
            throw new ImageException(IMAGE_ERROR_MSG);
        }
        try {
            image = new Image(input[1]);
            padding = new PaddingImage(image);
        }
        catch (IOException e) {
            throw new ImageException(IMAGE_ERROR_MSG);
        }
    }

    /**
     * Changes the output method for displaying ASCII art.
     *
     * @param input the user input indicating the new output method
     * @throws OutputException if the input format is incorrect for changing the output method
     */
    private void changeOutput(String[] input) throws OutputException {
        if (input.length != 2) {
            throw new OutputException(OUTPUT_ERROR_MSG);
        }
        if (input[1].equals(CONSOLE_OUTPUT)) {
            asciiOutput = new ConsoleAsciiOutput();
        } else if (input[1].equals(HTML_OUTPUT)) {
            asciiOutput = new HtmlAsciiOutput(HTML_FILENAME, HTML_FONT);
        } else {
            throw new OutputException(OUTPUT_ERROR_MSG);
        }
    }

    /**
     * Runs the ASCII art generation algorithm.
     *
     * @throws AlgorithmException if the charset is empty
     */
    private void runAlgorithm() throws AlgorithmException {
        if (charset.isEmpty()) {
            throw new AlgorithmException(ALGORITHM_ERROR_MSG);
        }
        AsciiArtAlgorithm asciiArtAlgorithm = new AsciiArtAlgorithm(image, resolution, subImgCharMatcher);
        asciiOutput.out(asciiArtAlgorithm.run());
    }

    /**
     * Main method to start the Shell program.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Shell shell = new Shell();
        shell.run();
    }
}
