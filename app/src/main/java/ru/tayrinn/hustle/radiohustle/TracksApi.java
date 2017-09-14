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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.tayrinn.hustle.radiohustle.model.Track;

public class TracksApi {

    private RadioSourceService mRadioSourceService;

    public TracksApi() {
        this(Urls.BASE_URL);
    }

    private TracksApi(@NonNull String baseUrl) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mRadioSourceService = retrofit.create(RadioSourceService.class);
    }

    public Observable<List<Track>> downloadTracks() {
        return mRadioSourceService.getTracks();
    }
}
