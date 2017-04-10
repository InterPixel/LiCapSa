package com.InterPixel.LiCapSa;

/**
 * Created by Renz on 09/04/2017.
 * Basically we have four type os AI
 * Noob AI which moves randomly
 * Easy AI which uses dynamic greedy algorithm
 * Hard AI which uses common sense
 * Insane AI which uses Monte Carlo Tree Search to predict the future
 * All AI is not allowed to cheat, but they may keep track of cards already dealt
 */

public interface AI {

    void onSummoned();

    void onUpdateState();

    void onMyTurn();
}
