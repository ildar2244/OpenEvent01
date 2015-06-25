package ru.javaapp.openevent01.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ru.javaapp.openevent01.R;
import ru.javaapp.openevent01.dao.AllEvents;

/**
 * Created by User on 23.06.2015.
 */
public class EventsAdapter extends ArrayAdapter {

    private AllEvents[] allEvents;
    private Context context;

    public EventsAdapter(Context context, AllEvents[] objects) {
        super(context, -1);
        allEvents = objects;
        this.context = context;
    }

    @Override
    public int getCount() {
        return allEvents.length;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Получим inflater который умеет из xml делать View
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate как раз это и делает
        View v = inflater.inflate(R.layout.item_event,null);


        //Найдем элементы на вьюшке которые нужно заполнить данными
        TextView date = (TextView)v.findViewById(R.id.txt_date);
        TextView time = (TextView)v.findViewById(R.id.txt_time);
        TextView description = (TextView) v.findViewById(R.id.txt_descriptionn);

        //По позиции строки получим данные из массива
        AllEvents events = allEvents[position];

        //Заполним элементы данными
        date.setText(events.getDate());
        time.setText(events.getTime());
        description.setText(events.getDescription());

        //Возвратим получившуюся вьюшку
        return v;
    }

}
