package vn.edu.poly.spotify.ui.music;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import vn.edu.poly.spotify.R;

public class MusicFragment extends Fragment {


    public RecyclerView rvSong;
    public MusicAdapter musicAdapter;
    List<Music> tempAudioList = new ArrayList<>();
    View root;
    MediaMetadataRetriever mmr = new MediaMetadataRetriever();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_music, container, false);
        AppDataBase appDataBase = Room.databaseBuilder(getActivity(), AppDataBase.class, "music.db").allowMainThreadQueries().build();
        rvSong = root.findViewById(R.id.rvSong);
        rvSong.setHasFixedSize(true);
        getAllAudioFromDevice(getActivity());
        musicAdapter = new MusicAdapter(getActivity(), tempAudioList, appDataBase);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvSong.setLayoutManager(linearLayoutManager);
        rvSong.setAdapter(musicAdapter);



        return root;
    }

//    public ArrayList<File> findSong(File file) {
//        ArrayList<File> arrayList = new ArrayList<>();
//       // File folderPath = new File(getFragmentManager() + "/" + getResources().getString(R.string.app_name));
//        File[] files = file.listFiles();
//
//        for (File singFile : files) {
//            if (singFile.isDirectory() && !singFile.isHidden()) {
//
//                arrayList.addAll(findSong(singFile));
//            } else {
//                if (singFile.getName().endsWith(".mp3") || singFile.getName().endsWith(".wav")) {
//                    arrayList.add(singFile);
//                }
//            }
//
//        }
//        return arrayList;
//    }

//    public void display() {
//         ArrayList<File> mySongs = findSong(new File(Environment.getExternalStorageDirectory().getAbsolutePath()));
//       // items = new String[mySongs.size()];
//        for (int i = 0; i < mySongs.size(); i++) {
//            //items[i] = mySongs.get(i).getName().toString().replace(".mp3", "").replace(".wav", "");
//            Toast.makeText(getActivity(),mySongs.get(i).getName().toString(),Toast.LENGTH_SHORT).show();
//        }
////        musicList=new ArrayList<>();
////        musicAdapter=new MusicAdapter(getActivity(),musicList);
////        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
////        rvSong.setLayoutManager(linearLayoutManager);
////        rvSong.setAdapter(musicAdapter);
//
//
//    }

    public List<Music> getAllAudioFromDevice(final Context context) {


        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST, MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AlbumColumns.ALBUM_ID,MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TRACK,};
        Cursor c = context.getContentResolver().query(uri, projection, null, null, null);

        if (c != null) {
            while (c.moveToNext()) {

                Music music = new Music();
                String path = c.getString(0);
                String album = c.getString(1);
                String artist = c.getString(2);
                String name = c.getString(3);
                String playlist = c.getString(4);
                String data=c.getString(5);

                int id=c.getInt(6);
                String genrer=c.getString(7);


//                FFmpegMediaMetadataRetriever retriever = new FFmpegMediaMetadataRetriever();
//                retriever.setDataSource(path);
//                byte [] datas = retriever.getEmbeddedPicture();
//
//// convert the byte array to a bitmap
//                Bitmap bitmap = BitmapFactory.decodeByteArray(datas, 0, datas.length);
//// do something with the image ...
//// mImageView.setImageBitmap(bitmap);
//
//
//                retriever.release();
              //  Log.e("Bitmap",bitmap+"");
                music.setImage(path);
                music.setNamesong(name);
                music.setAlbum(album);
                music.setNameartist(artist);
                music.setData(path);
                music.setId(id);


                Log.e("Name :" + name, " Album :" + album);
                Log.e("Playlist :" + playlist, " Artist :" + artist);
                Log.e("Gener :" + genrer, " Id :" + id);
                Log.e("+++++++++++", "++++++++++++");

                tempAudioList.add(music);

            }
            c.close();
        }

        return tempAudioList;
    }


//    public void getSongs() {
//        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//
//
//        ContentResolver contentResolver = getActivity().getContentResolver();
//        Cursor myCursor;
//
//
//        String img = MediaStore.Audio.Media._ID;
//        String data = MediaStore.Audio.Media.DATA;
//        String namesong = MediaStore.Audio.Media.DISPLAY_NAME;
//        String nameartist = MediaStore.Audio.Media.ARTIST;
//        String[] songColums = {img, namesong, nameartist, data};
//
//        final String musicOnly = MediaStore.Audio.Media.IS_MUSIC + "=1";
//
//        myCursor = contentResolver.query(songUri, songColums, musicOnly, null, null);
//
//        if (myCursor != null && myCursor.moveToFirst()) {
//            String songId = myCursor.getString(myCursor.getColumnIndexOrThrow(img));
//            String songData = myCursor.getString(myCursor.getColumnIndexOrThrow(data));
//            String songTitle = myCursor.getString(myCursor.getColumnIndexOrThrow(namesong));
//            String songArtist = myCursor.getString(myCursor.getColumnIndexOrThrow(nameartist));
//
//            do {
//
//                Music song = new Music();
//                song.setData(songData);
//                song.setImage(songId);
//                song.setNamesong(songTitle);
//                song.setNameartist(songArtist);
//
//                musicList.add(song);
//
//            } while (myCursor.moveToNext());
//        } else {
//
//        }
//        myCursor.close();
//    }


}