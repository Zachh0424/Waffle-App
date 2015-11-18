package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;

/**
 * Created by devinyancey on 10/30/15.
 */
class CustomAdapter extends ArrayAdapter<Post> {
    String[] posted;
    //ParseObject post = new ParseObject("");
    Bitmap bitmap, bitmap2;
    ByteArrayOutputStream stream;
    BitmapFactory.Options o;
    TextView userName;
    TextView description;
    ImageView userProfPic;
    Bitmap currPic;
    photoFile photo;
    ParseFile userImage;
    Post post;
    ParseQuery<ParseObject> query;
    ParseUser currUser = ParseUser.getCurrentUser();



    CustomAdapter(Context context, Post[] posts) {
        super(context, R.layout.the_post_layouts, posts);
       //query = new ParseQuery<ParseObject>("Post");
        stream = new ByteArrayOutputStream();
    //    Log.d("in costructor", "true");
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
     //   Log.d("In getView", "true");
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.post_description_layout, parent, false);
     //   ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Post");
        ;
        //get reference to everything
        final Post post = getItem(position);
        if (post == null) {
            Log.d("Post is null", "true");

        }
        if (post != null) {

            userName = (TextView) customView.findViewById(R.id.userNameInfo);
            description = (TextView) customView.findViewById(R.id.usersDescription);
//            comment = (EditText) customView.findViewById(R.id.comment);
            //userName.setText("Username: This will be the usernames");
            description.setText("Description: This mouth cheats without the shaky lord. " +
                    "Why won't the warp part the ");
            userProfPic = (ImageView) customView.findViewById(R.id.userProfPic);
            userName.setText("username: " + post.getString("displayName"));

        //Get the Post owner's Profile Picture
            try {
                if(post.getParseFile("profilePicture") != null){
                byte[] bytePic = post.getParseFile("profilePicture").getData();
             //    if(bytePic != null) {
                   //  post.setUserPicBmp(bytePic);
                     BitmapFactory.Options options = new BitmapFactory.Options();
                     currPic = BitmapFactory.decodeByteArray(bytePic, 0, bytePic.length, options);
                   //  currPic = post.getUserPicBmp();
                     userProfPic.setImageBitmap(currPic);
                 }
              else{
                     //userProfPic.setImageBitmap(currPic);
                 }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }



        return customView;

    }



}