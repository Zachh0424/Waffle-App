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
  //  ParseFile file;
  //  photoFile photoFile = new photoFile(file);
  //ParseObject photoFile = new ParseObject("photoFile");
    //byte[] userPic, userimg;

    private GridView gridView;



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


/**
        ParseFile photoFile = new ParseFile(userPic);
        makePhoto();

       // userPic.setOwner(user.getCurrentUser());


        photoFile.setUserPicture(userPic);
//    post.put("Image1", file);
        ParseACL acl = new ParseACL();
      //  photoFile.setACL(acl);
        photoFile.saveInBackground();
**/

        /*
        photoFile photoFile;
        makePhoto(userProfilePic);
*/
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

                //userImages.setImageURI(data.getData()); //not an image view so crashing

                //set URI img variable to userimage
               // img = data.getData();
              //Uri userImages = img;

              //  userProfilePic.setImage


                miniUserPic.setImageURI(data.getData());

               makePhoto(userProfilePic);
            }

            }

    public void makePhoto(ImageView userProfilePic){

        //userProfilePic.getDrawable();

        photoFile photo = new photoFile();
        ParseUser currentUser = ParseUser.getCurrentUser();


        //Resources res = getResources();
       //String picStringId = String.valueOf(userProfilePic);
       // int picIntId = Integer.valueOf(picStringId);


        // This is the section where the images is converted, saved, and uploaded. I have not been able Locate the image from the ImageView, where the user uploads the picture to imageview from either their gallery and later on from facebook */
     //   Bitmap bitmap = BitmapFactory.decodeResource(getResources(), picIntId);
        Bitmap bitmap = ((BitmapDrawable)userProfilePic.getDrawable()).getBitmap();


        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] userPic = stream.toByteArray();


        // Create the ParseFile
        ParseFile file = new ParseFile("profilePicture.png", userPic);
         //file = new ParseFile("profilePicture.png", userPic);
        // Upload the image into Parse Cloud


        // Create a column named "Profile Picture" and set the string
        //currentUser.put("ImageName", "Profile Picture");

        // Create a column named "ProfilePicture" and insert the image


        currentUser.put("profilePicture", file);

        photo.setUserPicture(file);

       file.saveInBackground();
        currentUser.saveInBackground();


    }


        /**
         // userProfilePic.setImageURI(img);

         //  Drawable drawable = res.getDrawable(resourceId);
         //  userProfilePic.setImageDrawable(res.getDrawable(resourceId));

         //Convert it to byte
         Bitmap bitmap = BitmapFactory.decodeResource(getResources(), picIntId);

         //Convert it to byte
         ByteArrayOutputStream stream = new ByteArrayOutputStream();

         //compress the image
         bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

         //get byte array here
         byte[] userimg = stream.toByteArray();
         userPic = userimg;

         **/
      //

        //converting back to imageview
        // Bitmap bmp = BitmapFactory.decodeByteArray(userPic, 0, userPic.length);
        //userProfilePic = (ImageView) findViewById(R.id.userProfilePic);

        // userProfilePic.setImageBitmap(bmp);


/**

    public void done(ParseException e) {
        setProgressBarIndeterminateVisibility(false);

        if (e == null) {
            // Success!
            Intent intent = new Intent(UserPage.this, UserPage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserPage.this);
            builder.setMessage(e.getMessage())
                    .setTitle(R.string.error_end)
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    // ParseUser user = new ParseUser();

**/

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
