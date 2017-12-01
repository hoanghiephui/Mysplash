package com.wallpapers.unsplash.common.ui.widget.preference;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.preference.ListPreference;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.common.utils.DisplayUtils;
import com.wallpapers.unsplash.common.utils.manager.ThemeManager;

/**
 * Unsplash list preference.
 *
 * A ListPreference that can set style of the texts.
 *
 * */

public class MysplashListPreference extends ListPreference {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MysplashListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MysplashListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MysplashListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MysplashListPreference(Context context) {
        super(context);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        TextView title = (TextView) view.findViewById(android.R.id.title);
        title.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                getContext().getResources().getDimension(R.dimen.title_text_size));
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextColor(ThemeManager.getTitleColor(getContext()));

        TextView summary = (TextView) view.findViewById(android.R.id.summary);
        summary.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                getContext().getResources().getDimension(R.dimen.subtitle_text_size));
        DisplayUtils.setTypeface(getContext(), summary);
        summary.setTextColor(ThemeManager.getSubtitleColor(getContext()));
    }
}