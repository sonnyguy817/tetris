package model;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TetrisModel {
    private static final int ROWS = 20;
    private static final int COLS = 10;
    private int[][] board;
    private int[][] currentPiece;
    private int currentX;
    private int currentY;
    private boolean gameOver;
    private int score;
    private long startTime;
    private long gameTime;
    private Timer gameTimer;
    
    public TetrisModel() {
        board = new int[ROWS][COLS];
        gameOver = false;
        score = 0;
        startTime = System.currentTimeMillis();
        gameTimer = new Timer();
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                gameTime = System.currentTimeMillis() - startTime;
            }
        }, 0, 1000);
        spwanBlock();
    }
    
    public void spwanBlock() {
        Random rand = new Random();
        int pieceType = rand.nextInt(7);
        currentPiece = getPieceShape(pieceType);
        currentX = COLS / 2 - currentPiece[0].length / 2;
        currentY = 0;
        
        if (!isValidMove(currentX, currentY, currentPiece)) {
            gameOver = true;
        }
    }
    
    private int[][] getPieceShape(int type) {
        int[][][] pieces = {
            {{1,1,1,1}}, // I one dimension
            {{1,1},{1,1}}, // square
            {{1,1,1},{0,1,0}}, // T
            {{1,1,1},{1,0,0}}, // L
            {{1,1,1},{0,0,1}}, // J
            {{1,1,0},{0,1,1}}, // S
            {{0,1,1},{1,1,0}}  // Z
        };
        return pieces[type];
    }
    
    public boolean moveDown() {
        if (gameOver) return false;
        if (isValidMove(currentX, currentY + 1, currentPiece)) {
            currentY++;
            return true;
        }
        mergePiece();
        clearLines();
        spwanBlock();
        return false;
    }
    
    public void moveLeft() {
        if (!gameOver && isValidMove(currentX - 1, currentY, currentPiece)) {
            currentX--;
        }
    }
    
    public void moveRight() {
        if (!gameOver && isValidMove(currentX + 1, currentY, currentPiece)) {
            currentX++;
        }
    }
    
    public void rotate() {
        if (gameOver) return;
        int[][] rotated = new int[currentPiece[0].length][currentPiece.length];
        for (int i = 0; i < currentPiece.length; i++) {
            for (int j = 0; j < currentPiece[0].length; j++) {
                rotated[j][currentPiece.length - 1 - i] = currentPiece[i][j];
            }
        }
        if (isValidMove(currentX, currentY, rotated)) {
            currentPiece = rotated;
        }
    }
    
    private boolean isValidMove(int newX, int newY, int[][] piece) {
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                if (piece[i][j] == 0) continue;
                int x = newX + j;
                int y = newY + i;
                if (x < 0 || x >= COLS || y >= ROWS) return false;
                if (y >= 0 && board[y][x] != 0) return false;
            }
        }
        return true;
    }
    
    private void mergePiece() {
        for (int i = 0; i < currentPiece.length; i++) {
            for (int j = 0; j < currentPiece[0].length; j++) {
                if (currentPiece[i][j] != 0) {
                    board[currentY + i][currentX + j] = currentPiece[i][j];
                }
            }
        }
    }
    
    private void clearLines() {
        for (int i = ROWS - 1; i >= 0; i--) {
            boolean isLineFull = true;
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == 0) {
                    isLineFull = false;
                    break;
                }
            }
            if (isLineFull) {
                score += 100;
                for (int k = i; k > 0; k--) {
                    System.arraycopy(board[k-1], 0, board[k], 0, COLS);
                }
                for (int j = 0; j < COLS; j++) {
                    board[0][j] = 0;
                }
                i++;
            }
        }
    }
    
    public int[][] getBoard() {
        return board;
    }
    
    public int[][] getCurrentPiece() {
        return currentPiece;
    }
    
    public int getCurrentX() {
        return currentX;
    }
    
    public int getCurrentY() {
        return currentY;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public int getScore() {
        return score;
    }
    
    public long getGameTime() {
        return gameTime;
    }
    
    public void resetGame() {
        board = new int[ROWS][COLS];
        gameOver = false;
        score = 0;
        startTime = System.currentTimeMillis();
        spwanBlock();
    }
} 