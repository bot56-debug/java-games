import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    Image birdImage = new ImageIcon("bird.png").getImage();
    enum State { MENU, PLAYING, GAMEOVER }
    State currentState = State.MENU;

    int birdY = 250;
    int velocity = 0;
    int gravity = 1;  
    int jumpStrength = -12;
    int pipeX = 400;
    int gap = 150;
    int pipeWidth = 60;
    int pipeHeight;
    int score = 0;
    int highScore = 0;

    Timer timer;
    Random rand = new Random();

    public FlappyBird() {
        JFrame frame = new JFrame("Flappy Bird");
        this.setPreferredSize(new Dimension(400, 600));  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack(); 
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.addKeyListener(this);

        pipeHeight = rand.nextInt(200) + 100;
        timer = new Timer(20, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.cyan);
        g.fillRect(0, 0, 400, 600);

        if (currentState == State.MENU) {
            drawMenu(g);
        } else if (currentState == State.PLAYING) {
            drawGame(g);
        } else if (currentState == State.GAMEOVER) {
            drawGameOver(g);
        }
    }

    private void drawMenu(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("FLAPPY BIRD", 75, 200);
        
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Press SPACE to Start", 100, 300);
        g.drawImage(birdImage, 180, 240, 40, 40, null);
    }

    private void drawGame(Graphics g) {
        g.setColor(new Color(34, 139, 34));
        g.fillRect(pipeX, 0, pipeWidth, pipeHeight);
        g.fillRect(pipeX, pipeHeight + gap, pipeWidth, 600);

        g.drawImage(birdImage, 100, birdY, 40, 40, null);

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("" + score, 190, 50);
    }

    private void drawGameOver(Graphics g) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, 400, 600);

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("GAME OVER", 85, 200);
        
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("SCORE: " + score, 130, 260);
        g.drawString("BEST: " + highScore, 135, 300);
        
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("Press SPACE to Restart", 100, 380);
    }

    public void actionPerformed(ActionEvent e) {
        if (currentState == State.PLAYING) {
            velocity += gravity;
            birdY += velocity;
            pipeX -= 5;

            if (pipeX < -pipeWidth) {
                pipeX = 400;
                pipeHeight = rand.nextInt(200) + 100;
                score++;
                if (score > highScore) highScore = score;
            }

            Rectangle birdRect = new Rectangle(100 + 5, birdY + 5, 30, 30);
            Rectangle topPipe = new Rectangle(pipeX, 0, pipeWidth, pipeHeight);
            Rectangle bottomPipe = new Rectangle(pipeX, pipeHeight + gap, pipeWidth, 600);

            if (birdRect.intersects(topPipe) || birdRect.intersects(bottomPipe) || birdY > 560 || birdY < 0) {
                currentState = State.GAMEOVER;
            }
        }
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (currentState == State.MENU) {
                currentState = State.PLAYING;
            } else if (currentState == State.PLAYING) {
                velocity = jumpStrength;
            } else if (currentState == State.GAMEOVER) {
                // Reset Game
                birdY = 250;
                velocity = 0;
                pipeX = 400;
                score = 0;
                currentState = State.PLAYING;
            }
        }
    }

    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}

    public static void main(String[] args) {
        new FlappyBird();
    } 
}