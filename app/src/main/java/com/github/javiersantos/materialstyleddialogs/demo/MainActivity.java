package com.github.javiersantos.materialstyleddialogs.demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //inflate view
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.custom_view,null);
        Button dismissButton = (Button)customView.findViewById(R.id.custom_button);

        // Build some dialogs for the sample app
        final MaterialStyledDialog.Builder dialogHeader_1 = new MaterialStyledDialog.Builder(context)
                .icon(new IconicsDrawable(context).icon(MaterialDesignIconic.Icon.gmi_google_play).color(Color.WHITE))
                .withDialogAnimation(true)
                .title("Awesome!")
                .content("Glad to see you like MaterialStyledDialogs! If you're up for it, we would really appreciate you reviewing us.")
                .headerColor(R.color.dialog_1)
                .positiveText("Google Play")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName())));
                    }
                })
                .negativeText("Later");

        final MaterialStyledDialog.Builder dialogHeader_2 = new MaterialStyledDialog.Builder(context)
                .icon(new IconicsDrawable(context).icon(MaterialDesignIconic.Icon.gmi_comment_alt).color(Color.WHITE))
                .withIconAnimation(false)
                .content("What can we improve? Your feedback is always welcome.")
                .headerColor(R.color.dialog_2)
                .positiveText("Feedback")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/javiersantos/MaterialStyledDialogs/issues")));
                    }
                })
                .negativeText("Not now");

        final MaterialStyledDialog.Builder dialogHeader_3 = new MaterialStyledDialog.Builder(context)
                .headerDrawable(R.drawable.header)
                .icon(new IconicsDrawable(context).icon(MaterialDesignIconic.Icon.gmi_github).color(Color.WHITE))
                .withDialogAnimation(true)
                .title("An awesome library?")
                .content("Do you like this library? Check out my other Open Source libraries and apps!")
                .positiveText("GitHub")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/javiersantos")));
                    }
                })
                .negativeText("Not now");

        final MaterialStyledDialog.Builder dialogHeader_4 = new MaterialStyledDialog.Builder(context)
                .headerDrawable(R.drawable.header_2)
                .title("Sweet!")
                .content("Check out my others apps with Material Design available on Google Play. Hope you find them interesting!")
                .positiveText("Google Play")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=9205902632927830308")));
                    }
                })
                .negativeText("Not now");

        final MaterialStyledDialog dialogHeader_5 = new MaterialStyledDialog.Builder(context)
                .icon(new IconicsDrawable(context).icon(MaterialDesignIconic.Icon.gmi_comment_alt).color(Color.WHITE))
                .withDialogAnimation(true)
                .content("What can we improve? Your feedback is always welcome.")
                .headerColor(R.color.dialog_2)
                .setCustomView(customView, 20, 20, 20, 0)
                .positiveText("Feedback")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/javiersantos/MaterialStyledDialogs/issues")));
                    }
                }).build();

        final MaterialStyledDialog.Builder dialogHeader_6 = new MaterialStyledDialog.Builder(context)
                .style(Style.HEADER_WITH_TITLE)
                .withDialogAnimation(true)
                .title("Awesome!")
                .content("Glad to see you like MaterialStyledDialogs! If you're up for it, we would really appreciate you reviewing us.")
                .headerColor(R.color.dialog_1)
                .positiveText("Google Play")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName())));
                    }
                })
                .negativeText("Later");

        final MaterialStyledDialog.Builder dialogHeader_7 = new MaterialStyledDialog.Builder(context)
                .style(Style.HEADER_WITH_TITLE)
                .headerDrawable(R.drawable.header)
                .withDialogAnimation(true)
                .withDarkerOverlay(true)
                .title("An awesome library?")
                .content("Do you like this library? Check out my other Open Source libraries and apps!")
                .positiveText("GitHub")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/javiersantos")));
                    }
                })
                .negativeText("Not now");

        // Init the card views
        CardView dialogHeaderView_1 = (CardView) findViewById(R.id.dialog_1);
        CardView dialogHeaderView_2 = (CardView) findViewById(R.id.dialog_2);
        CardView dialogHeaderView_3 = (CardView) findViewById(R.id.dialog_3);
        CardView dialogHeaderView_4 = (CardView) findViewById(R.id.dialog_4);
        CardView dialogHeaderView_5 = (CardView) findViewById(R.id.dialog_5);
        CardView dialogHeaderView_6 = (CardView) findViewById(R.id.dialog_6);
        CardView dialogHeaderView_7 = (CardView) findViewById(R.id.dialog_7);

        // Show the previous dialogs
        dialogHeaderView_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHeader_1.show();
            }
        });

        dialogHeaderView_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHeader_2.show();
            }
        });

        dialogHeaderView_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHeader_3.show();
            }
        });

        dialogHeaderView_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHeader_4.show();
            }
        });

        dialogHeaderView_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHeader_5.show();
            }
        });

        dialogHeaderView_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHeader_6.show();
            }
        });

        dialogHeaderView_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHeader_7.show();
            }
        });

        //custom viewGroup child events
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHeader_5.dismiss();
            }
        });

        // Floating Action Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_github).color(Color.WHITE).sizeDp(24));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/javiersantos/MaterialStyledDialogs")));

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.action_about).setIcon(new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_info).color(Color.WHITE).actionBar());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
