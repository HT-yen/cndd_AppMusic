package rimoka.com.navigationgoogleplay;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import rimoka.com.navigationgoogleplay.SongAdapter.SongHolder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by HOARIMOKA on 3/28/2017.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> implements Filterable {

    ArrayList<MyListSong.MySong> list;
    MainActivity context;
    private ListenSong listenSong;
    private ListenLongSong longlistenSong;
    LayoutInflater inflater;
    private ItemFilter mFilter=new ItemFilter();
    public SongAdapter(ArrayList<MyListSong.MySong> list, MainActivity context,ListenSong listen,ListenLongSong longlisten) {
        this.list = list;
        this.context = context;
         listenSong=listen;
        longlistenSong=longlisten;
    }

    @Override
    public SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.song_item,parent,false);
        return new SongHolder(v);
    }

    @Override
    public void onBindViewHolder(SongAdapter.SongHolder holder, final int position) {
        holder.baihat.setText(list.get(position).getTitle());
        holder.ngheSy.setText(list.get(position).getArtist());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenSong.onclick(position, context);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context,"xoa",Toast.LENGTH_SHORT).show();
                longlistenSong.onLongclick(position,context);
                return true;
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
        if(mFilter==null) mFilter=new ItemFilter();
        return mFilter;
    }
    private class ItemFilter extends Filter {
        FilterResults result = new FilterResults();

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().trim();
            if (!TextUtils.isEmpty(filterString)) {
                ArrayList<MyListSong.MySong> nlist = new ArrayList<MyListSong.MySong> ();
                for (MyListSong.MySong item : MyListSong.listSong) {
                    if (item.getTitle().toString().contains(filterString))
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
                list = (ArrayList<MyListSong.MySong>) result.values;
                notifyDataSetChanged();
            }
        }
    }


    protected class SongHolder extends RecyclerView.ViewHolder{
        private TextView baihat;
        private TextView ngheSy;
        public SongHolder(View itemView) {
            super(itemView);
            baihat=(TextView) itemView.findViewById(R.id.baihat);
            ngheSy=(TextView) itemView.findViewById(R.id.nghesy);
        }
    }

}
