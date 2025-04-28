package controller;

import model.TetrisModel;
import view.TetrisView;
import javax.swing.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class TetrisController {
    private TetrisModel model;
    private TetrisView view;
    private Timer gameTimer;
    private static final int GAME_SPEED = 500; // 方塊下落的速度（毫秒）
    
    public TetrisController(TetrisModel model, TetrisView view) {
        this.model = model;
        this.view = view;
        setupGameTimer();
        setupKeyBindings();
    }
    
    private void setupGameTimer() {
        gameTimer = new Timer();
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!model.isGameOver()) {
                    model.moveDown();
                    view.repaint();
                }
            }
        }, GAME_SPEED, GAME_SPEED);
    }
    
    private void setupKeyBindings() {
        view.setFocusable(true);
        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (model.isGameOver()) {
                    handleGameOver();
                    return;
                }
                
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        model.moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        model.moveRight();
                        break;
                    case KeyEvent.VK_DOWN:
                        model.moveDown();
                        break;
                    case KeyEvent.VK_UP:
                        model.rotate();
                        break;
                    case KeyEvent.VK_SPACE:
                        while (model.moveDown()) {
                        }
                        break;
                }
                view.repaint();
            }
        });
    }
    
    private void handleGameOver() {
        int choice = JOptionPane.showConfirmDialog(
            view,
            "遊戲結束！\n分數: " + model.getScore() + "\n時間: " + formatTime(model.getGameTime()) + "\n\n要重新開始嗎？",
            "遊戲結束",
            JOptionPane.YES_NO_OPTION
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            model.resetGame();
            view.repaint();
        } else {
            System.exit(0);
        }
    }
    
    private String formatTime(long timeInMillis) {
        long seconds = timeInMillis / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
} 