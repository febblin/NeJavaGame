package com.mygdx.game.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class InObject {
    public MyAnimation animation;
    public Sound sound;
    public Rectangle hitBox;
    private float scale;

    public InObject(String soundName, Texture anmName, int col, int row, int fps, int x, int y, Vector2 speed, float scale) {
        animation = new MyAnimation(anmName, col, row, fps, x, y, speed);
        sound = Gdx.audio.newSound(Gdx.files.internal(soundName));
        sound.play(0.15f, 1,0);
        this.scale = scale;
        float h = animation.getRegion().getRegionWidth()*scale/5;
        float w = animation.getRegion().getRegionHeight()*scale/5;
        hitBox = new Rectangle(x-w/2.f, y-h/2.f,w,h);
    }

    public void moveing() {
        animation.x += animation.speed.x;
        animation.y += animation.speed.y;
        hitBox.setPosition(hitBox.getX()+animation.speed.x, hitBox.getY()+animation.speed.y);
    }

    public void dispose(){
        animation.dispose();
        sound.dispose();
    }
}
