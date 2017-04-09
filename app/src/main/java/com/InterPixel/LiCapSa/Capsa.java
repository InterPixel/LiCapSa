package com.InterPixel.LiCapSa;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Renz on 07/04/2017.
 * This interface is meant to be implemented by all classes
 * who works with game mechanics
 */

public interface Capsa {

    enum Suits{

        Diamonds(1), Clubs(2), Hearts(3), Spades(4);

        private int value;

        Suits(int x){
            value = x;
        }

        public int getInt(){
            return value;
        }
    }

    enum Ranks{
        ace(1), two(2), three(3), four(4), five(5), six(6), seven(7), eight(8), nine(9), ten(10),
        jack(11), queen(12), king(13);

        private int value;

        Ranks(int x){
            value = x;
        }

        public int getInt(){
            return value;
        }
    }

    enum Players{
        localPlayer(1), noobAI(2), easyAI(3), hardAI(4), insaneAI(5), onlinePlayer(6);

        private int value;

        Players(int x){
            value = x;
        }

        public int getInt(){
            return value;
        }

        public boolean hasWon = false;
        public List<Cards> cardsInHand = new ArrayList<>();
        public int participantId = 0;
    }

    //class for all cards
    class Cards{

        //properties defined private for better encapsulation
        private Suits suit;
        private Ranks rank;

        //Constructor for making a card
        Cards(Suits mSuit, Ranks mRank){
            suit = mSuit;
            rank = mRank;
        }

        //Constructor with integer
        //It is recommended however to never use enum as int
        //because enum in Java is a monster on it's own
        Cards(int mSuit, int mRank){
            suit = Suits.values()[mSuit - 1];
            rank = Ranks.values()[mRank - 1];
        }

        //getter methods
        Suits getSuit(){
            return suit;
        }

        Ranks getRank(){
            return rank;
        }

        int getSuitinInt(){
            return suit.getInt();
        }

        int getRankinInt(){
            return rank.getInt();
        }

    }

    class Tools{
        public static void debug(String msg){
            Log.d(TAG, "debug: " + msg);
        }
    }
}
