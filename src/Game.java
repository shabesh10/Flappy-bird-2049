import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;  

//necessary for sound
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Game extends JPanel implements ActionListener, KeyListener{
    int boardWidth = 360;
    int boardHeight = 640;

    //Adding images
    Image background;
    Image birdimg;
    Image topPipeimg;
    Image bottomPipeimg;

    //Adding the bird
    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;
    int birdWidth = 34;
    int birdHeight = 24;

    class Bird
    {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img = null;

        Bird(Image img)
        {
            this.img = img;
        }
    }

    //Pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64; //scaled by 1/6
    int pipeHeight = 512;

    class Pipe{
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img = null;
        boolean passed = false;

        Pipe(Image img)
        {
            this.img = img;
        }
    }

    //game logic
    Bird bird = null;
    int velocityX = -4; //changing -4 pixels every frame; move pipes to left (stimulate bird moving right)
    int velocityY = 0; // so it moves up
    int gravity = 1;

    ArrayList<Pipe> pipes = null;
    Random random = new Random();

    Timer gameLoop = null;
    Timer placePipesTimer = null;
    boolean gameOver = false;
    double score = 0;
    GameState gameState = GameState.START;
    Clip bgmClip = null;

    enum GameState {
        START,
        PLAYING,
        GAME_OVER
    }

    Game()
    {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true); //make sure that the JPanel is the one that takes our key events
        addKeyListener(this);

        //loading the images
        background = new ImageIcon(getClass().getResource("./flappybirdbg3.jpeg")).getImage();
        birdimg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeimg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeimg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        //bird
        bird = new Bird(birdimg);
        pipes = new ArrayList<Pipe>();

        //game timer
        gameLoop = new Timer(1000/60, this); //1000/60 = 16.6
        gameLoop.start();
        //start the music

        //placing pipes timer
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        }); // every 1.5 seconds calling the action(placing pipes)
        placePipesTimer.start();

        try {
            bgmClip = AudioSystem.getClip();
            AudioInputStream bgmStream = AudioSystem.getAudioInputStream(getClass().getResource("/bgm.wav"));
            bgmClip.open(bgmStream);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the BGM
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        repaint(); // Ensure initial repaint to show start screen

    }

    public void placePipes()
    {
        //(0-1) * pipeHeight / 2 -> (0-256)
        //pipeHeight/4 = 512/4 = 128
        //0 - 128 - (0-256) --> 1/4 pipeHeight --> 3/ 4 pipeHeigt

        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * pipeHeight /2);
        int openingSpace = boardHeight / 4;

        Pipe topPipe = new Pipe(topPipeimg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeimg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g)
    {
        //System.out.println("draw") -> debug statement
        //background
        g.drawImage(background, 0, 0, boardWidth, boardHeight, null);

        //bird
        g.drawImage(birdimg, bird.x, bird.y, bird.width, bird.height, null);

        //pipes
        for (int i = 0; i < pipes.size(); i++)
        {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);

        }

        // Score
       g.setColor(Color.yellow); // Change text color here
       g.setFont(new Font("Comic Sans MS", Font.BOLD, 32)); // Change font here



       if (gameState == GameState.START) {
        String[] lines = {
            "The classic Flappy Bird has been pulled",
            "through a multiversal portal and",
            "transported to the year 2049—a bleak,",
            "dystopian future.",
            "Press SPACEBAR to start!"
        };
        int y = birdY - 120; // Position the text above the bird
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 16)); // Further decrease font size
        for (String line : lines) {
            g.drawString(line, 10, y);
            y += 20; // Adjust line spacing
        }
    } else if (gameState == GameState.GAME_OVER) {
        g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
    } else {
        g.drawString(String.valueOf((int) score), 10, 35);
    }
    }

    public void move()
    {
        //bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        //pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if (!pipe.passed && bird.x > pipe.x + pipe.width) { //if bird passes the right side of the pipe
                pipe.passed = true;
                score += 0.5; //since there are two pipes, 1 for each set of pipes
            }

            if(collison(bird, pipe)) {
                gameOver = true;
                gameState = GameState.GAME_OVER; 
                repaint();
            }
        }

        if (bird.y > boardHeight)
        {
            gameOver = true;
            gameState = GameState.GAME_OVER; 
            repaint();
        }

        

    }

    public boolean collison(Bird a, Pipe b)
    {
        return a.x < b.x + b.width && // bird's top left corner doesn't reach pipe's top right corner
        a.x + a.width > b.x && // bird's top right corner doesn't pass pipe's top left corner
        a.y < b.y + b.height && // bird's top left corner doesn't reach pipe's bottom left corner
        a.y + a.height > b.y; // bird's bottom left corner doesn't pass pipe's bottom left corner
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameState == GameState.PLAYING) { // New code
            move();
            repaint(); // this will call paintComponent
        }

        if (gameOver) {
            placePipesTimer.stop(); // will stop adding pipes
            gameLoop.stop(); // will stop adding frames
        }
    }

    

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameState == GameState.START) { 
                gameState = GameState.PLAYING; 
                gameLoop.start();
                placePipesTimer.start();
            } else if (gameState == GameState.PLAYING) {
                velocityY = -9;
            } else if (gameState == GameState.GAME_OVER) {
                // Restart the game by resetting the conditions
                pipes.clear();
                bird.y = birdY;
                velocityY = 0;
                score = 0;
                gameOver = false;
                gameState = GameState.START; 
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}


}
