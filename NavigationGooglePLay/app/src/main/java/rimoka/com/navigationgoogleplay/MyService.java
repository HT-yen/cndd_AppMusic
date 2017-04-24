package rimoka.com.navigationgoogleplay;


import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;

import android.widget.Toast;

import java.io.IOException;
import java.util.IllegalFormatException;

/**
 * Created by HOARIMOKA on 3/26/2017.
 */

public class MyService extends Service implements MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener{
    private static MediaPlayer song;
    private static int vitri=0;
    MyListSong mlist=new MyListSong();
    private int sizeList=0;

    //intent service
    public static final String ACTION_PAUSE ="rimoka.com.navigationgoogleplay.PAUSE";
    public static final String ACTION_CHOOSE_PLAY ="rimoka.com.navigationgoogleplay.CHOOSE_PLAY";
    public static final String ACTION_BACKWARD ="rimoka.com.navigationgoogleplay.BACKWARD";
    public static final String ACTION_FORWARD ="rimoka.com.navigationgoogleplay.FORWARD";
    public static final String ACTION_PREVIOUS ="rimoka.com.navigationgoogleplay.PREVIOUS";
    public static final String ACTION_NEXT ="rimoka.com.navigationgoogleplay.NEXT";

    //intent broadcast
    public static final String ACTION_MOVE_SONG ="rimoka.com.navigationgoogleplay.MOVE_SONG";

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

        if(intent!=null)
        {
            String action = intent.getAction();

            if (action.equals(MyService.ACTION_CHOOSE_PLAY)) {
                vitri = intent.getIntExtra("vi tri", -1);
                setSongPlay();
                makeIntent();
            }
            if(action.equals(MyService.ACTION_PAUSE)){
                song.start();//ở đây ko dùng song.prepare() được vì song.prepare() thì sẽ chuẩn bị Media trong back ground
                // rùi khi chuẩn bị xong thì hàm onPrepare() được override lại sẽ dk gọi và thực hiện, tuy nhiên ở đây do Media(bài hát đó) đã
                // được chuẩn bị trước rùi nên ko thể gọi song.prapare() mà goi song.start để chạy tiếp(song.start() có thể bắt đầu chạy)
                // cũng có thể chạy lúc pause xong)
                makeIntent();
            };
            if(action.equals(MyService.ACTION_BACKWARD)){
                createSongifNeed();
                setSongPlay();
                makeIntent();
            };
            if(action.equals(MyService.ACTION_FORWARD)){
                song.seekTo(song.getDuration());
                makeIntent();
            };
            if(action.equals(MyService.ACTION_NEXT)){
                createSongifNeed();
                if(vitri<sizeList -1)
                    vitri+=1;
                else vitri=0;
                setSongPlay();
                makeIntent();
            };
            if(action.equals(MyService.ACTION_PREVIOUS)){
                if(vitri!=0) {
                    vitri-=1;
                }
                else vitri=sizeList-1;
                createSongifNeed();
                setSongPlay();
                makeIntent();

            };
        }


        return START_STICKY;
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        if(song!=null)
            if(song.isPlaying()) song.pause();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if(song!=null) song.start();
    }
    public void createSongifNeed(){
        if (song == null) {
            song = new MediaPlayer();
            song.setOnPreparedListener(this);
            song.setOnCompletionListener(this);
        } else
            song.reset();
    }
    public void setSongPlay(){
        try {
            createSongifNeed();
//            ItemTwoFragment i2=new ItemTwoFragment();
            MyListSong listSong = new MyListSong(getApplicationContext());
            MyListSong.MySong mySong = listSong.getList().get(vitri);
            sizeList=ItemTwoFragment.getList().size();
            song.setAudioStreamType(AudioManager.STREAM_MUSIC);
            song.setDataSource(getApplicationContext(), Uri.parse(mySong.getData()));
            song.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalFormatException e) {
            e.printStackTrace();
        }

    }
    public static MediaPlayer getMediaPlayer(){
        return song;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        createSongifNeed();
        if(vitri<sizeList-1)
            vitri+=1;
        else vitri=0;
        setSongPlay();
        makeIntent();
    }
    public void makeIntent(){
        Intent i=new Intent();
        i.setAction(MyService.ACTION_MOVE_SONG);
        i.putExtra("vitri",vitri);
        sendBroadcast(i);
    }
}
