package com.InterPixel.LiCapSa;


import java.util.ArrayList;
import java.util.List;

public class ValidGroupCard implements Capsa {

    public enum Hands{
        Invalid, Single, Pair, Straight, Flush, FullHouse, FourOfAKind, StraightFlush, Dragon
    }
/*
    public Hands whatHandIsThis(List<Cards> cardsToBeEvaluated){

        List<Cards> sorted;

        switch (cardsToBeEvaluated.size()){
            case 1:
                return Hands.Single;
                break;

            case 2:
                if(cardsToBeEvaluated.get(0).number == cardsToBeEvaluated.get(1).number){
                    return Hands.Pair;
                }else{
                    return Hands.Invalid;
                }
                break;

            case 5:
                sorted = sortCard(cardsToBeEvaluated);

                if(sorted.get(0).number == sorted.get(1).number && sorted.get(3).number == sorted.get(4).number){
                    if ( sorted.get(2).number == sorted.get(0).number || sorted.get(2).number == sorted.get(3).number ){
                        return Hands.FullHouse;
                    }else{
                        return Hands.Invalid;
                    }
                }

                break;

            case 13:
                sorted = sortCard(cardsToBeEvaluated);
                break;

            default:
                return Hands.Invalid;
                break;
        }
    }

*/

    //sort cards with ascending order, rank first, then suit
    private static List<Cards> sortCard(List<Cards> CardsToBeSorted){

        boolean[][] sorter = new boolean[14][5];
        List<Cards> sortedCards = new ArrayList<>();

        for (int i = 0; i < sorter.length; i++) {
            for (int j = 0; j < sorter[i].length; j++){
                sorter[i][j] = false;
            }
        }

        for (int i = 0; i < CardsToBeSorted.size(); i++) {
            sorter[CardsToBeSorted.get(i).getRankinInt()][CardsToBeSorted.get(i).getSuitinInt()] = true;
        }

        for (int i = 0; i < sorter.length; i++) {
            for (int j = 0; j < sorter[i].length; j++){
                if ( sorter[i][j] ){
                    sortedCards.add(new Cards(j,i));
                }
            }
        }

        return sortedCards;
    }
}
