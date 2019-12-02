package vn.edu.poly.spotify;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
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

import com.triggertrap.seekarc.SeekArc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import vn.edu.poly.spotify.ui.music.Music;
import vn.edu.poly.spotify.ui.music.MusicFragment;
import wseemann.media.FFmpegMediaMetadataRetriever;

public class PlayerActivity extends AppCompatActivity {
    ImageView imgBack, imgSinger, imgPlay, imgPrevious, imgNext, imgShufle, imgRepeat, imgVolumeoff, imgVolumeup;
    TextView tvTenBaiHat, tvTenCaSi, tvTimeStart, tvTimeEnd;
    SeekBar seekBarVolume;
    MediaPlayer mp;
    SeekArc seekArc;
    Animation animation,animation1;
    ArrayList<Music> musicArrayList;
    String nameimage, namesong, nameartist;
    int size,position,pos;
    Boolean play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        innit();
        Interactive();
        initialization();
        click();

        FFmpegMediaMetadataRetriever retriever = new FFmpegMediaMetadataRetriever();
        retriever.setDataSource(nameimage);
        byte[] datas = retriever.getEmbeddedPicture();
        Bitmap bitmap = BitmapFactory.decodeByteArray(datas, 0, datas.length);
        retriever.release();
        imgSinger.setImageBitmap(bitmap);
    }

    private void innit() {
        imgBack = findViewById(R.id.imgBack);
        imgSinger = findViewById(R.id.imgSinger);
        imgPlay = findViewById(R.id.imgPlay);
        imgPrevious = findViewById(R.id.imgPrevious);
        imgNext = findViewById(R.id.imgNext);
        imgShufle = findViewById(R.id.imgShufle);
        imgRepeat = findViewById(R.id.imgRepeat);
        imgVolumeoff = findViewById(R.id.imgVolumeoff);
        imgVolumeup = findViewById(R.id.imgVolumeup);
        tvTenBaiHat = findViewById(R.id.tvTenBaiHat);
        tvTenCaSi = findViewById(R.id.tvTenCaSi);
        tvTimeStart = findViewById(R.id.tvTimeStart);
        tvTimeEnd = findViewById(R.id.tvTimeEnd);
        seekArc = findViewById(R.id.seekArc);
        musicArrayList=new ArrayList<>();
        seekBarVolume = findViewById(R.id.seekBarVolume);
        animation = AnimationUtils.loadAnimation(this, R.anim.disc);
        animation1 = AnimationUtils.loadAnimation(this, R.anim.stopdisc);
    }

    private void Interactive() {
        Intent intent = getIntent();
        namesong = intent.getStringExtra("songname");
        nameimage = intent.getStringExtra("songimage");
        nameartist = intent.getStringExtra("nameartist");
        size=intent.getIntExtra("size",0);
        pos=intent.getIntExtra("pos",0);
        Log.e("size",size+"");


    }
    private void click(){
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if (position>size-1){
                    position=0;
                }
                if (mp.isPlaying()){
                    mp.stop();
                    mp.release();
                }
                initialization();
                mp.start();
                imgPlay.setImageResource(R.drawable.pause);
                settimetotal();
                Updatetime();
                //imgSinger.startAnimation(animation);
            }
        });

        imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgShufle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



    public void initialization() {
         mp= new MediaPlayer();
        try {

            mp.setDataSource(nameimage);
            mp.prepare();
            mp.start();
            imgPlay.setImageResource(R.drawable.pause);
            imgSinger.startAnimation(animation);

        } catch (IOException e) {
            e.printStackTrace();
        }

        tvTenBaiHat.setText(namesong);
        tvTenCaSi.setText(nameartist);
    }

    public void settimetotal(){
        SimpleDateFormat dinhdang=new SimpleDateFormat("mm:ss");
        tvTimeEnd.setText(dinhdang.format(mp.getDuration()));
    }


    public void Updatetime(){

        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dingdang=new SimpleDateFormat("mm:ss");
                tvTimeStart.setText(dingdang.format(mp.getCurrentPosition()));
                //update progress seekbar
                seekArc.setProgress(mp.getCurrentPosition());

                //kiểm tra thười gian kết thúc thì next bài
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if (position>size-1){
                            position=0;
                        }
                        if (mp.isPlaying()){
                            mp.stop();
                            mp.release();
                        }
                        initialization();
                        mp.start();
                        imgPlay.setImageResource(R.drawable.pause);
                        settimetotal();
                        Updatetime();
                    }
                });
                handler.postDelayed(this,500);
            }
        },100);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mp.stop();
            mp.release();
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
