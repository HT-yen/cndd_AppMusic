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
    public  MyListSong(){};

    public MyListSong(Context context) {
        this.context = context;
    }

    public ArrayList<MySong> getList(){
        if(listSong==null)
        {
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
                do{
                    long idSong=cursor.getLong(id);
                    String title=cursor.getString(titleID);
                    String artist=cursor.getString(artistID);
                    String data=cursor.getString(dataID);
                    long time=cursor.getInt(timeId);
                    listSong.add(new MySong(idSong,title,artist,data,time));
                }
                while(cursor.moveToNext());
            }
        }
        return listSong;
    }
    protected class MySong{
        long songId;
        String title,artist,data;
       long time;

        public MySong(long songId, String title, String artist,String data,long time) {
            this.songId = songId;
            this.title = title;
            this.artist = artist;
            this.data=data;
            this.time=time;
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
    }
}
