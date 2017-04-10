package rimoka.com.navigationgoogleplay;


import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

    /**
     * Created by minhpq on 3/29/16.
     */
    public class ItemTwoFragment extends Fragment implements ListenSong,ListenLongSong {
        private static ImageButton previous,next,backward,forward,play,pause;
        private  static MainActivity mainActivity;
        private static RecyclerView recyclerView;
        private static TextView nowPlaySong,nowPlayArtist,timeNow,timeTotal;
        private static SeekBar seekBar;
        private static ArrayList<MyListSong.MySong> listSong;
        private static MyListSong myList;
        private static Utils util=new Utils();
        private static MediaPlayer song;
        private static SongAdapter adapter;
        private Handler hander=new Handler();
        private static MyBroadcast myBroadcast;
        static private EditText dl;
        private static int vitri=-1;
        private static String textSearch;


        public ItemTwoFragment() {
            // Required empty public constructor
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.fragment_item_two, container, false);
            recyclerView = (RecyclerView) v.findViewById(R.id.list_nhac);
            myList = new MyListSong(this.mainActivity);
            listSong = myList.getList();
            adapter = new SongAdapter(listSong, this.mainActivity, this,this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mainActivity, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
            previous=(ImageButton) v.findViewById(R.id.previous);
            next=(ImageButton) v.findViewById(R.id.next);
            play=(ImageButton) v.findViewById(R.id.play);
            pause=(ImageButton) v.findViewById(R.id.pause);
            backward=(ImageButton) v.findViewById(R.id.backward);
            forward=(ImageButton) v.findViewById(R.id.forward);

            nowPlaySong=(TextView) v.findViewById(R.id.nowPlaySong);
            nowPlayArtist=(TextView) v.findViewById(R.id.nowPlayArtist);
            timeNow=(TextView) v.findViewById(R.id.timeNow);
            timeTotal=(TextView) v.findViewById(R.id.timeTotal);


            seekBar=(SeekBar) v.findViewById(R.id.seekBar3);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                // Khi giá trị progress thay đổi.
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    //progress: giá trị sau khi thay đổi của seekbar
                }
                // Khi người dùng bắt đầu cử chỉ kéo thanh gạt.
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }
                // Khi người dùng kết thúc cử chỉ kéo thanh gạt.
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if(song!=null) {
                        song.seekTo(util.progressToTimer(seekBar.getProgress(), (int) song.getDuration()));
//                    if (seekBar.getProgress() == seekBar.getMax()) {
//                        play.setVisibility(ImageButton.INVISIBLE);
//                        pause.setVisibility(ImageButton.VISIBLE);
//                    }
                    }
                    else seekBar.setProgress(0);
                }
            });

            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    play.setVisibility(ImageButton.INVISIBLE);
                    pause.setVisibility(ImageButton.VISIBLE);
                    dungnhac(mainActivity);
                }
            });
            pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(song!=null)
                    {
                        pause.setVisibility(ImageButton.INVISIBLE);
                        play.setVisibility(ImageButton.VISIBLE);
//                        song.start();
                        Intent i = new Intent();

                        i.setAction(MyService.ACTION_PAUSE);
                        mainActivity.startService(i);
                    }

                }
            });
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(song!=null){
                        play.setVisibility(ImageButton.VISIBLE);
                        pause.setVisibility(ImageButton.INVISIBLE);
                        Intent i=new Intent();
                        i.setAction(MyService.ACTION_NEXT);
                        i.putExtra("sizeList",adapter.list.size());
                        mainActivity.startService(i);
                    }

                }
            });
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(song!=null) {
                        play.setVisibility(ImageButton.VISIBLE);
                        pause.setVisibility(ImageButton.INVISIBLE);
                        Intent i = new Intent();
                        i.setAction(MyService.ACTION_PREVIOUS);
                        mainActivity.startService(i);
                    }
                }
            });
            backward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(song!=null) {
                        play.setVisibility(ImageButton.VISIBLE);
                        pause.setVisibility(ImageButton.INVISIBLE);
                        Intent i = new Intent();
                        i.setAction(MyService.ACTION_BACKWARD);
                        mainActivity.startService(i);
                    }
                }
            });
            forward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(song!=null) {
                        Intent i = new Intent();
                        i.setAction(MyService.ACTION_FORWARD);
                        mainActivity.startService(i);
//                updateProgress();
                        play.setVisibility(ImageButton.INVISIBLE);
                        pause.setVisibility(ImageButton.VISIBLE);
                    }
                }
            });
