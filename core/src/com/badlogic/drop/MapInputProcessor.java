package com.badlogic.drop;

import com.badlogic.gdx.InputProcessor;

public class MapInputProcessor implements InputProcessor {
    private float lastX;
    private float lastY;
    private float mapX;
    private float mapY;

    public MapInputProcessor(float initialMapX, float initialMapY) {
        this.mapX = initialMapX;
        this.mapY = initialMapY;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        lastX = screenX;
        lastY = screenY;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float deltaX = screenX - lastX;
        float deltaY = screenY - lastY;
        mapX += deltaX;
        mapY -= deltaY; // Invert the Y direction since Y grows downwards
        lastX = screenX;
        lastY = screenY;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public float getMapX() {
        return mapX;
    }

    public float getMapY() {
        return mapY;
    }

    // Implement other methods of InputProcessor interface if needed
}
