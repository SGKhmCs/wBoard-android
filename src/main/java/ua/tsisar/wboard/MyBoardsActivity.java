package ua.tsisar.wboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MyBoardsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_boards);

        BoardService boardService = new  BoardService(this);
        boardService.getAllBoards(0, 3, "name");
        boardService.searchBoards(0, 10, "brd2", "name");
    }
}
