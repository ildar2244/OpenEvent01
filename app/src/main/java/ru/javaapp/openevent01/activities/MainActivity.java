package ru.javaapp.openevent01.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import ru.javaapp.openevent01.R;
import ru.javaapp.openevent01.adapters.EventsAdapter;
import ru.javaapp.openevent01.dao.AllEvents;


public class MainActivity extends ActionBarActivity {

    private ListView lv_events;
    private AllEvents[] allEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
