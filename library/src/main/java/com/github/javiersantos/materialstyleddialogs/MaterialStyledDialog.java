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
import android.support.v7.content.res.AppCompatResources;
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
    if (mBuilder != null && mBuilder.dialog != null && !isShowing())
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
        dialogContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, builder.contentSize);
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

  public boolean isShowing() {
    return getBuilder().dialog.isShowing();
  }

  public static class Builder {
    protected Context context;

    protected MaterialDialog dialog;

    protected Style style;
    protected Duration duration;
    protected boolean isIconAnimation, isDialogAnimation, isDialogDivider, isCancelable, isScrollable, isDarkerOverlay, isAutoDismiss;
    protected Drawable headerDrawable, iconDrawable;
    protected Integer primaryColor, maxLines;
    protected CharSequence title, content;
    protected View customView;
    protected int customViewPaddingLeft, customViewPaddingTop, customViewPaddingRight, customViewPaddingBottom;
    protected ImageView.ScaleType headerScaleType;

    protected CharSequence positive, negative, neutral;
    protected boolean showNegative, showNeutral;
    protected MaterialDialog.SingleButtonCallback positiveCallback, negativeCallback, neutralCallback;
    protected DialogInterface.OnDismissListener dismissListener;
    protected Integer iconWidth, iconHeight;
    protected Float titleSize, contentSize;
    
    // Context-dependent variables
    protected Integer paddingLeftDp, paddingTopDp, paddingRightDp, paddingBottomDp;
    protected Integer iconRes, pixWidthId, pixHeightId;
    protected Integer titleRes, titleDimen, contentRes, contentDimen;
    protected Integer headerColorRes, headerDrawableRes;
    protected Integer positiveTextRes, negativeTextRes, neutralTextRes;

    public Builder(Context context) {
      this();
      this.context = context;
    }

    public Builder() {
      this.style = Style.HEADER_WITH_ICON;
      this.isIconAnimation = false;
      this.isDialogAnimation = false;
      this.isDialogDivider = false;
      this.isDarkerOverlay = false;
      this.duration = Duration.NORMAL;
      this.isCancelable = true;
      this.isScrollable = false;
      this.maxLines = 5;
      this.isAutoDismiss = true;
      this.headerScaleType = ImageView.ScaleType.CENTER_CROP;
    }

    public Builder setCustomView(View customView) {
      this.customView = customView;
      this.customViewPaddingLeft = 0;
      this.customViewPaddingRight = 0;
      this.customViewPaddingTop = 0;
      this.customViewPaddingBottom = 0;
      return this;
    }

    public Builder setCustomView(View customView, int left, int top, int right, int bottom) {
      this.customView = customView;
      paddingLeftDp = left;
      paddingTopDp = top;
      paddingRightDp = right;
      paddingBottomDp = bottom;
      return this;
    }

    public Builder style(Style style) {
      this.style = style;
      return this;
    }

    public Builder withIconAnimation(Boolean withAnimation) {
      this.isIconAnimation = withAnimation;
      return this;
    }

    public Builder withDialogAnimation(Boolean withAnimation) {
      this.isDialogAnimation = withAnimation;
      return this;
    }

    public Builder withDialogAnimation(Boolean withAnimation, Duration duration) {
      this.isDialogAnimation = withAnimation;
      this.duration = duration;
      return this;
    }

    public Builder withDivider(Boolean withDivider) {
      this.isDialogDivider = withDivider;
      return this;
    }

    public Builder withDarkerOverlay(Boolean withDarkerOverlay) {
      this.isDarkerOverlay = withDarkerOverlay;
      return this;
    }

    public Builder icon(@NonNull Drawable icon) {
      this.iconDrawable = icon;
      return this;
    }

    public Builder icon(@DrawableRes Integer iconRes) {
      this.iconRes = iconRes;
      return this;
    }

    public Builder iconSize(int pixWidth, int pixHeight) {
      this.iconWidth = pixWidth;
      this.iconHeight = pixHeight;
      return this;
    }

    public Builder iconSize(int pixSize) {
      return iconSize(pixSize, pixSize);
    }

    public Builder iconSizeId(@DimenRes int pixSizeId) {
      return iconSizeIds(pixSizeId, pixSizeId);
    }

    public Builder iconSizeIds(@DimenRes int pixWidthId, @DimenRes int pixHeightId) {
      this.pixWidthId = pixWidthId;
      this.pixHeightId = pixHeightId;
      return this;
    }

    public Builder title(@StringRes int titleRes) {
      this.titleRes = titleRes;
      return this;
    }

    public Builder title(@NonNull CharSequence title) {
      this.title = title;
      return this;
    }

    public Builder titleSizeId(@DimenRes int titleDimen) {
      this.titleDimen = titleDimen;
      return this;
    }

    public Builder titleSize(float titleSize) {
      this.titleSize = titleSize;
      return this;
    }

    public Builder content(@StringRes int descriptionRes) {
      this.contentRes = descriptionRes;
      return this;
    }

    public Builder content(@NonNull CharSequence description) {
      this.content = description;
      return this;
    }

    public Builder contentSizeId(@DimenRes int contentDimen) {
      this.contentDimen = contentDimen;
      return this;
    }

    public Builder contentSize(float contentSize) {
      this.contentSize = contentSize;
      return this;
    }

    public Builder headerColor(@ColorRes int color) {
      this.headerColorRes = color;
      return this;
    }

    public Builder headerColorInt(@ColorInt int color) {
      this.primaryColor = color;
      return this;
    }

    public Builder headerDrawable(@NonNull Drawable drawable) {
      this.headerDrawable = drawable;
      return this;
    }

    public Builder headerDrawable(@DrawableRes Integer drawableRes) {
      this.headerDrawableRes = drawableRes;
      return this;
    }

    public Builder positiveText(@StringRes int buttonTextRes) {
      this.positiveTextRes = buttonTextRes;
      return this;
    }

    public Builder positiveText(@NonNull CharSequence buttonText) {
      this.positive = buttonText;
      return this;
    }

    public Builder onPositive(@NonNull MaterialDialog.SingleButtonCallback callback) {
      this.positiveCallback = callback;
      return this;
    }

    public Builder negativeText(@StringRes int buttonTextRes) {
      this.negativeTextRes = buttonTextRes;
      return this;
    }

    public Builder negativeText(@NonNull CharSequence buttonText) {
      this.negative = buttonText;
      return this;
    }

    public Builder onNegative(@NonNull MaterialDialog.SingleButtonCallback callback) {
      this.negativeCallback = callback;
      return this;
    }

    public Builder showNegative(boolean show) {
      showNegative = show;
      return this;
    }

    public Builder neutralText(@StringRes int buttonTextRes) {
      this.neutralTextRes = buttonTextRes;
      return this;
    }

    public Builder neutralText(@NonNull CharSequence buttonText) {
      this.neutral = buttonText;
      return this;
    }

    public Builder onNeutral(@NonNull MaterialDialog.SingleButtonCallback callback) {
      this.neutralCallback = callback;
      return this;
    }

    public Builder showNeutral(boolean show) {
      showNeutral = show;
      return this;
    }

    public Builder headerScaleType(ImageView.ScaleType scaleType) {
      this.headerScaleType = scaleType;
      return this;
    }

    public Builder cancelable(Boolean cancelable) {
      this.isCancelable = cancelable;
      return this;
    }

    public Builder scrollable(Boolean scrollable) {
      this.isScrollable = scrollable;
      return this;
    }

    public Builder scrollable(Boolean scrollable, Integer maxLines) {
      this.isScrollable = scrollable;
      this.maxLines = maxLines;
      return this;
    }

    public Builder autoDismiss(Boolean dismiss) {
      this.isAutoDismiss = dismiss;
      return this;
    }

    public Builder dismissListener(DialogInterface.OnDismissListener onDismissListener) {
      this.dismissListener = onDismissListener;
      return this;
    }

    @UiThread
    public MaterialStyledDialog build() {
      if (context == null) {
        throw new RuntimeException("Context cannot be null when building dialog. If you are deferring dialog creation, please use the '.build(context)' method");
      }
      Resources res = context.getResources();
      if (titleRes != null) {
        title(res.getString(titleRes));
      }
      if (titleDimen != null) {
        titleSize(res.getDimension(titleDimen));
      }
      if (contentRes != null) {
        content(res.getString(contentRes));
      }
      if (contentDimen != null) {
        contentSize(res.getDimension(contentDimen));
      }
      if (neutralTextRes != null) {
        neutralText(res.getString(neutralTextRes));
      }
      if (negativeTextRes != null) {
        negativeText(res.getString(negativeTextRes));
      }
      if (positiveTextRes != null) {
        positiveText(res.getString(positiveTextRes));
      }
      if (pixWidthId != null && pixHeightId != null) {
        iconSize(res.getDimensionPixelSize(pixWidthId), res.getDimensionPixelOffset(pixHeightId));
      }
      if (iconRes != null) {
        icon(ResourcesCompat.getDrawable(res, iconRes, null));
      }
      if (headerDrawableRes != null) {
        headerDrawable(AppCompatResources.getDrawable(context, headerDrawableRes));
      }
      headerColorInt(headerColorRes == null ? UtilsLibrary.getPrimaryColor(context) : UtilsLibrary.getColor(context, headerColorRes));
      if (paddingLeftDp != null && paddingTopDp != null && paddingRightDp != null && paddingBottomDp != null) {
        setCustomView(customView, UtilsLibrary.dpToPixels(context, paddingLeftDp), UtilsLibrary.dpToPixels(context, paddingTopDp), UtilsLibrary.dpToPixels(context, paddingRightDp), UtilsLibrary.dpToPixels(context, paddingBottomDp));
      }
      return new MaterialStyledDialog(this);
    }

    @UiThread
    public MaterialStyledDialog build(Context context) {
      this.context = context;
      return build();
    }

    @UiThread
    public MaterialStyledDialog show() {
      MaterialStyledDialog materialStyledDialog = build();
      materialStyledDialog.show();
      return materialStyledDialog;
    }

    @UiThread
    public MaterialStyledDialog show(Context context) {
      this.context = context;
      return show();
    }

  }

}
