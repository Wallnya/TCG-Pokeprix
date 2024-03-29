package com.pcp.pokemoncardprice.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pcp.pokemoncardprice.net.core.PokemonRequestQueue;

import org.json.JSONObject;

public class CardInfoAPI {

    static String API_ENDPOINT = "https://api.pokemontcg.io/v2/cards/";
    public static void getCardInfo(String playerTag, Context ctx, Response.Listener<JSONObject> onResponse, Response.ErrorListener onError) {
        String requestUrl = API_ENDPOINT + playerTag;
        JsonObjectRequest cardRequest = new JsonObjectRequest
                (Request.Method.GET, requestUrl, null, onResponse, null) {
        };
        PokemonRequestQueue.getInstance(ctx).addToRequestQueue(cardRequest);
    }
}
