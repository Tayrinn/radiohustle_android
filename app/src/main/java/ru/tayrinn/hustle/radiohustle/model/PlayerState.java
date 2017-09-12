/*
 * PlayerState.java
 *
 * This file is a part of the Yandex Search for Android project.
 *
 * (C) Copyright 2017 Yandex, LLC. All rights reserved.
 *
 * Author: Olga Kim <tayrinn@yandex-team.ru>
 */

package ru.tayrinn.hustle.radiohustle.model;

public class PlayerState {

    public State state;
    public Track track;

    public PlayerState(Track track, State state) {
        this.track = track;
        this.state = state;
    }

    public static enum State {
        PLAY, PAUSE, STOP
    }
}
