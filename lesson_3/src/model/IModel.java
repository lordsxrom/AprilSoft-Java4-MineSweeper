package model;

import presenter.ModelListener;

public interface IModel {

    void setListener(ModelListener listener);

    void start();

    void onMousePressed(int x, int y, int type);

}
