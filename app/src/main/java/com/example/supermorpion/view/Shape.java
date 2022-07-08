package com.example.supermorpion.view;

import static java.lang.Math.random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

public class Shape {
    private float top;
    private float left;
    private float right;
    private float bottom;
    private int color;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private float centerX, centerY, radius;
    private Paint paint;

    private Player player;

    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public float getRadius() {
        return radius;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public float getTop() {
        return top;
    }

    public float getLeft() {
        return left;
    }

    public float getRight() {
        return right;
    }

    public float getBottom() {
        return bottom;
    }


    public Player getPlayer() {
        return player;
    }

    public Shape(float left, float top, float right, float bottom, int color, String type, Player player) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.color = color;
        this.player = player;

        String[] typeList = new String[2];
        typeList[0] = "circle";
        typeList[1] = "cross";

        this.paint = new Paint();
        //this.type = typeList[(int) ((int)(random() * 100) % 2)];
        this.type = type;
    }

    public void drawShape(Canvas can) {

        if (this.type == "circle") {
            drawCircle(can);
        } else {
            drawCross(can);
        }

    }
    private void drawCircle(Canvas can) {
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(this.color);
        //this.paint.setColor(Color.GREEN);
        this.paint.setStrokeWidth(8);

        centerX = this.left + (this.right - this.left) / 2;
        centerY = this.top + (this.bottom - this.top) / 2;
        radius = (this.right - this.left) / 3;

        can.drawCircle(this.centerX, this.centerY,this.radius,this.paint);
    }

    private void drawCross(Canvas can) {
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(this.color);
        float strokeWidth = (this.bottom - this.top) / 4;
        this.paint.setStrokeWidth(strokeWidth);

        centerX = this.left + (this.right - this.left) / 2;
        centerY = this.top + (this.bottom - this.top) / 2;
        radius = (this.right - this.left) / 5;

        can.drawLine(this.centerX - this.radius, this.centerY - this.radius, this.centerX + this.radius, this.centerY + this.radius, this.paint);
        can.drawLine(this.centerX - this.radius, this.centerY + this.radius, this.centerX + this.radius, this.centerY - this.radius, this.paint);

    }
}
