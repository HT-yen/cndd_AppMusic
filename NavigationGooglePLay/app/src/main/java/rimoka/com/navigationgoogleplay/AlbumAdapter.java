package rimoka.com.navigationgoogleplay;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by HOARIMOKA on 3/28/2017.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder>implements Filterable {

    ArrayList<AlbumFragment.Album> list;
    MainActivity context;
    private ListenSong listenSong;
    LayoutInflater inflater;
    private AlbumAdapter.ItemFilter mFilter=new AlbumAdapter.ItemFilter();
    public AlbumAdapter(ArrayList<AlbumFragment.Album> list, MainActivity context, ListenSong listen) {
        this.list = list;
        this.context = context;
        listenSong=listen;
    }

    @Override
    public AlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.album_item,parent,false);
        return new AlbumHolder(v);
    }

    @Override
    public void onBindViewHolder(AlbumHolder holder, final int position) {
        holder.TenAlbum.setText(list.get(position).getAlbum());
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
    @Override
    public Filter getFilter() {
        // TODO Auto-generated method stub
        if(mFilter==null) mFilter=new AlbumAdapter.ItemFilter();
        return mFilter;
    }
    private class ItemFilter extends Filter {
        FilterResults result = new FilterResults();

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().trim();
            if (!TextUtils.isEmpty(filterString)) {
                ArrayList<AlbumFragment.Album> nlist = new ArrayList<AlbumFragment.Album> ();
                for (AlbumFragment.Album item : AlbumFragment.listAlbum) {
                    if (item.getAlbum().toString().contains(filterString))
                        nlist.add(item);
                }
                result.values = nlist;
                result.count = nlist.size();
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            if (results != null && results.count > 0) {
                list = (ArrayList<AlbumFragment.Album>) result.values;
                notifyDataSetChanged();
            }
        }
    }

    protected class AlbumHolder extends RecyclerView.ViewHolder{
        private TextView TenAlbum;
        private TextView Soluongbai;
        public AlbumHolder(View itemView) {
            super(itemView);
            TenAlbum=(TextView) itemView.findViewById(R.id.ten_album);
            Soluongbai=(TextView) itemView.findViewById(R.id.count_bh);
        }
    }

}
