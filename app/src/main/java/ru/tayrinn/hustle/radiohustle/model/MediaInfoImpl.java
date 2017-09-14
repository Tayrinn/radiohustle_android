/*
 * MediaInfoImpl.java
 *
 * This file is a part of the Yandex Search for Android project.
 *
 * (C) Copyright 2017 Yandex, LLC. All rights reserved.
 *
 * Author: Olga Kim <tayrinn@yandex-team.ru>
 */

package ru.tayrinn.hustle.radiohustle.model;

import ru.tayrinn.hustle.radiohustle.Utils;

public class MediaInfoImpl implements MediaInfo {

    private final Track track;

    public MediaInfoImpl(Track track) {
        this.track = track;
    }

    @Override
    public String getArtistName() {
        int start = track.name.indexOf("3d/") + 3;
        int end = track.name.indexOf("_-_");
        if (end == -1) {
            end = track.name.indexOf("_–_");
        }
        if (end == -1) {
            return "";
        }
        String name = track.name.substring(
                start > 0 ? start : track.name.indexOf("/"),
                end > 0 ? end : track.name.length() - 1);
        return name.replace("_", " ");
    }

    @Override
    public String getTrackName() {
        int start = track.name.indexOf("_-_");
        start = (start > 0 ? start : track.name.indexOf("_–_")) + 3;
        if (start == 2) {
            start = track.name.indexOf("/", 11) + 1;
        }

        String name = track.name.substring(start);
        name = name.substring(0, name.indexOf(".mp3"));
        return name.replace("_", " ");
    }

    @Override
    public int getBpm() {
        try {
            return Integer.valueOf(track.bpm);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public String getUrl() {
        return Utils.getUrl(track);
    }
}
