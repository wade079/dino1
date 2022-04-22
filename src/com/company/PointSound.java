package com.company;

import acm.graphics.GLabel;

import acm.program.GraphicsProgram;
import acm.util.SoundClip;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class PointSound extends GraphicsProgram implements KeyListener {

    private final String path_music = "C:\\Users\\wade079\\Desktop\\dino\\music\\";//Directorio de las pistas de audio
    private final String path_puntos = "C:\\Users\\wade079\\Desktop\\dino\\puntos\\";//Directorio de las pistas de audio

    private boolean jump = false;
    private boolean crouch = false;
    private final SoundClip dora = new SoundClip(path_music + "dora.wav");


    public void movDetect(boolean tecla, KeyEvent e) {

        if (e.getKeyChar() == ' ') {
            jump = true;

        }
        if (tecla) {
            if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                crouch = true;
            }
        }
        if (!tecla) {
            if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                crouch = false;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        movDetect(true, e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        movDetect(false, e);
    }


    public static void main(String[] args) {

        new PointSound().start(args);

    }

    public int getRandomNumber(int min, int max) {//generar numeros aleatorios
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void run() {

        addKeyListeners();


        //Seccion contadores y puntos * Paul *

        boolean forest = true;//run forest RUN!!
        boolean catusinator = false;

        int puntos = 0;
        int velocidad_fondo = 3;
        int time_before_update = 0;
        int ciclos_puntos = 0;
        int ciclos_sonido = 0;
        int cont_fondos = 0;
        int tempo_gen_cloud = 0;
        int tempo_update_cloud = 0;
        int cont_cactus = 0;
        int rdm_gen = getRandomNumber(1000, 10000);


        //Instanciacion del contador de puntos y del dino

        GLabel points = new GLabel(String.valueOf(puntos));
        points.setFont("Fixedsys Excelsior 3.01-30");

        ArrayList<Dino> dino = new ArrayList<>();
        dino.add(new Dino());

        //Instanciacion y posicionamiento del terreno * Paul *

        ArrayList<Fondo> fondos = new ArrayList<>();
        fondos.add(new Fondo());
        add(fondos.get(0).getBg(), fondos.get(0).getPos_x(), 319);//319 es donde el dino toca el terreno
        for (int i = 1; i < 10; i++) {
            fondos.add(new Fondo(fondos.get(i - 1).getPos_x() + 71));//71 anchura de la imagen de terreno
            add(fondos.get(i).getBg(), fondos.get(i).getPos_x(), 319);
        }

        ArrayList<Cactusito> caclist = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            caclist.add(new Cactusito());
        }

        ArrayList<Cloud> clouds = new ArrayList<>();
        clouds.add(new Cloud());
        add(clouds.get(0).getCloud(), clouds.get(0).getPos_x(), clouds.get(0).getPos_y());

        dora.setVolume(0.5);
        dora.loop();

        int colision = 0;
        while (forest) {

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Generacion de terreno * Paul *

            if (cont_fondos == velocidad_fondo) {
                for (Fondo fondo : fondos) {
                    fondo.move();
                    add(fondo.getBg(), fondo.getPos_x(), 319);
                }
                cont_fondos = 0;
            }
            cont_fondos++;

            //Creacion y eliminacion de terreno * Paul *

            if (fondos.get(fondos.size() - 1).getPos_x() + 71 <= 1000) {//1000 es a partir de donde se tiene que generar terreno
                fondos.add(new Fondo(fondos.get(fondos.size() - 1).getPos_x() + 71));
                add(fondos.get(fondos.size() - 1).getBg(), fondos.get(fondos.size() - 1).getPos_x(), 319);
            }

            if (fondos.get(0).getPos_x() + 71 == 0) {
                fondos.get(0).getBg().setVisible(false);
                fondos.remove(0);
            }

            //Sector puntos y sonido * Paul *

            points.setLabel("Points : " + (puntos));


            add(points, 50, 50);

            if (ciclos_puntos >= 50) {//Normalizar la velocidad de los puntos
                puntos++;
                ciclos_puntos = 0;
                ciclos_sonido++;

            }

            if (ciclos_sonido >= 100) {//Cada cuantos puntos tiene que sonar
                SoundClip point = new SoundClip(path_music + "point.wav");
                point.setVolume(0.1);
                point.play();
                ciclos_sonido = 0;
            }

            ciclos_puntos++;

            //Sector salto dino * Paul *

            add(dino.get(0).getSprite(), 100, dino.get(0).getPos_y());

            if (jump && !crouch) {
                dino.get(0).jump_sound();
                dino.get(0).jump();
                if (dino.get(0).reached_ground()) {
                    jump = false;
                }
            }
            if (time_before_update == 50) {
                dino.get(0).update_image(crouch);
                time_before_update = 0;

            }
            time_before_update++;

            if (tempo_gen_cloud == rdm_gen) {
                clouds.add(new Cloud());
                add(clouds.get(clouds.size() - 1).getCloud(), clouds.get(clouds.size() - 1).getPos_x(), clouds.get(clouds.size() - 1).getPos_y());
                tempo_gen_cloud = 0;
                rdm_gen = getRandomNumber(1000, 5000);
            }

            tempo_gen_cloud++;

            if (tempo_update_cloud == 10) {
                for (Cloud cloud : clouds) {
                    add(cloud.getCloud(), cloud.getPos_x(), cloud.getPos_y());
                    cloud.move();
                    tempo_update_cloud = 0;
                }
            }
            tempo_update_cloud++;

            if (dino.get(0).secretSound()) {
                dino.remove(0);
                dino.add(new Dino());
                dora.setVolume(0.001);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dora.setVolume(0.5);
                dora.play();
            }
            dora.setVolume(0.5);

            if (cont_cactus == velocidad_fondo) {
                add(caclist.get(0).getimg(), caclist.get(0).getPosx(), caclist.get(0).getPosy());
                caclist.get(0).run();
                if (caclist.get(0).getPosx() == 430) {
                    if (getRandomNumber(1, 6) > 2) {
                        catusinator = true;
                    }
                }
                if (catusinator) {
                    add(caclist.get(1).getimg(), caclist.get(1).getPosx(), caclist.get(1).getPosy());
                    caclist.get(1).run();
                }
                cont_cactus = 0;
            }
            cont_cactus++;

            if (caclist.get(0).getPosx() < -70) {
                caclist.add(new Cactusito());
                caclist.remove(0);
                catusinator = false;
            }

            if (caclist.get(0).getPosx() < 130 && caclist.get(0).getPosx() > 100 - caclist.get(0).getgordura()) {
                if (caclist.get(0).crash(dino.get(0).getPos_y())) {
                    System.out.println("punto colision");
                    forest = false;
                    colision = colision + 1;
                    crear_file();
                }
            }

            if (puntos < 2) {
                velocidad_fondo = 3;
            }
            if (puntos == 150) {
                velocidad_fondo = 2;
                System.out.println("aceleracion");
            }
            if (puntos == 500) {
                velocidad_fondo = 1;
                System.out.println("aceleracion");
            }


        }//fin  while

        caclist.add(new Cactusito());
        caclist.get(1).getimg().setVisible(false);
        caclist.remove(1);
        caclist.add(new Cactusito());
        caclist.get(0).getimg().setVisible(false);
        caclist.remove(0);
        points.setVisible(false);

        for (int i = 0; i < clouds.size(); i++) {
            clouds.add(new Cloud());
            clouds.get(0).getCloud().setVisible(false);
            clouds.remove(0);

        }

        dino.get(0).getSprite().setVisible(false);


        try {
            FileWriter myWriter = new FileWriter(path_puntos + "puntos.txt");
            myWriter.write(String.valueOf(puntos));
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        if (colision == 1) {
            puntos_anterior_max();
            try {
                Thread.sleep(5500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            run();


        }
    }

    private void crear_file() {
        try {
            File myObj = new File(path_puntos + "puntos.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }

    private void puntos_anterior_max() {
        String point = null;


        try {
            File myObj = new File(path_puntos + "puntos.txt");
            Scanner myReader = new Scanner(myObj);




            while (myReader.hasNextLine()) {
                 point = myReader.nextLine();

            }

            while (true){
                GLabel points_max = new GLabel(null);
                points_max.setFont("Fixedsys Excelsior 3.01-30");
                points_max.setLabel("Points_max : " + (point));
                add(points_max, 350, 50);
            }



            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }



}
//fin run









