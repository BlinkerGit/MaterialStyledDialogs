package com.github.javiersantos.materialstyleddialogs;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v4.content.res.ResourcesCompat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.github.javiersantos.materialstyleddialogs.enums.Duration;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

public class MaterialStyledDialog {
  protected final Builder mBuilder;

  public final Builder getBuilder() {
    return mBuilder;
  }

  protected MaterialStyledDialog(Builder builder) {
    mBuilder = builder;
    mBuilder.dialog = initMaterialStyledDialog(builder);
  }

  @UiThread
  public void show() {
    if (mBuilder != null && mBuilder.dialog != null)
      mBuilder.dialog.show();
  }

  @UiThread
  public void dismiss() {
    if (mBuilder != null && mBuilder.dialog != null)
      mBuilder.dialog.dismiss();
  }

  @UiThread
  private MaterialDialog initMaterialStyledDialog(final Builder builder) {
    final MaterialDialog.Builder dialogBuilder = new MaterialDialog.Builder(builder.context).theme(Theme.LIGHT);

    // Set cancelable
    dialogBuilder.cancelable(builder.isCancelable);

    // Set style
    dialogBuilder.customView(initStyle(builder), false);

    // Set positive button
    if (builder.positive != null && builder.positive.length() != 0)
      dialogBuilder.positiveText(builder.positive);
    if (builder.positiveCallback != null)
      dialogBuilder.onPositive(builder.positiveCallback);

    // set negative button
    if (builder.negative != null && builder.negative.length() != 0)
      dialogBuilder.negativeText(builder.negative);
    if (builder.negativeCallback != null)
      dialogBuilder.onNegative(builder.negativeCallback);

    // Set neutral button
    if (builder.neutral != null && builder.neutral.length() != 0)
      dialogBuilder.neutralText(builder.neutral);
    if (builder.neutralCallback != null)
      dialogBuilder.onNeutral(builder.neutralCallback);

    // Set auto dismiss when touching the buttons
    dialogBuilder.autoDismiss(builder.isAutoDismiss);

    if (builder.dismissListener != null)
      dialogBuilder.dismissListener(builder.dismissListener);

    // Build the dialog with the previous configuration
    MaterialDialog materialDialog = dialogBuilder.build();

    // Set dialog animation and animation duration
    if (builder.isDialogAnimation) {
      if (builder.duration == Duration.NORMAL) {
        materialDialog.getWindow().getAttributes().windowAnimations = R.style.MaterialStyledDialogs_DialogAnimationNormal;
      } else if (builder.duration == Duration.FAST) {
        materialDialog.getWindow().getAttributes().windowAnimations = R.style.MaterialStyledDialogs_DialogAnimationFast;
      } else if (builder.duration == Duration.SLOW) {
        materialDialog.getWindow().getAttributes().windowAnimations = R.style.MaterialStyledDialogs_DialogAnimationSlow;
      }
    }

    return materialDialog;
  }

