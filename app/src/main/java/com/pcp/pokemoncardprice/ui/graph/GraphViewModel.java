package com.pcp.pokemoncardprice.ui.graph;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pcp.pokemoncardprice.jsonreader.JsonReader;
import com.pcp.pokemoncardprice.models.CardItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class GraphViewModel extends AndroidViewModel {
    private MutableLiveData<CardItem> cardItems;
    public static String playerTag;
    private JsonReader jsonReader = new JsonReader();

    private final MutableLiveData<String> mText;

    public GraphViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
    }

    public LiveData<CardItem> getCardInfo(String playerTag) {
        GraphViewModel.playerTag = playerTag;
        cardItems = new MutableLiveData<>();
        cardItems = retrieveData(cardItems, playerTag);
        return cardItems;
    }

    private MutableLiveData<CardItem> retrieveData(MutableLiveData<CardItem> mCards, String id){
        String jsonString = jsonReader.read(getApplication().getApplicationContext(), "data.json");
        JSONObject obj;
        CardItem card = null;
        try {
            obj = new JSONObject(jsonString);
            JSONArray array=obj.getJSONArray("data");
            for(int i=0;i<array.length();i++){
                if(array.getJSONObject(i).getString("id").equals(id)){
                    String extensionUrl = array.getJSONObject(i).getString("extensionImage").replace("\"","");
                    card = new CardItem(array.getJSONObject(i).getString("id"),array.getJSONObject(i).getString("name"),
                            array.getJSONObject(i).getString("extension"),extensionUrl,array.getJSONObject(i).getJSONArray("prices").getJSONObject(0).getString("prix"),array.getJSONObject(i).getJSONArray("prices").getJSONObject(0).getString("date"),
                            array.getJSONObject(i).getString("releasedDate"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mCards.setValue(card);
        return mCards;
    }

    public LiveData<String> getText() {
        return mText;
    }

    public String getID(){
        return GraphViewModel.playerTag;
    }
}