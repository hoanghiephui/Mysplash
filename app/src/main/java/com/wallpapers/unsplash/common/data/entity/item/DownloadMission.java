package com.wallpapers.unsplash.common.data.entity.item;

import android.support.annotation.FloatRange;

import com.wallpapers.unsplash.common.data.entity.table.DownloadMissionEntity;

/**
 * Download mission.
 *
 * The item model for {@link com.wallpapers.unsplash.common.ui.adapter.DownloadAdapter}.
 *
 * */

public class DownloadMission {

    public DownloadMissionEntity entity;
    @ProcessRangeRule
    public float process;

    @FloatRange(from = 0.0, to = 100.0)
    public @interface ProcessRangeRule {}

    public DownloadMission(DownloadMissionEntity entity) {
        this.entity = entity;
        this.process = 0;
    }

    public DownloadMission(DownloadMissionEntity entity,
                           @ProcessRangeRule float process) {
        this.entity = entity;
        this.process = process;
    }
}