  @UiThread
  @TargetApi(16)
  private View initStyle(final Builder builder) {
    LayoutInflater inflater = LayoutInflater.from(builder.context);
    View contentView;

    switch (builder.style) {
      case HEADER_WITH_ICON:
        contentView = inflater.inflate(R.layout.style_dialog_header_icon, null);
        break;
      case HEADER_WITH_TITLE:
        contentView = inflater.inflate(R.layout.style_dialog_header_title, null);
        break;
      default:
        contentView = inflater.inflate(R.layout.style_dialog_header_icon, null);
        break;
    }

    // Init Views
    RelativeLayout dialogHeaderColor = (RelativeLayout) contentView.findViewById(R.id.md_styled_header_color);
    ImageView dialogHeader = (ImageView) contentView.findViewById(R.id.md_styled_header);
    ImageView dialogPic = (ImageView) contentView.findViewById(R.id.md_styled_header_pic);
    TextView dialogTitle = (TextView) contentView.findViewById(R.id.md_styled_dialog_title);
    TextView dialogContent = (TextView) contentView.findViewById(R.id.md_styled_dialog_description);
    FrameLayout dialogCustomViewGroup = (FrameLayout) contentView.findViewById(R.id.md_styled_dialog_custom_view);
    View dialogDivider = contentView.findViewById(R.id.md_styled_dialog_divider);

    // Set header color or drawable
    if (builder.headerDrawable != null) {
      dialogHeader.setImageDrawable(builder.headerDrawable);
      // Apply gray/darker overlay to the header if enabled
      if (builder.isDarkerOverlay) {
        dialogHeader.setColorFilter(Color.rgb(123, 123, 123), PorterDuff.Mode.MULTIPLY);
      }
    }
    dialogHeaderColor.setBackgroundColor(builder.primaryColor);
    dialogHeader.setScaleType(builder.headerScaleType);

    //Set the custom view
    if (builder.customView != null) {
      dialogCustomViewGroup.addView(builder.customView);
      dialogCustomViewGroup.setPadding(builder.customViewPaddingLeft, builder.customViewPaddingTop, builder.customViewPaddingRight, builder.customViewPaddingBottom);
    }

    // Set header icon
    if (builder.iconDrawable != null) {
      if (builder.style == Style.HEADER_WITH_TITLE) {
        Log.e("MaterialStyledDialog", "You can't set an icon to the HEADER_WITH_TITLE style.");
      } else {
        dialogPic.setImageDrawable(builder.iconDrawable);
      }
      if (builder.iconWidth != null && builder.iconHeight != null) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) dialogPic.getLayoutParams();
        params.width = builder.iconWidth;
        params.height = builder.iconHeight;
        dialogPic.setLayoutParams(params);
      }
    }

    // Set dialog title
    if (builder.title != null && builder.title.length() != 0) {
      dialogTitle.setText(builder.title);
      if (builder.titleSize != null) {
        dialogTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, builder.titleSize);
      }
    } else {
      dialogTitle.setVisibility(View.GONE);
    }

    // Set dialog content
    if (builder.content != null && builder.content.length() != 0) {
      dialogContent.setText(builder.content);

      if (builder.contentSize != null) {
        dialogTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, builder.contentSize);
      }

      // Set scrollable
      dialogContent.setVerticalScrollBarEnabled(builder.isScrollable);
      if (builder.isScrollable) {
        dialogContent.setMaxLines(builder.maxLines);
        dialogContent.setMovementMethod(new ScrollingMovementMethod());
      } else {
        dialogContent.setMaxLines(Integer.MAX_VALUE);
      }
    } else {
      dialogContent.setVisibility(View.GONE);
    }

    // Set icon animation
    if (builder.isIconAnimation) {
      if (builder.style != Style.HEADER_WITH_TITLE) {
        UtilsAnimation.zoomInAndOutAnimation(builder.context, dialogPic);
      }
    }

    // Show dialog divider if enabled
    if (builder.isDialogDivider) {
      dialogDivider.setVisibility(View.VISIBLE);
    }

    return contentView;
  }

  public TextView getTitleView() {
    if (mBuilder == null || mBuilder.dialog == null || mBuilder.dialog.getCustomView() == null) return null;
    return (TextView) mBuilder.dialog.getCustomView().findViewById(R.id.md_styled_dialog_title);
  }

  public TextView getContentView() {
    if (mBuilder == null || mBuilder.dialog == null || mBuilder.dialog.getCustomView() == null) return null;
    return (TextView) mBuilder.dialog.getCustomView().findViewById(R.id.md_styled_dialog_description);
  }

  public ImageView getHeaderView() {
    if (mBuilder == null || mBuilder.dialog == null || mBuilder.dialog.getCustomView() == null) return null;
    return (ImageView) mBuilder.dialog.getCustomView().findViewById(R.id.md_styled_header);
  }

  public ImageView getIconView() {
    if (mBuilder == null || mBuilder.dialog == null || mBuilder.dialog.getCustomView() == null) return null;
    return (ImageView) mBuilder.dialog.getCustomView().findViewById(R.id.md_styled_header_pic);
  }

  public FrameLayout getCustomView() {
    if (mBuilder == null || mBuilder.dialog == null || mBuilder.dialog.getCustomView() == null) return null;
    return (FrameLayout) mBuilder.dialog.getCustomView().findViewById(R.id.md_styled_dialog_custom_view);
  }

  public View getView() {
    return mBuilder != null && mBuilder.dialog != null ? mBuilder.dialog.getCustomView() : null;
  }

  public static class Builder implements IBuilder {
    protected Context context;

    // build() and show()
    protected MaterialDialog dialog;

    protected Style style; // setStyle()
    protected Duration duration; // withDialogAnimation()
    protected boolean isIconAnimation, isDialogAnimation, isDialogDivider, isCancelable, isScrollable, isDarkerOverlay, isAutoDismiss; // withIconAnimation(), withDialogAnimation(), withDivider(), setCancelable(), setScrollable(), withDarkerOverlay(), autoDismiss()
    protected Drawable headerDrawable, iconDrawable; // setHeaderDrawable(), setIconDrawable()
    protected Integer primaryColor, maxLines; // setHeaderColor(), setScrollable()
    protected CharSequence title, content; // setTitle(), setDescription()
    protected View customView; // setCustomView()
    protected int customViewPaddingLeft, customViewPaddingTop, customViewPaddingRight, customViewPaddingBottom;
    protected ImageView.ScaleType headerScaleType;

    // .setPositive(), setNegative() and setNeutral()
    protected CharSequence positive, negative, neutral;
    protected MaterialDialog.SingleButtonCallback positiveCallback, negativeCallback, neutralCallback;
    protected DialogInterface.OnDismissListener dismissListener;
    protected Integer iconWidth, iconHeight;
    protected Float titleSize, contentSize;

    public Builder(Context context) {
      this.context = context;
      this.style = Style.HEADER_WITH_ICON;
      this.isIconAnimation = true;
      this.isDialogAnimation = false;
      this.isDialogDivider = false;
      this.isDarkerOverlay = false;
      this.duration = Duration.NORMAL;
      this.isCancelable = true;
      this.primaryColor = UtilsLibrary.getPrimaryColor(context);
      this.isScrollable = false;
      this.maxLines = 5;
      this.isAutoDismiss = true;
      this.headerScaleType = ImageView.ScaleType.CENTER_CROP;
    }

    @Override
    public Builder setCustomView(View customView) {
      this.customView = customView;
      this.customViewPaddingLeft = 0;
      this.customViewPaddingRight = 0;
      this.customViewPaddingTop = 0;
      this.customViewPaddingBottom = 0;
      return this;
    }

    @Override
    public Builder setCustomView(View customView, int left, int top, int right, int bottom) {
      this.customView = customView;
      this.customViewPaddingLeft = UtilsLibrary.dpToPixels(context, left);
      this.customViewPaddingRight = UtilsLibrary.dpToPixels(context, right);
      this.customViewPaddingTop = UtilsLibrary.dpToPixels(context, top);
      this.customViewPaddingBottom = UtilsLibrary.dpToPixels(context, bottom);
      return this;
    }

    @Override
    public Builder style(Style style) {
      this.style = style;
      return this;
    }

    @Override
    public Builder withIconAnimation(Boolean withAnimation) {
      this.isIconAnimation = withAnimation;
      return this;
    }

    @Override
    public Builder withDialogAnimation(Boolean withAnimation) {
      this.isDialogAnimation = withAnimation;
      return this;
    }

    @Override
    public Builder withDialogAnimation(Boolean withAnimation, Duration duration) {
      this.isDialogAnimation = withAnimation;
      this.duration = duration;
      return this;
    }

    @Override
    public Builder withDivider(Boolean withDivider) {
      this.isDialogDivider = withDivider;
      return this;
    }

    @Override
    public Builder withDarkerOverlay(Boolean withDarkerOverlay) {
      this.isDarkerOverlay = withDarkerOverlay;
      return this;
    }

    @Override
    public Builder icon(@NonNull Drawable icon) {
      this.iconDrawable = icon;
      return this;
    }

    @Override
    public Builder icon(@DrawableRes Integer iconRes) {
      this.iconDrawable = ResourcesCompat.getDrawable(context.getResources(), iconRes, null);
      return this;
    }

    @Override
    public Builder iconSize(int pixWidth, int pixHeight) {
      this.iconWidth = pixWidth;
      this.iconHeight = pixHeight;
      return this;
    }

    @Override
    public Builder iconSize(int pixSize) {
      return iconSize(pixSize, pixSize);
    }

    @Override
    public Builder iconSizeId(@DimenRes int pixSizeId) {
      return iconSize(context.getResources().getDimensionPixelSize(pixSizeId));
    }

    @Override
    public Builder iconSizeIds(@DimenRes int pixWidthId, @DimenRes int pixHeightId) {
      Resources res = context.getResources();
      return iconSize(res.getDimensionPixelSize(pixWidthId), res.getDimensionPixelOffset(pixHeightId));
    }

    @Override
    public Builder title(@StringRes int titleRes) {
      title(this.context.getString(titleRes));
      return this;
    }

    @Override
    public Builder title(@NonNull CharSequence title) {
      this.title = title;
      return this;
    }

    @Override
    public Builder titleSizeId(@DimenRes int titleDimen) {
      return titleSize(context.getResources().getDimension(titleDimen));
    }

    @Override
    public Builder titleSize(float titleSize) {
      this.titleSize = titleSize;
      return this;
    }

    @Override
    public Builder content(@StringRes int descriptionRes) {
      content(this.context.getString(descriptionRes));
      return this;
    }

    @Override
    public Builder content(@NonNull CharSequence description) {
      this.content = description;
      return this;
    }

    @Override
    public Builder contentSizeId(@DimenRes int contentDimen) {
      return contentSize(context.getResources().getDimension(contentDimen));
    }

    @Override
    public Builder contentSize(float contentSize) {
      this.contentSize = contentSize;
      return this;
    }

    @Override
    public Builder headerColor(@ColorRes int color) {
      this.primaryColor = UtilsLibrary.getColor(context, color);
      return this;
    }

    @Override
    public Builder headerColorInt(@ColorInt int color) {
      this.primaryColor = color;
      return this;
    }

    @Override
    public Builder headerDrawable(@NonNull Drawable drawable) {
      this.headerDrawable = drawable;
      return this;
    }

    @Override
    public Builder headerDrawable(@DrawableRes Integer drawableRes) {
      this.headerDrawable = ResourcesCompat.getDrawable(context.getResources(), drawableRes, null);
      return this;
    }

    @Override
    @Deprecated
    public Builder setPositive(String text, MaterialDialog.SingleButtonCallback callback) {
      this.positive = text;
      this.positiveCallback = callback;
      return this;
    }

    @Override
    public Builder positiveText(@StringRes int buttonTextRes) {
      positiveText(this.context.getString(buttonTextRes));
      return this;
    }

    @Override
    public Builder positiveText(@NonNull CharSequence buttonText) {
      this.positive = buttonText;
      return this;
    }

    @Override
    public Builder onPositive(@NonNull MaterialDialog.SingleButtonCallback callback) {
      this.positiveCallback = callback;
      return this;
    }

    @Override
    @Deprecated
    public Builder setNegative(String text, MaterialDialog.SingleButtonCallback callback) {
      this.negative = text;
      this.negativeCallback = callback;
      return this;
    }

    @Override
    public Builder negativeText(@StringRes int buttonTextRes) {
      negativeText(this.context.getString(buttonTextRes));
      return this;
    }

    @Override
    public Builder negativeText(@NonNull CharSequence buttonText) {
      this.negative = buttonText;
      return this;
    }

    @Override
    public Builder onNegative(@NonNull MaterialDialog.SingleButtonCallback callback) {
      this.negativeCallback = callback;
      return this;
    }

    @Override
    @Deprecated
    public Builder setNeutral(String text, MaterialDialog.SingleButtonCallback callback) {
      this.neutral = text;
      this.neutralCallback = callback;
      return this;
    }

    @Override
    public Builder neutralText(@StringRes int buttonTextRes) {
      neutralText(this.context.getString(buttonTextRes));
      return this;
    }

    @Override
    public Builder neutralText(@NonNull CharSequence buttonText) {
      this.neutral = buttonText;
      return this;
    }

    @Override
    public Builder onNeutral(@NonNull MaterialDialog.SingleButtonCallback callback) {
      this.neutralCallback = callback;
      return this;
    }

    @Override
    public Builder headerScaleType(ImageView.ScaleType scaleType) {
      this.headerScaleType = scaleType;
      return this;
    }

    @Override
    public Builder cancelable(Boolean cancelable) {
      this.isCancelable = cancelable;
      return this;
    }

    @Override
    public Builder scrollable(Boolean scrollable) {
      this.isScrollable = scrollable;
      return this;
    }

    @Override
    public Builder scrollable(Boolean scrollable, Integer maxLines) {
      this.isScrollable = scrollable;
      this.maxLines = maxLines;
      return this;
    }

    @Override
    public Builder autoDismiss(Boolean dismiss) {
      this.isAutoDismiss = dismiss;
      return this;
    }

    @Override
    public Builder dismissListener(DialogInterface.OnDismissListener onDismissListener) {
      this.dismissListener = onDismissListener;
      return this;
    }

    @UiThread
    public MaterialStyledDialog build() {
      return new MaterialStyledDialog(this);
    }

    @UiThread
    public MaterialStyledDialog show() {
      MaterialStyledDialog materialStyledDialog = build();
      materialStyledDialog.show();
      return materialStyledDialog;
    }

  }

}
