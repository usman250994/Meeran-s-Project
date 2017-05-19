package com.example.root.meeransunday;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import layout.BlankFragment;
import layout.message;
import layout.scan;

import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
public  int check=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        message frags = new message();
        fragmentTransaction.add(R.id.content_main,frags,"text");
        fragmentTransaction.commit();
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        check+=1;
    }
    public void message(View v)
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        message sms = new message();
fragmentTransaction.replace(R.id.content_main,sms,"text");
fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    public void scan(View v)
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        scan scan = new scan();


            fragmentTransaction.replace(R.id.content_main, scan, "scan");
        fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


    }
public void loadooncreate(){
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    message frags = new message();
    fragmentTransaction.add(R.id.content_main,frags,"text");
    fragmentTransaction.commit();
    check+=1;
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar1);
        toolbar2.inflateMenu(R.menu.notify);

        toolbar2.getMenu().findItem(R.id.notifications).setIcon(buildCounterDrawable(2, R.drawable.notification));

        toolbar2.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem arg0) {
                if(arg0.getItemId() == R.id.notifications){
                    arg0.setIcon(buildCounterDrawable(0, R.drawable.notification));
                    ((RelativeLayout)findViewById(R.id.content_main)).removeAllViews();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    BlankFragment  frag = new BlankFragment();
                    fragmentTransaction.add(R.id.content_main, frag, "first");
                    fragmentTransaction.commit();
 }
                return false;
            }
        });

         check=1;
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //code to reset view
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if(fragmentManager.findFragmentByTag("first")!=null)
            {

                fragmentTransaction.remove(fragmentManager.findFragmentByTag("first"));
                fragmentTransaction.commit();
                loadooncreate();
            }
            else
            {

                finish();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.counter_menuitem_layout, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        } else {
//            TextView textView = (TextView) view.findViewById(count);
//            textView.setText("1");
        }
        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

@Override
    public  boolean onPrepareOptionsMenu(Menu menu){
    if(check>3) {
       Toast.makeText(this.getApplicationContext(),"sdsdsdfdsfsd",Toast.LENGTH_SHORT).show();
        MenuItem item = menu.getItem(R.id.notifications);
        item.setIcon(buildCounterDrawable(2, R.drawable.notification));

    }
//MenuItem item = (MenuItem)findViewById(R.id.notifications);
//    item.setIcon(buildCounterDrawable(2, R.drawable.notification));

    return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.Home)
        {
   //close current fragment;
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if(fragmentManager.findFragmentByTag("first")!=null)

                fragmentTransaction.remove(fragmentManager.findFragmentByTag("first"));
            fragmentTransaction.commit();
loadooncreate();

        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}