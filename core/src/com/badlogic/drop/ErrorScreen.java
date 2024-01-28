
package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ErrorScreen implements Screen {
    Game game;
    SpriteBatch batch;
    Texture img;
    public Stage stage;
    public BitmapFont font;
    String typeError;
    AuthScreen authScreen;
    Table table;
    public ErrorScreen(Game game, String typeError, AuthScreen authScreen ){
        this.game=game;
        this.typeError=typeError;
        this.authScreen=authScreen;
        init();
    }
    private void init(){
        Gdx.graphics.setContinuousRendering(true);
        font = new BitmapFont();
        stage = new Stage(new FitViewport((float) Gdx.graphics.getWidth() ,(float)Gdx.graphics.getHeight()  ));

        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        img = new Texture("authPicture/badlogic.jpg");
        Skin skin =new Skin(Gdx.files.internal("authPicture/uiskin.json"));;
        table = new Table();

        table.setFillParent(true);
        Label labelError=new Label(typeError,skin);
        font = labelError.getStyle().font; // получение шрифта TextField
        font.getData().setScale(3);
        TextButton buttonReturn = new TextButton("Return", skin);
        buttonReturn.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.setScreen(new AuthScreen(game));
                dispose();
                return false;
            }
        });


        table.defaults().width(300);
        table.add(labelError);
        table.row();
        table.add(buttonReturn);
        stage.addActor(table);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        batch.draw(img, 0, 0);
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
    public void dispose() {
        font.dispose();
        game.dispose();
        stage.dispose();
        batch.dispose();
        img.dispose();
    }
}
