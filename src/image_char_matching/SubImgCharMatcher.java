package image_char_matching;

import java.util.TreeMap;
import java.util.TreeSet;

/**
 * A class for matching characters to image brightness values.
 * This class provides functionality to match characters from a charset to image brightness values.
 */
public class SubImgCharMatcher {
    /**
     * The size used for calculating the brightness of characters.
     */
    private final int CHAR_BRIGHT_SIZE = 16;
    private final TreeMap<Double, TreeSet<Character>> brightnessMap;
    private final TreeMap<Double, TreeSet<Character>> stageOneBrightness;


    /**
     * Constructs a new SubImgCharMatcher with the given charset.
     *
     * @param charset the character set to use for matching
     */
    public SubImgCharMatcher(char[] charset) {
        stageOneBrightness = new TreeMap<>();
        brightnessMap = new TreeMap<>();
        for (char c : charset) {
            stageOneBrightness.computeIfAbsent(calculateBrightness(c), k -> new TreeSet<>()).add(c);
        }
        initBrightnessMap();
    }

    /**
     * Initializes the brightness map.
     * Adjusts the brightness values to a normalized range between 0 and 1.
     */
        private void initBrightnessMap() {
            for(Double key : stageOneBrightness.keySet()){
                for(Character c :stageOneBrightness.get(key) ){
                    brightnessMap.computeIfAbsent(newCharBrightness(key,
                            stageOneBrightness.firstEntry().getKey(),stageOneBrightness.lastEntry().getKey()),
                            k -> new TreeSet<>()).add(c);
                }
            }
        }

    /**
     * Retrieves the character associated with the closest brightness value to the given brightness.
     *
     * @param brightness the brightness value to match against
     * @return the character associated with the closest brightness value
     */
    public char getCharByImageBrightness(double brightness) {
        Double closestUpVal = brightnessMap.ceilingKey(brightness);
        Double closestDownVal = brightnessMap.floorKey(brightness);
        TreeSet<Character> closestEntry;
        TreeSet<Character> closestDownEntry = brightnessMap.get(closestDownVal);
        TreeSet<Character> closestUpEntry = brightnessMap.get(closestUpVal);
        if(Math.abs(closestUpVal-brightness) < Math.abs(closestDownVal-brightness)) {
           closestEntry = closestUpEntry;
        }
        else if(Math.abs(closestUpVal-brightness) > Math.abs(closestDownVal-brightness)) {
            closestEntry = closestDownEntry;
        }
        else {
            if(closestDownEntry.first() < closestUpEntry.first()) {
                return closestDownEntry.first();
            }
                return closestUpEntry.first();
        }

        return closestEntry.first();
    }

    /**
     * Adds a character to the brightness map.
     *
     * @param c the character to add
     */
    public void addChar(char c) {
        double charBrightness = calculateBrightness(c);
        stageOneBrightness.computeIfAbsent(charBrightness, k -> new TreeSet<>()).add(c);
        brightnessMap.clear();
        initBrightnessMap();

    }

    /**
     * Removes a character from the brightness map.
     *
     * @param c the character to remove
     */
    public void removeChar(char c) {
        double charBrightness = calculateBrightness(c);
        stageOneBrightness.computeIfPresent(charBrightness, (key, set) -> {
            set.remove(c);
            if (set.isEmpty()) {
                return null; // Remove the entry if the set is empty
            }
            return set;
        });
        brightnessMap.clear();
        initBrightnessMap();

    }

    /**
     * Calculates the brightness value for a given character.
     *
     * @param c the character to calculate brightness for
     * @return the brightness value
     */
    private double calculateBrightness(char c) {
        boolean[][] charBrightness;
        int countTrue = 0;
        charBrightness = CharConverter.convertToBoolArray(c);
        for (boolean[] brightness : charBrightness) {
            for (boolean b : brightness) {
                if (b) {
                    countTrue += 1;
                }
            }
        }
        return ((double) countTrue / CHAR_BRIGHT_SIZE * CHAR_BRIGHT_SIZE);
    }

    /**
     * Normalizes the brightness value to a range between 0 and 1.
     *
     * @param brightness the brightness value to normalize
     * @param minBright the minimum brightness value in the map
     * @param maxBright the maximum brightness value in the map
     * @return the normalized brightness value
     */
    private double newCharBrightness(double brightness, double minBright, double maxBright) {
        double numerator = brightness - minBright;
        double denominator = maxBright - minBright;
        return numerator / denominator;
    }
}
