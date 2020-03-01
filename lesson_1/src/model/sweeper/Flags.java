package model.sweeper;

import model.enums.Box;

public class Flags {

    private Matrix flagMap;

    public void initFlags() {
        flagMap = new Matrix(Box.CLOSED);
    }

    public Matrix getFlagMap() {
        return flagMap;
    }

}