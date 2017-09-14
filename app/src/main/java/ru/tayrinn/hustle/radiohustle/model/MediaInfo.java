/*
 * MediaInfo.java
 *
 * This file is a part of the Yandex Search for Android project.
 *
 * (C) Copyright 2017 Yandex, LLC. All rights reserved.
 *
 * Author: Olga Kim <tayrinn@yandex-team.ru>
 */

package ru.tayrinn.hustle.radiohustle.model;

public interface MediaInfo {

    String getArtistName();
    String getTrackName();
    int getBpm();
    String getUrl();

}
