package com.badlogic.drop;

import org.json.simple.JSONObject;

import java.io.InputStream;

public class HelperForJsonBody {
    private JSONObject object;
    public HelperForJsonBody(JSONObject object){
        this.object=object;

    }
    public HelperForJsonBody(InputStream inputStream){

    }
    public JSONObject FormAuth(String login, String password){
        object.put("login",login);
        object.put("password",password);
        return object;
    }

}
