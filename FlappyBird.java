import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    Image birdImage = new ImageIcon("bird.png").getImage();

    int birdY = 250;
    int velocity = 0;
    int gravity = 1;  
    int jumpStrength = -12;
    int pipeX = 400;
    int gap = 150;
    int pipeWidth = 60;
    int pipeHeight;
    int highScore = 0;
    int score = 0;
    boolean gameOver = false;

    Timer timer;
    Random rand = new Random();

public FlappyBird() {
    JFrame frame = new JFrame("Flappy Bird");
    this.setPreferredSize(new Dimension(400, 600)); // Set the panel size
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(this);
    frame.pack(); // This fits the window to the panel size exactly
    frame.setLocationRelativeTo(null); // Centers the window on your screen
    frame.setResizable(false);
    frame.setVisible(true);
    frame.addKeyListener(this);

    pipeHeight = rand.nextInt(200) + 100;
    timer = new Timer(20, this);
    timer.start();
}

   public void paintComponent(Graphics g) {
    super.paintComponent(g);

    
    g.setColor(Color.cyan);
    g.fillRect(0, 0, 400, 600);

    
    g.setColor(new Color(34, 139, 34)); 
    g.fillRect(pipeX, 0, pipeWidth, pipeHeight);
    g.fillRect(pipeX, pipeHeight + gap, pipeWidth, 600);

    g.setColor(Color.green); 
    g.fillRect(pipeX + 5, 0, pipeWidth - 10, pipeHeight);
    g.fillRect(pipeX + 5, pipeHeight + gap, pipeWidth - 10, 600);

    
    g.setColor(new Color(222, 184, 135)); 
    g.fillRect(0, 550, 400, 50);
    g.setColor(new Color(101, 67, 33)); 
    g.fillRect(0, 550, 400, 5);

    
    g.drawImage(birdImage, 100, birdY, 40, 40, null);

    
    g.setColor(Color.black);
    g.setFont(new Font("Arial", Font.BOLD, 25));
    g.drawString("Score: " + score, 20, 40);

    if (gameOver) {
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("GAME OVER", 85, 300);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Press SPACE to Restart", 95, 340);
    }
}

    public void actionPerformed(ActionEvent e) {
        

        if(gameOver) return;

        velocity += gravity;
        birdY += velocity;

        pipeX -= 5;

        if(pipeX < -pipeWidth){
    pipeX = 400;
    pipeHeight = rand.nextInt(200) + 100;
    score++;
    if (score > highScore) {
        highScore = score;
    }
}
        Rectangle bird = new Rectangle(100 + 5, birdY + 5, 20, 20);
        Rectangle topPipe = new Rectangle(pipeX,0,pipeWidth,pipeHeight);
        Rectangle bottomPipe = new Rectangle(pipeX, pipeHeight + gap, pipeWidth, 600);

        if(bird.intersects(topPipe) || bird.intersects(bottomPipe) || birdY > 570 || birdY < 0){
            gameOver = true;
        }

        repaint();
    }

    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            velocity = -10;

            if(gameOver){
                birdY = 250;
                velocity = 0;
                pipeX = 400;
                score = 0;
                gameOver = false;
            }
        }
    }

    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}

    public static void main(String[] args) {
        new FlappyBird();
    } 
}