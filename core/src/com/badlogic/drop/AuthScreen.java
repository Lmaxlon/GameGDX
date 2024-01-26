package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class AuthScreen implements Screen{
	public Game game;
	SpriteBatch batch;
	Texture img;
	Texture lastFrame;
	public Stage stage;
	public TextureAtlas textureAtlas;
	public Animation<TextureRegion> animation;
	public BitmapFont font;
	public AuthScreen(Game game){
		this.game=game;
		init();
	}

	private void init(){
		Gdx.graphics.setContinuousRendering(true);
		font = new BitmapFont();
		stage = new Stage(new FitViewport((float) Gdx.graphics.getWidth() ,(float)Gdx.graphics.getHeight()  ));

		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();
		img = new Texture("authPicture/black.jpg");
		lastFrame = new Texture("atlas/authback_atlas42.png");
		textureAtlas = new TextureAtlas(Gdx.files.internal("atlas/authback_atlas.atlas"));
		animation = new Animation<>(0.1f, textureAtlas.findRegions("authback_atlas"), Animation.PlayMode.LOOP);
		Texture[] textures = new Texture[43];
		for (int i = 0; i < 42; i++) {
			textures[i] = new Texture(Gdx.files.internal("atlas/authback_atlas" + (i + 1) + ".png"));
		}
		TextureRegion[] frames = new TextureRegion[43];
		for (int i = 0; i < 42; i++) {
			frames[i] = new TextureRegion(textures[i]);
		}

		animation = new Animation<TextureRegion>(0.07f, frames);







		Skin skin =new Skin(Gdx.files.internal("authPicture/uiskin.json"));;
		Table table = new Table();

		table.setFillParent(true);
		//table.add(button).center().center().center();
		Label labelLogin=new Label("login:",skin);

		BitmapFont font = labelLogin.getStyle().font; // получение шрифта TextField
		font.getData().setScale(3);
		final TextField textLogin=new TextField("login", skin);
		textLogin.setMaxLength(10);
		Label labelPasswoed=new Label("password:", skin);
		TextField textPassword=new TextField("password", skin);
		textPassword.setMaxLength(10);
		TextButton buttonCreate = new TextButton("Create", skin);
		final TextButton buttonLog = new TextButton("Log in", skin);
		buttonLog.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				Gdx.input.setOnscreenKeyboardVisible(false);
				game.dispose();
				String login=textLogin.getText();
				String password=textPassword.getText();
				Gdx.app.postRunnable(new Task(login,password));
				game.setScreen(new Map());
				return false;
			}
		});

		table.top();
		table.defaults().width(300);
		table.add(labelLogin);
		table.add(textLogin);
		table.row();
		table.add(labelPasswoed);
		table.add(textPassword);
		table.row();
		table.add(buttonLog).width(600).colspan(2);
		table.row();
		table.add(buttonCreate).width(600).colspan(2);
		stage.addActor(table);

	}

	@Override
	public void show() {


	}

	private float stateTime = 0;

	@Override
	public void render(float delta) {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		stateTime += delta; // Увеличиваем время анимации на время прошедшее с предыдущего кадра
		TextureRegion currentFrame = animation.getKeyFrame(stateTime, false);
		if (currentFrame != null) {
			batch.draw(currentFrame, 50, -875, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() + 800);
		} else {
			// Анимация завершилась, начнем ее сначала
			batch.draw(lastFrame, 50, -875, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() + 800);
			stateTime = 0;
		}


		batch.end();
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {

	}



	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}


	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		textureAtlas.dispose();
	}
}
