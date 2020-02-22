import model.IModel;
import model.Model;
import presenter.IPresenter;
import presenter.Presenter;
import view.IView;
import view.View;

import javax.swing.*;

public class Application {

    public Application() {
        IView view = new View();
        IModel model = new Model();

        IPresenter presenter = new Presenter();
        presenter.setView(view);
        presenter.setModel(model);
        presenter.initListeners();
        presenter.onStart();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Application();
            }
        });
    }
}
