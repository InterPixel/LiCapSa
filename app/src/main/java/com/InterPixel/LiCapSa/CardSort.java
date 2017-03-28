package com.InterPixel.LiCapSa;

import java.util.*;

public class CardSort extends GameMain {

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
    //Sort The Cards :D
    List<Cards> onSortByNumberButtonPressed(List<Cards> card){
        Collections.sort(card, new CardSort.CompareLogicNumber());
        return card;
    }
    List<Cards> onSortBySuitButtonPressed(List<Cards> card){
        Collections.sort(card, new CardSort.CompareLogicSuit());
        return card;
    }

    //fucking hell
    void onSortByGroupButtonPressed(List<Cards> card){
        //cari dari yang paling gede
        //cari kartu lain biar jadi complete kartu 5
        //kartu yang paling kecil jadi pengcompletenya
        //ganti posisi dia jadi di depan
        //inget untuk ganti posisi group" lain
        //inget untuk cek dia udh di grup ato belom (pair < groupedCards = 5)
        //kalau ga ketemu completer jadi value terendah tetep taro di kiri
        byte sortedIndex = 0;
        //putFours
        for (int i = 0; i < fours.size(); i++) {
            for (int a = 0; a < 4; a++) {
                byte indexToBeSwaped = fours.get(i).get(a);
                Collections.swap(card, a, indexToBeSwaped);
            }
        }
    }

    //check kartu di tangan ada yang sama 4 biji ato ga
    List<List<Byte>> pairs = new ArrayList<>();
    List<List<Byte>> tris = new ArrayList<>();
    List<List<Byte>> fours = new ArrayList<>();

    void checkGroups(List<Cards> card){

        //The Whole Fucking Logic
        label1 : for (byte i = 0; i < card.size(); i++){
            List<Byte> pairedCandidate = new ArrayList<>();
            byte candidate = card.get(i).number;

            //yang sekarang di check udh di jadiin pair ato belom, kalo udh lanjut ke candidate berikutnya
            for (int a = 0; a < pairs.size(); a++) {
                if (candidate == card.get(pairs.get(a).get(1)).number) {
                    i++;
                    continue label1;
                }
            }
            for (int a = 0; a < tris.size(); a++){
                if (candidate == card.get(tris.get(a).get(1)).number) {
                    i++;
                    continue label1;
                }
            }
            for (int a = 0; a < pairs.size(); a++){
                if (candidate == card.get(pairs.get(a).get(1)).number){
                    i++;
                    continue label1;
                }
            }

            //cek masih ada yang sama lagi ga di tangan
            for (byte a = 0; a < card.size(); a++){
                if (candidate == card.get(a).number) {
                    pairedCandidate.add(a);
                }
            }

            //kalo ada 4 kartu yang sama jadi pair 4, 3 jadi tris, 2 jadi pair
            if (pairedCandidate.size() == 4){
                fours.add(pairedCandidate);
            }else if (pairedCandidate.size() == 3){
                tris.add(pairedCandidate);
            }else if (pairedCandidate.size() == 2){
                pairs.add(pairedCandidate);
            }
        }
        System.out.println("Pairs: " + pairs.size() + " Tris: " + tris.size() + " Fours: " + fours.size());
    }
    void checkTris(){
    }
}
