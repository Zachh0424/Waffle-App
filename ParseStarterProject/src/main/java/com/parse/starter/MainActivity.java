package com.parse.starter;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseACL;
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
  AbsListView lv;

  List<ParseObject> ob;
  ArrayAdapter<String> adapter;
  ProgressDialog mProgressDialog;
  ParseQuery<ParseObject> query;
  ArrayList<String> list;             //Changed from:  ArrayList<String> list;
  TextView txt;
  ListAdapter listAdapter;
  ProgressDialog pd;


  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    un = (TextView) findViewById(R.id.mainUserId);
    un.setText(getIntent().getSerializableExtra("userName").toString().trim());

    list = new ArrayList<>();
    query = new ParseQuery<>("Post");

//    Added devin's code
    Post test = new Post();
    Post test1 = new Post();
    Post[] arrayOfPost = {test, test1};
//

    //arrayAdapter = new ArrayAdapter<Device>(this, android.R.layout.simple_list_item_1, deviceList);
    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
   // lv = (ListView) findViewById(R.id.postListView);


//    added devin's list adapter code
    listAdapter = new CustomAdapter(this, arrayOfPost);
    lv = (AbsListView) findViewById(R.id.postListView);
    lv.setAdapter(listAdapter);
    pd = new ProgressDialog(MainActivity.this);
//



    ParseUser user = new ParseUser();

    Post post = new Post();
    post.setOwner(user.getCurrentUser());
    post.setDisplayName(user.getCurrentUser().getUsername());
    post.setVote1(2);


    ParseACL acl = new ParseACL();
    acl.setPublicReadAccess(true);
    acl.setPublicWriteAccess(true);

    post.setACL(acl);


/*
//  Devin's    starting here
    query.whereEqualTo("objectId", "OSDWgkbkVy");


    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if (e == null) {

          Log.d("object size:", objects.size() + "");
          for (int i = 0; i < objects.size(); i++) {
            Log.d("Object:", objects.get(i).toString());
            String s = objects.get(i).getString("voteImage1"); //removed (String) cast
            txt.setText(s);
          }
        } else {

        }

      }


    });

//    ending here




/*
    adapter.clear();
    Log.d("Clearing Adapter", "Success");
    for (int i = 0; i < list.size(); i++) {
      Log.d("Adding to adapter", "success");
      adapter.add(list.get(i).toString());

    }
    adapter.notifyDataSetChanged();

    lv.setAdapter(adapter);

*/
    un.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {

        //ParseUser currentUser = ParseUser.getCurrentUser();
        Intent intent = new Intent(MainActivity.this, UserPage.class);
       // intent.putExtra("userName", currentUser.getUsername().toString().trim());
        intent.putExtra("userName",un.getText());
        startActivity(intent);
      }
    });


    // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_main );

    //lv.setAdapter(arrayAdapter);



    findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, UserPage.class);
            // intent.putExtra("userName", currentUser.getUsername().toString().trim());
            intent.putExtra("userName",un.getText());
            startActivity(intent);
        }
    });



    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }




//  Devin's
public void addToListArray(List<ParseObject> lst){
  list.clear();
  for (int i = 0; i < lst.size(); i++){

    list.add(lst.get(i).toString());
    Log.d("Adding to Array list", "Added " + i +" "+lst.get(i).getString("objectId"));
  }
  txt.setText("Here");
}


  public void getPosts(){
    adapter.clear();

    Log.d("Clearing Adapter", "Success");
    for (int i = 0; i < list.size(); i++) {
      Log.d("Adding to adapter", "success");
      adapter.add(list.get(i).toString());

    }
    adapter.notifyDataSetChanged();

    //surround setAdapter call in version check
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      lv.setAdapter(adapter);
    }

    //pd.dismiss(); <- leave commented
 }
//end of devin's




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
