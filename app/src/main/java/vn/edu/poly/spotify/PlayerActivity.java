package vn.edu.poly.spotify;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.triggertrap.seekarc.SeekArc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import vn.edu.poly.spotify.Services.OnClearFromRecentService;
import vn.edu.poly.spotify.databinding.ActivityPlayerBinding;
import vn.edu.poly.spotify.ui.music.Music;


public class PlayerActivity extends AppCompatActivity implements Playable {
    ImageView imgSinger, imgPlay, imgPrevious, imgNext, imgShufle, imgRepeat;
    TextView tvTenBaiHat, tvTenCaSi, tvTimeStart, tvTimeEnd;
    SeekBar seekBarVolume;
    MediaPlayer mp;
    Animation animation, animation1;
    ArrayList<Music> musicArrayList;
    String data, namesong, nameartist, img;
    int size, position, pos;
    boolean repeat = false, random = false, playing = false;
    NotificationManager notificationManager;
    ActivityPlayerBinding activityPlayerBinding;
    Music music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPlayerBinding= DataBindingUtil.setContentView(this,R.layout.activity_player);





//        setContentView(R.layout.activity_player);
        innit();
        Interactive();
        initialization();
        click();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
            registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        }
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,
                    "Spotify", NotificationManager.IMPORTANCE_LOW);

            notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionname");
            switch (action) {
                case CreateNotification.ACTION_PREVIUOS:
                    onTrackPrevious();
                    break;
                case CreateNotification.ACTION_PLAY:
                    if (playing) {
                        onTrackPause();
                    } else {
                        onTrackPlay();
                    }
                    break;
                case CreateNotification.ACTION_NEXT:
                    onTrackNext();
                    break;

            }
        }
    };

    private void innit() {

        imgSinger = findViewById(R.id.imgSinger);
        imgPlay = findViewById(R.id.imgPlay);
        imgPrevious = findViewById(R.id.imgPrevious);
        imgNext = findViewById(R.id.imgNext);
        imgShufle = findViewById(R.id.imgShufle);
        imgRepeat = findViewById(R.id.imgRepeat);
        tvTenBaiHat = findViewById(R.id.tvTenBaiHat);
        tvTenCaSi = findViewById(R.id.tvTenCaSi);
        tvTimeStart = findViewById(R.id.tvTimeStart);
        tvTimeEnd = findViewById(R.id.tvTimeEnd);
        seekBarVolume = findViewById(R.id.seekBarVolume);
        animation = AnimationUtils.loadAnimation(this, R.anim.disc);
        animation1 = AnimationUtils.loadAnimation(this, R.anim.stopdisc);
    }

    private void Interactive() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        musicArrayList = (ArrayList<Music>) bundle.getSerializable("musiclist");
        namesong = intent.getStringExtra("songname");
        data = intent.getStringExtra("data");
        nameartist = intent.getStringExtra("nameartist");
        img = intent.getStringExtra("songimage");
        size = intent.getIntExtra("size", 0);
        pos = intent.getIntExtra("pos", 0);
        Log.e("size", musicArrayList + "");


    }

    private void click() {
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mp.isPlaying()) {
                    mp.pause();
                    imgPlay.setImageResource(R.drawable.play);
                    imgSinger.startAnimation(animation1);
                    settimetotal();
                    Updatetime();
                } else {
                    mp.start();
                    imgPlay.setImageResource(R.drawable.pause);
                    imgSinger.startAnimation(animation);
                    settimetotal();
                    Updatetime();
                }

                if (playing) {
                    onTrackPause();
                } else {
                    onTrackPlay();
                }
            }
        });


        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (musicArrayList.size() > 0) {

                    if (mp.isPlaying() || mp != null) {
                        mp.stop();
                        mp.release();
                        mp = null;
                    }
                    if (position < musicArrayList.size()) {
                        imgPlay.setImageResource(R.drawable.pause);
                        position++;
                        if (repeat == true) {
                            if (position == 0) {
                                position = musicArrayList.size();
                            }
                            position -= 1;
                        }
                        if (random == true) {
                            Random random = new Random();
                            int index = random.nextInt(musicArrayList.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > musicArrayList.size() - 1) {
                            position = 0;
                        }
                    }
                }

                imgPrevious.setClickable(false);
                imgNext.setClickable(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPrevious.setClickable(true);
                        imgNext.setClickable(true);
                    }
                }, 1000);
                try {
                    initialization1();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mp.start();
                imgPlay.setImageResource(R.drawable.pause);
                settimetotal();
                Updatetime();
                CreateNotification.createNotification(PlayerActivity.this, musicArrayList.get(position), R.drawable.pause_black, position, size - 1);
            }
        });

        imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (size > 0) {

                    if (mp.isPlaying() || mp != null) {
                        mp.stop();
                        mp.release();
                        mp = null;
                    }
                    if (position < size) {
                        imgPlay.setImageResource(R.drawable.pause);
                        position--;
                        if (position < 0) {
                            position = size - 1;
                        }
                        if (repeat == true) {
                            position += 1;
                        }
                        if (random == true) {
                            Random random = new Random();
                            int index = random.nextInt(size);
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                    }
                }

                try {
                    initialization1();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mp.start();
                imgPlay.setImageResource(R.drawable.pause);
                settimetotal();
                Updatetime();
                CreateNotification.createNotification(PlayerActivity.this, musicArrayList.get(position), R.drawable.pause_black, position, size - 1);

            }
        });

        imgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeat == false) {
                    if (random == true) {
                        random = false;
                        imgRepeat.setImageResource(R.drawable.rep1);
                        imgShufle.setImageResource(R.drawable.shu2);

                    }
                    imgRepeat.setImageResource(R.drawable.rep1);
                    repeat = true;
                } else {
                    imgRepeat.setImageResource(R.drawable.rep2);
                    repeat = false;
                }
            }
        });

        imgShufle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (random == false) {
                    if (repeat == true) {
                        repeat = false;
                        imgShufle.setImageResource(R.drawable.shu1);
                        imgRepeat.setImageResource(R.drawable.rep2);

                    }
                    imgShufle.setImageResource(R.drawable.shu1);
                    random = true;
                } else {
                    imgShufle.setImageResource(R.drawable.shu2);
                    random = false;
                }
            }
        });

        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
            }
        });
    }


    public void initialization() {
        mp = new MediaPlayer();
        try {
                position = pos;
                mp.setDataSource(data);
                mp.prepare();
                mp.start();
                imgPlay.setImageResource(R.drawable.pause);
                imgSinger.startAnimation(animation);
                Log.e("p00000000000", position + "");
                settimetotal();
                Updatetime();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Glide.with(PlayerActivity.this).load(img).into(imgSinger);
//        tvTenBaiHat.setText(namesong);
//        tvTenCaSi.setText(nameartist);
        music=new Music();
        music.namesong=namesong;
        music.nameartist=nameartist;
        activityPlayerBinding.setMusic(music);


        CreateNotification.createNotification(PlayerActivity.this, musicArrayList.get(position), R.drawable.pause_black, 1, size - 1);

    }

    public void initialization1() throws IOException {
        mp = new MediaPlayer();
        mp.setDataSource(musicArrayList.get(position).data);
        mp.prepare();
        mp.start();
        imgPlay.setImageResource(R.drawable.pause);
        imgSinger.startAnimation(animation);
        music=new Music();
        music.namesong=musicArrayList.get(position).namesong;
        music.nameartist=musicArrayList.get(position).nameartist;
        activityPlayerBinding.setMusic(music);
//        tvTenBaiHat.setText(musicArrayList.get(position).namesong);
//        tvTenCaSi.setText(musicArrayList.get(position).nameartist);
        Glide.with(PlayerActivity.this).load(musicArrayList.get(position).image).into(imgSinger);
    }

    public void settimetotal() {
        SimpleDateFormat dinhdang = new SimpleDateFormat("mm:ss");
        tvTimeEnd.setText(dinhdang.format(mp.getDuration()));
        seekBarVolume.setMax(mp.getDuration());

    }


    public void Updatetime() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dingdang = new SimpleDateFormat("mm:ss");
                tvTimeStart.setText(dingdang.format(mp.getCurrentPosition()));
                // update progress seekbar
                seekBarVolume.setProgress(mp.getCurrentPosition());


                //kiểm tra thười gian kết thúc thì next bài
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (size > 0) {
                            if (mp.isPlaying() || mp != null) {
                                mp.stop();
                                mp.release();

                            }
                            if (position < size) {
                                imgPlay.setImageResource(R.drawable.pause);
                                position++;
                                if (repeat == true) {
                                    if (position == 0) {
                                        position = size;
                                    }
                                    position -= 1;
                                }
                                if (random == true) {
                                    Random random = new Random();
                                    int index = random.nextInt(size);
                                    if (index == position) {
                                        position = index - 1;
                                    }
                                    position = index;
                                }
                                if (position > size - 1) {
                                    position = 0;
                                }
                            }
                        }
                        imgPrevious.setClickable(false);
                        imgNext.setClickable(false);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                imgPrevious.setClickable(true);
                                imgNext.setClickable(true);
                            }
                        }, 3000);
                        try {
                            initialization1();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        MediaPlayer mp1 = new MediaPlayer();
                        mp1.start();
                        imgPlay.setImageResource(R.drawable.pause);
                        settimetotal();
                        Updatetime();
                        CreateNotification.createNotification(PlayerActivity.this, musicArrayList.get(position), R.drawable.pause_black, position, size - 1);
                    }
                });

                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mp.stop();
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onTrackPrevious() {
        if (size > 0) {

            if (mp.isPlaying() || mp != null) {
                mp.stop();
                mp.release();
                mp = null;
            }
            if (position < size) {
                imgPlay.setImageResource(R.drawable.pause);
                position--;
                if (position < 0) {
                    position = size - 1;
                }
                if (repeat == true) {
                    position += 1;
                }
                if (random == true) {
                    Random random = new Random();
                    int index = random.nextInt(size);
                    if (index == position) {
                        position = index - 1;
                    }
                    position = index;
                }
            }
        }

        try {
            initialization1();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
        imgPlay.setImageResource(R.drawable.pause);
        settimetotal();
        Updatetime();
        CreateNotification.createNotification(PlayerActivity.this, musicArrayList.get(position), R.drawable.pause_black, position, size - 1);
    }

    @Override
    public void onTrackPlay() {

        if (mp.isPlaying()) {
            mp.pause();
            imgPlay.setImageResource(R.drawable.play);
            imgSinger.startAnimation(animation1);
            settimetotal();
            Updatetime();
        }
        playing = true;
        CreateNotification.createNotification(PlayerActivity.this, musicArrayList.get(position), R.drawable.play_black, position, size - 1);
    }

    @Override
    public void onTrackPause() {

        mp.start();
        imgPlay.setImageResource(R.drawable.pause);
        imgSinger.startAnimation(animation);
        settimetotal();
        Updatetime();
        playing = false;
        CreateNotification.createNotification(PlayerActivity.this, musicArrayList.get(position), R.drawable.pause_black, position, size - 1);

    }

    @Override
    public void onTrackNext() {

        if (musicArrayList.size() > 0) {

            if (mp.isPlaying() || mp != null) {
                mp.stop();
                mp.release();
                mp = null;
            }
            if (position < musicArrayList.size()) {
                imgPlay.setImageResource(R.drawable.pause);
                position++;
                if (repeat == true) {
                    if (position == 0) {
                        position = musicArrayList.size();
                    }
                    position -= 1;
                }
                if (random == true) {
                    Random random = new Random();
                    int index = random.nextInt(musicArrayList.size());
                    if (index == position) {
                        position = index - 1;
                    }
                    position = index;
                }
                if (position > musicArrayList.size() - 1) {
                    position = 0;
                }
            }
        }

        imgPrevious.setClickable(false);
        imgNext.setClickable(false);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgPrevious.setClickable(true);
                imgNext.setClickable(true);
            }
        }, 1000);
        try {
            initialization1();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
        imgPlay.setImageResource(R.drawable.pause);
        settimetotal();
        Updatetime();
        CreateNotification.createNotification(PlayerActivity.this, musicArrayList.get(position), R.drawable.pause_black, position, size - 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.cancelAll();
        }

        unregisterReceiver(broadcastReceiver);
    }
}
