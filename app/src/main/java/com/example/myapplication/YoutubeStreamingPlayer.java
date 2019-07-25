package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class YoutubeStreamingPlayer extends AppCompatActivity {
    String videoUrl = null;
    YouTubePlayerView youTubePlayerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_streaming_player);

        // bind youtube player layout
        youTubePlayerView = findViewById(R.id.youtube_player_view);


        //TODO
        // get steaming video url from api
        videoUrl = "https://www.youtube.com/watch?v=bebuiaSKtU4";
        //final String videoUrl = "https://www.youtube.com/watch?v=hHW1oY26hxQ"; // not available url

        // check if youtube player has instantiated successfully and videoUrl is valid yotube url
        if (youTubePlayerView != null
                && videoUrl != null
                && videoUrl.length() > 0
                && URLUtil.isValidUrl(videoUrl)
                && Patterns.WEB_URL.matcher(videoUrl).matches()) {
            // set full screen mode
            setFullScreenVideoMode();

            // add youtube player observer
            getLifecycle().addObserver(youTubePlayerView);

            // add youtube player listener
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    // extract videoId from url
                    final String videoId = videoUrl.split("=")[1];
                    // play youtube video
                    youTubePlayer.loadVideo(videoId, 0);
                }
            });
        }
    }

    private void setFullScreenVideoMode() {
        // hide toolbar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // set transparent status bar
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);

        // set full screen layout
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        // set youtube player full screen mode
        youTubePlayerView.enterFullScreen();
    }


}
