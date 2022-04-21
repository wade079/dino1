package com.company;

import acm.graphics.GImage;

public class Fondo {

    private final String land_path = "C:\\Users\\wade079\\Desktop\\dino\\img\\";//Directorio de las imagenes
    String[] lands = new String[]{"land1.png", "land2.png", "land3.png"};

    int width = 71;
    int height = 29;
    int pos_x = 0;
    GImage bg;

    public Fondo() {

        bg = new GImage(land_path + lands[getRandomNumber(0, lands.length)]);
        bg.setSize(width, height);
        bg.sendBackward();
    }

    public Fondo(int pos) {
        bg = new GImage(land_path + lands[getRandomNumber(0, lands.length)]);
        bg.setSize(width, height);
        bg.sendBackward();
        pos_x = pos;
    }

    public void move() {
        pos_x = pos_x - 1;

    }

    public GImage getBg() {
        return this.bg;
    }

    public int getPos_x() {
        return pos_x;
    }

    public int getRandomNumber(int min, int max) {//generar numeros aleatorios
        return (int) ((Math.random() * (max - min)) + min);
    }
}
