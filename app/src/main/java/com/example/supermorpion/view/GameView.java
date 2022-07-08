package com.example.supermorpion.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import static java.lang.Math.random;

import java.util.List;


public class GameView extends View {
    public GameView(Context context) {
        super(context);
    }
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    float phone_width = 0f;
    float phone_height = 0f;

    Player joueur1 = new Player("P1", "player","cross",Color.BLUE);
    Player cpu = new Player("CPU", "cpu","cross",Color.YELLOW);

    Shape[][] matrice = null;

    float matrixTop = 0f;
    float matrixLeft = 0f;
    float matrixRight = 0f;
    float matrixBottom = 0f;
    float matrixWidth = 350f;

    int nbr_square = 4;

    public void setNbr_square(int nbr_square) {
        this.nbr_square = nbr_square;
    }

    int nbrShape = 0;

    Paint paint = new Paint();
    Paint paintFill = new Paint();
    Shape gameShape = null;
    Shape victoriousShape = null;
    String victoryLayout = "";
    boolean gameOver = false;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        phone_width = getWidth();
        phone_height = getHeight();

        if(matrice == null) {
            matrice = new Shape[nbr_square][nbr_square];
            for (int i = 0 ; i <nbr_square; i++) {
                for (int j = 0; j<nbr_square; j++) {
                    matrice[i][j] = null;
                }
            }
        }
        if (nbrShape == 0) {
            nbrShape = nbr_square*nbr_square;
        }

        matrixLeft = 50;
        matrixTop = 100;
        //width = phone_width - 2 * left;
        matrixRight = matrixWidth + matrixLeft;
        matrixBottom = matrixWidth + matrixTop;

        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(3f);
        paint.setStyle(Paint.Style.STROKE);

        paintFill.setStyle(Paint.Style.FILL_AND_STROKE);
        paintFill.setStrokeWidth(4f);
        paintFill.setColor(Color.MAGENTA);

        canvas.drawRect(matrixLeft, matrixTop , matrixRight, matrixBottom, paintFill);
        for(int i = 0; i < nbr_square; i++) {
            float startX = matrixLeft + i * (matrixWidth / nbr_square);
            canvas.drawLine(startX, matrixTop, startX, matrixBottom, paint);
            canvas.drawLine(matrixLeft, matrixTop + i * (matrixWidth / nbr_square), matrixRight, matrixTop + i* (matrixWidth/nbr_square), paint);
        }

        for (int i = 0; i < nbr_square; i++) {
            for(int j = 0; j < nbr_square; j++) {
                if (matrice[i][j] != null) {
                    //canvas.drawCircle(gameShape.getCenterX(), gameShape.getCenterY(), gameShape.getRadius(), gameShape.getPaint());
                    matrice[i][j].drawShape(canvas);
                    //System.out.println(gameShape.getCenterX() + " - " + gameShape.getCenterY() + " - " + gameShape.getRadius() + gameShape.getPaint().getColor());
                }
            }
        }
        victoriousShape = checkWinner(matrice);

