package com.InterPixel.LiCapSa;


import java.util.ArrayList;
import java.util.List;

public class ValidGroupCard implements Capsa {

    public static Hands whatHandIsThis(List<Cards> cardsToBeEvaluated){

        List<Cards> sorted;

        switch (cardsToBeEvaluated.size()){
            case 1:
                return Hands.Single;
                //break;

            case 2:
                if(cardsToBeEvaluated.get(0).getRank() == cardsToBeEvaluated.get(1).getRank()){
                    return Hands.Pair;
                }else{
                    return Hands.Invalid;
                }
                //break;

            case 5:
                sorted = sortCard(cardsToBeEvaluated);

                if(sorted.get(1).getRank() == sorted.get(2).getRank() && sorted.get(2).getRank() == sorted.get(3).getRank())  {
                    if ( sorted.get(0).getRank() == sorted.get(1).getRank() || sorted.get(4).getRank() == sorted.get(1).getRank() ){
                        return Hands.FourOfAKind;
                    }
                }

                if(sorted.get(0).getRank() == sorted.get(1).getRank() && sorted.get(3).getRank() == sorted.get(4).getRank()){
                    if ( sorted.get(2).getRank() == sorted.get(0).getRank() || sorted.get(2).getRank() == sorted.get(3).getRank() ){
                        return Hands.FullHouse;
                    }else{
                        if ( checkFlush(sorted) ){
                            return Hands.Flush;
                        }else{
                            return Hands.Invalid;
                        }
                    }
                }

                if ( checkFlush(sorted) && checkStraight(sorted) ){
                    return Hands.StraightFlush;
                }else if(checkStraight(sorted)){
                    return  Hands.Straight;
                }else if(checkFlush(sorted)){
                    return Hands.Flush;
                }

                return Hands.Invalid;

                //break;

            case 13:
                sorted = sortCard(cardsToBeEvaluated);
                int anchor = sorted.get(0).getRankinInt();
                if ( anchor + 1 == sorted.get(1).getRankinInt() && anchor + 2 == sorted.get(2).getRankinInt()
                        && anchor + 3 == sorted.get(3).getRankinInt() && anchor + 4 == sorted.get(4).getRankinInt()
                        && anchor + 5 == sorted.get(5).getRankinInt() && anchor + 6 == sorted.get(6).getRankinInt()
                        && anchor + 7 == sorted.get(7).getRankinInt() && anchor + 8 == sorted.get(8).getRankinInt()
                        && anchor + 9 == sorted.get(9).getRankinInt() && anchor + 10 == sorted.get(10).getRankinInt()
                        && anchor + 11 == sorted.get(11).getRankinInt() && anchor + 12 == sorted.get(12).getRankinInt()){
                    return Hands.Dragon;
                }
                return Hands.Invalid;
                //break;

            default:
                return Hands.Invalid;
                //break;
        }
    }

    private static boolean checkStraight(List<Cards> cards){
        int anchor = cards.get(0).getRealValue();
        if ( anchor + 1 == cards.get(1).getRealValue() && anchor + 2 == cards.get(2).getRealValue()
                && anchor + 3 == cards.get(3).getRealValue() && anchor + 4 == cards.get(4).getRealValue()){
            return true;
        }
        return false;
    }

    private static boolean checkFlush(List<Cards> cards){
        Suits anchor = cards.get(0).getSuit();
        if (cards.get(1).getSuit() == anchor && cards.get(2).getSuit() == anchor && cards.get(3).getSuit() == anchor
                && cards.get(4).getSuit() == anchor){
            return true;
        }
        return false;
    }



    //sort cards with ascending order, rank first, then suit
    public static List<Cards> sortCard(List<Cards> CardsToBeSorted){

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
