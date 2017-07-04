package ua.tsisar.wboard.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import retrofit2.Response;
import ua.tsisar.wboard.activity.base.BoardActivityBase;
import ua.tsisar.wboard.service.BoardService;
import ua.tsisar.wboard.dto.BoardDTO;
import ua.tsisar.wboard.R;

public class BoardsActivity extends BoardActivityBase {

    private static final int RESPONSE_OK = 200;
    private static final String TAG = "myLogs";

    private BoardService boardService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boards);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boardService = new  BoardService(this);
        boardService.getAllBoards(0, 3, "name");
        boardService.searchBoards(0, 10, "brd2", "name");
        boardService.getBoard(951);
        BoardDTO boardDTO = new BoardDTO(952, "brdUUUU", true);
        boardService.updateBoard(boardDTO);
        String s = boardDTO.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if(null!=searchManager ) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: " + query);
                boardService.searchBoards(0, 99, query, "id");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange: " + newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onCreateBoardResponse(Response<BoardDTO> response) {
        super.onCreateBoardResponse(response);
    }

    @Override
    public void onGetAllBoardsResponse(Response<List<BoardDTO>> response) {
        if(response.code() == RESPONSE_OK){
            List list = response.body();
            return;
        }
        super.onGetAllBoardsResponse(response);
    }

    @Override
    public void onSearchBoardsResponse(Response<List<BoardDTO>> response) {
        if(response.code() == RESPONSE_OK){
            List list = response.body();
            Log.d(TAG, "onSearchBoardsResponse: " + list);
            return;
        }
        super.onSearchBoardsResponse(response);
    }
}
