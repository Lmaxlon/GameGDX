package com.badlogic.drop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
public class MyPacker {
    public static void main (String[] args) throws Exception {
        TexturePacker.process("assets/authback_source", "assets/atlas", "authback_atlas");
    }
}
