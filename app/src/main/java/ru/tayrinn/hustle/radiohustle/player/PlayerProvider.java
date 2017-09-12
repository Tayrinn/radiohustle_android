/*
 * PlayerProvider.java
 *
 * This file is a part of the Yandex Search for Android project.
 *
 * (C) Copyright 2017 Yandex, LLC. All rights reserved.
 *
 * Author: Olga Kim <tayrinn@yandex-team.ru>
 */

package ru.tayrinn.hustle.radiohustle.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v4.media.session.PlaybackStateCompat;

import java.io.IOException;

public class PlayerProvider implements Player, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener {

    private MediaPlayer mMediaPlayer;
    private Player.Callback mCallback;
    private int mState;
    private final Context mContext;

    public PlayerProvider(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public void start() {
        mCallback.onPlaybackStatusChanged(0);
    }

    @Override
    public void stop(boolean notifyListeners) {
        mState = PlaybackStateCompat.STATE_STOPPED;
        relaxResources();
    }

    @Override
    public int getState() {
        return mState;
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    @Override
    public int getCurrentStreamPosition() {
        return mMediaPlayer != null ? mMediaPlayer.getCurrentPosition() : 0;
    }

    @Override
    public void setCurrentStreamPosition(int pos) {

    }

    @Override
    public void play(String url) {
        createMediaPlayerIfNeeded();
        try {
            mCallback.onPrepare();
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {
        mMediaPlayer.pause();
    }

    private void createMediaPlayerIfNeeded() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();

            // Make sure the media player will acquire a wake-lock while
            // playing. If we don't do that, the CPU might go to sleep while the
            // song is playing, causing playback to stop.
            mMediaPlayer.setWakeMode(mContext.getApplicationContext(),
                    PowerManager.PARTIAL_WAKE_LOCK);

            // we want the media player to notify us when it's ready preparing,
            // and when it's done playing:
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnErrorListener(this);
            mMediaPlayer.setOnSeekCompleteListener(this);
        } else {
            mMediaPlayer.reset();
        }
    }

    private void relaxResources() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void seekTo(int position) {

    }

    @Override
    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaPlayer.start();
        mCallback.onStartPlaying();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {

    }
}
