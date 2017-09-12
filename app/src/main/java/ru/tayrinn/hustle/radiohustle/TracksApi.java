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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.tayrinn.hustle.radiohustle.model.Track;

public class TracksApi {

    private RadioSourceService mRadioSourceService;
    private Gson gson;

    private final static String TRACKS_JSON = "[\n" +
            "\t{\"name\":\"audio/Adele_-_108_bpm_Set_Fire_to_the_Rain.mp3\",\"bpm\":\"108\"},\n" +
            "\t{\"name\":\"audio/Akon_Feat_Kardinal_Offishall_-_Dangerous.mp3\",\"bpm\":\"117\"},\n" +
            "\t{\"name\":\"audio/Alaska_Lakin_-_Go_Solo__Radio_.mp3\",\"bpm\":\"088\"},\n" +
            "\t{\"name\":\"audio/Alex_G_-_Payphone_-_Maroon_5_Ft_Wiz_Khalifa__Acoustic_Cover_ft_Jameson_Bass_.mp3\",\"bpm\":\"099\"},\n" +
            "\t{\"name\":\"audio/Alex_G_ft_Shaun_Reynolds_Acoustic_Cover_-_Starships__Nicki_Minaj_.mp3\",\"bpm\":\"105\"},\n" +
            "\t{\"name\":\"audio/Alex_Hepburn_-_Under.mp3\",\"bpm\":\"084\"},\n" +
            "\t{\"name\":\"audio/Alex_Vargas_-_More___Usher_cover_.mp3\",\"bpm\":\"111\"},\n" +
            "\t{\"name\":\"audio/Alicia_Keys_-_Tears_Always_Win.mp3\",\"bpm\":\"075\"},\n" +
            "\t{\"name\":\"audio/Alicia_Keys_-_Un-Thinkable__Im_Ready_.mp3\",\"bpm\":\"086\"},\n" +
            "\t{\"name\":\"audio/All_Time_Low_-_I_Feel_Like_Dancing.mp3\",\"bpm\":\"092\"},\n" +
            "\t{\"name\":\"audio/Aloe_Blacc_-_The_Man.mp3\",\"bpm\":\"086\"},\n" +
            "\t{\"name\":\"audio/Anastacia-Absolutely-Positively.mp3\",\"bpm\":\"101\"},\n" +
            "\t{\"name\":\"audio/Anastacia_-_Defeated.mp3\",\"bpm\":\"080\"},\n" +
            "\t{\"name\":\"audio/Anastacia_-_Sick_And_Tired.mp3\",\"bpm\":\"102\"},\n" +
            "\t{\"name\":\"audio/Anthony_Hamilton_-_Mad.mp3\",\"bpm\":\"095\"},\n" +
            "\t{\"name\":\"audio/Anuhea_-_Looking_For_Love.mp3\",\"bpm\":\"091\"},\n" +
            "\t{\"name\":\"audio/Arctic_Monkeys_-_Arabella.mp3\",\"bpm\":\"091\"},\n" +
            "\t{\"name\":\"audio/Arctic_Monkeys_-_Whyd_You_Only_Call_Me_When_Youre_High_.mp3\",\"bpm\":\"092\"},\n" +
            "\t{\"name\":\"audio/Ariana_Grande_-_Be_My_Baby__feat._Cashmere_Cat_.mp3\",\"bpm\":\"073\"},\n" +
            "\t{\"name\":\"audio/Ariana_Grande_-_Just_a_Little_Bit_of_Your_Heart.mp3\",\"bpm\":\"078\"},\n" +
            "\t{\"name\":\"audio/Ariana_Grande_-_My_Everything.mp3\",\"bpm\":\"062\"},\n" +
            "\t{\"name\":\"audio/Ariana_Grande_-_Why_Try.mp3\",\"bpm\":\"081\"},\n" +
            "\t{\"name\":\"audio/Ashley_Tisdale_-_Youre_Always_Here.mp3\",\"bpm\":\"085\"},\n" +
            "\t{\"name\":\"audio/Aura_Dione_-_112_bpm_Master_Piece.mp3\",\"bpm\":\"112\"},\n" +
            "\t{\"name\":\"audio/Aura_Dione_-_Reconnect.mp3\",\"bpm\":\"061\"},\n" +
            "\t{\"name\":\"audio/Avicii_feat._Salem_Al_Fakir_-_You_Make_Me__Radio_Edit_.mp3\",\"bpm\":\"125\"},\n" +
            "\t{\"name\":\"audio/Avril_Lavigne_-_Bitchin_Summer.mp3\",\"bpm\":\"088\"},\n" +
            "\t{\"name\":\"audio/Avril_Lavigne_-_Fallin_Fast.mp3\",\"bpm\":\"089\"},\n" +
            "\t{\"name\":\"audio/Avril_Lavigne_-_Give_You_What_You_Like.mp3\",\"bpm\":\"079\"},\n" +
            "\t{\"name\":\"audio/Avril_Lavigne_-_I_Love_You.mp3\",\"bpm\":\"107\"},\n" +
            "\t{\"name\":\"audio/Avril_Lavigne_-_I_Wish_you_were_here.mp3\",\"bpm\":\"083\"},\n" +
            "\t{\"name\":\"audio/Avril_Lavigne_-_Wish_You_Were_Here__Acoustic_.mp3\",\"bpm\":\"083\"},\n" +
            "\t{\"name\":\"audio/Baby_and_me_-_Here_comes_the_Hotstepper.mp3\",\"bpm\":\"108\"},\n" +
            "\t{\"name\":\"audio/Babyface_-_When_Can_I_See_You.mp3\",\"bpm\":\"085\"},\n" +
            "\t{\"name\":\"audio/BackStreet_Boys_-_All_I_Have_To_Give.mp3\",\"bpm\":\"097\"},\n" +
            "\t{\"name\":\"audio/BackStreet_Boys_-_Larger_Than_Life.mp3\",\"bpm\":\"108\"},\n" +
            "\t{\"name\":\"audio/Backstreet_Boys_-_I_Want_It_That_Way.mp3\",\"bpm\":\"099\"},\n" +
            "\t{\"name\":\"audio/Backstreet_Boys_-_Masquerade.mp3\",\"bpm\":\"124\"},\n" +
            "\t{\"name\":\"audio/Ben_Cocks_-_So_Cold.mp3\",\"bpm\":\"075\"},\n" +
            "\t{\"name\":\"audio/Bernhoft_C2C_Remix_-_Shout.mp3\",\"bpm\":\"110\"},\n" +
            "\t{\"name\":\"audio/Beyonce_-_076_bpm_Dreaming.mp3\",\"bpm\":\"076\"},\n" +
            "\t{\"name\":\"audio/Beyonce_-_Halo.mp3\",\"bpm\":\"080\"}]";

    public TracksApi() {
        this(Urls.BASE_URL);
    }

    private TracksApi(@NonNull String baseUrl) {
        gson = new GsonBuilder()
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
//        Type listType = new TypeToken<List<Track>>() {}.getType();
//        return Observable.just(gson.fromJson(TRACKS_JSON, listType));
    }
}
