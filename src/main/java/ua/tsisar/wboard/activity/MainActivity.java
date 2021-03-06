package ua.tsisar.wboard.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.mrengineer13.snackbar.SnackBar;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import ua.tsisar.wboard.activity.base.MainActivityBase;
import ua.tsisar.wboard.dialog.CreateBoardDialog;
import ua.tsisar.wboard.dto.RoleToolsDTO;
import ua.tsisar.wboard.rest.helper.AccountHelper;
import ua.tsisar.wboard.rest.helper.BoardHelper;
import ua.tsisar.wboard.dto.BoardDTO;
import ua.tsisar.wboard.dto.UserDTO;
import ua.tsisar.wboard.dialog.SignOutDialog;
import ua.tsisar.wboard.R;
import ua.tsisar.wboard.dialog.CreateBoardDialog.CreateBoardDialogListener;

public class MainActivity extends MainActivityBase implements CreateBoardDialogListener {

    private static final int REQUEST_CODE_USER_SETTINGS = 3;
    private static final int REQUEST_CODE_BOARDS = 4;
    private static final int RESULT_SIGN_OUT = -2;
    private static final String TAG = "myLogs";

    private Toolbar toolbar;
    private Drawer drawer;

    private AccountHelper accountHelper;
    private BoardHelper boardHelper;

    public Context getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        accountHelper = new AccountHelper(this);
        accountHelper.getAccount();

        boardHelper = new BoardHelper(this);

        drawerImageLoader();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accountHelper.dispose();
        boardHelper.dispose();
    }

    @Override
    public void onBackPressed() {
        if(drawer != null && drawer.isDrawerOpen()){
            drawer.closeDrawer();
        }else {
            dialogSignOut();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_USER_SETTINGS:
                if(resultCode == RESULT_OK){
                    // TODO костиль
                    accountHelper.getAccount();
                }
                break;
            case REQUEST_CODE_BOARDS:
                if(resultCode == RESULT_OK){
                    Log.d(TAG, "onActivityResult board id: " + data.getIntExtra("boardId", 0));
                }
                break;
        }

    }

    @Override
    public void onCreateBoard(BoardDTO boardDTO) {
        boardHelper.createBoard(boardDTO);
    }

    private void dialogSignOut(){
        DialogFragment dlg_exit = new SignOutDialog();
        dlg_exit.show(getSupportFragmentManager(), "dialogSignOut");
    }

    @Override
    public void onGetAccountSuccess(UserDTO userDTO) {
         drawer = buildDrawer(userDTO);
    }

    @Override
    public void onCreateBoardSuccess(BoardDTO boardDTO) {
        new SnackBar.Builder(this)
            .withMessage("Your board created.")
            .withStyle(SnackBar.Style.INFO)
            .show();
    }

    private void drawerImageLoader(){
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }

        });
    }

    private Drawer buildDrawer(UserDTO userDTO){
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem itemCreateBoard = new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName("Create board")
                .withIcon(GoogleMaterial.Icon.gmd_developer_board);

        PrimaryDrawerItem itemMyBoards = new PrimaryDrawerItem()
                .withIdentifier(2)
                .withName("My boards")
                .withIcon(GoogleMaterial.Icon.gmd_list);

        PrimaryDrawerItem itemUsers = new PrimaryDrawerItem()
                .withIdentifier(3)
                .withName("Users")
                .withIcon(GoogleMaterial.Icon.gmd_account_box);

        SecondaryDrawerItem itemSignOut = new SecondaryDrawerItem()
                .withIdentifier(4)
                .withName("Sign out")
                .withIcon(GoogleMaterial.Icon.gmd_exit_to_app);

        //create the drawer and remember the `Drawer` result object
        return new DrawerBuilder()
                .withAccountHeader(buildAccount(userDTO))
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        itemCreateBoard,
                        itemMyBoards,
                        itemUsers,
                        new DividerDrawerItem(),
                        itemSignOut
                )
                .withOnDrawerItemClickListener((View view, int position, IDrawerItem drawerItem) -> {
                    drawer.closeDrawer();
                    // do something with the clicked item :D
                        switch (position) {
                            case 1:
                                CreateBoardDialog createBoardDialog = new CreateBoardDialog();
                                createBoardDialog.show(getSupportFragmentManager(), "boardNameDialog");
                                break;
                            case 2:
                                Intent intent = new Intent(getActivity(), BoardsActivity.class);
                                startActivityForResult(intent, REQUEST_CODE_BOARDS);
                                break;
                            case 5:
                                setResult(RESULT_SIGN_OUT);
                                dialogSignOut();
                                break;
                        }
                        return true;
                })
                .build();
    }

    private AccountHeader buildAccount(UserDTO userDTO){
        // Create the AccountHeader
        if(userDTO == null) {
            return null;
        }

        ProfileDrawerItem profileDrawerItem = new ProfileDrawerItem()
                .withName(userDTO.getFirstName() + " " + userDTO.getLastName())
                .withEmail(userDTO.getEmail())
                .withIcon(userDTO.getImageUrl());

        return new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.background_blue)
                .addProfiles(profileDrawerItem)
                .withOnAccountHeaderListener((View view, IProfile profile, boolean currentProfile) -> {
                        Intent intent = new Intent(getActivity(), UserSettingsActivity.class);
                        startActivityForResult(intent, REQUEST_CODE_USER_SETTINGS);
                        return false;
                })
                .build();
    }
}