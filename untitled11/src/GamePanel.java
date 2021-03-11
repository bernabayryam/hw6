import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 1000;
    static final int SCREEN_HEIGHT = 700;
    static final int UNIT_SIZE = 20;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    static final int DELAY = 87;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 1;
    int applesEaten;
    int foodX;
    int foodY;
    int wallX;
    int wallY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;



    public GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    /**Start game
     *
     */

    public void startGame() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        smokoFood();
    }

    public void paint(Graphics g) {
        super.paint(g);
        draw(g);
    }


    public void draw(Graphics g) {

        if (running) {
            g.setColor(Color.RED);
            g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);


            for (int i = 0; i < bodyParts; i++) {

                if (i == 0) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.green);

                }
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("Text", Font.PLAIN, 50));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score:" + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score:" + applesEaten)) / 2, g.getFont().getSize() / 2);
        } else {
            if (applesEaten >= 30) {
                Win(g);
            } else {
                gameOver(g);
            }
        }
    }


    public void smokoFood() {
        foodX = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        foodY = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U' -> y[0] = y[0] - UNIT_SIZE;
            case 'D' -> y[0] = y[0] + UNIT_SIZE;
            case 'L' -> x[0] = x[0] - UNIT_SIZE;
            case 'R' -> x[0] = x[0] + UNIT_SIZE;
        }
    }


    public void checkFood() {
        if ((x[0] == foodX) && (y[0] == foodY)) {
            bodyParts++;
            applesEaten++;

        }
    }


    public void check() {


        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
            if ((x[0] == wallX) && (y[0] == wallY)) {
                running = false;
            }
        }
        if (x[0] < 0) {
            running = false;
        }
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        if (y[0] < 0) {
            running = false;
        }
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }

    public void Walls() {
        wallX = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        wallY = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;

    }

    public void winCondition() {
        if (applesEaten == 10) {
            running = false;
            timer.stop();
        }
    }



    /**Win Message
     *
     * @param g
     */
    public void Win(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("YES",Font.BOLD, 80));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Win",
                (SCREEN_WIDTH - metrics.stringWidth("Win")) / 2,
                SCREEN_HEIGHT / 2);
    }

    /**Game Over Message
     *
     * @param g
     */
    public void gameOver(Graphics g) {

        g.setColor(Color.green);
        g.setFont(new Font("NOO", Font.BOLD, 80));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score:" + applesEaten,
                (SCREEN_WIDTH - metrics1.stringWidth("Score:" + applesEaten)) / 2,
                g.getFont().getSize() / 2);


        g.setColor(Color.GREEN);
        g.setFont(new Font("Text2", Font.BOLD, 150));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("",
                (SCREEN_WIDTH - metrics2.stringWidth("GameOver")) / 2,
                SCREEN_HEIGHT / 2);
    }


    public void actionPerformed(ActionEvent e) {

        if (running) {
            move();
            checkFood();
            winCondition();
            Walls();
            check();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {

                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                        break;
                    }
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                        break;
                    }
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                        break;

                    }
            }
        }
    }
}