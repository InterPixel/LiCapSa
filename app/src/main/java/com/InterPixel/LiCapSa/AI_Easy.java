package com.InterPixel.LiCapSa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Renz on 10/04/2017.
 *
 * 1.Memilah kartu di tangan menjadi set
 * 2.Mengeluarkan set dgn nlai terkecil jika ada
 *
 */

public class AI_Easy implements Capsa{

    AI_Easy(){

    }

    //Aside from five hand card, solve with histogram
    //Must be more than or equal to 5
    List<Cards[]> possibleSetsIn(List<Cards> card){

        int numberOf5Set = 0;
        int numberOfPair = 0;

        List<Cards[]> ListOfPossibleSets = new ArrayList<>();
        List<Cards> remainingCards = card;
        List<Cards> toBeTested = new ArrayList<>();

        toBeTested.add(null);
        toBeTested.add(null);
        toBeTested.add(null);
        toBeTested.add(null);
        toBeTested.add(null);

        for (int i = 0; i < card.size() - 4; i++) {
            toBeTested.set(0,card.get(i));
            for (int j = i + 1; j < card.size() - 3; j++) {
                toBeTested.set(1,card.get(j));
                for (int k = j + 1; k < card.size() - 2; k++) {
                    toBeTested.set(2,card.get(k));
                    for (int l = k + 1; l < card.size() - 1; l++) {
                        toBeTested.set(3,card.get(l));
                        for (int m = l + 1; m < card.size(); m++) {
                            toBeTested.set(4,card.get(m));
                            if ( ValidGroupCard.whatHandIsThis(toBeTested) != Hands.Invalid ){
                                //A 5 set was found, calculate the remaining hand
                                numberOf5Set++;
                                ListOfPossibleSets.add((Cards[]) toBeTested.toArray());
                                //make a equals() in cards class, remove card from hand if alrdy used for set
                                //make it a list of list of array List<List<Cards[]>>

                            }
                        }
                    }
                }
            }
        }

        return ListOfPossibleSets;
    }

}
