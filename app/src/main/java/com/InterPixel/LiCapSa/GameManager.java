package com.InterPixel.LiCapSa;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Renz on 27/03/2017.
 * This class is the dealer / table class which handles everything related to game play
 */

public class GameManager implements Capsa {

    private final static byte numberOfCards = 52;

    //Singleton declaration
    private static GameManager instance = null;

    public static GameManager Instance(){
        if(instance != null){
            return instance;
        }else{
            instance = new GameManager();
            return instance;
        }

    }

    private Cards[] fullDeck = new Cards[52];
    public List<Cards> cardsOnTable = new ArrayList<>();

    private Players[] player = new Players[4];
    public int existingPlayers;

    public void SinglePlayer(Players firstAI, Players secondAI, Players thirdAI){
        player[0] = Players.localPlayer;
        player[1] = firstAI;
        player[2] = secondAI;
        player[3] = thirdAI;
        existingPlayers = 4;
        shuffleCards();
        distributeCards();

        int playerTurn = whoGoesFirst();

        while (existingPlayers > 0){
            //yourTurn()
            playerTurn++;
            playerTurn = playerTurn % 4;
        }

    }

    private void shuffleCards(){

        //Remember that the deck goes from 0 to 51
        //Firstly we need to fill in the array
        int i = 0;
        for (Ranks rank : Ranks.values()) {
            for(Suits suit : Suits.values()){
                fullDeck[i] = new Cards(suit,rank);
                i++;
            }
        }

        //Then we can shuffle the cards
        //We need a temp to store card that is being swapped
        Cards temp;
        int toSwap;
        //We start from the card on the top all the way down
        for (int j = 51; j > 0; j--) {
            toSwap = (int) Math.floor(Math.random() * j);
            temp = fullDeck[j];
            fullDeck[j] = fullDeck[toSwap];
            fullDeck[toSwap] = temp;
        }
    }

    private void distributeCards(){
        int j = 0;
        for (int i = 0; i < 13; i++) {
            player[0].cardsInHand.add(fullDeck[j]);
            j++;
            player[1].cardsInHand.add(fullDeck[j]);
            j++;
            player[2].cardsInHand.add(fullDeck[j]);
            j++;
            player[3].cardsInHand.add(fullDeck[j]);
            j++;
        }

        //send to respective players if multi
    }

    private int whoGoesFirst(){
        Cards toBeChecked;
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 4; j++) {
                toBeChecked = player[j].cardsInHand.get(i);
                if(toBeChecked.getRank() == Ranks.three && toBeChecked.getSuit() == Suits.Diamonds){
                    return j;
                }
            }
        }
        return -1; //berarti error
    }

    private void summonAI(Players[] players){

        int numberOfNoobAI = 0;
        int numberOfEasyAI = 0;
        int numberOfHardAI = 0;
        int numberOfInsaneAI = 0;

        for (int i = 0; i < players.length; i++) {
            if ( players[i] == Players.noobAI ){
                numberOfNoobAI++;
            }else if ( players[i] == Players.easyAI ){
                numberOfEasyAI++;
            }else  if ( players[i] == Players.hardAI ){
                numberOfHardAI++;
            }else if ( players[i] == Players.insaneAI ){
                numberOfInsaneAI++;
            }
        }

        AI[] AIs = new AI[5];
        //AIs[1] = new AI_Hard();

    }

}
