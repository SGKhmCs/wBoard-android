package ua.tsisar.wboard.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.github.mrengineer13.snackbar.SnackBar;

import java.util.List;

import ua.tsisar.wboard.BoardAdapter;
import ua.tsisar.wboard.activity.base.BoardActivityBase;
import ua.tsisar.wboard.dialog.CreateBoardDialog;
import ua.tsisar.wboard.dialog.CreateBoardDialog.CreateBoardDialogListener;
import ua.tsisar.wboard.dto.BoardDTO;
import ua.tsisar.wboard.rest.helper.BoardHelper;
import ua.tsisar.wboard.R;


public class BoardsActivity extends BoardActivityBase implements CreateBoardDialogListener {

    private static final String TAG = "myLogs";

    private FloatingActionButton addBoardFAB;
    private BoardHelper boardHelper;

    private static final int PAGE = 0;
    private static final int SIZE = 99;
    private static final String SORT = "id";

    private AlphaAnimation buttonClick;

    private BoardAdapter boardAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boards);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonClick = new AlphaAnimation(1F, 0.8F);

        addBoardFAB = (FloatingActionButton) findViewById(R.id.add_board_fab);
        addBoardFAB.setOnClickListener((View v) -> {
            CreateBoardDialog createBoardDialog = new CreateBoardDialog();
            createBoardDialog.show(getSupportFragmentManager(), "boardNameDialog");
            addBoardFAB.startAnimation(buttonClick);
        });

        boardHelper = new BoardHelper(this);
        boardHelper.getAllBoards(PAGE, SIZE, SORT);

        recyclerView = (RecyclerView) findViewById(R.id.board_recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        boardHelper.dispose();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                boardHelper.searchBoards(PAGE, SIZE, query, SORT);
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
    public void onGetAllBoardsSuccess(List<BoardDTO> list) {
        Log.d(TAG, "onGetAllBoardsSuccess: " + list);
        boardAdapter = new BoardAdapter(this, list);
        recyclerView.setAdapter(boardAdapter);
    }

    @Override
    public void onSearchBoardsSuccess(List<BoardDTO> list) {
        Log.d(TAG, "onSearchBoardsSuccess: " + list);
        boardAdapter = new BoardAdapter(this, list);
        recyclerView.setAdapter(boardAdapter);
    }

    @Override
    public void onCreateBoard(BoardDTO boardDTO) {
        boardHelper.createBoard(boardDTO);
    }

    @Override
    public void onCreateBoardSuccess(BoardDTO boardDTO) {
        new SnackBar.Builder(this)
                .withMessage("Your board created.")
                .withStyle(SnackBar.Style.INFO)
                .show();
    }
}
