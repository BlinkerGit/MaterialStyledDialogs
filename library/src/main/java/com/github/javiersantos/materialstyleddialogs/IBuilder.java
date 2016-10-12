package com.github.javiersantos.materialstyleddialogs;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Duration;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

public interface IBuilder {

  /**
   * Set custom view for the dialog.
   *
   * @param customView to apply
   * @return this
   */
  MaterialStyledDialog.Builder setCustomView(View customView);

  /**
   * Set custom view for the dialog with optional padding in DP.
   *
   * @param customView to apply
   * @param left       padding left in DP
   * @param top        padding top in DP
   * @param right      padding right in DP
   * @param bottom     padding bottom in DP
   * @return this
   */
  MaterialStyledDialog.Builder setCustomView(View customView, int left, int top, int right, int bottom);

  /**
   * Set an style for the dialog. Default: Style.STYLE_HEADER.
   *
   * @param style to apply
   * @return this
   * @see com.github.javiersantos.materialstyleddialogs.enums.Style
   */
  MaterialStyledDialog.Builder style(Style style);

  /**
   * Set if the header icon will be displayed with an initial animation. Default: true.
   *
   * @param withAnimation true to enable animation, false otherwise
   * @return this
   */
  MaterialStyledDialog.Builder withIconAnimation(Boolean withAnimation);

  /**
   * Set if the dialog will be displayed with an open and close animation. Default: false.
   *
   * @param withAnimation true to enable animation, false otherwise
   * @return this
   */
  MaterialStyledDialog.Builder withDialogAnimation(Boolean withAnimation);

  /**
   * Set if the dialog will be displayed with an open and close animation, with custom duration. Default: false, Duration.NORMAL.
   *
   * @param withAnimation true to enable animation, false otherwise
   * @return this
   * @see com.github.javiersantos.materialstyleddialogs.enums.Duration
   */
  MaterialStyledDialog.Builder withDialogAnimation(Boolean withAnimation, Duration duration);

  /**
   * Set if the divider will be displayed before the buttons and after the dialog content. Default: false.
   *
   * @param withDivider true to enable animation, false otherwise
   * @return this
   */
  MaterialStyledDialog.Builder withDivider(Boolean withDivider);

  /**
   * Set if the header will display a gray/darker overlay. Default: false.
   *
   * @param withDarkerOverlay true to apply a darker overlay, false otherwise
   * @return this
   */
  MaterialStyledDialog.Builder withDarkerOverlay(Boolean withDarkerOverlay);

  /**
   * Set an icon for the dialog header
   *
   * @param icon to show
   * @return this
   */
  MaterialStyledDialog.Builder icon(@NonNull Drawable icon);

  /**
   * Set an icon for the dialog header
   *
   * @param iconRes to show
   * @return this
   */
  MaterialStyledDialog.Builder icon(@DrawableRes Integer iconRes);

  /**
   * Set custom icon size to override default
   *
   * @param pixWidth the width of the icon in pixels
   * @param pixHeight the height of the icon in pixels
   * @return this
   */
  MaterialStyledDialog.Builder iconSize(int pixWidth, int pixHeight);

  /**
   * Set a title for the dialog
   *
   * @param titleRes to show
   * @return this
   */
  MaterialStyledDialog.Builder title(@StringRes int titleRes);

  /**
   * Set a title for the dialog
   *
   * @param title to show
   * @return this
   */
  MaterialStyledDialog.Builder title(@NonNull CharSequence title);

  /**
   * Set a content for the dialog
   *
   * @param descriptionRes to show
   * @return this
   */
  MaterialStyledDialog.Builder content(@StringRes int descriptionRes);

  /**
   * Set a content for the dialog
   *
   * @param description to show
   * @return this
   */
  MaterialStyledDialog.Builder content(@NonNull CharSequence description);

  /**
   * Set a color for the dialog header. Default: Theme primary color.
   *
   * @param color for the header
   * @return this
   */
  MaterialStyledDialog.Builder headerColor(@ColorRes int color);

  /**
   * Set a color for the dialog header. Default: Theme primary color.
   *
   * @param color for the header
   * @return this
   */
  MaterialStyledDialog.Builder headerColorInt(@ColorInt int color);

  /**
   * Set an image for the dialog header.
   *
   * @param drawable image for the header
   * @return this
   */
  MaterialStyledDialog.Builder headerDrawable(@NonNull Drawable drawable);

  /**
   * Set an image for the dialog header
   *
   * @param drawableRes image for the header
   * @return this
   */
  MaterialStyledDialog.Builder headerDrawable(@DrawableRes Integer drawableRes);

