package model;

import model.enums.Box;
import model.enums.State;
import model.sweeper.Bombs;
import model.sweeper.Flags;
import presenter.ModelListener;
import utils.Coord;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Map;

public class Model implements IModel {

    private ModelListener listener;

    private Bombs bombs;
    private Flags flags;
    private State state;

    private int time;
    private Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            listener.updateTimer(time++);
        }
    });

    public Model() {
        initImages();

        bombs = new Bombs();
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
        bombs.initBombs();
        flags.initFlags();
        state = State.PLAY;
        time = 0;
        timer.start();

        listener.updateState(state);
        listener.updateRemainingBomb(flags.getCountFlags());
        listener.updateTimer(time);
        listener.updateMap(draw());
    }

    @Override
    public void onMousePressed(int x, int y, int type) {
        if (isPlaying()) return;

        Coord coord = new Coord(x, y);

        if (type == MouseEvent.BUTTON1) {
            openBox(coord);
            checkWin();
        } else if (type == MouseEvent.BUTTON3) {
            flags.toggleFlaggedToBox(coord);
            checkWin();
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
                setOpenedToClosedBoxesAroundNumber(coord);
                return;
            case FLAGGED:
                return;
            case CLOSED:
                switch (bombs.getBox(coord)) {
                    case ZERO:
                        openBoxesAround(coord);
                        return;
                    case BOMB:
                        openBombs(coord);
                        return;
                    default:
                        flags.setOpenedToBox(coord);
                        return;
                }
        }
    }

    private void checkWin() {
        if (flags.getCountOpened() == Utils.BOMBS && isPlaying()) {
            state = State.WIN;
            timer.stop();
            listener.updateState(state);
        }
    }

    private void openBombs(Coord bombed) {
        state = State.BOMBED;
        flags.setBombedToBox(bombed);

        for (Map.Entry<Coord, Box> entry : bombs.getBombMap().getMap().entrySet()) {
            Coord coord = entry.getKey();
            Box box = entry.getValue();
            if (Box.BOMB == box) {
                flags.setOpenedToClosedBombBox(coord);
            } else {
                flags.setNobombToFlaggedSafeBox(coord);
            }
        }

        timer.stop();
        listener.updateState(state);
    }

    private void openBoxesAround(Coord coord) {
        flags.setOpenedToBox(coord);
        for (Coord around : Utils.getCoordsAround(coord)) {
            openBox(around);
        }
    }

    private boolean isPlaying() {
        return state != State.PLAY;
    }

    private void setOpenedToClosedBoxesAroundNumber(Coord coord) {
        if (bombs.getBox(coord) != Box.BOMB) {
            if (flags.getCountOfFlaggedBoxesAround(coord) == bombs.getBox(coord).getBoxType()) {
                for (Coord around : Utils.getCoordsAround(coord)) {
                    if (flags.getBox(around) == Box.CLOSED) {
                        openBox(around);
                    }
                }
            }
        }
    }

    private BufferedImage draw() {
        BufferedImage img = new BufferedImage(Utils.COLS * Utils.IMAGE_SIZE,
                Utils.ROWS * Utils.IMAGE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();

        for (Map.Entry<Coord, Box> entry : flags.getFlagMap().getMap().entrySet()) {
            Coord coord = entry.getKey();
            Box flagBox = entry.getValue();
            Box bombBox = bombs.getBombMap().getBox(coord);

            if (Box.OPENED == flagBox && Box.ZERO != bombBox) {
                g.drawImage((Image) bombBox.image,
                        coord.x * Utils.IMAGE_SIZE, coord.y * Utils.IMAGE_SIZE,
                        Utils.IMAGE_SIZE, Utils.IMAGE_SIZE, null);
            } else {
                g.drawImage((Image) flagBox.image,
                        coord.x * Utils.IMAGE_SIZE, coord.y * Utils.IMAGE_SIZE,
                        Utils.IMAGE_SIZE, Utils.IMAGE_SIZE, null);
            }
        }

        return img;
    }
}
