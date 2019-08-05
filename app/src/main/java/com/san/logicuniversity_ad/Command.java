package com.san.logicuniversity_ad;


import org.json.JSONObject;

public class Command {
    protected AsyncToServer.IServerResponse callback;
    protected String context;
    protected String endPt;
    protected JSONObject data;

    public Command(AsyncToServer.IServerResponse callback,
            String context, String endPt, JSONObject data)
    {
        this.callback = callback;
        this.context = context;
        this.endPt = endPt;
    }
}
