package com.parse.starter;

import android.graphics.Bitmap;
import android.media.Image;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;

import com.parse.ParseACL;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;


/**
 * Created by Zachary on 11/6/2015.
 */

@ParseClassName("photoFile")
public class photoFile extends ParseObject {

        Bitmap userImage, img2;
        byte[] img1, userImg;
        int voteForimg1, voteForimg2;
        String userName;
        String userComment;


        public photoFile(){

        }

    /*
        public photoFile( ParseFile file){  //byte[] userPic, Bitmap img1, Bitmap img2,
            //this.userImage = userPic;
            ParseFile photo = file;
            //this.img2 = img2;

        }

*/

        public void setImages(Bitmap image, Bitmap image2){

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
        public byte[] getImg1() {
            return img1;
        }

        public Bitmap getImg2() {
            return img2;
        }
/**
        public int getVoteForimg1() {
            return voteForimg1;
        }

        public String getUserName() {
            return userName;
        }
**/
        public byte[] getUserPicture(){
            return userImg;
        }
/**
        public void setUserName(String userName){
            this.userName = userName;
        }
**/
        public void setUserPicture(ParseFile photo){
            put("profilePicture", photo);
        }

/**
        public String getDisplayName() {
            return getString("displayName");
        }
        public void setDisplayName(String displayName) {
            put("displayName", displayName);
        }
**/
        public ParseUser getOwner() {
            return getParseUser("owner");
        }
        public void setOwner(ParseUser user) {
            put("owner", user);
        }

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


