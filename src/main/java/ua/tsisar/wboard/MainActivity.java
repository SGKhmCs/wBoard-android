package ua.tsisar.wboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    private static final int REQUEST_CODE_USER_SETTINGS = 3;
    private static final int RESULT_SIGN_OUT = -2;

    private Toolbar toolbar;
    private Drawer drawer;

    public Context getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO не завантажує картинку з нету
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

        getUser();
    }

    @Override
    public void onBackPressed() {
        if(drawer != null && drawer.isDrawerOpen()){
            drawer.closeDrawer();
        }else {
            DialogFragment dlg_exit = new DialogSignOut();
            dlg_exit.show(getSupportFragmentManager(), "signOut");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_USER_SETTINGS:
                if(resultCode == RESULT_OK){
                    // TODO костиль
                    getUser();
                }
                break;
        }

    }

    private void getUser(){
        Call<User> userCall = App.getApi().getAccount(App.getToken().getIdTokenEx());
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                switch (response.code()){
                    case 200:
                        drawer = buildDrawer(response.body());
                        break;
                    default:
                        Message.makeText(getActivity(), "Error",
                                response.message() + ", status code: " + response.code()).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Message.makeText(getActivity(), "Error", throwable.getMessage()).show();
            }
        });
    }

    private Drawer buildDrawer(User user){
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
                .withAccountHeader(getAccount(user))
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        itemCreateBoard,
                        itemMyBoards,
                        itemUsers,
                        new DividerDrawerItem(),
                        itemSignOut
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        switch (position) {
                            case 5:
                                setResult(RESULT_SIGN_OUT);
                                finish();
                                break;
                        }
                        return true;
                    }
                })
                .build();
    }

    private AccountHeader getAccount(User user){
        // Create the AccountHeader
        String name = "";
        String email = "";
        String icon = "";

        if(user != null) {
            name = user.getFirstName() + " " + user.getLastName();
            email = user.getEmail();
            icon = user.getImageUrl();
        }

        ProfileDrawerItem profileDrawerItem = new ProfileDrawerItem()
                .withName(name)
                .withEmail(email)
                .withIcon(icon);

        return new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.background_blue)
                .addProfiles(profileDrawerItem)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        Intent intent = new Intent(getActivity(), UserSettingsActivity.class);
                        startActivityForResult(intent, REQUEST_CODE_USER_SETTINGS);

                        return false;
                    }
                })
                .build();
    }

}