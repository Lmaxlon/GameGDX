package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class StageExtension extends Stage {
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.unfocusAll();
        Gdx.input.setOnscreenKeyboardVisible(false);
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
