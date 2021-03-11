import javax.swing.*;

public class GameFrame extends JFrame {
    /**Game Frame
     *
     *
     */
    public GameFrame(){
        this.add(new GamePanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,1000);
        this.setTitle("Snake");
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

}
