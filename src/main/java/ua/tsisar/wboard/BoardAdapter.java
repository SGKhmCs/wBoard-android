package ua.tsisar.wboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ua.tsisar.wboard.dto.BoardDTO;


public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.boardViewHolder> {

    private final LayoutInflater inflater;

    private List<BoardDTO> list;

    public BoardAdapter(Context context, List<BoardDTO> list){
        inflater = LayoutInflater.from(context);
        this.list = list;
    }
    @Override
    public boardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_board_list, parent, false);
        boardViewHolder holder = new boardViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(boardViewHolder holder, int position) {
        if(list == null)
            return;

        //TODO add owner, date etc
        holder.name.setText(list.get(position).getName());
        holder.desc.setText("Created by User, 01.07.2017");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class boardViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView desc;

        public boardViewHolder(View itemView) {

            super(itemView);
            name = (TextView) itemView.findViewById(R.id.board_name_textView);
            desc = (TextView) itemView.findViewById(R.id.board_desc_textView);
        }
    }
}