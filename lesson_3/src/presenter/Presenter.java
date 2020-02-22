package presenter;

import model.IModel;
import model.enums.State;
import view.IView;

import java.awt.image.BufferedImage;

public class Presenter implements IPresenter, ViewListener, ModelListener {

    private IView view;
    private IModel model;

    public void setView(IView view) {
        this.view = view;
    }

    public void setModel(IModel model) {
        this.model = model;
    }

    public void initListeners() {
        view.setListener(this);
        model.setListener(this);
    }

    @Override
    public void onStart() {
        model.start();
    }

    @Override
    public void onMousePressed(int x, int y, int btn) {
        model.onMousePressed(x, y, btn);
    }

    @Override
    public void onStartButtonPressed() {
        model.start();
    }

    @Override
    public void updateMap(BufferedImage map) {
        view.updatePanel(map);
    }

    @Override
    public void updateRemainingBomb(int bombs) {
        view.updateCounter(bombs);
    }

    @Override
    public void updateState(State state) {
        view.updateState(state.ordinal());
    }

    @Override
    public void updateTimer(int timer) {
        view.updateTimer(timer);
    }
}
