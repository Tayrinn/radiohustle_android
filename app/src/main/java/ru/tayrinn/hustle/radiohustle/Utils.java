/*
 * Utils.java
 *
 * This file is a part of the Yandex Search for Android project.
 *
 * (C) Copyright 2017 Yandex, LLC. All rights reserved.
 *
 * Author: Olga Kim <tayrinn@yandex-team.ru>
 */

package ru.tayrinn.hustle.radiohustle;

import ru.tayrinn.hustle.radiohustle.model.Track;

public class Utils {

    public static String getUrl(Track track) {
        return Urls.TRACKS_BASE_URL + track.name;
    }
}
