package com.company;

import acm.graphics.GImage;

public class Cloud {
    private final String path_image = "C:\\Users\\wade079\\Desktop\\dino\\img\\";//Directorio de las imagenes


    GImage cloud = new GImage(path_image + "cloud.png");

    private int pos_x;
    private final int pos_y;
    private final int velocity;

    public Cloud() {

        pos_x = getRandomNumber(600, 1000);
        pos_y = getRandomNumber(50, 220);
        int size_x = getRandomNumber(44, 70);
        int size_y = getRandomNumber(23, 50);
        velocity = getRandomNumber(1, 3);
        cloud.setSize(size_x, size_y);

    }


    public GImage getCloud() {
        return cloud;
    }

    public int getPos_x() {
        return pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }

    public void move() {

        pos_x = pos_x - velocity;
    }

    public int getRandomNumber(int min, int max) {//generar numeros aleatorios
        return (int) ((Math.random() * (max - min)) + min);
    }
}
