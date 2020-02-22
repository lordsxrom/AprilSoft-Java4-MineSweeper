package presenter;

import model.enums.State;

import java.awt.image.BufferedImage;

public interface ModelListener {

    void updateMap(BufferedImage map);

    void updateRemainingBomb(int bombs);

    void updateState(State state);

    void updateTimer(int timer);

}