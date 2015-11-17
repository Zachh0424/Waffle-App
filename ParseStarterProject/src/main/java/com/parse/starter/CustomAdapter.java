package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    Post post;
    ParseQuery<ParseObject> query;
    ParseUser currUser = ParseUser.getCurrentUser();

//    EditText comment;


    CustomAdapter(Context context, Post[] posts) {
        super(context, R.layout.the_post_layouts, posts);
        //query = new ParseQuery<ParseObject>("Post");
        stream = new ByteArrayOutputStream();
        Log.d("in costructor", "true");
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d("In getView", "true");
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.post_description_layout, parent, false);
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Post");
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
           // callProfilePic();
            if(post.getDisplayName() == currUser.getUsername()){
                callProfilePic();
            }

            userProfPic.setImageBitmap(currPic);
            userName.setText("username: " + post.getString("displayName"));


        }

            return customView;

    }

    public Bitmap callProfilePic() {
        photo = new photoFile();
        ParseFile file = photo.getUserPicture();
        if (file != null) {
            byte[] userPic = new byte[0];
            try {
                userPic = file.getData();
                photo.setUserPicBmp(userPic);
                currPic = photo.getUserPicBmp();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

            return currPic;
        }



/**
    public Bitmap callProfilePic() {

   // ParseQuery<ParseObject> query = ParseQuery.getQuery("ClassName");
   query.whereEqualTo(post.getString("displayName"), );
    query.getFirstInBackground(new GetCallback<ParseObject>() {
        public void done(ParseObject object, ParseException e) {
            if (object != null) {

                ParseFile file = (ParseFile) object.get("profilePicture");
                file.getDataInBackground(new GetDataCallback() {


                    public void done(byte[] data, ParseException e) {
                        if (e == null) {


                            photo.setUserPicBmp(data);
                            currPic = photo.getUserPicBmp();


                        }
                    }
                });

            }
        }
    });

    return currPic;

}
**/


}