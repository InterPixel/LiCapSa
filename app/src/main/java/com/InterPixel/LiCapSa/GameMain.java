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
    private enum Suits{Diamonds, Clubs, Hearts, Spades}

    //definer kartunya
    private class Cards{
        Suits suit;
        byte number;
        private Cards(Suits selSuit, byte cardNumber){
            suit = selSuit;
            number = cardNumber;
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
        System.out.println(cardNumber + " " + cardSuit);
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
        for (int i = 0; i < o.card.size(); i++) {
            System.out.print("Suit: " + o.card.get(i).suit + " Number: " + o.card.get(i).number + "\n");
        }
    }

    void sort(){

    }

    void changePos(byte position, byte selectedCard){
    }
}
