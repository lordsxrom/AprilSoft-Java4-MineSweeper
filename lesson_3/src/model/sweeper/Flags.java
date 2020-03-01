package model.sweeper;

import model.enums.Box;
import utils.Coord;
import utils.Utils;

public class Flags {

    private Matrix flagMap;
    private int countFlags;
    private int countOpened;

    public void initFlags() {
        flagMap = new Matrix(Box.CLOSED);
        countOpened = Utils.COLS * Utils.ROWS;
        countFlags = Utils.BOMBS;
    }

    public Box getBox(Coord coord) {
        return flagMap.getBox(coord);
    }

    public void setOpenedToBox(Coord coord) {
        countOpened--;
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

    public void setBombedToBox(Coord coord) {
        flagMap.setBox(coord, Box.BOMBED);
    }

    public void setOpenedToClosedBombBox(Coord coord) {
        if (flagMap.getBox(coord) == Box.CLOSED) {
            flagMap.setBox(coord, Box.OPENED);
        }
    }

    public void setNobombToFlaggedSafeBox(Coord coord) {
        if (flagMap.getBox(coord) == Box.FLAGGED) {
            flagMap.setBox(coord, Box.NOBOMB);
        }
    }

    public int getCountOfFlaggedBoxesAround(Coord coord) {
        int count = 0;
        for (Coord around : Utils.getCoordsAround(coord)) {
            if (flagMap.getBox(around) == Box.FLAGGED) {
                count++;
            }
        }
        return count;
    }

    public int getCountOpened() {
        return countOpened;
    }

    public int getCountFlags() {
        return countFlags;
    }

    public Matrix getFlagMap() {
        return flagMap;
    }

}