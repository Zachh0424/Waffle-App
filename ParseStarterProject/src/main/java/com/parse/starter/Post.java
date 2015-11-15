package com.parse.starter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;

import com.parse.ParseACL;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by devinyancey on 10/6/15.
 */

@ParseClassName("Post")

public class Post extends ParseObject {
    Bitmap userImage;
   // byte[] img1, userImg;
    int voteForimg1, voteForimg2;
    String userName;
    String userComment;
    ImageView userPic;
    ParseFile pic;

    photoFile photo;

    ParseUser currUser = ParseUser.getCurrentUser();
    private byte[] bytePic, img1, img2;

    public Post() {

    }

    public Post(Bitmap user, byte[] img1, byte[] img2, int voteForimg1, byte[] arr,
                int voteForimg2, String userName, String userComment, ImageView temp, ParseFile file) {
        userImage = user;
        pic = file;
        this.img2 = img2;
        this.voteForimg1 = voteForimg1;
        this.voteForimg2 = voteForimg2;
        this.userName = userName;
        this.userComment = userComment;
        bytePic = arr;

    }

/*
    public void setImages(Bitmap image, Bitmap image2) {

        img2 = image2;
    }
*/
    public void setImg1(byte[] image) {
        put("imageTest", image);
        img1 = image;
    }

    public void setImg2(byte[] img2) {
        this.img2 = img2;
    }


    /**
     * public void updateVoteForimg1(){
     * voteForimg1 += 1;
     * }
     * <p/>
     * public byte[] getImg1() {
     * return img1;
     * }
     * <p/>
     * public Bitmap getImg2() {
     * return img2;
     * }
     * <p/>
     * public int getVoteForimg1() {
     * return voteForimg1;
     * }
     **/

    public void setComment(String comment) {
        this.userComment = comment;
    }


    public String getUserName() {
        return userName;
    }

    /* public Bitmap getUserPicture(){
         return userImage;
     }
 */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPicture(ParseFile photo) {
        // callProfilePic();
        put("profilePicture", photo);
    }

    public void setUserPicBmp(byte[] pic) {
        bytePic = pic;

        BitmapFactory.Options options = new BitmapFactory.Options();
        userImage = BitmapFactory.decodeByteArray(bytePic, 0, bytePic.length, options); //Convert bytearray to bitmap
        bytePic = null;


    }

    public ParseFile getUserPicture() {
        ParseFile pic = (ParseFile) currUser.get("profilePicture");
        return pic;

    }

    public Bitmap getUserPictureBmp() {

        return userImage;

    }

    public String getDisplayName() {
        return getString("displayName");
    }

    public void setDisplayName(String displayName) {
        put("displayName", displayName);
    }

    public ParseUser getOwner() {
        return getParseUser("owner");
    }

    public void setOwner(ParseUser user) {
        put("owner", user);
    }


    public void setVote1(int voteForimg1) {
        put("voteImage1", voteForimg1);
    }

    public void putImage(Image img) {
        put("Image", img);
    }

    public int getVote() {
        return 1;
    }


    public Bitmap callProfilePic() {
        photo = new photoFile();
        ParseFile file = photo.getUserPicture();
        if (file != null) {
            byte[] userPic = new byte[0];
            try {
                userPic = file.getData();
                photo.setUserPicBmp(userPic);
                userImage = photo.getUserPicBmp();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return userImage;
    }



}

