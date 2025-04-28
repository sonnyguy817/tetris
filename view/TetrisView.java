package view;

import javax.swing.*;
import java.awt.*;
import model.TetrisModel;

public class TetrisView extends JPanel {
    private static final int BLOCK_SIZE = 30;
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 20;
    private TetrisModel model;
    
    public TetrisView(TetrisModel model) {
        this.model = model;
        setPreferredSize(new Dimension(BLOCK_SIZE * (BOARD_WIDTH + 6), BLOCK_SIZE * BOARD_HEIGHT));
        setBackground(Color.BLACK);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // 繪製遊戲區域
        g2d.setColor(Color.GRAY);
        g2d.drawRect(0, 0, BLOCK_SIZE * BOARD_WIDTH, BLOCK_SIZE * BOARD_HEIGHT);
        
        // 繪製已固定的方塊
        int[][] board = model.getBoard();
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (board[i][j] != 0) {
                    drawBlock(g2d, j, i, Color.CYAN);
                }
            }
        }
        
        // 繪製當前方塊
        int[][] currentPiece = model.getCurrentPiece();
        int currentX = model.getCurrentX();
        int currentY = model.getCurrentY();
        
        for (int i = 0; i < currentPiece.length; i++) {
            for (int j = 0; j < currentPiece[0].length; j++) {
                if (currentPiece[i][j] != 0) {
                    drawBlock(g2d, currentX + j, currentY + i, Color.YELLOW);
                }
            }
        }
        
        // 繪製分數和時間
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("分數: " + model.getScore(), BLOCK_SIZE * (BOARD_WIDTH + 1), 30);
        g2d.drawString("時間: " + formatTime(model.getGameTime()), BLOCK_SIZE * (BOARD_WIDTH + 1), 60);
        
        // 如果遊戲結束，顯示遊戲結束信息
        if (model.isGameOver()) {
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
            String gameOver = "遊戲結束!";
            FontMetrics fm = g2d.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(gameOver)) / 2;
            int y = getHeight() / 2;
            g2d.drawString(gameOver, x, y);
        }
    }
    
    private void drawBlock(Graphics2D g2d, int x, int y, Color color) {
        g2d.setColor(color);
        g2d.fillRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE - 1, BLOCK_SIZE - 1);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE - 1, BLOCK_SIZE - 1);
    }
    
    private String formatTime(long timeInMillis) {
        long seconds = timeInMillis / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
} 