//            if(MainActivity.searchView!=null)
            MainActivity.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    textSearch=newText.toString();
                    tim(textSearch);
                    return true;
                }
            });
            updateProgress();
            return v;
        }

        public void tim(String text) {
            adapter = new SongAdapter(listSong,mainActivity,this,this);
            adapter.getFilter().filter(text.trim());
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void onStart() {
            super.onStart();
            myBroadcast=new MyBroadcast();
            IntentFilter intentFilter=new IntentFilter();
            intentFilter.addAction(MyService.ACTION_MOVE_SONG);
            mainActivity.registerReceiver(myBroadcast,intentFilter);
        }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainActivity.unregisterReceiver(myBroadcast);
    }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            if(context instanceof MainActivity)
                this.mainActivity=(MainActivity) context;
        }

        @Override
        public void onclick(int position, MainActivity mainActivity) {
            MyListSong.MySong s1=adapter.list.get(position);
            for(int i=0;i<listSong.size();i++)
            {
                if(s1.getData().equals(listSong.get(i).getData()))
                {
                    vitri=i;
                    break;
                }
            }
            nghenhac(vitri,mainActivity);
            play.setVisibility(ImageButton.VISIBLE);
            pause.setVisibility(ImageButton.INVISIBLE);
        }
        @Override
        public void onLongclick(int position, MainActivity mainActivity) {

            try {
                play.setVisibility(ImageButton.INVISIBLE);
                pause.setVisibility(ImageButton.VISIBLE);
                MyListSong.MySong s1 = adapter.list.get(position);
                for (int i = 0; i < listSong.size(); i++) {
                    if (s1.getData().equals(listSong.get(i).getData())) {
                        position = i;
                        break;
                    }
                }
                FragmentManager fm = getFragmentManager();
                DialogFragment df = DeleteDialog.newInstance(position);
                df.show(fm, "Confirm delete!");
            }catch (Exception e)
            {}
        }

        public void nghenhac(int position,Context mainActivity){
            if(vitri!=-1)
            {
                Intent intent=new Intent();
                intent.setAction(MyService.ACTION_CHOOSE_PLAY);
                intent.putExtra("vi tri",position);
                mainActivity.startService(intent);
            }
            else Toast.makeText(mainActivity.getApplicationContext(),"choose your song,please!",Toast.LENGTH_LONG).show();
        }
        public void dungnhac(MainActivity mainActivity){
            Intent myIntent =new Intent(mainActivity,MyService.class);

            mainActivity.stopService(myIntent);
        }
        public void updateProgress(){
            hander.postDelayed(run,100);
        }
        private Runnable run=new Runnable() {
            @Override
            public void run() {
//            Toast.makeText(getContext(),"a",Toast.LENGTH_SHORT).show();
                song=MyService.getMediaPlayer();
                if(song!=null)
                {
                    if(song.isPlaying())
                    {
                        play.setVisibility(ImageButton.VISIBLE);
                        pause.setVisibility(ImageButton.INVISIBLE);
                    }else
                    {

                        play.setVisibility(ImageButton.INVISIBLE);
                        pause.setVisibility(ImageButton.VISIBLE);
                    }
                    long current=song.getCurrentPosition();
                    long total=song.getDuration();
                    timeNow.setText(util.showTime(current));
                    timeTotal.setText(util.showTime(total));
                    nowPlaySong.setText(listSong.get(vitri).getTitle());
                    nowPlayArtist.setText(listSong.get(vitri).getArtist());
                    seekBar.setProgress(util.getProgressPercentage(current,total));
                }
                hander.postDelayed(this,100);
            }
        };


        class  MyBroadcast extends BroadcastReceiver{
            @Override
            public void onReceive(Context context, Intent intent) {
                String s=intent.getAction();
                if(s.equals(MyService.ACTION_MOVE_SONG)){
                    vitri=intent.getIntExtra("vitri",-1);
                    Toast.makeText(getContext(),Integer.toString(vitri),Toast.LENGTH_SHORT).show();
                    Log.i("vitri:  ",String.valueOf(vitri));
                }
            }
        }
        public static int getSizeList(){
            return listSong.size();
        }
        public void  delListSong(int position)
        {
            if(position<vitri){
                if(vitri>0)
                     vitri=vitri-1;
            }
            MyListSong.listSong.remove(position);//xoa list chinh
            if(textSearch!=null) tim(textSearch); //xoa xong thi tim lai beus dang o adapter sau khi tim
            else adapter.notifyDataSetChanged();

        }
        public int getVitri(){return vitri;}
        public static SongAdapter getAdapter()
        {return adapter;}
        public static ArrayList<MyListSong.MySong> getList()
        {return listSong;}
        public void setVitri(int vt)
        {vitri=vt;}
    }



