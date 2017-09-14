/*
 * PlaylistFragment.java
 *
 * This file is a part of the Yandex Search for Android project.
 *
 * (C) Copyright 2017 Yandex, LLC. All rights reserved.
 *
 * Author: Olga Kim <tayrinn@yandex-team.ru>
 */

package ru.tayrinn.hustle.radiohustle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.tayrinn.hustle.radiohustle.eventbus.PlayerEvent;
import ru.tayrinn.hustle.radiohustle.model.MediaInfo;
import ru.tayrinn.hustle.radiohustle.model.MediaInfoImpl;
import ru.tayrinn.hustle.radiohustle.model.PlayerState;
import ru.tayrinn.hustle.radiohustle.model.Track;

public class PlaylistFragment extends Fragment {

    private TrackAdapter mTrackAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.playlist_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TracksApi tracksApi = new TracksApi();
        final RecyclerView recyclerView = (RecyclerView) view;
        mTrackAdapter = new TrackAdapter(new ArrayList<>());
        recyclerView.setAdapter(mTrackAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tracksApi.downloadTracks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(tracks -> {
                    List<MediaInfo> mediaInfos = new ArrayList<>();
                    for (Track track : tracks) {
                        MediaInfo info = new MediaInfoImpl(track);
                        if (info.getBpm() > 0) {
                            mediaInfos.add(info);
                        }
                    }
                    return mediaInfos;
                })
                .doOnNext(tracks -> {
                        mTrackAdapter.mTracks.clear();
                        mTrackAdapter.mTracks.addAll(tracks);
                        mTrackAdapter.notifyDataSetChanged();
                })
        .subscribe();
    }

    private class TrackAdapter extends RecyclerView.Adapter<TrackHolder> {

        List<MediaInfo> mTracks;

        TrackAdapter(@NonNull List<MediaInfo> mTracks) {
            this.mTracks = mTracks;
        }

        @Override
        public TrackHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final TrackHolder holder = new TrackHolder(inflater.inflate(R.layout.track_item, parent, false));
            holder.itemView.setOnClickListener(view -> {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    EventBus.getDefault().post(new PlayerEvent(mTracks.get(position), PlayerState.State.PLAY));
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(TrackHolder holder, int position) {
            holder.bind(mTracks.get(position));
        }

        @Override
        public int getItemCount() {
            return mTracks.size();
        }
    }

    class TrackHolder extends RecyclerView.ViewHolder{

        TextView mName;
        TextView mBpm;

        TrackHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.track_name);
            mBpm = itemView.findViewById(R.id.track_bpm);
        }

        void bind(@NonNull MediaInfo track) {
            mName.setText(track.getArtistName() + " - " + track.getTrackName());
            mBpm.setText(track.getBpm() + " bmp");
        }
    }
}
