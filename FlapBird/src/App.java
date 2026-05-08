import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {

        // Definição das dimensões da janela
        int LarguraBorda = 360;
        int AlturaBorda = 640;

        // Criação da janela com o título
        JFrame janela = new JFrame("Flappy Bird");

        // Torna a janela visível
        janela.setVisible(true);

        // Define o tamanho baseado nas variáveis acima
        janela.setSize(LarguraBorda, AlturaBorda);

        // Centraliza a janela na tela
        janela.setLocationRelativeTo(null);

        // Impede que o usuário mude o tamanho da janela
        janela.setResizable(false);

        // Garante que o programa feche ao clicar no 'X'
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        FlappyBird flappyBird = new FlappyBird();
        janela.add(flappyBird);
        janela.pack();
        janela.setVisible(true);
    }
}
