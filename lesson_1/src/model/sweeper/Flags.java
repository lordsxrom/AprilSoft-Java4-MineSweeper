package model.sweeper;

import model.enums.Box;
import utils.Coord;
import utils.Utils;

import java.util.Map;

public class Flags {

    private Matrix flagMap;

    public void initFlags() {
        flagMap = new Matrix(Box.CLOSED);
    }

    public Matrix getFlagMap() {
        return flagMap;
    }
}