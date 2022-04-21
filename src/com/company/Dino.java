package com.company;

import acm.graphics.*;
import acm.util.SoundClip;

public class Dino {

    private final String path_image = "C:\\Users\\wade079\\Desktop\\dino\\img\\";//Directorio de las imagenes
    private final String path_music = "C:\\Users\\wade079\\Desktop\\dino\\music\\";//Directorio de las pistas de audio
    private int pos_y = 300;
    private final GImage sprite;
    private boolean fall = false;
    private boolean play_jump_1_time = false;
    private boolean update = true;
    private int ciclo_before_fall = 0;


    public Dino() {
        sprite = new GImage(path_image + "dino1.png");
        sprite.setSize(50, 50);

    }

    public GImage getSprite() {
        return sprite;
    }

    public int getPos_y() {
        return pos_y;
    }

    public void jump() {


        if (!reached_top() && !fall) {
            pos_y = pos_y - 1;

        }

        if (reached_top()) {
            fall = true;

        }

        if (fall) {

            if (ciclo_before_fall >= 60) {
                pos_y = pos_y + 1;
            }

            ciclo_before_fall++;
        }

        if (reached_ground()) {
            fall = false;
            ciclo_before_fall = 0;
            play_jump_1_time = false;
        }
    }


    public boolean reached_ground() {

        int GROUND = 300;
        return pos_y == GROUND;
    }

    public boolean reached_top() {

        int MAX_HEIGHT = 180;
        return pos_y == MAX_HEIGHT;
    }

    public void jump_sound() {

        if (!play_jump_1_time) {
            SoundClip jump_sound = new SoundClip(path_music + "jump.wav");
            jump_sound.setVolume(0.5);
            jump_sound.play();
            play_jump_1_time = true;
        }
    }

    public boolean secretSound() {

        if (pos_y == 600) {
            SoundClip fall = new SoundClip(path_music + "scream.wav");
            fall.setVolume(1);
            fall.play();
            return true;
        } else {
            return false;
        }
    }

    public void update_image(boolean crouch) {

        sprite.sendForward();

        if (!play_jump_1_time && !crouch) {
            this.pos_y = 300;
            if (update) {
                sprite.setImage(path_image + "dino1.png");
                update = false;
            } else {
                sprite.setImage(path_image + "dino2.png");
                update = true;
            }
        }
        if (play_jump_1_time) {
            sprite.setImage(path_image + "dino3.png");
        }
        if (crouch) {
            this.pos_y = 320;
            if (update) {
                sprite.setImage(path_image + "dino5.png");
                update = false;
            } else {
                sprite.setImage(path_image + "dino6.png");
                update = true;

            }

        }


    }

}
