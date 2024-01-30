package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class EmptyWindow extends Stage {
    private Skin skin;
    private Window window;
    public boolean isWindowOpen;
    public int great_push;
    public int cost;
    private Label costLabel;

    public EmptyWindow() {
        skin = new Skin(Gdx.files.internal("authPicture/uiskin.json"));
        initialize();
    }

    private void initialize() {
        window = new Window("Buy Building", skin);
        window.setSize(1500, 1000);
        window.setPosition(600, 125);

        Label messageLabel = new Label("Are you really want to buy this building? This building have a cost", skin);
        messageLabel.setFontScale(3);

        TextButton closeButton = new TextButton("Close", skin);
        closeButton.getLabel().setFontScale(3);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide(); // Hide the window when the close button is clicked
            }
        });

        // Create a label for displaying the cost
        costLabel = new Label("", skin);
        costLabel.setFontScale(2);

        TextButton purchaseButton = new TextButton("Purchase", skin);
        purchaseButton.getLabel().setFontScale(3);
        purchaseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                great_push = 1;
                hide();
            }
        });

        Table contentTable = new Table();
        contentTable.add(messageLabel).pad(10).row();
        contentTable.add(costLabel).pad(10).row(); // Add the cost label
        contentTable.add(purchaseButton).pad(10).row(); // Add the purchase button
        contentTable.add(closeButton).pad(10);

        window.add(contentTable).expand().fill();
        addActor(window);
    }

    private void updateCostLabel() {
        costLabel.setText("Cost: " + cost + "M$");
        costLabel.setFontScale(3);
    }


    public void set_cost(int num) {
        if (num == 1){
            cost = 10;
        }
        if (num == 2){
            cost = 20;
        }
        if (num == 3){
            cost = 30;
        }
        updateCostLabel();
    }

    public void show(){
        window.setVisible(true);
        isWindowOpen = true;
    }

    public void hide() {
        isWindowOpen = false;
        window.setVisible(false);
    }
}