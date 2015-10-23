package com.parse.starter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
  TextView un;
  ListView lv;
  List<ParseObject> ob;
  ArrayAdapter<String> adapter;
  ProgressDialog mProgressDialog;
  ParseQuery<ParseObject> query;
  ArrayList<String> list;
  TextView txt;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    un = (TextView) findViewById(R.id.mainUserId);
    un.setText(getIntent().getSerializableExtra("userName").toString().trim());

    list = new ArrayList<>();
    query = new ParseQuery<>("Post");
    txt = (TextView) findViewById(R.id.textView3);
    //arrayAdapter = new ArrayAdapter<Device>(this, android.R.layout.simple_list_item_1, deviceList);
    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
    lv = (ListView) findViewById(R.id.listView);

    ParseUser user = new ParseUser();

    Post post = new Post();
    post.setOwner(user.getCurrentUser());
    post.setDisplayName(user.getCurrentUser().getUsername());
    post.setVote1(2);
    byte[] yourBytes = new byte[0];
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutput out = null;
    try {
      out = new ObjectOutputStream(bos);
      out.writeObject(post);
       yourBytes = bos.toByteArray();

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (out != null) {
          out.close();
        }
      } catch (IOException ex) {
        // ignore close exception
      }
      try {
        bos.close();
      } catch (IOException ex) {
        // ignore close exception
      }
    }

    /**
    ParseObject upload = new ParseObject("sampleObject");

    upload.put("postTest", post);
    upload.put("num", 4);

    upload.saveInBackground();
     **/

    //ParseObject posted = new ParseObject("Post");

    post.saveInBackground();



      //query.whereEqualTo("num", j);
      query.whereExists("createdAt");


      query.findInBackground(new FindCallback<ParseObject>() {
        @Override
        public void done(List<ParseObject> objects, ParseException e) {
          if (e == null) {
            for (int i = 0; i < objects.size(); i++) {
              if (list.add(objects.get(i).toString())) {
                Log.d("Adding to the object", "success");
                //txt.setText(objects.get(i).toString());
                //getPost(objects.get(i).get("post2").toString());

                  Log.d("Setting Text", "Text Hopefully Set");
                  Post post1 = (Post) objects.get(i);
                  txt.setText(post1.getVoteForimg1() + "");

              } else {
                Log.d("Adding to the object", "false");
              }
            }
          }
        }
      });


    adapter.clear();
    Log.d("Clearing Adapter", "Success");
    for (int i = 0; i < list.size(); i++) {
      Log.d("Adding to adapter", "success");
      adapter.add(list.get(i).toString());
    }
    adapter.notifyDataSetChanged();

    lv.setAdapter(adapter);



    un.setOnClickListener(new View.OnClickListener(){
      public void onClick(View view){
        setContentView(R.layout.user_page);
        //user_page();
        Intent intent = new Intent(MainActivity.this, UserPage.class);
      }
    });



    // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_main );

              //lv.setAdapter(arrayAdapter);


              ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }


  public Post getPost(byte[] bytes){
    Post post = new Post();

    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    ObjectInput in = null;


    return post;
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
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
