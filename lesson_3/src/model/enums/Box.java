package model.enums;

public enum Box {
    ZERO,
    NUM_1,
    NUM_2,
    NUM_3,
    NUM_4,
    NUM_5,
    NUM_6,
    NUM_7,
    NUM_8,
    BOMB,
    OPENED,
    CLOSED,
    FLAGGED,
    BOMBED,
    NOBOMB;

    public Object image;

    public int getBoxType() {
        return this.ordinal();
    }

    public Box getNextBoxType() {
        return Box.values()[this.ordinal() + 1];
    }

}