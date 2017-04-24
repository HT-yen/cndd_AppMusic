package rimoka.com.navigationgoogleplay;
 //remember : External_content_uri:uri tới bộ nhớ ngoài(vd uri tới SD card)
//   Internal_content_uri: uri tới bộ nhớ trong

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by HOARIMOKA on 3/26/2017.
 */

public class MyListSong {
    static ArrayList<MySong> listSong;
    Context context;
    ContentResolver contentResolver;
    private static String viewfollow,stringcompare;
    public  MyListSong(){};

    public MyListSong(Context context) {
        this.context = context;
    }

    public ArrayList<MySong> getList(){
        listSong=new ArrayList<>();
        contentResolver=this.context.getContentResolver();
        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor=contentResolver.query(uri,null,null,null,null);
        cursor.moveToFirst();
        if(cursor!=null&&cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int titleID = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistID = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int albumId = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int dataID = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int timeId = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            do {
                long idSong = cursor.getLong(id);
                String title = cursor.getString(titleID);
                String artist = cursor.getString(artistID);
                String album = cursor.getString(albumId);
                String data = cursor.getString(dataID);
                long time = cursor.getInt(timeId);
                listSong.add(new MySong(idSong, title, artist, album, data, time));
            }
            while (cursor.moveToNext());
        }
        return listSong;
}
    public ArrayList<MySong> getListBaseArtist(){
            listSong=new ArrayList<>();
            contentResolver=this.context.getContentResolver();
            Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor cursor=contentResolver.query(uri,null,null,null,null);
            cursor.moveToFirst();
            if(cursor!=null&&cursor.moveToFirst()){
                int id=cursor.getColumnIndex( MediaStore.Audio.Media._ID);
                int titleID=cursor.getColumnIndex( MediaStore.Audio.Media.TITLE);
                int artistID=cursor.getColumnIndex( MediaStore.Audio.Media.ARTIST);
                int dataID=cursor.getColumnIndex( MediaStore.Audio.Media.DATA);
                int timeId=cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
                int albumId = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                do{
                    String artist=cursor.getString(artistID);
                    if(artist.equals(stringcompare))
                    {
                        long idSong=cursor.getLong(id);
                        String title=cursor.getString(titleID);
                        String data=cursor.getString(dataID);
                        long time=cursor.getInt(timeId);
                        String album = cursor.getString(albumId);
                        listSong.add(new MySong(idSong, title, artist, album, data, time));
                    }
                }
                while(cursor.moveToNext());
        }
        return listSong;
    }
    public ArrayList<MySong> getListBaseAlbum(){
        listSong=new ArrayList<>();
        contentResolver=this.context.getContentResolver();
        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor=contentResolver.query(uri,null,null,null,null);
        cursor.moveToFirst();
        if(cursor!=null&&cursor.moveToFirst()){
            int id=cursor.getColumnIndex( MediaStore.Audio.Media._ID);
            int titleID=cursor.getColumnIndex( MediaStore.Audio.Media.TITLE);
            int artistID=cursor.getColumnIndex( MediaStore.Audio.Media.ARTIST);
            int dataID=cursor.getColumnIndex( MediaStore.Audio.Media.DATA);
            int timeId=cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int albumId = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            do{
                String album = cursor.getString(albumId);
                if(album.equals(stringcompare))
                {
                    long idSong=cursor.getLong(id);
                    String title=cursor.getString(titleID);
                    String data=cursor.getString(dataID);
                    long time=cursor.getInt(timeId);
                    String artist=cursor.getString(artistID);
                    listSong.add(new MySong(idSong, title, artist, album, data, time));
                }
            }
            while(cursor.moveToNext());
        }
        return listSong;
    }
    public static String getViewfollow() {
        return viewfollow;
    }

    public static void setViewfollow(String viewfollow) {
        MyListSong.viewfollow = viewfollow;
    }


    public static String getStringcompare() {
        return stringcompare;
    }

    public static void setStringcompare(String stringcompare) {
        MyListSong.stringcompare = stringcompare;
    }
    protected class MySong{
        long songId;
        String title,artist,data,album;
        long time;

        public MySong(long songId, String title, String artist,String album,String data,long time) {
            this.songId = songId;
            this.title = title;
            this.artist = artist;
            this.data=data;
            this.time=time;
            this.album=album;
        }

        public long getSongId() {
            return songId;
        }

        public void setSongId(long songId) {
            this.songId = songId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getAlbum() {
            return album;
        }

        public void setAlbum(String album) {
            this.album = album;
        }
    }
}