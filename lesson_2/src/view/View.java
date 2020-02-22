package view;

import presenter.ViewListener;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class View implements IView {

    private ViewListener listener;

    private JPanel mainPanel;

    private BufferedImage map;

    public View() {
        initDisplay();
        initPanel();
        initFrame();
    }

    private void initDisplay() {

    }

    private void initPanel() {
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(map, 0, 0, null);
            }
        };

        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / Utils.IMAGE_SIZE;
                int y = e.getY() / Utils.IMAGE_SIZE;
                int btn = e.getButton();
                listener.onMousePressed(x, y, btn);
            }
        });

        mainPanel.setFocusable(true);
        mainPanel.setPreferredSize(new Dimension(Utils.COLS * Utils.IMAGE_SIZE,
                Utils.ROWS * Utils.IMAGE_SIZE));
    }

    private void initFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Mine Sweeper");
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setIconImage(new ImageIcon("img/icon.png").getImage());
        frame.setLocationRelativeTo(null);

        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.pack();
    }

    public void setListener(ViewListener listener) {
        this.listener = listener;
    }

    @Override
    public void updatePanel(BufferedImage map) {
        this.map = map;
        mainPanel.repaint();
    }


    @Override
    public void updateState(int state) {

    }

    @Override
    public void updateCounter(int flags) {

    }

    @Override
    public void updateTimer(int time) {

    }
}
