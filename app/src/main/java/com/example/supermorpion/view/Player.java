package com.example.supermorpion.view;

import android.graphics.Color;

public class Player {
    private String name;
    private String shapeType;
    private int shapeColor;
    private String playerType;

    public Player(String name, String playerType, String shapeType, int shapeColor) {
        this.name = name;
        this.playerType = playerType;
        this.shapeType = shapeType;
        this.shapeColor = shapeColor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShapeType(String shapeType) {
        this.shapeType = shapeType;
    }

    public void setShapeColor(int shapeColor) {
        this.shapeColor = shapeColor;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public String getName() {
        return name;
    }

    public String getShapeType() {
        return shapeType;
    }

    public int getShapeColor() {
        return shapeColor;
    }

    public String getPlayerType() {
        return playerType;
    }
}
