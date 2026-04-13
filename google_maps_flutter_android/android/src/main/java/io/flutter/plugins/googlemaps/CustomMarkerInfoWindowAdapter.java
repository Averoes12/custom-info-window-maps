package io.flutter.plugins.googlemaps;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
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
import org.json.JSONException;
import org.json.JSONObject;

class CustomMarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
  private final Context context;

  CustomMarkerInfoWindowAdapter(@NonNull Context context) {
    this.context = context;
  }

  @Override
  @Nullable
  public android.view.View getInfoWindow(@NonNull Marker marker) {
    return buildInfoView(marker);
  }

  @Override
  @Nullable
  public android.view.View getInfoContents(@NonNull Marker marker) {
    return buildInfoView(marker);
  }

  @Nullable
  private android.view.View buildInfoView(@NonNull Marker marker) {
    final String title = marker.getTitle();
    final String snippet = marker.getSnippet();
    if (TextUtils.isEmpty(title) && TextUtils.isEmpty(snippet)) {
      return null;
    }

    final InfoWindowNativeStyle style = InfoWindowNativeStyle.fromJson(marker.getTag());
    final int horizontalPadding = dp(style.horizontalPadding);
    final int verticalPadding = dp(style.verticalPadding);
    final int titleTextSizeSp = (int) Math.round(style.titleFontSize);
    final int snippetTextSizeSp = (int) Math.round(style.snippetFontSize);
    final int dynamicWidth = Math.max(
        dp(style.minWidth),
        Math.min(
            dp(style.maxWidth),
            Math.round(
                Math.max(
                        measureTextWidth(title, titleTextSizeSp, style.titleBold),
                        measureTextWidth(snippet, snippetTextSizeSp, style.snippetBold))
                    + (horizontalPadding * 2))));

    final LinearLayout container = new LinearLayout(context);
    container.setOrientation(LinearLayout.VERTICAL);
    container.setGravity(Gravity.CENTER);
    container.setLayoutParams(
        new ViewGroup.LayoutParams(dynamicWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
    container.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);

    final GradientDrawable background = new GradientDrawable();
    background.setColor(style.backgroundColor);
    background.setCornerRadius(dp(style.cornerRadius));
    background.setStroke(dp(style.borderWidth), style.borderColor);
    container.setBackground(background);

    final TextView titleView = new TextView(context);
    titleView.setGravity(Gravity.CENTER);
    titleView.setTextColor(style.titleTextColor);
    titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSizeSp);
    titleView.setTypeface(
        style.titleBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT,
        style.titleBold ? Typeface.BOLD : Typeface.NORMAL);
    titleView.setSingleLine(true);
    titleView.setEllipsize(TextUtils.TruncateAt.END);
    titleView.setText(title);

    final TextView snippetView = new TextView(context);
    snippetView.setGravity(Gravity.CENTER);
    snippetView.setTextColor(style.snippetTextColor);
    snippetView.setTextSize(TypedValue.COMPLEX_UNIT_SP, snippetTextSizeSp);
    snippetView.setTypeface(
        style.snippetBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT,
        style.snippetBold ? Typeface.BOLD : Typeface.NORMAL);
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

  private int dp(double value) {
    return Math.round((float) value * context.getResources().getDisplayMetrics().density);
  }

  private float measureTextWidth(@Nullable String text, int textSizeSp, boolean bold) {
    if (TextUtils.isEmpty(text)) {
      return 0;
    }

    final Paint paint = new Paint();
    paint.setTextSize(
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            textSizeSp,
            context.getResources().getDisplayMetrics()));
    paint.setTypeface(bold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
    return paint.measureText(text);
  }

  private static final class InfoWindowNativeStyle {
    final int backgroundColor;
    final int borderColor;
    final int titleTextColor;
    final int snippetTextColor;
    final double cornerRadius;
    final double borderWidth;
    final double horizontalPadding;
    final double verticalPadding;
    final double titleFontSize;
    final double snippetFontSize;
    final double minWidth;
    final double maxWidth;
    final boolean titleBold;
    final boolean snippetBold;

    private InfoWindowNativeStyle(
        int backgroundColor,
        int borderColor,
        int titleTextColor,
        int snippetTextColor,
        double cornerRadius,
        double borderWidth,
        double horizontalPadding,
        double verticalPadding,
        double titleFontSize,
        double snippetFontSize,
        double minWidth,
        double maxWidth,
        boolean titleBold,
        boolean snippetBold) {
      this.backgroundColor = backgroundColor;
      this.borderColor = borderColor;
      this.titleTextColor = titleTextColor;
      this.snippetTextColor = snippetTextColor;
      this.cornerRadius = cornerRadius;
      this.borderWidth = borderWidth;
      this.horizontalPadding = horizontalPadding;
      this.verticalPadding = verticalPadding;
      this.titleFontSize = titleFontSize;
      this.snippetFontSize = snippetFontSize;
      this.minWidth = minWidth;
      this.maxWidth = maxWidth;
      this.titleBold = titleBold;
      this.snippetBold = snippetBold;
    }

    static InfoWindowNativeStyle fromJson(@Nullable Object tag) {
      int backgroundColor = Color.parseColor("#14A3B8");
      int borderColor = Color.WHITE;
      int titleTextColor = Color.WHITE;
      int snippetTextColor = Color.WHITE;
      double cornerRadius = 18.0;
      double borderWidth = 2.0;
      double horizontalPadding = 18.0;
      double verticalPadding = 12.0;
      double titleFontSize = 12.0;
      double snippetFontSize = 18.0;
      double minWidth = 144.0;
      double maxWidth = 220.0;
      boolean titleBold = false;
      boolean snippetBold = true;

      if (tag instanceof String) {
        try {
          JSONObject json = new JSONObject((String) tag);
          backgroundColor = json.optInt("backgroundColor", backgroundColor);
          borderColor = json.optInt("borderColor", borderColor);
          titleTextColor = json.optInt("titleTextColor", titleTextColor);
          snippetTextColor = json.optInt("snippetTextColor", snippetTextColor);
          cornerRadius = json.optDouble("cornerRadius", cornerRadius);
          borderWidth = json.optDouble("borderWidth", borderWidth);
          horizontalPadding = json.optDouble("horizontalPadding", horizontalPadding);
          verticalPadding = json.optDouble("verticalPadding", verticalPadding);
          titleFontSize = json.optDouble("titleFontSize", titleFontSize);
          snippetFontSize = json.optDouble("snippetFontSize", snippetFontSize);
          minWidth = json.optDouble("minWidth", minWidth);
          maxWidth = json.optDouble("maxWidth", maxWidth);
          titleBold = json.optBoolean("titleBold", titleBold);
          snippetBold = json.optBoolean("snippetBold", snippetBold);
        } catch (JSONException ignored) {
          // Keep defaults if style payload is invalid.
        }
      }

      return new InfoWindowNativeStyle(
          backgroundColor,
          borderColor,
          titleTextColor,
          snippetTextColor,
          cornerRadius,
          borderWidth,
          horizontalPadding,
          verticalPadding,
          titleFontSize,
          snippetFontSize,
          minWidth,
          maxWidth,
          titleBold,
          snippetBold);
    }
  }
}
