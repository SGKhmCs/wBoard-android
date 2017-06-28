package ua.tsisar.wboard;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Activity activity = this;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getUser();
    }

    @Override
    public void onBackPressed() {
        DialogFragment dlg_exit = new DialogSignOut();
        dlg_exit.show(getSupportFragmentManager(), "signOut");
    }


    private void getUser(){
        Call<User> userCall = App.getApi().getAccount(App.getToken().getIdTokenEx());
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                switch (response.code()){
                    case 200:
                        buildDrawer(response.body());
                        break;
                    default:
                        Message.makeText(activity, "Error",
                                response.message() + ", status code: " + response.code()).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Message.makeText(activity, "Error", throwable.getMessage()).show();
            }
        });
    }

    private Drawer buildDrawer(User user){
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName("Home")
                .withIcon(GoogleMaterial.Icon.gmd_wb_sunny);

        SecondaryDrawerItem item2 = new SecondaryDrawerItem()
                .withIdentifier(2)
                .withName("Sett")
                .withIcon(
                        new IconicsDrawable(this)
                                .icon(GoogleMaterial.Icon.gmd_3d_rotation)
                                .color(Color.RED)
                                .sizeDp(24)
                );

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withAccountHeader(getAccount(user))
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new SecondaryDrawerItem().withName("SecondaryDrawerItem")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        int id = position;
                        return true;
                    }
                })
                .build();
        return result;
    }

    private AccountHeader getAccount(User user){
        // Create the AccountHeader
        String name = "";
        String email = "";
        Drawable icon = null;

        if(user != null) {
            name = user.getFirstName() + " " + user.getLastName();
            email = user.getEmail();
            icon = getIcon(user.getImageUrl());
        }

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.background_blue)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(name)
                                .withEmail(email)
                                .withIcon(icon)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        return headerResult;
    }

    private Drawable getIcon(String url){
        Drawable icon = drawableFromUrl(url);

        if(icon == null){
            icon = new IconicsDrawable(this)
                    .icon(GoogleMaterial.Icon.gmd_account_circle)
                    .color(Color.GRAY)
                    .sizeDp(64);
        }
        return icon;
    }

    private Drawable drawableFromUrl(String url){
        return null;
    }

//    private class drawableFromUrl extends AsyncTask<String, Void, Bitmap> {
//        Drawable icon;
//
//        public drawableFromUrl(Drawable icon){
//            this.icon = icon;
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap mIcon11 = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return mIcon11;
//        }
//
//        protected void onPostExecute(Bitmap bitmap) {
//            icon = new BitmapDrawable(getResources(), bitmap);
//        }
//    }
}