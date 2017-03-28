package com.InterPixel.LiCapSa;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.WindowManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;

import java.util.ArrayList;
import java.util.List;


public class PlayGames extends GameActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, RealTimeMessageReceivedListener,
        RoomStatusUpdateListener, RoomUpdateListener, OnInvitationReceivedListener{

    /*
       API Implementation only, please don't refer to API directly, use methods defined HERE
       Invoke with singleton


    */

    //singleton declaration
    private static PlayGames Instance = null;

    public static PlayGames fap(){
        if ( Instance != null ){
            return Instance;
        }else{
            Instance = new PlayGames();
            return Instance;
        }
    }

    //Variables
    ArrayList<Participant> participants = null;
    boolean isMultiplayer = false;
    Intent intent;
    boolean GameMode; //false for normal

    //constructor
    private void PlayGames(){

    }

    //The client used for contacting API
    private GoogleApiClient apiClient;

    //Building Client, call this method first before using anything else here
    public void initialize(){
         apiClient = new GoogleApiClient.Builder(this)
                 .addApi(Games.API)
                 .addScope(Games.SCOPE_GAMES)
                 .addOnConnectionFailedListener(this)
                 .addConnectionCallbacks(this)
                 .build();
    }

    public void SignIn(){
        apiClient.connect();
    }

    public void SignOut(){
        apiClient.disconnect();
    }

    public boolean isConnected(){
        return apiClient.isConnected();
    }

    @Override
    public void onConnected(Bundle bundle){
        //sign in sucessful
    }

    @Override
    public void onConnectionSuspended(int i) {
        //reconecting
        apiClient.connect();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //failed to sign in
    }

    void startQuickGame() {
        final int MIN_OPPONENTS = 1, MAX_OPPONENTS = 3;
        Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(MIN_OPPONENTS,
                MAX_OPPONENTS, 0);
        RoomConfig.Builder rtmConfigBuilder = RoomConfig.builder(this);
        rtmConfigBuilder.setMessageReceivedListener(this);
        rtmConfigBuilder.setRoomStatusUpdateListener(this);
        rtmConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);
        keepScreenOn();
        Games.RealTimeMultiplayer.create(apiClient, rtmConfigBuilder.build());
    }

    //Keeping screen on while matching to prevent errors
    private void keepScreenOn() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    // Clears the flag that keeps the screen on.
    private void stopKeepingScreenOn() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onInvitationReceived(Invitation invitation) {

    }

    @Override
    public void onInvitationRemoved(String s) {

    }

    @Override
    public void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) {

    }

    @Override
    public void onRoomConnecting(Room room) {

    }

    @Override
    public void onRoomAutoMatching(Room room) {

    }

    @Override
    public void onPeerInvitedToRoom(Room room, List<String> list) {

    }

    @Override
    public void onPeerDeclined(Room room, List<String> list) {

    }

    @Override
    public void onPeerJoined(Room room, List<String> list) {

    }

    @Override
    public void onPeerLeft(Room room, List<String> list) {

    }

    @Override
    public void onConnectedToRoom(Room room) {

    }

    @Override
    public void onDisconnectedFromRoom(Room room) {

    }

    @Override
    public void onPeersConnected(Room room, List<String> list) {

    }

    @Override
    public void onPeersDisconnected(Room room, List<String> list) {

    }

    @Override
    public void onP2PConnected(String s) {

    }

    @Override
    public void onP2PDisconnected(String s) {

    }

    @Override
    public void onRoomCreated(int i, Room room) {

    }

    @Override
    public void onJoinedRoom(int i, Room room) {

    }

    @Override
    public void onLeftRoom(int i, String s) {

    }

    @Override
    public void onRoomConnected(int i, Room room) {

    }

    public void invite(boolean gamemode){
        intent = Games.RealTimeMultiplayer.getSelectOpponentsIntent(apiClient, 1, 3);
        startActivity(intent);
    }


}
