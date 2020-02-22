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

    private JPanel mainPanel, topPanel;
    private JLabel flagsLabel, timeLabel;

    private BufferedImage map;

    public View() {
        initDisplay();
        initPanel();
        initFrame();
    }

    private void initDisplay() {
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        Font font = new Font("arial", Font.BOLD, 50);

        flagsLabel = new JLabel("0");
        flagsLabel.setSize(90,60);
        flagsLabel.setFont(font);
        flagsLabel.setForeground(Color.RED);
        flagsLabel.setBackground(Color.BLACK);
        flagsLabel.setOpaque(true);
        topPanel.add(flagsLabel, BorderLayout.WEST);

        JButton btn = new JButton();
        btn.setBackground(Color.ORANGE);
        btn.setSize(60, 60);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.onStartButtonPressed();
            }
        });
        topPanel.add(btn, BorderLayout.CENTER);

        timeLabel = new JLabel("0");
        timeLabel.setSize(90,60);
        timeLabel.setFont(font);
        timeLabel.setForeground(Color.RED);
        timeLabel.setBackground(Color.BLACK);
        timeLabel.setOpaque(true);
        topPanel.add(timeLabel, BorderLayout.EAST);
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
        frame.add(topPanel, BorderLayout.NORTH);
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
        String message;
        String title;
        
        if (state == 1) {
            message = "You lose. Do you want to try again";
            title = "Game Over";
        } else if (state == 2) {
            message = "You won. Congratulation! Do you want to try again?";
            title = "Win";
        } else {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                int action = JOptionPane.showConfirmDialog(null,
                        message,
                        title,
                        JOptionPane.YES_NO_OPTION);

                if (action == JOptionPane.YES_OPTION) {
                    listener.onStartButtonPressed();
                } else {
                    System.exit(0);
                }
            }
        }).start();
    }

    @Override
    public void updateCounter(int flags) {
        flagsLabel.setText(String.valueOf(flags));
    }

    @Override
    public void updateTimer(int time) {
        timeLabel.setText(String.valueOf(time));
    }
}
