package ru.javaapp.openevent01.fragments;

import android.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;
import java.util.Random;

import ru.javaapp.openevent01.R;

import ru.javaapp.openevent01.activities.EventDetailActivity;
import ru.javaapp.openevent01.adapters.EventsAdapter;
import ru.javaapp.openevent01.dao.AllEvents;

/**
 * Created by User on 26.06.2015.
 */
public class EventListFragment extends android.support.v4.app.Fragment {



    private RecyclerView recyclerView;
    private String jsonResult;
    private String url = "http://javaapp.ru/select_events_from_Events_table.php";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_cheese_list, container, false);
        //setupRecyclerView(rv);

        new JsonReadTask().execute();

        return rv;
    }

    /*private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(),
                getSublist());
    }*/

    private class JsonReadTask extends AsyncTask<String, Void, String> {
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
                Toast.makeText(getActivity(),
                        "Error..." + e.toString(), Toast.LENGTH_LONG).show();
            }
            return answer;
        }

        protected void onPostExecute(String result) {

            getSublist();
        }

    }

    private void getSublist() {
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
                //event.setDate(jsonChildNode.optString("data"));
                //event.setTime(jsonChildNode.optString("vremya"));
                event.setDescription(jsonChildNode.optString("description"));
                //event.setCoastId(Integer.parseInt(jsonChildNode.optString("coast")));
                //event.setBlocked(Integer.parseInt(jsonChildNode.optString("blocked")));

                eventsList.add(event);
            }
        } catch (JSONException e) {
            Toast.makeText(getActivity(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();
        }

        recyclerView = new RecyclerView(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        SimpleStringRecyclerViewAdapter eventAdapter = new SimpleStringRecyclerViewAdapter(getActivity(),
                eventsList);
        recyclerView.setAdapter(eventAdapter);

    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<AllEvents> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            //private List<AllEvents> allEvents;
            public AllEvents mBoundString;

            public final View mView;
            //public final ImageView mImageView;
            public final TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                //mImageView = (ImageView) view.findViewById(R.id.avatar);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
                //mBoundString = allEvents.get(getPosition());
                mTextView.setText(mBoundString.getDescription());

            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public AllEvents getValueAt(int position) {
            return mValues.get(position);
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<AllEvents> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mBoundString = mValues.get(position);
            holder.mTextView.setText((CharSequence) mValues.get(position));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, EventDetailActivity.class);
                    intent.putExtra(EventDetailActivity.EXTRA_NAME, (Parcelable) holder.mBoundString);

                    context.startActivity(intent);
                }
            });

            /*Glide.with(holder.mImageView.getContext())
                    .load(Cheeses.getRandomCheeseDrawable())
                    .fitCenter()
                    .into(holder.mImageView);*/
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
