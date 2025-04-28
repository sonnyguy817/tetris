import model.TetrisModel;
import view.TetrisView;
import controller.TetrisController;
import javax.swing.*;

public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("«XÃ¹´µ¤è¶ô");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            TetrisModel model = new TetrisModel();
            TetrisView view = new TetrisView(model);
            new TetrisController(model, view);
            
            frame.add(view);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
