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

import ua.tsisar.wboard.activity.base.BoardActivityBase;
import ua.tsisar.wboard.dto.BoardDTO;
import ua.tsisar.wboard.service.BoardService;
import ua.tsisar.wboard.R;


public class BoardsActivity extends BoardActivityBase {

    private static final String TAG = "myLogs";

    private BoardService boardService;

    private static final int PAGE = 0;
    private static final int SIZE = 99;
    private static final String SORT = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boards);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boardService = new  BoardService(this);
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
                boardService.searchBoards(PAGE, SIZE, query, SORT);
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
    public void onSearchBoardsSuccess(List<BoardDTO> list) {
        Log.d(TAG, "onSearchBoardsSuccess: " + list);
    }
}
