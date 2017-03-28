package com.InterPixel.LiCapSa;

import java.util.*;

public class GameMain {
    /*TODO
    AI = Stanley
    Networking = ?
    Gambar/Asset(Kartu dkk) = Arya
    Main game mechanic = Julius
    UI = JE
     */

	/*
	 * Ace jadi 13
	 */

    //Suit punya isi diamond, club, heart, spade
    public enum Suits{Diamonds, Clubs, Hearts, Spades}

    //definer kartunya
    public class Cards{
        Suits suit;
        byte number;
        private Cards(Suits selSuit, byte cardNumber){
            suit = selSuit;
            number = cardNumber;
        }
        private int getNumber(){
            return number;
        }
        private int getSuit(){
            int suitNumber;
            if (suit == Suits.Diamonds) suitNumber = 1;
            else if (suit == Suits.Clubs) suitNumber = 2;
            else if (suit == Suits.Hearts) suitNumber = 3;
            else suitNumber = 4;
            return suitNumber;
        }
    }

    //Comparator (Things needed to sort)
    class CompareLogicNumber implements Comparator<Cards> {
        public int compare(Cards o1, Cards o2) {
            int temp = ((Integer)o1.getNumber()).compareTo(o2.getNumber());
            //if compared numbers are the same
            if (temp == 0){
                temp = o1.getSuit() - o2.getSuit();
            }
            return temp;
        }
    }
    class CompareLogicSuit implements  Comparator<Cards>{
        public int compare (Cards o1, Cards o2){
            int temp = o1.getSuit() - o2.getSuit();
            if (temp == 0){
                temp = ((Integer) (o1.getNumber())).compareTo(o2.getNumber());
            }
            return temp;
        }
    }

    //kartu di tangan orangnya
    private List<Cards> card = new ArrayList<>();

    //angka yang bakal di bagiin ke orang
    private List<Integer> number = new ArrayList<>();

    private void giveCards(){
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
            for (byte j = 1; j <= 4; j++){
                //output angka random sesuai size listnya
                int randIndexNumber = (int)Math.floor(Math.random()*number.size());
                //ngasih angka ke playernya
                givePlayer (j, number.get(randIndexNumber).byteValue());
                number.remove(randIndexNumber);
            }
        }
    }
    //handle player mana di kasih kemana
    private void givePlayer(byte player, byte cardNumber){
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
    private void recieveCard(byte cardNumber){
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
        o.giveCards();
        o.onSortBySuitButtonPressed();
        for (int i = 0; i < o.card.size(); i++) {
            System.out.print("Suit: " + o.card.get(i).suit + " Number: " + o.card.get(i).number + "\n");
        }
    }

    void onSortByNumberButtonPressed(){
        Collections.sort(card, new GameMain.CompareLogicNumber());
    }
    void onSortBySuitButtonPressed(){
        Collections.sort(card, new GameMain.CompareLogicSuit());
    }
    void onSortByGroupButtonPressed(){

    }
    void checkTris(){

    }
    boolean validPair(Cards o1, Cards o2){
        if (o1.suit == o2.suit){
            return true;
        }
        return false;
    }


    void changePos(byte position, byte selectedCard){
    }
}
