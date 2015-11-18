
package com.parse.starter;

import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.ParseFile;
import com.parse.ParseACL;

import java.io.ByteArrayOutputStream;
import java.io.File;


public class CreatePost extends ActionBarActivity{

    ParseUser user = new ParseUser();

    ImageView postUserPic;

    TextView userPost;
    Boolean leftClicked = false;
    Boolean rightClicked = false;
    Post newPost;
    ParseFile leftFile, rightFile;
    photoFile photo;
    Bitmap currPic;

    //Define variables to handle comments
    EditText text;
    String comment;

    boolean description = false;

    //Define variables to handle buttons
    Button submit;

    //Define variables to handle retrieving username from intent
    Bundle extras;
    String userName;

    byte[] lImage;
    byte[] rImage;

    //Define variables to handle picture selection
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    private String filemanagerstring;
    String imagePicked;
    String leftImg, rightImg;
    ImageView leftImageView;
    ImageView rightImageView;


    //Monitors if both images have been uploaded
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post2);
        postUserPic = (ImageView) findViewById(R.id.userPicture);
       // callProfilePic();
      //  postUserPic.setImageBitmap(currPic);
        userPost = (TextView) findViewById(R.id.userName2);
        userPost.setText(getIntent().getStringExtra("userName"));
        newPost = new Post();
        //photo = new photoFile();
        //Accept intent
        extras = getIntent().getExtras();
        if (extras != null) {
            userName = extras.getString(userName);
        }

        text = (EditText) findViewById(R.id.userDescription);
        leftImageView = (ImageView) findViewById(R.id.imageVote1);
        rightImageView = (ImageView) findViewById(R.id.imageVote2);
        submit = (Button) findViewById(R.id.submitBtn);



        //set onclick listener for userProfile pic
        leftImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i++;

                //User selects image
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                leftClicked = true;

            }

        });


        //set onclick listener for userProfile pic
        rightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;

                //User selects image
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                rightClicked = true;

            }


        });


        //set onclick listener for userProfile pic
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Both images have been uploaded, submit post and return to main activity
                if (i == 2) {

                    creatingPost();

                } else {  //Notify users that two images have to be uploaded to submit post
                    Toast.makeText(getApplicationContext(), "Please Upload Two Images",
                            Toast.LENGTH_LONG).show();

                }

            }

        });


    }

    public boolean descriptionIsThere() {

        return description;
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {


                if(leftClicked == true) {

                    Uri selectedImageUri = data.getData();


                    //OI FILE Manager
                    filemanagerstring = selectedImageUri.getPath();

                    //MEDIA GALLERY
                    selectedImagePath = getPath(selectedImageUri);

                    //NOW WE HAVE OUR WANTED STRING
                    if (selectedImagePath != null)
                        imagePicked = selectedImagePath;
                    else
                        imagePicked = filemanagerstring;


                    leftImageView.setImageURI(selectedImageUri);


                    Bitmap bitmap = ((BitmapDrawable) leftImageView.getDrawable()).getBitmap();


                    // Convert it to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    // Compress image to lower quality scale 1 - 100
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] arr = stream.toByteArray();
                    lImage = arr;


                    // Create the ParseFile passing the byte[] and the name of this specific image file
                    leftFile = new ParseFile("LeftPostPic", lImage);
                    leftFile.saveInBackground();




                }


                else if(rightClicked == true) {

                    Uri selectedImageUri = data.getData();


                    //OI FILE Manager
                    filemanagerstring = selectedImageUri.getPath();

                    //MEDIA GALLERY
                    selectedImagePath = getPath(selectedImageUri);

                    //NOW WE HAVE OUR WANTED STRING
                    if (selectedImagePath != null)
                        imagePicked = selectedImagePath;
                    else
                        imagePicked = filemanagerstring;


                    rightImageView.setImageURI(selectedImageUri);


                    Bitmap bitmap = ((BitmapDrawable) rightImageView.getDrawable()).getBitmap();


                    // Convert it to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    // Compress image to lower quality scale 1 - 100
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] arr2 = stream.toByteArray();
                    rImage = arr2;



                    // Create the ParseFile passing the byte[] and the name of this specific image file


                    rightFile = new ParseFile("RightPostPic", rImage);

                    rightFile.saveInBackground();


                }

                leftClicked = false;
                rightClicked = false;
            }
        }
    }


    public ParseFile leftPic(ParseFile file){

        leftFile = file;
        return leftFile;
    }
    public ParseFile rightPic(ParseFile file){
        rightFile = file;
        return rightFile;
    }



    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor!=null)
        {
            //HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            //THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        else return null;
    }


    public void creatingPost(){

        photo = new photoFile();
        ParseFile postPic = photo.getUserPicture();

        //Retrieve comment about post
        comment = text.getText().toString();


        //Save post to Parse

        newPost.setOwner(user.getCurrentUser());
        newPost.setDisplayName(user.getCurrentUser().getUsername());
        newPost.setImg1(leftFile);
        newPost.setImg2(rightFile);
        newPost.setVote1(0);
        newPost.setVote2(0);
        newPost.setComment(comment);
        newPost.setUserPicture(postPic);
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        newPost.setACL(acl);

        newPost.saveInBackground();



        //Return to MainActivity
   //     Intent intent = new Intent(CreatePost.this, MainActivity.class);
   //     startActivity(intent);

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





}
