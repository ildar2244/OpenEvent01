package ru.javaapp.openevent01.activities;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.support.design.widget.NavigationView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.javaapp.openevent01.R;
import ru.javaapp.openevent01.adapters.EventsAdapter;
import ru.javaapp.openevent01.dao.AllEvents;
import ru.javaapp.openevent01.fragments.EventListFragment;


public class MainActivity extends AppCompatActivity {

    private ListView lv_events;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    //private String jsonResult;
    //private String url = "http://javaapp.ru/select_events_from_Events_table.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //lv_events = (ListView)findViewById(R.id.lv_events);
        //mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager == null) {
            setupViewPager(viewPager);
        }

        //addDrawerItems();
        //setupDrawer();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);



       //new JsonReadTask().execute();
    }

    // Async Task to access the web
    /*private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            try {
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();
            }

            catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            }

            catch (IOException e) {
                // e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Error..." + e.toString(), Toast.LENGTH_LONG).show();
            }
            return answer;
        }

        protected void onPostExecute(String result) {

            ListDrwaer();
        }

    }*/



    // build hash set for list view
    /*public void ListDrwaer() {
        AllEvents event;
        ArrayList<AllEvents> eventsList = new ArrayList<AllEvents>();

        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("allEvents_info");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                event = new AllEvents();
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                //event.setCategoryId(Integer.parseInt(jsonChildNode.optString("categoryId")));
                //event.setPlaceId(Integer.parseInt(jsonChildNode.optString("placeId")));
               // event.setManagerId(Integer.parseInt(jsonChildNode.optString("managerId")));
                //event.setName(jsonChildNode.optString("name"));
                event.setDate(jsonChildNode.optString("data"));
                event.setTime(jsonChildNode.optString("vremya"));
                event.setDescription(jsonChildNode.optString("description"));
                //event.setCoastId(Integer.parseInt(jsonChildNode.optString("coast")));
                //event.setBlocked(Integer.parseInt(jsonChildNode.optString("blocked")));

                eventsList.add(event);
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }

        EventsAdapter eventsAdapter = new EventsAdapter(MainActivity.this, eventsList);
        lv_events.setAdapter(eventsAdapter);
    }*/


    /*private void addDrawerItems() {
        String[] osArray = { "Ночная жизнь", "Театры", "Спорт", "Хуйня какая-то", "Херня" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

    }*/

    /*private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {


            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Категории!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }


            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }*/

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
        switch (item.getItemId()) {

            case android.R.id.home:
            {
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new EventListFragment(), "Category 1");
        //adapter.addFragment(new CheeseListFragment(), "Category 2");
        //adapter.addFragment(new CheeseListFragment(), "Category 3");
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
                        Adapter adapter = new Adapter(getSupportFragmentManager());

                        switch (menuItem.getItemId()) {
                            case R.id.nav_home: {
                                adapter.addFragment(new EventListFragment(), "Category 1");
                                viewPager.setAdapter(adapter);
                                menuItem.setChecked(true);
                                mDrawerLayout.closeDrawers();
                                return true;
                            }
                            case R.id.nav_messages: {
                                adapter.addFragment(new EventListFragment(), "Category 2");
                                viewPager.setAdapter(adapter);
                                menuItem.setChecked(true);
                                mDrawerLayout.closeDrawers();
                                return true;
                            }
                            case R.id.nav_friends: {
                                adapter.addFragment(new EventListFragment(), "Category 3");
                                viewPager.setAdapter(adapter);
                                menuItem.setChecked(true);
                                mDrawerLayout.closeDrawers();
                                return true;
                            }
                        }

                /*menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();*/
                        return true;
                    }
                });
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
