package com.InterPixel.LiCapSa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Renz on 01/04/2017.
 * The computer would sort their cards by rank.
 The computer makes an exhaustive list of any and every valid poker hand contained within their 13 cards (pair, full house, four of a kind, etc.)
 The computer assigns a score to each card, depending on how many poker hands it’s contained in, as well as how rare of each of those hands are (the score added to card contained in a flush would be worth more than one in a straight).
 The computer sorts the aforementioned list of valid poker hands by the average score of the cards contained in each hand.
 The computer plays the lowest ranked valid poker hand that is permissible based on the cards in the center of the table (for example, it can’t play a 5 card flush when there is a pair on the table).
 If a card has been played, any poker hand in the list containing that particular card must be deleted.
 */

public class AI_Hard implements Capsa, AI {

    private List<Cards> cardsInHand = new ArrayList<>();

    AI_Hard(List<Cards> cards){
        cardsInHand = ValidGroupCard.sortCard(cards);
    }
    


    @Override
    public void onSummoned() {

    }

    @Override
    public void onUpdateState() {

    }

    @Override
    public void onMyTurn() {

    }
}
