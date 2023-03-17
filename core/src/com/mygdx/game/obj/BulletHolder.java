package com.mygdx.game.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BulletHolder {
    private static boolean isFire = true;
    private static int shots = 12;
    private static double delayTime = 1/2f;
    private static Texture reArm = new Texture("reArm.png");
    private static Texture bullet = new Texture("bullet.png");
    private static Rectangle reArmRect = new Rectangle(0,0, reArm.getWidth(), reArm.getHeight());

    public static boolean canFire(SpriteBatch batch, Vector2 coord){
        batch.draw(reArm, 0, 0);

        int dx = Gdx.graphics.getWidth() / 24;
        for (int i = 0; i < shots; i++) {
            batch.draw(bullet, i*dx, Gdx.graphics.getHeight()-200/2, 105/2, 200/2);
        }

        if (reArmRect.contains(coord) || Gdx.input.isKeyJustPressed(Input.Keys.R)){
			if (shots < 12) {shots++;}
			isFire = true;
            return false;
        }

        delayTime -= Gdx.graphics.getDeltaTime();
        if (delayTime <= 0 & isFire & Gdx.input.isTouched()) {
            if (shots-- == 0) {
                isFire = false;
            }
            delayTime = 1/2f;
            return  true;
        }
        return false;
    }
}