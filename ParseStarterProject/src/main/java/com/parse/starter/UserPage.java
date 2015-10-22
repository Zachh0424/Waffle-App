package com.parse.starter;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;




public class UserPage extends AppCompatActivity {
    TextView un;
    ImageView userProfilePic;
   // public final Object image;


    private GridView gridView;

    public UserPage(){

   //     image = null;
    }

/*
    public UserPage(Object image) {
        this.image = image;
    }
    //  private PhotoGallery gridAdapter;
*/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_gallery);
        un = (TextView) findViewById(R.id.mainUserId);
        un.setText(getIntent().getSerializableExtra("userName").toString().trim());

        userProfilePic.setClickable(true);
        userProfilePic = (ImageView) findViewById(R.id.userProfilePic);


        //set onclick listener for userProfile pic
        userProfilePic.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), 1);

            }

        });



/*

        //set onclick listener for userProfile pic
        userProfilePic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View image) {
              /*  Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), 1);


                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
                startActivityForResult(galleryIntent, 1);

            }

        });

*/


    }

    /* Result activity method for the User Profile pic
        this gets the data for the image stored on the user's device if
        the user DOES choose one and
        sets the chosen image
     */
        public void onActivityResult(int request,int result, Intent data){
        //check if change has been made
            if(result == RESULT_OK){
                if(request == 1)
                    userProfilePic.setImageURI(data.getData());
            }

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








}