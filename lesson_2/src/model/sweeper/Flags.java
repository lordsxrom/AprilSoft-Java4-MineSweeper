package model.sweeper;

import model.enums.Box;
import utils.Coord;
import utils.Utils;

public class Flags {

    private Matrix flagMap;
    private int countFlags;

    public void initFlags() {
        flagMap = new Matrix(Box.CLOSED);
        countFlags = Utils.BOMBS;
    }

    public Box getBox(Coord coord) {
        return flagMap.getBox(coord);
    }

    public void setOpenedToBox(Coord coord) {
        flagMap.setBox(coord, Box.OPENED);
    }

    public void toggleFlaggedToBox(Coord coord) {
        switch (flagMap.getBox(coord)) {
            case FLAGGED:
                countFlags++;
                flagMap.setBox(coord, Box.CLOSED);
                break;
            case CLOSED:
                countFlags--;
                flagMap.setBox(coord, Box.FLAGGED);
                break;
        }
    }

    public int getCountFlags() {
        return countFlags;
    }

    public Matrix getFlagMap() {
        return flagMap;
    }

}