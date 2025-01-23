import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) throws Exception {
    int boardWidth = 360;
    int boardHeight = 640;

    JFrame frame = new JFrame("Flappy Bird : 2049");
    frame.setSize(boardWidth, boardHeight);
    frame.setLocationRelativeTo(null); //will place the window at center of the screen
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Set the frame icon
    Image icon = Toolkit.getDefaultToolkit().getImage(App.class.getResource("/flappybird.png"));
    frame.setIconImage(icon);
    
    Game game = new Game();
    frame.add(game);
    frame.pack(); //will resize the window to fit the preferred size of its contents  
    game.requestFocus();
    frame.setVisible(true);

    }
}
