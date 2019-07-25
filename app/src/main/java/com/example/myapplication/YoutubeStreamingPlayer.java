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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class YoutubeStreamingPlayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_streaming_player);

        //TODO
        // get steaming video url from api
        final String videoUrl = "https://www.youtube.com/watch?v=bebuiaSKtU4";
        //final String videoUrl = "https://www.youtube.com/watch?v=hHW1oY26hxQ"; // not available url

        // hide toolbar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // set transparent status bar
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        // bind youtube player layout
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);

        // add youtube player listener
        getLifecycle().addObserver(youTubePlayerView);

        // add youtube player listener
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                // check if is valid url
                if (URLUtil.isValidUrl(videoUrl))
                    // check if is youtube url
                    if (Patterns.WEB_URL.matcher(videoUrl).matches()) {
                        // extract videoId from url
                        final String videoId = videoUrl.split("=")[1];

                        // play youtube video
                        youTubePlayer.loadVideo(videoId, 0);
                    }
            }
        });
    }
}
