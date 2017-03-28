package com.InterPixel.LiCapSa;
import java.util.*;

public class GameMain {

	/*
	Ace jadi 1
	*/

    //Suit punya isi diamond, club, heart, spade
    enum Suits{Diamonds, Clubs, Hearts, Spades}

    //definer kartunya
    class Cards{
        Suits suit;
        byte number;
        Cards(Suits selSuit, byte cardNumber){
            suit = selSuit;
            number = cardNumber;
        }
        int getNumber(){
            return number;
        }
        int getSuit(){
            int suitNumber;
            if (suit == Suits.Diamonds) suitNumber = 1;
            else if (suit == Suits.Clubs) suitNumber = 2;
            else if (suit == Suits.Hearts) suitNumber = 3;
            else suitNumber = 4;
            return suitNumber;
        }
    }

    //kartu di tangan orangnya
    List<Cards> card = new ArrayList<>();

    //angka yang bakal di bagiin ke orang
    List<Integer> number = new ArrayList<>();

    void giveCards(){
        //populate list number
        //males gw :P (masalah di int i)
        {
            int i = 0;
            do{
                i++;
                number.add(i);
            }while(number.size() < 52);
        }
        //ngasih angka ke player"nya
        for (byte i = 1; i <= 13; i++){
            for (byte a = 1; a <= 4; a++){
                //output angka random sesuai size listnya
                int randIndexNumber = (int)Math.floor(Math.random()*number.size());
                //ngasih angka ke playernya
                givePlayer (a, number.get(randIndexNumber).byteValue());
                number.remove(randIndexNumber);
            }
        }
    }
    //handle player mana di kasih kemana
    void givePlayer(byte player, byte cardNumber){
        switch (player) {
            case 1:
                recieveCard(cardNumber);
                break;
            case 2:
                //kirim angkanya ke device yang lain
                break;
        }
    }
    //ngehandle kartu yang di dapetin
    void recieveCard(byte cardNumber){
        byte cardSuit = 1;
        while (cardNumber>13){
            cardNumber -= 13;
            cardSuit++;
        }

        //convert angka jadi suit
        switch (cardSuit){
            case 1:
                card.add(new Cards(Suits.Diamonds, cardNumber));
                break;
            case 2:
                card.add(new Cards(Suits.Clubs, cardNumber));
                break;
            case 3:
                card.add(new Cards(Suits.Hearts, cardNumber));
                break;
            case 4:
                card.add(new Cards(Suits.Spades, cardNumber));
                break;
        }
    }
    public static void main(String[] args){
        GameMain o = new GameMain();
        CardSort oSort = new CardSort();
        o.giveCards();
        //o.card = oSort.onSortByNumberButtonPressed(o.card);
        oSort.checkGroups(o.card);
        for (int i = 0; i < o.card.size(); i++) {
            System.out.print("Suit: " + o.card.get(i).suit + " \tNumber: " + o.card.get(i).number + "\n");
        }

    }
}
