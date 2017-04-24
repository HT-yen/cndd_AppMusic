package rimoka.com.navigationgoogleplay;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by HOARIMOKA on 3/23/2017.
 */

public class AlbumFragment extends Fragment implements ListenSong {
    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    ArrayList<MyListSong.MySong> listSong;
    static ArrayList<Album> listAlbum=new ArrayList<>();
    AlbumAdapter adapter;

    public AlbumFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_two, container, false);
        recyclerView=(RecyclerView) v.findViewById(R.id.list_album);
        listSong =ItemTwoFragment.getList();
        ArrayList<MyListSong.MySong> listSongTam=new ArrayList<MyListSong.MySong>();
        for (int i = 0; i < listSong.size(); i++)
            listSongTam.add(listSong.get(i));
        listAlbum=new ArrayList<>();
        //refesh lại để tránh add tiếp vào listAlbum trước đó(static)
        if(listSongTam!=null) {
            for (int i = 0; i < listSongTam.size(); i++) {
                listAlbum.add(new AlbumFragment.Album(listSongTam.get(i).getAlbum(), 1, " "));
                for (int j = i + 1; j < listSongTam.size(); j++)
                    if (listSongTam.get(j).getAlbum().equals(listAlbum.get(i).getAlbum())) {
                        listAlbum.get(i).setCount(listAlbum.get(i).getCount() + 1);
                        listSongTam.remove(j);
                        --j;
                        // nếu remove thì giảm j xuống để tí tăng lên j không đổi
                    }
            }
        }
        adapter = new AlbumAdapter(listAlbum, this.mainActivity, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.mainActivity,3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        MainActivity.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tim(newText.toString());
                return true;
            }
        });
        return v;

    }
    public void tim(String text) {
        adapter = new AlbumAdapter(listAlbum, this.mainActivity, this);
        adapter.getFilter().filter(text.trim());
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity)
            this.mainActivity=(MainActivity) context;
    }
    public static ArrayList<Album> getListAlbum()
    {return listAlbum;}

    @Override
    public void onclick(int position, MainActivity mainActivity) {
        String album=adapter.list.get(position).getAlbum();
        FragmentManager manager = mainActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MyListSong.setViewfollow("ALBUM");
        //set xem MyListSong khi lấy ra listSong sẽ hiển thị theo Album
        MyListSong.setStringcompare(album);
        mainActivity.startFragment(new ItemTwoFragment());
    }
    protected class Album {
        String album;
        int count;
        String image;

        public Album(String album, int count, String image) {
            this.album = album;
            this.count = count;
            this.image = image;
        }

        public String getAlbum() {
            return album;
        }

        public void setAlbum(String album) {
            this.album = album;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}

