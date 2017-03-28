package com.InterPixel.LiCapSa;

public class ValidGroupCard extends GameMain {
    boolean validPair(Cards o1, Cards o2){
        if (o1.suit == o2.suit){
            return true;
        }
        return false;
    }
}