  /**
   * Set a positive button for the dialog
   *
   * @param text     for the button
   * @param callback action to do
   * @return this
   * @deprecated use {{@link #positiveText(CharSequence)}, {@link #positiveText(int)} and {@link #onPositive(MaterialDialog.SingleButtonCallback)}} instead
   */
  MaterialStyledDialog.Builder setPositive(String text, MaterialDialog.SingleButtonCallback callback);

  /**
   * Set a positive button text for the dialog. E.g.: R.string.accept
   *
   * @param buttonTextRes for the button
   * @return this
   */
  MaterialStyledDialog.Builder positiveText(@StringRes int buttonTextRes);

  /**
   * Set a positive button text for the dialog. E.g.: "Accept"
   *
   * @param buttonText for the button
   * @return this
   */
  MaterialStyledDialog.Builder positiveText(@NonNull CharSequence buttonText);

  /**
   * Set a positive button action for the dialog
   *
   * @param callback for the button
   * @return this
   */
  MaterialStyledDialog.Builder onPositive(@NonNull MaterialDialog.SingleButtonCallback callback);

  /**
   * Set a negative button for the dialog
   *
   * @param text     for the button
   * @param callback action to do
   * @return this
   * @deprecated use {{@link #negativeText(CharSequence)}, {@link #negativeText(int)} and {@link #onNegative(MaterialDialog.SingleButtonCallback)}} instead
   */
  MaterialStyledDialog.Builder setNegative(String text, MaterialDialog.SingleButtonCallback callback);

  /**
   * Set a negative button text for the dialog. E.g.: R.string.cancel
   *
   * @param buttonTextRes for the button
   * @return this
   */
  MaterialStyledDialog.Builder negativeText(@StringRes int buttonTextRes);

  /**
   * Set a negative button text for the dialog. E.g.: "Decline"
   *
   * @param buttonText for the button
   * @return this
   */
  MaterialStyledDialog.Builder negativeText(@NonNull CharSequence buttonText);

  /**
   * Set a negative button action for the dialog
   *
   * @param callback for the button
   * @return this
   */
  MaterialStyledDialog.Builder onNegative(@NonNull MaterialDialog.SingleButtonCallback callback);

  /**
   * Set a neutral button for the dialog
   *
   * @param text     for the button
   * @param callback action to do
   * @return this
   * @deprecated use {{@link #neutralText(CharSequence)}, {@link #neutralText(int)} and {@link #onNeutral(MaterialDialog.SingleButtonCallback)}} instead
   */
  MaterialStyledDialog.Builder setNeutral(String text, MaterialDialog.SingleButtonCallback callback);

  /**
   * Set a neutral button text for the dialog. E.g.: R.string.later
   *
   * @param buttonTextRes for the button
   * @return this
   */
  MaterialStyledDialog.Builder neutralText(@StringRes int buttonTextRes);

  /**
   * Set a neutral button text for the dialog. E.g.: "Maybe later"
   *
   * @param buttonText for the button
   * @return this
   */
  MaterialStyledDialog.Builder neutralText(@NonNull CharSequence buttonText);

  /**
   * Set a neutral button action for the dialog
   *
   * @param callback for the button
   * @return this
   */
  MaterialStyledDialog.Builder onNeutral(@NonNull MaterialDialog.SingleButtonCallback callback);

  /**
   * Set the scale type for the header ImageView.
   *
   * @param scaleType the scale type for the header ImageView
   * @return this
   */
  MaterialStyledDialog.Builder headerScaleType(ImageView.ScaleType scaleType);

  /**
   * Set if the dialog will be hidden when touching outside
   *
   * @param cancelable true to enable, false otherwise
   * @return this
   */
  MaterialStyledDialog.Builder cancelable(Boolean cancelable);

  /**
   * Set if the content will be isScrollable. Default: false.
   *
   * @param scrollable true to enable isScrollable content, false otherwise
   * @return this
   */
  MaterialStyledDialog.Builder scrollable(Boolean scrollable);

  /**
   * Set if the content will be isScrollable, with custom maximum lines. Default: false, 5.
   *
   * @param scrollable true to enable isScrollable content, false otherwise
   * @return this
   */
  MaterialStyledDialog.Builder scrollable(Boolean scrollable, Integer maxLines);

  /**
   * Set if the dialog will be dismissed when a button is pressed. Default: true.
   *
   * @param dismiss true to dismiss dialog when a button is pressed
   * @return this
   */
  MaterialStyledDialog.Builder autoDismiss(Boolean dismiss);

  /**
   * Set listener to be triggered when dismissing dialog
   *
   * @param onDismissListener the listener to be set
   * @return this
   */
  MaterialStyledDialog.Builder dismissListener(DialogInterface.OnDismissListener onDismissListener);

}
