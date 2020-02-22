package model;

import model.enums.Box;
import model.enums.State;
import model.sweeper.Flags;
import presenter.ModelListener;
import utils.Coord;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Map;

public class Model implements IModel {

    private ModelListener listener;

    private Flags flags;
    private State state;

    public Model() {
        initImages();

        flags = new Flags();
    }

    private void initImages() {
        for (Box box : Box.values()) {
            String filename = "img/" + box.name().toLowerCase() + ".png";
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            box.image = new ImageIcon(classloader.getResource(filename)).getImage();
        }
    }

    public void start() {
        flags.initFlags();
        state = State.PLAY;

        listener.updateState(state);
        listener.updateRemainingBomb(flags.getCountFlags());
        listener.updateMap(draw());
    }

    @Override
    public void onMousePressed(int x, int y, int type) {
        if (isPlaying()) return;

        Coord coord = new Coord(x, y);

        if (type == MouseEvent.BUTTON1) {
            openBox(coord);
        } else if (type == MouseEvent.BUTTON3) {
            flags.toggleFlaggedToBox(coord);
        } else if (type == MouseEvent.BUTTON2) {
            start();
            return;
        }

        listener.updateRemainingBomb(flags.getCountFlags());
        listener.updateMap(draw());
    }

    public void setListener(ModelListener listener) {
        this.listener = listener;
    }

    private void openBox(Coord coord) {
        Box box = flags.getBox(coord);
        switch (box) {
            case OPENED:
                return;
            case FLAGGED:
                return;
            case CLOSED:
                flags.setOpenedToBox(coord);
                return;
        }
    }


    private boolean isPlaying() {
        return state != State.PLAY;
    }

    private BufferedImage draw() {
        BufferedImage img = new BufferedImage(Utils.COLS * Utils.IMAGE_SIZE,
                Utils.ROWS * Utils.IMAGE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();

        for (Map.Entry<Coord, Box> entry : flags.getFlagMap().getMap().entrySet()) {
            Coord coord = entry.getKey();
            Box flagBox = entry.getValue();

                g.drawImage((Image) flagBox.image,
                        coord.x * Utils.IMAGE_SIZE, coord.y * Utils.IMAGE_SIZE,
                        Utils.IMAGE_SIZE, Utils.IMAGE_SIZE, null);
        }

        return img;
    }
}
