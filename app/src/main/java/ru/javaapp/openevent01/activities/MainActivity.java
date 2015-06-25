package ru.javaapp.openevent01.activities;

import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import ru.javaapp.openevent01.R;
import ru.javaapp.openevent01.adapters.EventsAdapter;
import ru.javaapp.openevent01.dao.AllEvents;


public class MainActivity extends ActionBarActivity {

    private ListView lv_events;
    private AllEvents[] allEvents;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        AllEvents event1 = new AllEvents();
        event1.setTime("6:00");
        event1.setDate("21 февраля");
        event1.setDescription("Это крутое мероприятие");

        AllEvents event2 = new AllEvents();
        event2.setTime("7:00");
        event2.setDate("21 февраля");
        event2.setDescription("Так себе");

        AllEvents event3 = new AllEvents();
        event3.setTime("8:00");
        event3.setDate("21 февраля");
        event3.setDescription("Это крутое мероприятие");

        AllEvents event4 = new AllEvents();
        event4.setTime("9:00");
        event4.setDate("21 февраля");
        event4.setDescription("Это крутое мероприятие");

        AllEvents event5 = new AllEvents();
        event5.setTime("10:00");
        event5.setDate("21 февраля");
        event5.setDescription("Это крутое мероприятие");

        AllEvents event6 = new AllEvents();
        event6.setTime("11:00");
        event6.setDate("21 февраля");
        event6.setDescription("Это крутое мероприятие");


        allEvents = new AllEvents[] {event1, event2, event3, event4, event5, event6};

        EventsAdapter eventsAdapter = new EventsAdapter(this, allEvents);

        lv_events = (ListView)findViewById(R.id.lv_events);
        lv_events.setAdapter(eventsAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addDrawerItems() {
        String[] osArray = { "Ночная жизнь", "Театры", "Спорт", "Хуйня какая-то", "Херня" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Категории!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
