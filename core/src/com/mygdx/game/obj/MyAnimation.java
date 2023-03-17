package com.mygdx.game.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class MyAnimation {

    private Texture img;
    private Animation<TextureRegion> animation;
    private float time;
    protected float x, y, scaleX, scaleY, rotation;
    private boolean clockwise;
    public Vector2 speed;
    private boolean isTime;

    public MyAnimation(Texture name, int col, int row, int fps, int x, int y, Vector2 speed){
        img = name;

        TextureRegion region = new TextureRegion(img);
        TextureRegion[][] regions = region.split(img.getWidth()/col, img.getHeight()/row);
        TextureRegion[] tmp = new TextureRegion[regions.length * regions[0].length];
        int cnt = 0;
        for (int i = 0; i < regions.length; i++) {
            for (int j = 0; j < regions[0].length; j++) {
                tmp[cnt++]  = regions[i][j];
            }
        }
        float dur = 1.0f/fps;
        animation = new Animation<>(dur, tmp);
        animation.setPlayMode(Animation.PlayMode.NORMAL);

        this.x = x - animation.getKeyFrame(0).getRegionWidth()/2.0f;
        this.y = y - animation.getKeyFrame(0).getRegionHeight()/2.0f;

        this.speed = speed;
        scaleX = scaleY = 1;
        isTime = true;
    }

    public void setIsTime(boolean isTime) {this.isTime = isTime;}

    public void setRotate(float rotation){this.rotation = rotation;}
    public float getRotation() {return rotation;}
    public void rotate(float rotation){this.rotation += rotation;}

    public void setScale(float scaleX, float scaleY){
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public void setPlayMode(Animation.PlayMode mode) {animation.setPlayMode(mode);}

    public TextureRegion getRegion() {
        return animation.getKeyFrame(time);
    }

    public void shiftTime(){time += Gdx.graphics.getDeltaTime();}

    public Vector2 getPosition() {return new Vector2(x*scaleX,y*scaleY);}

    public void setPosition(Vector2 position) {
        x = position.x;
        y = position.y;
    }

    public float getY() {return y;}

    public boolean isFinished(){
        boolean animationFinished = animation.isAnimationFinished(time);
        return animationFinished;}

    public void dispose(){
        //img.dispose();
    }

    public void draw(SpriteBatch batch, float dTime) {
        if (isTime) time += dTime;
        float originX = animation.getKeyFrame(time).getRegionWidth()/2.0f;
        float originY = animation.getKeyFrame(time).getRegionHeight()/2.0f;
        float width = animation.getKeyFrame(time).getRegionWidth();
        float height = animation.getKeyFrame(time).getRegionHeight();
        batch.draw(animation.getKeyFrame(time), x, y, originX, originY, width, height, scaleX, scaleY, rotation, clockwise);

    }
}