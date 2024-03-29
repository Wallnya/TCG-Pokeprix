package com.pcp.pokemoncardprice.ui.card_info;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pcp.pokemoncardprice.models.CardItem;
import com.pcp.pokemoncardprice.net.CardInfoAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CardsInfoViewModel extends AndroidViewModel {

    private MutableLiveData<CardItem> cardItems;

    public static String playerTag;

    public CardsInfoViewModel(Application application) {
        super(application);
        cardItems = new MutableLiveData<>();

        CardInfoAPI.getCardInfo(playerTag, getApplication().getApplicationContext(), response -> {
            cardItems = retrieveData(response);
        }, error -> {
            cardItems.setValue(null);
        });
    }

    public LiveData<CardItem> getLastPlayer() {
        return cardItems;
    }

    public LiveData<CardItem> getCardInfo(String playerTag) {
        CardsInfoViewModel.playerTag = playerTag;
        cardItems = new MutableLiveData<>();

        CardInfoAPI.getCardInfo(playerTag, getApplication().getApplicationContext(), response -> {
            cardItems = retrieveData(response);
        }, error -> {
            cardItems.setValue(null);
        });
        return cardItems;
    }

    public MutableLiveData<CardItem> retrieveData(JSONObject response){
        try {
            JSONObject cardInfo = response.getJSONObject("data");
            String id = cardInfo.getString("id");
            String name = cardInfo.getString("name");
            String set = cardInfo.getJSONObject("set").getString("name");
            String setImage = cardInfo.getJSONObject("set").getJSONObject("images").getString("symbol");

            String releasedDate = cardInfo.getJSONObject("set").getString("releaseDate");

            SimpleDateFormat spf = new SimpleDateFormat("yyyy/MM/dd");
            Date newDate = spf.parse(releasedDate);
            spf = new SimpleDateFormat("dd/MM/yyyy");
            String newDateString = spf.format(newDate);


            String cardMarketaverageSellPrice = "0";
            String cardMarketavg1 = " / ";
            String cardMarketavg7 = " / ";
            String cardMarketavg30 = " / ";
            String date = " / ";
            if(cardInfo.has("cardmarket")) {
                if (cardInfo.getJSONObject("cardmarket").has("prices")) {
                    cardMarketaverageSellPrice = cardInfo.getJSONObject("cardmarket").getJSONObject("prices").getString("trendPrice");
                    cardMarketavg1 = cardInfo.getJSONObject("cardmarket").getJSONObject("prices").getString("avg1");
                    cardMarketavg7 = cardInfo.getJSONObject("cardmarket").getJSONObject("prices").getString("avg7");
                    cardMarketavg30 = cardInfo.getJSONObject("cardmarket").getJSONObject("prices").getString("avg30");
                    date = cardInfo.getJSONObject("cardmarket").getString("updatedAt");
                    spf = new SimpleDateFormat("yyyy/MM/dd");
                    newDate = spf.parse(date);
                    spf = new SimpleDateFormat("dd/MM/yyyy");
                    date = spf.format(newDate);
                }
            }
            String tcgPlayerMarket = " / ";
            String tcgPlayerLow = " / ";
            String tcgPlayerMid = " / ";
            String tcgPlayerHigh = " / ";
            if(cardInfo.has("tcgplayer")){
                if(cardInfo.getJSONObject("tcgplayer").has("prices")){
                    if(cardInfo.getJSONObject("tcgplayer").getJSONObject("prices").has("holofoil")){
                        if(cardInfo.getJSONObject("tcgplayer").getJSONObject("prices").getJSONObject("holofoil").has("market")){
                            tcgPlayerMarket = cardInfo.getJSONObject("tcgplayer").getJSONObject("prices").getJSONObject("holofoil").getString("market");
                        }
                        tcgPlayerLow = cardInfo.getJSONObject("tcgplayer").getJSONObject("prices").getJSONObject("holofoil").getString("low");
                        tcgPlayerMid = cardInfo.getJSONObject("tcgplayer").getJSONObject("prices").getJSONObject("holofoil").getString("mid");
                        tcgPlayerHigh = cardInfo.getJSONObject("tcgplayer").getJSONObject("prices").getJSONObject("holofoil").getString("high");
                    }
                    else if (cardInfo.getJSONObject("tcgplayer").getJSONObject("prices").has("normal")){
                        tcgPlayerMarket = cardInfo.getJSONObject("tcgplayer").getJSONObject("prices").getJSONObject("normal").getString("market");
                        tcgPlayerLow = cardInfo.getJSONObject("tcgplayer").getJSONObject("prices").getJSONObject("normal").getString("low");
                        tcgPlayerMid = cardInfo.getJSONObject("tcgplayer").getJSONObject("prices").getJSONObject("normal").getString("mid");
                        tcgPlayerHigh = cardInfo.getJSONObject("tcgplayer").getJSONObject("prices").getJSONObject("normal").getString("high");
                    }
                }
            }
            String rarity = " / ";
            if(cardInfo.has("rarity")) {
                rarity = cardInfo.getString("rarity");
            }
            else{
                rarity = "no rarity";
            }
            String cardImage = cardInfo.getJSONObject("images").getString("small");

            CardItem cardItem = new CardItem(id, name, set,setImage,cardMarketaverageSellPrice,cardMarketavg1,cardMarketavg7,cardMarketavg30,tcgPlayerMarket,
                    tcgPlayerLow,tcgPlayerMid,tcgPlayerHigh,rarity,cardImage,date,newDateString);
            cardItems.setValue(cardItem);

        } catch (JSONException e) {
            e.printStackTrace();
            cardItems.setValue(null);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cardItems;
    }

    public MutableLiveData<CardItem> updateCard(String playerTag) {
        CardInfoAPI.getCardInfo(playerTag,getApplication().getApplicationContext(), response -> {
            cardItems = retrieveDataOneCard(response);
        }, error -> {
            cardItems.setValue(null);
        });
        return cardItems;
    }

    public MutableLiveData<CardItem> retrieveDataOneCard(JSONObject response){
        try {
            JSONObject cardInfo = response.getJSONObject("data");
            String id = cardInfo.getString("id");
            String cardMarketaverageSellPrice = "0";
            String date = " / ";
            if(cardInfo.has("cardmarket")) {
                if (cardInfo.getJSONObject("cardmarket").has("prices")) {
                    cardMarketaverageSellPrice = cardInfo.getJSONObject("cardmarket").getJSONObject("prices").getString("trendPrice");
                    date = cardInfo.getJSONObject("cardmarket").getString("updatedAt");
                    SimpleDateFormat spf = new SimpleDateFormat("yyyy/MM/dd");
                    spf = new SimpleDateFormat("yyyy/MM/dd");
                    Date newDate = spf.parse(date);
                    spf = new SimpleDateFormat("dd/MM/yyyy");
                    date = spf.format(newDate);
                }
            }
            CardItem cardItem = new CardItem(id,cardMarketaverageSellPrice,date);
            cardItems.setValue(cardItem);
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
            cardItems.setValue(null);
        }
        return cardItems;
    }
}
