package com.parse.starter;

        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.drawable.Drawable;
        import android.media.Image;
        import android.os.Parcel;
        import android.os.Parcelable;

        import com.parse.ParseACL;
        import com.parse.ParseClassName;
        import com.parse.ParseFile;
        import com.parse.ParseObject;
        import com.parse.ParseUser;

        import java.io.Serializable;

/**
 * Created by devinyancey on 10/6/15.
 */

@ParseClassName("Post")

public class Post extends ParseObject implements Serializable{
    Bitmap userImage;
    byte[] img1, img2;
    int voteForimg1, voteForimg2;
    String userName;
    String userComment;
    ParseFile postPic;
    Post post;
    byte[] bytePic;
    public Post(){

    }

    public Post(Bitmap user, Bitmap img1, Bitmap img2, int voteForimg1,
                int voteForimg2, String userName, String userComment, ParseFile pic){
        this.userImage = user;
        postPic = pic;

        this.voteForimg1 = voteForimg1;
        this.voteForimg2 = voteForimg2;
        this.userName = userName;
        this.userComment = userComment;

    }

    /**
public ParseFile getPostPic(){
  postPic = ;


    return postPic;
}
**/
    public void setImg1(ParseFile image){
        put("leftImage", image);
    }

    public void setImg2(ParseFile image){
        put("rightImage", image);
    }

    public void setImg3(byte[] image){
        img2 = image;
    }

    public void setComment(String comment){
        put("comment",comment);
    }


    public void setUserPicture(ParseFile photo){

        put("profilePicture", photo);

    }

    /*
public ParseFile getUserPicture(){
    ParseFile postPic = (ParseFile) post.getOwner().get("profilePicture");

    return postPic;

}
*/

    public void setUserPicBmp(byte[] pic) {
        bytePic = pic;

        BitmapFactory.Options options = new BitmapFactory.Options();
        userImage = BitmapFactory.decodeByteArray(bytePic, 0, bytePic.length, options); //Convert bytearray to bitmap
       // bytePic = null;


    }

    public Bitmap getUserPicBmp(){
        return userImage;
    }

    public void updateVoteForimg1(){
        voteForimg1 += 1;
    }

    public byte[] getImg1() {
        return img1;
    }

    public byte[] getImg2() {
        return img2;
    }

    public int getVoteForimg1() {
        return voteForimg1;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
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

    public void setVote2(int voteForimg2){
        put("voteImage1", voteForimg2);
    }

    public void putImage(ParseFile file){
        put("Image", file);
    }

    public int getVote(){
        return 1;
    }

}