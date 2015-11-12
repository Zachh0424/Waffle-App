package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseFile;
import com.parse.ParseUser;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;



public class UserPage extends ActionBarActivity {
    TextView un;
    ImageView userProfilePic, miniUserPic, userImages;
    Uri img;
    ParseQuery<ParseObject> picQuery;
    //  ParseFile file;
    //  photoFile photoFile = new photoFile(file);
    //ParseObject photoFile = new ParseObject("photoFile");
    //byte[] userPic, userimg;

    private GridView gridView;

    photoFile photo;



    //  private PhotoGallery gridAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page);
        un = (TextView) findViewById(R.id.profileUserId);
        un.setText(getIntent().getStringExtra("userName"));

        //       un.setText(getIntent().getSerializableExtra("userName").toString());

        userProfilePic = (ImageView) findViewById(R.id.userProfilePic);
        miniUserPic = (ImageView) findViewById(R.id.miniUserPic);

        ParseUser currentUser = ParseUser.getCurrentUser();




        //  picQuery = new ParseQuery<>("User");

        // ParseFile currentProfilePic = ParseFile(currentUser.getUsername().);
        //userProfilePic.setImageDrawable(currentUser.getUserPicture());




        //set onclick listener for userProfile pic
        userProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  System.out.print("clicked image");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), 1);

            }

        });


        un.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                Intent intent = new Intent(UserPage.this, MainActivity.class);
                intent.putExtra("userName", currentUser.getUsername().trim());
                startActivity(intent);
            }
        });

        miniUserPic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                Intent intent = new Intent(UserPage.this, MainActivity.class);
                intent.putExtra("userName", currentUser.getUsername().trim()); //send the userName to MAinActivity
                // intent.putExtra("miniUserPic", );                //send the userProfilePic to MainActivity
                startActivity(intent);
            }
        });


        ParseAnalytics.trackAppOpenedInBackground(getIntent());

    }


    /* Result activity method for the User Profile pic
        this gets the data for the image stored on the user's device if
        the user DOES choose one and
        sets the chosen image
     */
    public void onActivityResult(int request,int result, Intent data){
        //check if change has been made
        if(result == RESULT_OK) {
            if (request == 1)
                userProfilePic.setImageURI(data.getData());
            miniUserPic.setImageURI(data.getData());

           makePhoto(userProfilePic);
        }

    }

    // This is the section where the images is converted, saved, and uploaded.
    public void makePhoto(ImageView userProfilePic){

        photo = new photoFile();                            //create PhotoFile object
        ParseUser currentUser = ParseUser.getCurrentUser();     //get the user
        photo.setOwner(currentUser.getCurrentUser());       //set this photo's owner as current user




        //Bitmap the Imageview userProfilePic
        Bitmap bitmap = ((BitmapDrawable)userProfilePic.getDrawable()).getBitmap();


        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] userPic = stream.toByteArray();


        // Create the ParseFile passing the byte[] and the name of this specific image file
        ParseFile file = new ParseFile("UserPicture", userPic);


        //set the pic
        photo.setUserPicture(file);

        //may need to check here for duplicates before saving again
        photo.saveInBackground();
        file.saveInBackground();
        currentUser.saveInBackground();


    }



/*
        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new PhotoGallery(this, R.layout.photo_gallery_layout, getPic());
        gridView.setAdapter(gridAdapter);
        //appImageView = (ImageView) findViewById(R.id.imageView);

    }


    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getPic(){
        final ArrayList<ImageItem> imageItems = new ArrayList<>();

        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }


    @Override
    View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    */



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

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_main:
                startActivity(new Intent(UserPage.this, MainActivity.class));
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }



}
