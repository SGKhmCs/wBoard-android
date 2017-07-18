package ua.tsisar.wboard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lucasurbas.listitemview.ListItemView;

import java.util.List;

import ua.tsisar.wboard.R;
import ua.tsisar.wboard.dto.BoardDTO;


public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.boardViewHolder> {

    private final LayoutInflater inflater;

    private List<BoardDTO> list;

    public BoardAdapter(Context context, List<BoardDTO> list){
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public BoardDTO getBoardDTO(int position){
        return list.get(position);
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

        BoardDTO boardDTO = list.get(position);

        //TODO add owner, date etc
        holder.listItemView.setTitle(boardDTO.getName());
        holder.listItemView.setSubtitle((boardDTO.getPub()?"Board is public. ":"")
                + "Created by " + boardDTO.getCreatedBy() + ".");
    }

    @Override
    public int getItemCount() {
        if(list == null) {
            return 0;
        }else{
            return list.size();
        }
    }

    public class boardViewHolder extends RecyclerView.ViewHolder {

        ListItemView listItemView;

        public boardViewHolder(View itemView) {

            super(itemView);
            listItemView = (ListItemView) itemView.findViewById(R.id.board_list_item_view);
        }
    }
}