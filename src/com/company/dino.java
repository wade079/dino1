package com.company;
import acm.graphics.GImage;
import acm.program.GraphicsProgram;

public class dino extends GraphicsProgram {
    private final String path_image = "C:\\Users\\wade079\\Desktop\\dino1\\img\\";//Directorio de las imagenes

    public static void main(String[] args) {//main
        new dino().start(args);
    }

    public void run() {//Donde se arranca el juego e implementamos sonido, imagenes con Acm Graphics

        canvas();
        terreno();

    }

    private void canvas() {
        setSize(960, 680);//tamaño del canvas
    }

    private void terreno() {

        GImage terreno = new GImage(path_image + "terreno.jpg");
        GImage terreno1 = new GImage(path_image + "terreno.jpg");
        terreno.setSize(960, 650);//definim mida del gif
        terreno1.setSize(960, 650);//definim mida del gif

        for (int i = 0; i >-1960; i--) {

            add(terreno1, 958+i, 0);
            add(terreno, i, 0);

            try {
                Thread.sleep(051);//Sleep entre la transicion de la pantalla de carga y el inicio del juego
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }



}