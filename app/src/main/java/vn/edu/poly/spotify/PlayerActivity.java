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

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.triggertrap.seekarc.SeekArc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import vn.edu.poly.spotify.Services.OnClearFromRecentService;
import vn.edu.poly.spotify.ui.music.Music;

public class PlayerActivity extends AppCompatActivity {
    ImageView imgBack, imgSinger, imgPlay, imgPrevious, imgNext, imgShufle, imgRepeat;
    TextView tvTenBaiHat, tvTenCaSi, tvTimeStart, tvTimeEnd;
    SeekBar seekBarVolume;
    MediaPlayer mp;
    SeekArc seekArc;
    Animation animation, animation1;
    ArrayList<Music> musicArrayList;
    String data, namesong, nameartist, img;
    int size, position, pos;
    boolean repeat = false, random = false;
    NotificationManager notificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        innit();
        Interactive();
        initialization();
        click();
        CreateNotification.createNotification(PlayerActivity.this, musicArrayList.get(position),
                R.drawable.pause, position, size - 1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
//            registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
//            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        }

        Glide.with(PlayerActivity.this).load(img).into(imgSinger);
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,
                    "KOD Dev", NotificationManager.IMPORTANCE_LOW);

            notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

//    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getExtras().getString("actionname");
//
//            switch (action){
//                case CreateNotification.ACTION_PREVIUOS:
//                    onTrackPrevious();
//                    break;
//                case CreateNotification.ACTION_PLAY:
//                    if (mp.isPlaying()){
//                        onTrackPause();
//                    } else {
//                        onTrackPlay();
//                    }
//                    break;
//                case CreateNotification.ACTION_NEXT:
//                    onTrackNext();
//                    break;
//            }
//        }
//    };

    private void innit() {
        imgBack = findViewById(R.id.imgBack);
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
        seekArc = findViewById(R.id.seekArc);

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
                    CreateNotification.createNotification(PlayerActivity.this, musicArrayList.get(position),
                            R.drawable.pause, position, size - 1);
//                    onTrackPause();
                    mp.pause();
                    imgPlay.setImageResource(R.drawable.play);
                    imgSinger.startAnimation(animation1);
                    settimetotal();
                    Updatetime();
                } else {

//                    onTrackPlay();
                    mp.start();
                    imgPlay.setImageResource(R.drawable.pause);
                    imgSinger.startAnimation(animation);
                    settimetotal();
                    Updatetime();
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
                }, 3000);
                try {
                    initialization1();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mp.start();
                imgPlay.setImageResource(R.drawable.pause);
                settimetotal();
                Updatetime();

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

        seekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {

            }

            @Override
            public void onStopTrackingTouch(SeekArc seekAra) {
                mp.seekTo(seekArc.getArcRotation());

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

        tvTenBaiHat.setText(namesong);
        tvTenCaSi.setText(nameartist);
    }

    public void initialization1() throws IOException {
        mp = new MediaPlayer();
        mp.setDataSource(musicArrayList.get(position).data);
        mp.prepare();
        mp.start();
        imgPlay.setImageResource(R.drawable.pause);
        imgSinger.startAnimation(animation);
        tvTenBaiHat.setText(musicArrayList.get(position).namesong);
        tvTenCaSi.setText(musicArrayList.get(position).nameartist);
        Glide.with(PlayerActivity.this).load(musicArrayList.get(position).image).into(imgSinger);

    }

    public void settimetotal() {
        SimpleDateFormat dinhdang = new SimpleDateFormat("mm:ss");
        tvTimeEnd.setText(dinhdang.format(mp.getDuration()));
        seekBarVolume.setMax(mp.getDuration());
        seekArc.setProgress(mp.getDuration());
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
                seekArc.setProgress(mp.getCurrentPosition());


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
//
//    @Override
//    public void onTrackPrevious() {
//
//    }
//
//    @Override
//    public void onTrackPlay() {
//        CreateNotification.createNotification(PlayerActivity.this, musicArrayList.get(position),
//                R.drawable.pause, position, size-1);
//
////        isPlaying = true;
//    }
//
//    @Override
//    public void onTrackPause() {
//        CreateNotification.createNotification(PlayerActivity.this, musicArrayList.get(position),
//                R.drawable.play, position, size-1);
//    }
//
//    @Override
//    public void onTrackNext() {
//
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            notificationManager.cancelAll();
//        }
//
//        unregisterReceiver(broadcastReceiver);
//    }
}
