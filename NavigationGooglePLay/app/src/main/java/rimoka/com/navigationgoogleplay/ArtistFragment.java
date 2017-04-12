package rimoka.com.navigationgoogleplay;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class ArtistFragment extends Fragment implements ListenSong {
    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    ArrayList<MyListSong.MySong> listSong;
    static ArrayList<Artist> listArtist=new ArrayList<>();
    ArtistAdapter adapter;

    public ArtistFragment() {
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
        View v= inflater.inflate(R.layout.fragment_one, container, false);
        recyclerView=(RecyclerView) v.findViewById(R.id.list_nghe_sy);
         listSong = MyListSong.listSong;
        ArrayList<MyListSong.MySong> listSongTam=new ArrayList<MyListSong.MySong>();
        for (int i = 0; i < listSong.size(); i++)
            listSongTam.add(listSong.get(i));
        listArtist=new ArrayList<>();
        //new l?i d? tránh add ti?p vào listArtist(vì static)
        if(listSongTam!=null) {
            for (int i =0; i < listSongTam.size(); i++) {
                listArtist.add(new Artist(listSongTam.get(i).getArtist(),1," "));
                for (int j = i+1; j < listSongTam.size(); j++)
                    if (listSongTam.get(j).getArtist().equals(listArtist.get(i).getArtist())) {
                        listArtist.get(i).setCount(listArtist.get(i).getCount() + 1);
                        listSongTam.remove(j);
                        --j;
                        // n?u remove thì --j
                    }
            }
        }
        //m?i l?n vào fragment Artist là add l?i element vao listArtist d? c?p nh?t khi có xóa bài hát
        adapter = new ArtistAdapter(listArtist, this.mainActivity, this);
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
        adapter = new ArtistAdapter(listArtist, this.mainActivity, this);
        adapter.getFilter().filter(text.trim());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity)
            this.mainActivity=(MainActivity) context;
    }

    @Override
    public void onclick(int position, MainActivity mainActivity) {


    }
    public static ArrayList<Artist> getListArtist()
    {return listArtist;}
    protected class Artist {
        String artist;
        int count;
        String image;

        public Artist(String artist, int count, String image) {
            this.artist = artist;
            this.count = count;
            this.image = image;
        }

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
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
