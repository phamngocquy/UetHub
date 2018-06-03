package com.uet.qpn.uethub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.uet.qpn.uethub.fragment.CustomSlide;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;

public class IntroActivity extends MaterialIntroActivity {

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.CustomTheme);
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);

        prefs = getSharedPreferences("com.uet.qpn.uethub", MODE_PRIVATE);
        if (!prefs.getBoolean("firstrun", true)) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.switchColor)
                        .buttonsColor(R.color.thumbColor)
                        .image(R.drawable.intro)
                        .title("Welcome to UETHUB")
                        .description("Theo dõi fanpage của chúng mình để cập nhật những thông tin mới nhất nhé")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(getOpenFacebookIntent(getBaseContext()));
                    }
                }, "Fanpage"));

       /* addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.green)
                .buttonsColor(R.color.red_wine)
                .title("Want more?")
                .description("Go on")
                .build());
*/
        addSlide(new CustomSlide());

     /*   addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.third_slide_background)
                        .buttonsColor(R.color.third_slide_buttons)
                        .possiblePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS})
                        .neededPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
                        .image(R.drawable.img_equipment)
                        .title("We provide best tools")
                        .description("ever")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("Try us!");
                    }
                }, "Tools"));*/

     /*   addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.thumbColor)
                .buttonsColor(R.color.green)
                .title("That's it")
                .description("Would you join us?")
                .build());*/
    }


    public static Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/543378776058444"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/uethub.vnu"));
        }
    }

    @Override
    public void onFinish() {
        prefs.edit().putBoolean("firstrun", false).commit();
        startActivity(new Intent(this, LoginActivity.class));
    }

}
