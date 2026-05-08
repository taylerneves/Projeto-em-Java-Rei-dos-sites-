import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int LarguraBorda = 360;
    int AlturaBorda = 640;

    // IMAGENS
    Image birdImage;
    Image backgroundImage;
    Image bottomPipeImage;
    Image topPipeImage;

    // ATRIBUTOS DO PASSARO
    int birdX = LarguraBorda / 8;
    int birdY = AlturaBorda / 2;
    int birdWidth = 34;
    int birdHeight = 24;

    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    // CANOS
    int PipeX = LarguraBorda;
    int PipeY = 0;
    int PipeWidth = 64;
    int PipeHeight = 512;

    class Pipe {
        int x = PipeX;
        int y = PipeY;
        int width = PipeWidth;
        int height = PipeHeight;
        Image img;
        boolean Passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }

    // LOGICA DO JOGO
    Bird bird;
    int VelocityX = -4; 
    int VelocityY = 0;
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Timer gameLoop;
    Timer placePipesTimer;
    boolean gameOver = false;

    FlappyBird() {
        setPreferredSize(new Dimension(LarguraBorda, AlturaBorda));
        
        // Configurações de Teclado
        setFocusable(true);
        addKeyListener(this);

        // Inicializar a lista de canos
        pipes = new ArrayList<Pipe>();

        // Carregamento das imagens (Certifique-se que os arquivos estão na pasta correta)
        backgroundImage = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImage = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImage = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        // Inicializar pássaro
        bird = new Bird(birdImage);

        // Timer para criar canos a cada 1.5 segundos
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        placePipesTimer.start();

        // Game Loop (60 FPS)
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    public void placePipes() {
        int randomPipeY = (int) (PipeY - PipeHeight / 4 - Math.random() * (PipeHeight / 2));
        int openingSpace = AlturaBorda / 4;

        Pipe topPipe = new Pipe(topPipeImage);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImage);
        bottomPipe.y = topPipe.y + PipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    public void move() {
        // Pássaro
        VelocityY += gravity;
        bird.y += VelocityY;
        bird.y = Math.max(bird.y, 0);

        // Canos
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += VelocityX;

            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        // Queda
        if (bird.y > AlturaBorda) {
            gameOver = true;
        }
    }

    public boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&
               a.x + a.width > b.x &&
               a.y < b.y + b.height &&
               a.y + a.height > b.y;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Fundo
        g.drawImage(backgroundImage, 0, 0, LarguraBorda, AlturaBorda, null);

        // Pássaro
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        // Canos
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // Texto de Game Over
        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString("GAME OVER", LarguraBorda/4, AlturaBorda/2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            VelocityY = -9; // Pulo

            if (gameOver) {
                // Reiniciar o jogo
                bird.y = birdY;
                VelocityY = 0;
                pipes.clear();
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();
            }
        }
    }

    // Métodos obrigatórios do KeyListener
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

// ... final do código anterior (métodos do teclado) ...

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");
        FlappyBird game = new FlappyBird();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false); // Recomendo adicionar isso para não quebrar o layout
        frame.setVisible(true);
    }
} // Esta é a última chave da classe