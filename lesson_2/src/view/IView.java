package view;

import presenter.ViewListener;

import java.awt.image.BufferedImage;

public interface IView {

    void setListener(ViewListener listener);

    void updatePanel(BufferedImage map);

    void updateState(int state);

    void updateCounter(int count);

    void updateTimer(int time);

}
