package rimoka.com.navigationgoogleplay;
        import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by HOARIMOKA on 3/28/2017.
 */

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistHolder> {

    ArrayList<ArtistFragment.Artist> list;
    MainActivity context;
    private ListenSong listenSong;
    LayoutInflater inflater;
    public ArtistAdapter(ArrayList<ArtistFragment.Artist> list, MainActivity context, ListenSong listen) {
        this.list = list;
        this.context = context;
        listenSong=listen;
    }

    @Override
    public ArtistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.artist_item,parent,false);
        return new ArtistHolder(v);
    }

    @Override
    public void onBindViewHolder(ArtistHolder holder, final int position) {
        holder.TenNghesy.setText(list.get(position).getArtist());
        holder.Soluongbai.setText(String.valueOf(list.get(position).getCount()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenSong.onclick(position, context);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    protected class ArtistHolder extends RecyclerView.ViewHolder{
        private TextView TenNghesy;
        private TextView Soluongbai;
        public ArtistHolder(View itemView) {
            super(itemView);
            TenNghesy=(TextView) itemView.findViewById(R.id.ten_nghe_sy);
            Soluongbai=(TextView) itemView.findViewById(R.id.count_bai_hat);
        }
    }

}
