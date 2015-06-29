package ru.javaapp.openevent01.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.javaapp.openevent01.R;

/**
 * Created by User on 26.06.2015.
 */
public class AddEventActivity extends Activity {

    private Spinner category_spinner;
    private Button add_btn;
    private EditText youEventName_et, youTime_et, youDate_et, youDescription_et;
    Random r;

    ArrayAdapter<String> categoryAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event_activity);

        add_btn = (Button)findViewById(R.id.add_btn);
        category_spinner= (Spinner)findViewById(R.id.categ_spinner);
        youEventName_et = (EditText)findViewById(R.id.youEventName_editText);
        youTime_et = (EditText)findViewById(R.id.youTime_editText);
        youDate_et = (EditText)findViewById(R.id.youDate_editText);
        youDescription_et = (EditText)findViewById(R.id.youDescription_editText);
        category_spinner.setAdapter(fillCategorySpinner());

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFillAllFields()) {
                    addEventToDb();
                }
                else {
                    Toast.makeText(AddEventActivity.this, R.string.notFillFields, Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }
    boolean checkFillAllFields(){
        if(youEventName_et.getText().toString().trim().length() == 0 ||
                youTime_et.getText().toString().trim().length() == 0 ||
                youDate_et.getText().toString().trim().length() == 0 ||
                youDescription_et.getText().toString().trim().length() == 0){
            return false;
        }
        else{
            return true;
        }
    }

    private void addEventToDb(){
        InsertData task1 = new InsertData();
        task1.execute(new String[]{"http://javaapp.ru/insert_event_to_Events_table.php"});
    }

     private ArrayAdapter fillCategorySpinner(){
         String[] categs = getResources().getStringArray(R.array.category_list);
         categoryAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, categs);
         categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         return categoryAdapter;
     }

    private class InsertData extends AsyncTask<String, Void, Boolean> {
        ProgressDialog dialog = new ProgressDialog(AddEventActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Sending Data...");
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            r = new Random();

            for(String url : urls) {
                try {
                    ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("id", Integer.toString(r.nextInt(1000))));
                    pairs.add(new BasicNameValuePair("categoryId", Integer.toString(r.nextInt(1000))));
                    pairs.add(new BasicNameValuePair("placeId", Integer.toString(r.nextInt(1000))));
                    pairs.add(new BasicNameValuePair("managerId", Integer.toString(r.nextInt(1000))));
                    pairs.add(new BasicNameValuePair("name", youEventName_et.getText().toString()));
                    pairs.add(new BasicNameValuePair("data", youDate_et.getText().toString()));
                    pairs.add(new BasicNameValuePair("vremya", youTime_et.getText().toString()));
                    pairs.add(new BasicNameValuePair("description", youDescription_et.getText().toString()));
                    pairs.add(new BasicNameValuePair("coast", "1"));
                    pairs.add(new BasicNameValuePair("blocked", "0"));

                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);
                    post.setEntity(new UrlEncodedFormEntity(pairs));
                    HttpResponse responce = client.execute(post);
                } catch (IOException e) {
                    Toast.makeText(AddEventActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    return  false;
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result == true){
                Toast.makeText(AddEventActivity.this, "Insert success", Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(AddEventActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
            dialog.dismiss();
        }
    }
}
