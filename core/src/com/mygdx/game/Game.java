package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.obj.BulletHolder;
import com.mygdx.game.obj.InObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shape;
	ArrayList<InObject> explosions, birds, deadBeards;
	float r, g, b;
	private double time = 1/6f, birdTime, maxBirdTime  = 1;
	Texture explsionAnm, birdAnim, back;

	@Override
	public void create () {
		shape = new ShapeRenderer();
		batch = new SpriteBatch();
		explosions = new ArrayList<>();
		birds = new ArrayList<>();
		deadBeards = new ArrayList<>();

		explsionAnm = new Texture("explosion.png");
		birdAnim = new Texture("bird.png");
		back = new Texture("gameFon.png");
	}

	private void setRGB(float dTime){
		time += dTime;
		r = (float) Math.sin(time);
		g = (float) Math.cos(time);
		b = (float) 0.5f;
	}

	private void tach(){
		System.out.println(explosions.size());
	}

	@Override
	public void render () {
//		ScreenUtils.clear(r, r, 0, 1);
		tach();
//		setRGB(Gdx.graphics.getDeltaTime());

		int x = Gdx.input.getX();
		int y = Gdx.graphics.getHeight() - Gdx.input.getY();

		birdTime -= Gdx.graphics.getDeltaTime();
		if (birdTime <= 0) {
			addBird();
			birdTime = maxBirdTime;
		}

		batch.begin();
		batch.draw(back, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.graphics.setTitle(String.valueOf(birds.size()) + " " + 1.0f/Gdx.graphics.getDeltaTime());
		//птички
		for (InObject bird: birds) {
			bird.animation.draw(batch, Gdx.graphics.getDeltaTime());
		}

		// это про взрывы
		if (BulletHolder.canFire(batch, new Vector2(x, y))) {
			explosions.add(new InObject("vzryiv.mp3", explsionAnm, 4, 4, 16, x, y, new Vector2(new Vector2(0,0)), 1));
			Iterator<InObject> iterator = birds.iterator();
			while (iterator.hasNext()){
				InObject bird = iterator.next();
				if (bird.hitBox.contains(x, y)) {
					deadBeards.add(bird);
					iterator.remove();
				}
			}
		}
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).animation.draw(batch, Gdx.graphics.getDeltaTime());
		}
		for (InObject bird: deadBeards) {
			bird.moveing();
			bird.animation.draw(batch, Gdx.graphics.getDeltaTime());
		}
		batch.end();

		for (int i = 0; i < explosions.size(); i++) {
			if (explosions.get(i).animation.isFinished()) {
				explosions.get(i).dispose();
				explosions.remove(i);
			}
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		shape.begin(ShapeRenderer.ShapeType.Line);
		shape.setColor(Color.LIME);
		for (InObject bird: birds) {
			bird.moveing();
			shape.rect(bird.hitBox.x,bird.hitBox.y, bird.hitBox.width, bird.hitBox.height);
		}
		shape.end();

		Iterator<InObject> iterator = deadBeards.iterator();
		while (iterator.hasNext()) {
			animateBirdDead(iterator.next());
		}
	}

	private void addBird(){
		float spd = (MathUtils.random() - 1.0f)*2.0f - 1;
		float scale = MathUtils.random(0.125f, 0.375f);
		birds.add(new InObject("vzryiv.mp3", birdAnim, 4, 3, 16, Gdx.graphics.getWidth(), (int) ((MathUtils.random())*Gdx.graphics.getHeight()), new Vector2(spd, 0), scale));
		birds.get(birds.size()-1).animation.setPlayMode(Animation.PlayMode.LOOP);
		birds.get(birds.size()-1).animation.setScale(scale, scale);
		birds.get(birds.size()-1).animation.setRotate(-90f);
		birds.get(birds.size()-1).sound.pause();
	}

	private void animateBirdDead(InObject bird){
		if (bird.animation.getRotation() < 90)  bird.animation.rotate(1);
		if (bird.animation.speed.x < 0 ) bird.animation.speed.x += .02f;
		if (bird.animation.speed.x > 0) bird.animation.speed.x = 0;
		if (bird.animation.speed.y > -2) bird.animation.speed.y -= .02f;
		else bird.animation.speed.y = 0;
		bird.animation.setIsTime(false);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}