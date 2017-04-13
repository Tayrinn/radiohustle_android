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
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.tayrinn.hustle.radiohustle.model.Track;
import ru.tayrinn.hustle.radiohustle.player.PlayerService;

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
        mTrackAdapter = new TrackAdapter(new ArrayList<Track>());
        recyclerView.setAdapter(mTrackAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tracksApi.downloadTracks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(tracks -> {
                        mTrackAdapter.mTracks.clear();
                        mTrackAdapter.mTracks.addAll(tracks);
                        mTrackAdapter.notifyDataSetChanged();
                })
        .subscribe();
    }

    class TrackAdapter extends RecyclerView.Adapter<TrackHolder> {

        List<Track> mTracks;

        public TrackAdapter(@NonNull List<Track> mTracks) {
            this.mTracks = mTracks;
        }

        @Override
        public TrackHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final TrackHolder holder = new TrackHolder(inflater.inflate(R.layout.track_item, parent, false));
            holder.itemView.setOnClickListener(view -> {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(getActivity(), PlayerService.class);
                    intent.putExtra(PlayerService.TAG_URL, Urls.TRACKS_BASE_URL + mTracks.get(position).name);
                    getActivity().startService(intent);
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
            mName = (TextView) itemView.findViewById(R.id.track_name);
            mBpm = (TextView) itemView.findViewById(R.id.track_bpm);
        }

        void bind(@NonNull Track track) {
            mName.setText(track.name);
            mBpm.setText(track.bpm + " bmp");
        }
    }
}
