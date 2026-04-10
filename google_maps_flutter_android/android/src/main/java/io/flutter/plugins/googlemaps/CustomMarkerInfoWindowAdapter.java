package io.flutter.plugins.googlemaps;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

class CustomMarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
  private static final int BACKGROUND_COLOR = Color.parseColor("#14A3B8");
  private static final int BORDER_COLOR = Color.WHITE;
  private final Context context;

  CustomMarkerInfoWindowAdapter(@NonNull Context context) {
    this.context = context;
  }

  @Override
  @Nullable
  public android.view.View getInfoWindow(@NonNull Marker marker) {
    final String title = marker.getTitle();
    final String snippet = marker.getSnippet();
    if (TextUtils.isEmpty(title) && TextUtils.isEmpty(snippet)) {
      return null;
    }

    final LinearLayout container = new LinearLayout(context);
    container.setOrientation(LinearLayout.VERTICAL);
    container.setGravity(Gravity.CENTER);
    container.setLayoutParams(
        new ViewGroup.LayoutParams(dp(144), ViewGroup.LayoutParams.WRAP_CONTENT));
    container.setPadding(dp(12), dp(10), dp(12), dp(10));

    final GradientDrawable background = new GradientDrawable();
    background.setColor(BACKGROUND_COLOR);
    background.setCornerRadius(dp(16));
    background.setStroke(dp(2), BORDER_COLOR);
    container.setBackground(background);

    final TextView titleView = new TextView(context);
    titleView.setGravity(Gravity.CENTER);
    titleView.setTextColor(BORDER_COLOR);
    titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    titleView.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
    titleView.setSingleLine(true);
    titleView.setEllipsize(TextUtils.TruncateAt.END);
    titleView.setText(title);

    final TextView snippetView = new TextView(context);
    snippetView.setGravity(Gravity.CENTER);
    snippetView.setTextColor(BORDER_COLOR);
    snippetView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
    snippetView.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
    snippetView.setSingleLine(true);
    snippetView.setEllipsize(TextUtils.TruncateAt.END);
    snippetView.setText(snippet);

    container.addView(titleView);
    if (!TextUtils.isEmpty(snippet)) {
      final LinearLayout.LayoutParams snippetLayoutParams =
          new LinearLayout.LayoutParams(
              ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      snippetLayoutParams.topMargin = dp(2);
      snippetView.setLayoutParams(snippetLayoutParams);
      container.addView(snippetView);
    }

    return container;
  }

  @Override
  @Nullable
  public android.view.View getInfoContents(@NonNull Marker marker) {
    return null;
  }

  private int dp(int value) {
    return Math.round(value * context.getResources().getDisplayMetrics().density);
  }
}
