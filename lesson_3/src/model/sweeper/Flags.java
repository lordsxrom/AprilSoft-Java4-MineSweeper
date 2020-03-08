package model.sweeper;

import model.enums.Box;
import utils.Coord;
import utils.Utils;

public class Flags {

    private Matrix flagMap;
    private int countFlags;
    private int countClosed;

    public void initFlags() {
        flagMap = new Matrix(Box.CLOSED);
        countFlags = Utils.BOMBS;

        countClosed = Utils.COLS * Utils.ROWS;
    }

    public Box getBox(Coord coord) {
        return flagMap.getBox(coord);
    }

    public void setOpenedToBox(Coord coord) {
        countClosed--;
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

    public int getCountClosed() {
        return countClosed;
    }

    public int getCountFlags() {
        return countFlags;
    }

    public Matrix getFlagMap() {
        return flagMap;
    }

}