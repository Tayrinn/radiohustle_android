/*
 * StartPlayingEvent.java
 *
 * This file is a part of the Yandex Search for Android project.
 *
 * (C) Copyright 2017 Yandex, LLC. All rights reserved.
 *
 * Author: Olga Kim <tayrinn@yandex-team.ru>
 */

package ru.tayrinn.hustle.radiohustle.eventbus;

import ru.tayrinn.hustle.radiohustle.model.PlayerState;
import ru.tayrinn.hustle.radiohustle.model.Track;

public class PlayerEvent {
    public PlayerState player;

    public PlayerEvent(Track track, PlayerState.State state) {
        player = new PlayerState(track, state);
    }
}
