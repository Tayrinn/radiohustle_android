/*
 * PlayerService.java
 *
 * This file is a part of the Yandex Search for Android project.
 *
 * (C) Copyright 2017 Yandex, LLC. All rights reserved.
 *
 * Author: Olga Kim <tayrinn@yandex-team.ru>
 */

package ru.tayrinn.hustle.radiohustle.player;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ru.tayrinn.hustle.radiohustle.Utils;
import ru.tayrinn.hustle.radiohustle.eventbus.PlayerEvent;
import ru.tayrinn.hustle.radiohustle.model.PlayerState;

public class PlayerService extends Service implements Player.Callback {

    public static final String TAG_URL = "track_url";
    private PlayerProvider mPlayerProvider;
    private final IBinder audioPlayerBinder = new AudioPlayerBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayerProvider = new PlayerProvider(this);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return audioPlayerBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        String url = intent.getStringExtra(TAG_URL);
//        if (!TextUtils.isEmpty(url)) {
//            mPlayerProvider.stop(false);
//            mPlayerProvider.play(url);
//            Toast.makeText(this, "playing", Toast.LENGTH_SHORT).show();
//        }
        return START_STICKY;
    }

    @Override
    public void onCompletion() {
        mPlayerProvider.stop(true);
        Toast.makeText(this, "stop playing", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPlaybackStatusChanged(int state) {
    }

    @Override
    public void onError(String error) {
        mPlayerProvider.stop(true);
        Toast.makeText(this, "error, stop playing", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartPlaying() {

    }

    @Override
    public void onPrepare() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlayerEvent event) {
        if (event.player.state == PlayerState.State.PLAY) {
            mPlayerProvider.stop(false);
            mPlayerProvider.play(event.player.track.getUrl());
            Toast.makeText(this, "playing", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public class AudioPlayerBinder extends Binder {
        public PlayerService getService() {
            return PlayerService.this;
        }
    }
}
