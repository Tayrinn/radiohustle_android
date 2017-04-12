/*
 * Track.java
 *
 * This file is a part of the Yandex Search for Android project.
 *
 * (C) Copyright 2017 Yandex, LLC. All rights reserved.
 *
 * Author: Olga Kim <tayrinn@yandex-team.ru>
 */

package ru.tayrinn.hustle.radiohustle.model;

import com.google.gson.annotations.SerializedName;

public class Track {

    @SerializedName("name")
    public String url;

    @SerializedName("bpm")
    public Integer bpm;
}
