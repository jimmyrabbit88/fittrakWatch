package com.example.fittrack2;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.wear.widget.drawer.WearableActionDrawerView;
import androidx.wear.widget.drawer.WearableDrawerLayout;
import androidx.wear.widget.drawer.WearableNavigationDrawerView;

import com.example.fittrack2.ui.homepage.HomeFragment;
import com.example.fittrack2.ui.runpage.RunFragment;
import com.example.fittrack2.ui.testpage.TestFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        MenuItem.OnMenuItemClickListener {
    private WearableDrawerLayout wearableDrawerLayout;
    private WearableNavigationDrawerView wearableNavigationDrawer;
    private WearableActionDrawerView wearableActionDrawer;
    public ArrayList<AppPage> appPagesList;
    FragmentContainerView frag;
    FragmentManager fm;

    Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appPagesList = new ArrayList<>();
        appPagesList.add(new AppPage("Home", getDrawable(R.drawable.ic_baseline_home_24)));
        appPagesList.add(new AppPage("Runs", getDrawable(R.drawable.ic_baseline_directions_run_24)));
        appPagesList.add(new AppPage("Test", getDrawable(R.drawable.ic_baseline_watch_later_24)));

        frag = findViewById(R.id.contentFragmentArea);
        context = getApplicationContext();

        // Top navigation drawer
        wearableNavigationDrawer = findViewById(R.id.top_navigation_drawer);
        wearableNavigationDrawer.setAdapter(new NavAdapter(appPagesList));
        //wearableNavigationDrawer.getController().peekDrawer();
//        // Bottom action drawer
//        wearableActionDrawer = findViewById(R.id.bottom_action_drawer);
//        // Peeks action drawer on the bottom.
//        wearableActionDrawer.getController().peekDrawer();
        final Fragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragmentArea, new HomeFragment()).commit();


        wearableNavigationDrawer.addOnItemSelectedListener(new WearableNavigationDrawerView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int pos) {
               //Toast.makeText(context, appPagesList.get(pos).getTitle(), Toast.LENGTH_SHORT).show();
               switch(appPagesList.get(pos).getTitle()) {
                    case "Home":
                        //is home
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragmentArea, new HomeFragment()).commit();
                        break;
                   case "Runs":
                       getSupportFragmentManager().beginTransaction().replace(R.id.contentFragmentArea, new RunFragment()).commit();
                       break;
                   case "Test":
                       getSupportFragmentManager().beginTransaction().replace(R.id.contentFragmentArea, new TestFragment()).commit();
               }
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return false;
    }
}