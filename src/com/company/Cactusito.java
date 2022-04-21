package com.company;

import acm.graphics.GImage;

public class Cactusito {


    private final int rdm;
    private GImage cac;
    private int posy;
    private int posx = 900;

    public Cactusito() {
        rdm = getRandomNumber(1, 4);

        //Directorio de las imagenes
        String path_image = "C:\\Users\\wade079\\Desktop\\dino\\img\\";
        if (rdm == 1) {
            cac = new GImage(path_image + "cactus1.png");
            cac.setSize(30, 30);
            posy = 320;
        }
        if (rdm == 2) {
            cac = new GImage(path_image + "cactus1.png");
            cac.setSize(30, 50);
            posy = 300;
        }
        if (rdm == 3) {
            cac = new GImage(path_image + "cactus2.png");
            cac.setSize(50, 30);
            posy = 320;
        }
    }

    public int getRandomNumber(int min, int max) {//generar numeros aleatorios
        return (int) ((Math.random() * (max - min)) + min);
    }

    public GImage getimg() {
        return cac;
    }

    public int getPosy() {
        return posy;
    }

    public int getPosx() {
        return posx;
    }

    public void run() {
        posx--;
    }

    public int getgordura() {
        if (rdm == 1 || rdm == 2) {
            return 27;
        } else {
            return 45;
        }
    }

    public boolean crash(int dinoy) {
        return dinoy + 40 >= posy;
    }

}
