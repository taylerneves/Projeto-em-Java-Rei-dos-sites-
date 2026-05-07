import java.awt.*;
import javax.swing.*;

public class FlappyBird extends JPanel {
    int LarguraBorda = 360;
    int AlturaBorda = 640;

    // IMAGEM
    Image birdImage;
    Image backgroundImage;
    Image bottomPipeImage;
    Image topPipeImage;

    // ATRIBUTOS DO PASSARO
    int birdX = LarguraBorda / 8;
    int birdY = AlturaBorda / 2;
    int birdWidth = 34;
    int birdHeight = 24;

    // Molde para o objeto Bird
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

    // LOGICA DO JOGO (Declarar o pássaro aqui, fora da classe Bird)
    Bird bird;

    FlappyBird() {
        setPreferredSize(new Dimension(LarguraBorda, AlturaBorda));

        // Carregamento das imagens
        backgroundImage = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImage = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImage = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        // Inicializar o objeto bird (Corrigido para birdImage)
        bird = new Bird(birdImage); 
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Desenha o fundo
        g.drawImage(backgroundImage, 0, 0, LarguraBorda, AlturaBorda, null);

        // Desenha o pássaro usando o objeto 'bird'
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
    }
}