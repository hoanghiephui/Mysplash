package com.wallpapers.unsplash.user.model.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.wallpapers.unsplash.common.data.service.UserService;
import com.wallpapers.unsplash.common.interfaces.model.BrowsableModel;
import com.wallpapers.unsplash.user.view.activity.UserActivity;

import java.util.List;

/**
 * Browsable object.
 */

public class BorwsableObject
        implements BrowsableModel {

    private Uri intentUri;
    private UserService service;

    public BorwsableObject(Intent intent) {
        if (intent.getDataString() == null) {
            String username = intent.getStringExtra(UserActivity.KEY_USER_ACTIVITY_USERNAME);
            if (TextUtils.isEmpty(username)) {
                intentUri = null;
            } else {
                intentUri = Uri.parse("https://unsplash.com/@" + username);
            }
        } else {
            intentUri = Uri.parse(intent.getDataString());
        }
        service = UserService.getService();
    }

    @Override
    public Uri getIntentUri() {
        return intentUri;
    }

    @Override
    public boolean isBrowsable() {
        return intentUri != null;
    }

    @Override
    public List<String> getBrowsableDataKey() {
        return intentUri.getPathSegments();
    }

    @Override
    public Object getService() {
        return service;
    }
}
