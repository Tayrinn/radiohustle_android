/*
 * TrackSourceService.java
 *
 * This file is a part of the Yandex Search for Android project.
 *
 * (C) Copyright 2017 Yandex, LLC. All rights reserved.
 *
 * Author: Olga Kim <tayrinn@yandex-team.ru>
 */

package ru.tayrinn.hustle.radiohustle;

import java.util.List;

import retrofit2.http.GET;
import ru.tayrinn.hustle.radiohustle.model.Track;

public interface RadioSourceService {

    @GET(Urls.RADIO_HUSTLE)
    List<Track> getTracks();
}