        if (victoriousShape != null) {
            System.out.println("Victoire :\n" + victoriousShape.getType() + "\n" + victoryLayout + "\n" + victoriousShape.getPlayer().getName());
            markWiningShape(canvas);
        }

    }

    private void markWiningShape(Canvas canvas) {
        float startX = 0, startY = 0, stopX = 0, stopY=0;

        Paint lignePaint = new Paint();
        float paintWidth = (victoriousShape.getBottom() - victoriousShape.getTop()) / 7;
        lignePaint.setColor(Color.RED);
        lignePaint.setStrokeWidth(paintWidth);
        lignePaint.setStyle(Paint.Style.STROKE);

        if (victoryLayout == "ligne") {
            startY = victoriousShape.getCenterY() ;
            startX = victoriousShape.getRadius() / 3 + matrixLeft;
            stopY = victoriousShape.getCenterY();
            stopX = matrixRight - victoriousShape.getRadius() / 3;
        }
        if (victoryLayout == "colonnes") {
            startX = victoriousShape.getCenterX();
            startY = matrixTop + victoriousShape.getRadius() / 3;
            stopX = victoriousShape.getCenterX();
            stopY = matrixBottom - victoriousShape.getRadius() / 3;
        }

        if (victoryLayout == "diagonale_gauche") {
            startX = matrixLeft + victoriousShape.getRadius();
            startY = matrixTop + victoriousShape.getRadius();
            stopX = matrixRight - victoriousShape.getRadius();
            stopY = matrixBottom - victoriousShape.getRadius();
        }

        if (victoryLayout == "diagonale_droite") {
            startX = matrixLeft + victoriousShape.getRadius();
            startY = matrixBottom - victoriousShape.getRadius();
            stopX = matrixRight - victoriousShape.getRadius();
            stopY = matrixTop + victoriousShape.getRadius();
        }

        canvas.drawLine(startX,startY,stopX,stopY,lignePaint);
        System.out.println("Ligne dessinée");
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        float clicX = event.getX();
        float clicY = event.getY();

        float shapeTop, shapeLeft, shapeBottom, shapeRight, square_width;

        int coordTop, coordLeft;

        square_width = (matrixWidth / nbr_square);
        coordTop = (int) ((clicY - matrixTop) / square_width);
        coordLeft = (int) ((clicX - matrixLeft) / square_width);

        if (clicX >= matrixLeft && clicX <= (matrixLeft + matrixWidth) && clicY >= matrixTop && clicY <= (matrixTop + matrixWidth) && gameOver == false) {
            if (matrice[coordLeft][coordTop] == null) {
                shapeTop = matrixTop + coordTop * square_width;
                shapeLeft = matrixLeft + coordLeft * square_width;
                shapeBottom = shapeTop + square_width;
                shapeRight = shapeLeft + square_width;

                //System.out.println("onTouchEvent: " + shapeLeft + " - " + shapeTop);

                gameShape = new Shape(shapeLeft, shapeTop, shapeRight, shapeBottom, joueur1.getShapeColor(), joueur1.getShapeType(),joueur1);
                matrice[coordLeft][coordTop] = gameShape;
                gameShape = null;
                nbrShape -= 1;

                // Code fametahana CPU
               // System.out.println(nbrShape);
                victoriousShape = checkWinner(matrice);
                if (nbrShape >= 1 && gameOver == false) {
                    coordLeft = (int) (random() * 100) % nbr_square;
                    coordTop = (int) (random() * 100) % nbr_square;
                    while (matrice[coordLeft][coordTop] != null) {
                        coordLeft = (int) (random() * 100) % nbr_square;
                        coordTop = (int) (random() * 100) % nbr_square;
                    }

                    shapeTop = matrixTop + coordTop * square_width;
                    shapeLeft = matrixLeft + coordLeft * square_width;
                    shapeBottom = shapeTop + square_width;
                    shapeRight = shapeLeft + square_width;

                    gameShape = new Shape(shapeLeft, shapeTop, shapeRight, shapeBottom, cpu.getShapeColor(), cpu.getShapeType(),cpu);
                    matrice[coordLeft][coordTop] = gameShape;
                    victoriousShape = checkWinner(matrice);
                    gameShape = null;
                    nbrShape -= 1;
                }

                invalidate();
            }
            if (nbrShape == 0 && gameOver == false) {
                matrice = null;
                System.out.println("Draw");
                invalidate();
            }
        }

        return true;
    }

    private Shape checkWinner(Shape[][] matrice) {
        int counter = 1;
        int counter_diagonal_gauche = 0;
        // Verification des colonnes
        for(int i=0; i< nbr_square;i++) {
            counter = 1;
            for (int j=1; j < nbr_square;j++) {
                int next_index = (i+j) % nbr_square;

                if(matrice[i][i] == null || matrice[i][next_index] == null) {
                    break;
                } else if(matrice[i][i].getPlayer().getName() != matrice[i][next_index].getPlayer().getName()){
                    break;
                } else {
                    counter++;
                }
                //System.out.print(counter + " ");
                if(counter == nbr_square) {
                    victoryLayout = "colonnes";
                    gameOver = true;
                    return matrice[i][next_index];
                }
            }
            //System.out.println("");
            int next_diag = (i+1) % nbr_square;
            if (matrice[i][i] != null && matrice[next_diag][next_diag] != null && matrice[i][i].getType() == matrice[next_diag][next_diag].getType() && matrice[i][i].getPlayer().getName() == matrice[next_diag][next_diag].getPlayer().getName()) {
                counter_diagonal_gauche++;
            }
            if (counter_diagonal_gauche == nbr_square) {
                victoryLayout = "diagonale_gauche";
                gameOver = true;
                return matrice[i][i];
            }
        }

        counter = 1;
        int counter_diagonal_droite = 0;
        // Verification des lignes
        for (int j = (nbr_square - 1 ); j >= 0; j-- ) {
            counter = 0;
            for(int i = 0; i < nbr_square; i++) {
                int next_index = (i+j) % nbr_square;

                if(matrice[j][j]== null || matrice[next_index][j] == null) {
                    break;
                } else if(matrice[j][j].getPlayer().getName() != matrice[next_index][j].getPlayer().getName()) {
                    break;
                } else {
                    counter++;
                }

                if(counter == nbr_square) {
                    victoryLayout = "ligne";
                    gameOver = true;
                    return matrice[next_index][j];
                }
            }
            int next_x = (nbr_square - (j +1) ) % nbr_square;
            int next_y = (j -1 + nbr_square) % nbr_square;

            if (matrice[next_x][j] != null  && matrice[(next_x +1 ) % nbr_square][next_y] != null && matrice[next_x][j].getType() == matrice[(next_x +1 ) % nbr_square][next_y].getType() && matrice[next_x][j].getPlayer().getName() == matrice[(next_x +1 ) % nbr_square][next_y].getPlayer().getName()) {
                counter_diagonal_droite++;
            }

            if (counter_diagonal_droite == nbr_square) {
                victoryLayout = "diagonale_droite";
                gameOver = true;
                return matrice[next_x][j];
            }
        }
        gameOver = false;
        return null;
    }
}