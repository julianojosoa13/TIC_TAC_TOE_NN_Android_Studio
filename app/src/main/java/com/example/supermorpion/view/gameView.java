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


public class gameView extends View {
    public gameView(Context context) {
        super(context);
    }
    public gameView (Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    float phone_width = 0f;
    float phone_height = 0f;

    Player joueur1 = new Player("P1", "player","cross",Color.BLUE);
    Player cpu = new Player("CPU", "cpu","circle",Color.YELLOW);

    Shape[][] matrice = null;

    float matrixTop = 0f;
    float matrixLeft = 0f;
    float matrixRight = 0f;
    float matrixBottom = 0f;
    float matrixWidth = 350f;

    int nbr_square = 4;

    int nbrShape = 0;

    Paint paint = new Paint();
    Paint paintFill = new Paint();
    Shape gameShape = null;


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

        if (clicX >= matrixLeft && clicX <= (matrixLeft + matrixWidth) && clicY >= matrixTop && clicY <= (matrixTop + matrixWidth)) {
            if (matrice[coordLeft][coordTop] == null) {
                shapeTop = matrixTop + coordTop * square_width;
                shapeLeft = matrixLeft + coordLeft * square_width;
                shapeBottom = shapeTop + square_width;
                shapeRight = shapeLeft + square_width;

                //System.out.println("onTouchEvent: " + shapeLeft + " - " + shapeTop);

                gameShape = new Shape(shapeLeft, shapeTop, shapeRight, shapeBottom, joueur1.getShapeColor(), joueur1.getShapeType());
                matrice[coordLeft][coordTop] = gameShape;
                gameShape = null;
                nbrShape -= 1;

                // Code fametahana CPU
               // System.out.println(nbrShape);

                if (nbrShape > 1) {
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

                    gameShape = new Shape(shapeLeft, shapeTop, shapeRight, shapeBottom, cpu.getShapeColor(), cpu.getShapeType());
                    matrice[coordLeft][coordTop] = gameShape;
                    gameShape = null;
                    nbrShape -= 1;
                }

                invalidate();


            }
        }

        return true;
    }
}