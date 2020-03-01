package utils;

public class Utils {

    public static final int IMAGE_SIZE = 30;
    public static final int ROWS = 9;
    public static final int COLS = 9;
    public static final int BOMBS = 10;

    public static boolean inRange(Coord coord) {
        return coord.x >= 0 && coord.x < ROWS && coord.y >= 0 && coord.y < COLS;
    }

}