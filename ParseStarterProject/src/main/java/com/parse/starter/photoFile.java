package com.parse.starter;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

import com.parse.ParseACL;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Zachary on 11/6/2015.
 */

@ParseClassName("photoFile")
public class photoFile extends ParseObject {

    Bitmap img1, img2, userImage;
    byte[]  pic, bytePic;
    int voteForimg1, voteForimg2;
    // String userName, title;
    String userComment;
    ParseFile photo;
    Post post;
    ParseQuery<ParseObject> query;

    ParseUser currUser = ParseUser.getCurrentUser();



    public photoFile(){

    }
    /**
     public photoFile(Bitmap image, String title) {
     super();
     this.userImage = image;
     this.title = title;
     }
     **/

    public photoFile( ParseFile file, ParseUser user, Bitmap userimg, byte[] arr){// Bitmap img1, Bitmap img2,
        pic = arr;
        setUserPicBmp(pic);
        currUser = user;
        photo = file;
        userImage = userimg;
        // title = txt;

    }



    public void setImages(Bitmap image, Bitmap image2){
        img1 = image;
        img2 = image2;
    }

    /**
     public void setImg1(byte[] image){
     put("imageTest", image);
     img1 = image;
     }


     public void updateVoteForimg1(){
     voteForimg1 += 1;
     }
     **/
    public Bitmap getImg1() {
        return img1;
    }

    public Bitmap getImg2() {
        return img2;
    }


    /**
     public int getVoteForimg1() {
     return voteForimg1;
     }
     **/
    /**
     public String getUserName() {
     return userName;
     }

     public ParseFile getUserPictureFile(){
     return photo;
     }
     public void setUserName(String userName){
     this.userName = userName;
     }
     **/




     public void setDisplayName(String displayName) {
     put("displayName", displayName);
     }

    public String getDisplayName() {
        return getString("displayName");
    }


    public void setOwner(ParseUser user) {
        put("owner", user);
    }

    public ParseUser getOwner() {

        return getParseUser("owner");
    }


    /*
        public Bitmap getImage() {
            return userPic;
        }

        public void setImage(Bitmap image) {
            userPic = image;
        }
    */
    /**
     public String getTitle() {
     return title;
     }

     public void setTitle(String title) {
     this.title = title;

     }
     **/

    public void setUserPicture(ParseFile photo){
        put("profilePicture", photo);
        currUser.put("profilePicture", photo);
        // post.setUserPicture(photo);
    }


    public Bitmap getUserPicBmp(){
        return userImage;
    }


     /*
        newest try to find the curr user's profile pic and convert it back to bitmap
        and set as image view
        */
    public void setUserPicBmp(byte[] pic) {
        bytePic = pic;

        BitmapFactory.Options options = new BitmapFactory.Options();
        userImage = BitmapFactory.decodeByteArray(bytePic, 0, bytePic.length, options); //Convert bytearray to bitmap
        bytePic = null;


    }





/*
bytePic = pic;
    query.whereEqualTo("profilePicture", photo);
    query.findInBackground(new FindCallback<ParseObject>() {
        @Override
        public void done(List<ParseObject> objects, ParseException e) {
            if (e == null) {

                Bitmap bitmap = BitmapFactory.decodeByteArray(bytePic, 0, bytePic.length);
            }
        }
    });


 */


/**
 public void setFeatureData(Context context, String className, String itemSelected,
 String[] valueToGet, String colName) {
 featureList = new ArrayList<String>();
 ParseQuery query = new ParseQuery(className);
 query.whereEqualTo(colName, itemSelected);
 try {
 List<ParseObject> dataHolder = query.find();
 if(dataHolder!= null){
 for(int counter =0;counter<dataHolder.size();counter++){
 for (int innerCounter = 0 ; innerCounter < valueToGet.length; innerCounter ++) {
 String datas = dataHolder.get(counter).getString(valueToGet[innerCounter]);
 featureList.add(datas);
 }
 }
 }
 objectId = dataHolder.get(0).getObjectId();
 ParseObject fileHolder = query.get(objectId);
 ParseFile bum = (ParseFile) fileHolder.get("Image");
 byte[] file = bum.getData();
 image = BitmapFactory.decodeByteArray(file,0,file.length);
 } catch (ParseException e) {
 // message if faile
 }
 }
 **/


/**
 public void setVote1(int voteForimg1) {
 put("voteImage1", voteForimg1);
 }

 public void putImage(Image img){
 put("Image", img);
 }

 public int getVote(){
 return 1;
 }

 **/


}




