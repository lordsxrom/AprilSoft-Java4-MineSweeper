package model.sweeper;

import model.enums.Box;
import utils.Coord;
import utils.Utils;

public class Bombs {

    private Matrix bombMap;

    public void initBombs() {
        bombMap = new Matrix(Box.ZERO);
        placeBombs();
    }

    private void placeBombs() {
        for (int i = 0; i < Utils.BOMBS; i++) {
            while (true) {
                Coord coord = Utils.getRandomCoord();
                if (Box.BOMB == bombMap.getBox(coord)) {
                    continue;
                }
                bombMap.setBox(coord, Box.BOMB);
                increaseNumbersAroundBomb(coord);
                break;
            }
        }
    }

    private void increaseNumbersAroundBomb(Coord coord) {
        for (Coord around : Utils.getCoordsAround(coord)) {
            if (Box.BOMB != bombMap.getBox(around)) {
                bombMap.setBox(around, bombMap.getBox(around).getNextBoxType());
            }
        }
    }

    public Box getBox(Coord coord) {
        return bombMap.getBox(coord);
    }

    public Matrix getBombMap() {
        return bombMap;
    }

}