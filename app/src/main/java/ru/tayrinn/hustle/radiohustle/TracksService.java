/*
 * DatabaseService.java
 *
 * This file is a part of the Yandex Search for Android project.
 *
 * (C) Copyright 2017 Yandex, LLC. All rights reserved.
 *
 * Author: Olga Kim <tayrinn@yandex-team.ru>
 */

package ru.tayrinn.hustle.radiohustle;

import android.support.annotation.NonNull;

import java.util.List;

import retrofit2.Retrofit;
import ru.tayrinn.hustle.radiohustle.model.Track;

public class TracksService {

    private List<Track> mTracks;

    public void downloadTracks(@NonNull String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .build();
        RadioSourceService radioSourceService = retrofit.create(RadioSourceService.class);
        mTracks = radioSourceService.getTracks();
    }
}
