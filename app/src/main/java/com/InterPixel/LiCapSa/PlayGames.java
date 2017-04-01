package com.InterPixel.LiCapSa;


import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.Multiplayer;
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


public class PlayGames extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, RealTimeMessageReceivedListener,
        RoomStatusUpdateListener, RoomUpdateListener, OnInvitationReceivedListener{

    /*
       API Implementation only, please don't refer to API directly, use methods defined HERE
       Invoke with singleton
       Breaching rules will result in system instability

       In short, there will be these multiplayer setups
       1. Automatching which match a player with random people
            -Always 4 people plays
       2. Invite which player can choose who he plays with
            -Incase player < 4, AI will fill in the gaps
            -All AIs are from whoever assigned player 1
       3. Accepting from inbox where invited player joins the match


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
    static boolean isMultiplayer = false;
    Intent intent;
    final static int VARIANT_NORMAL = 888;
    final static int VARIANT_GOSOK = 666;
    static int gamemodes = VARIANT_NORMAL;
    final static int MAX_OPPONENTS = 3, MIN_OPPONENTS = 1;
    final static int RC_SELECT_PLAYERS = 10000;
    final static int RC_INVITATION_INBOX = 10001;
    final static int RC_WAITING_ROOM = 10002;
    final static int RC_SIGN_IN = 10003;
    Activity activity;

    //constructor
    private void PlayGames(){

    }

    //The client used for contacting API
    private GoogleApiClient apiClient;

    //Building Client, call this method first before using anything else here
    public void initialize(Activity activity){
        debug("Attempt to build client API");
        this.activity = activity;
         apiClient = new GoogleApiClient.Builder(activity)
                 .addOnConnectionFailedListener(this)
                 .addConnectionCallbacks(this)
                 .addApi(Games.API)
                 .addScope(Games.SCOPE_GAMES)
                 .build();
        debug("Client API built");
    }

    public void SignIn(){
        debug("Attempt to sign in");
        if(!isConnected()){
            apiClient.connect();
        }else{
            debug("Already signed in");
        }
    }

    public void SignOut(){
        debug("Signing out");
        if(isConnected()){
            Games.signOut(apiClient);
            apiClient.disconnect();
            debug("Signed out");
        }else{
            debug("Not signed in");
        }

    }

    public boolean isConnected(){
        return apiClient.isConnected();
    }

    @Override
    public void onConnected(Bundle bundle){
        //sign in sucessful
        debug("Sucessfully logged in");
    }

    @Override
    public void onConnectionSuspended(int i) {
        //reconecting
        debug("Connection problem");
        apiClient.connect();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //failed to sign in
        debug("Failed to sign in : " + connectionResult);

        if ( connectionResult.hasResolution() ){
            try {
                connectionResult.startResolutionForResult(activity, RC_SIGN_IN);
                debug("Resolution ran");
            } catch (IntentSender.SendIntentException e) {
                debug("Exception when resolving signing in " + e);
                //just give up on signing in
            }
        }

    }

    void automatch() {
        Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(MAX_OPPONENTS,
                MAX_OPPONENTS, gamemodes);
        RoomConfig.Builder roomConfigBuilder = RoomConfig.builder(this);
        roomConfigBuilder.setMessageReceivedListener(this);
        roomConfigBuilder.setRoomStatusUpdateListener(this);
        roomConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);
        keepScreenOn();
        Games.RealTimeMultiplayer.create(apiClient, roomConfigBuilder.build());
    }

    void showInvitation(){
        intent = Games.Invitations.getInvitationInboxIntent(apiClient);
        activity.startActivityForResult(intent,RC_INVITATION_INBOX);
    }

    void invitePlayers(){
        intent = Games.RealTimeMultiplayer.getSelectOpponentsIntent(apiClient, MIN_OPPONENTS, MAX_OPPONENTS);
        activity.startActivityForResult(intent, RC_SELECT_PLAYERS);
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent resultIntent){

        RoomConfig.Builder roomConfigBuilder;

        switch (requestCode){
            case RC_SELECT_PLAYERS:

                if ( responseCode != Activity.RESULT_OK ){
                    debug("Select player UI cancelled");
                    return;
                }

                debug("Select player UI suceeded");

                //get the invitee list
                final ArrayList<String> invitedPeople = resultIntent.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);
                debug("Invited people = " + invitedPeople.size());

                // get auto-match criteria
                Bundle autoMatchCriteria = null;
                int minAutoMatchPlayers =
                        resultIntent.getIntExtra(Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
                int maxAutoMatchPlayers =
                        resultIntent.getIntExtra(Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);

                if (minAutoMatchPlayers > 0) {
                    autoMatchCriteria = RoomConfig.createAutoMatchCriteria(minAutoMatchPlayers, maxAutoMatchPlayers, gamemodes);
                } else {
                    autoMatchCriteria = null;
                }

                //Create the room
                roomConfigBuilder = RoomConfig.builder(this)
                        .setRoomStatusUpdateListener(this)
                        .setMessageReceivedListener(this)
                        .addPlayersToInvite(invitedPeople)
                        .setVariant(gamemodes);
                if ( autoMatchCriteria != null ){
                    roomConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);
                }
                RoomConfig roomConfig = roomConfigBuilder.build();
                Games.RealTimeMultiplayer.create(apiClient, roomConfig);

                keepScreenOn();

                break;

            case RC_INVITATION_INBOX:
                if ( responseCode != Activity.RESULT_OK ){
                    debug("Invitation inbox UI cancelled " + responseCode);
                    return;
                }
                debug("Invitation inbox UI suceeded");

                Invitation inv = resultIntent.getExtras().getParcelable(Multiplayer.EXTRA_INVITATION);

                roomConfigBuilder = RoomConfig.builder(this)
                        .setInvitationIdToAccept(inv.getInvitationId())
                        .setRoomStatusUpdateListener(this)
                        .setMessageReceivedListener(this)
                        .setVariant(inv.getVariant());
                gamemodes = inv.getVariant();
                keepScreenOn();
                Games.RealTimeMultiplayer.create(apiClient, roomConfigBuilder.build());

                break;
            case RC_WAITING_ROOM:
                break;

            case RC_SIGN_IN:
                debug("Resolution ends");
                if(responseCode == Activity.RESULT_OK){
                    debug("Attempt to reconnect again");
                    apiClient.connect();
                }
                break;
        }
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
    public void onRoomCreated(int statusCode, Room room) {
        if(statusCode != GamesStatusCodes.STATUS_OK){
            stopKeepingScreenOn();
            debug("Error creating room");
        }else{
            debug("Room created");
        }
    }

    @Override
    public void onJoinedRoom(int statusCode, Room room) {
        if(statusCode != GamesStatusCodes.STATUS_OK){
            stopKeepingScreenOn();
            debug("Error joining room");
        }else{
            debug("Room joined");
        }

    }

    @Override
    public void onLeftRoom(int i, String s) {

    }

    @Override
    public void onRoomConnected(int statusCode, Room room) {
        if(statusCode != GamesStatusCodes.STATUS_OK){
            stopKeepingScreenOn();
            debug("Error connecting to room");
        }else{
            debug("Connected to room");
        }
    }

    public void invite(boolean gamemode){
        intent = Games.RealTimeMultiplayer.getSelectOpponentsIntent(apiClient, 1, 3);
        startActivity(intent);
    }

    public void debug(String string){
        Log.d("LiCapSa", "debug: " + string);
    }


}
