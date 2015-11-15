package com.parse.starter;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    TextView un;
    ListView lv;

    List<ParseObject> ob;
    ArrayAdapter<String> adapter;
    ListAdapter listAdapter;
    ProgressDialog mProgressDialog;
    ParseQuery<ParseObject> query;
    ArrayList<Post> list;
    TextView txt;
    ProgressDialog pd;
    photoFile photo;
    Bitmap currPic;
    ImageView userPic, postUserPic;

    String[] objectIds;

    Post[] objectPosts;


    ParseUser currUser = ParseUser.getCurrentUser();

    byte[] image, userimg;



    public Post test;
    int arraySize;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        un = (TextView) findViewById(R.id.mainUserId);
        un.setText(getIntent().getSerializableExtra("userName").toString().trim());
        userPic = (ImageView) findViewById(R.id.imageView);
        callProfilePic();
        userPic.setImageBitmap(currPic);
        lv = (ListView) findViewById(R.id.postListView);
        pd = new ProgressDialog(MainActivity.this);

        objectIds = null;
        objectPosts = null;


        query = new ParseQuery<>("Post");
        list = new ArrayList<>();

/*
        postUserPic = (ImageView) findViewById(R.id.userPicture);
        postUserPic.setImageBitmap(currPic);
*/



        un.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //ParseUser currentUser = ParseUser.getCurrentUser();
                Intent intent = new Intent(MainActivity.this, UserPage.class);
                // intent.putExtra("userName", currentUser.getUsername().toString().trim());
                intent.putExtra("userName", un.getText());
                startActivity(intent);
            }
        });




        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserPage.class);
                // intent.putExtra("userName", currentUser.getUsername().toString().trim());
                intent.putExtra("userName", un.getText());
                startActivity(intent);
            }
        });

/*
        query.getInBackground("", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // object will be your game score
                } else {
                    // something went wrong
                }
            }
        });
*/
    //    query.whereEqualTo("profilePic", currUser);
     //   userPic.setImageDrawable();

      //  Bitmap bmp = BitmapFactory.decode//ByteArray(byteArray, 0, byteArray.length);
     //   ImageView image = (ImageView) findViewById(R.id.imageView1);

        //image.setImageBitmap(bmp);








        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.d("number of objects", objects.size() + "");
                    Post[] pst = new Post[objects.size()];
                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("ParseObject:", objects.get(i).toString());
                        pst[i] = (Post) objects.get(i);
                    }
                    sendToAdpater(pst);
                    lv.setAdapter(listAdapter);
                }
            }
        });

/**
        ParseUser user = new ParseUser();

       // makePost();
        Post post = new Post();
        post.setOwner(user.getCurrentUser());
        post.setUserName(user.getCurrentUser().toString());
        post.setDisplayName(user.getCurrentUser().getUsername());
       userimg = post.getUserPicture();
        //converting Byte[] to imageView
        Bitmap bmp = BitmapFactory.decodeByteArray(userimg, 0, userimg.length);
        userPic.setImageBitmap(bmp);
     //   postUserPic.setImageBitmap(bmp);

      //  userPic = post.getUserPicture();
     //   post.getUserPicture();

/**        post.setVote1(2);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable
                .user_icon);
        post.setImg1(image);
//      post.put("Image1", file);
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);

        post.setACL(acl);
        post.saveInBackground();
**/

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    public void sendToAdpater(Post[] array){

        pd = ProgressDialog.show(this, "dialog title",
                "dialog message", true);
        for (int i = 0; i < array.length; i++){
            Log.d("Object:", array[i].toString());
        }
        listAdapter = new CustomAdapter(this, array);
        pd.dismiss();
    }

    public void addToListArray(List<ParseObject> lst){
        list.clear();
        for (int i = 0; i < lst.size(); i++){

            list.add((Post) lst.get(i));
            Log.d("Adding to Array list", "Added " + i +" "+((Post) lst.get(i)).getUserName());
        }
        txt.setText("Here");
    }

    public void makePost(){
        //THis is to locate the image but it will be replaced by going into the
        //gallery/taking a picture
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user_icon);

        //Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //compress the image
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image2 = stream.toByteArray();
        image = image2;
        //create the parse file
        //file = new ParseFile("postImage.png", image);
        //file.saveInBackground();
    }


    public void getPosts(){
        adapter.clear();

        Log.d("Clearing Adapter", "Success");
        for (int i = 0; i < list.size(); i++) {
            Log.d("Adding to adapter", "success");
            adapter.add(list.get(i).toString());

        }
        adapter.notifyDataSetChanged();

        lv.setAdapter(adapter);

        //pd.dismiss();
    }



    public Bitmap callProfilePic() {
        photo = new photoFile();
        ParseFile file = photo.getUserPicture();
        if(file != null) {
            byte[] userPic = new byte[0];
            try {
                userPic = file.getData();
                photo.setUserPicBmp(userPic);
                currPic = photo.getUserPicBmp();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
                   /*
                    Intent intent = new Intent(this, .class);
                    intent.putExtra("profilePicture", data);
                    startActivity(intent);
                   */


        return currPic;
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

        //Determine what action bar item user selected and perform action
        if(id == R.id.action_new_post){
            Intent intent2 = new Intent(MainActivity.this, CreatePost.class);
            intent2.putExtra("userName", un.getText());
            //Retrieve username to pass to next activity
            String userName = un.getText().toString();
           // intent2.putExtra(userName, userName);

            //Start CreatePost activity
            startActivity(intent2);

            finish();
            return true;
        } else if(id == R.id.action_refresh){
            return true;
        } else if(id == R.id.action_logout){
            Intent intent = new Intent(MainActivity.this,WelcomeActivity.class);

            startActivity(intent);
            finish();
            return true;

        } else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

}