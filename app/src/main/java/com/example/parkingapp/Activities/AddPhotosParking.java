package com.example.parkingapp.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parkingapp.Adapters.ImageParkingAdapter;
import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.AddImageParking;
import com.example.parkingapp.Models.ImageParkingModel;
import com.example.parkingapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPhotosParking extends AppCompatActivity {

    String parkingId;
    Button add_image;
    ImageView backPress;

    RecyclerView imageRecyclerView;
    ArrayList<ImageParkingModel> modelArrayList=new ArrayList<>();
    GridLayoutManager layoutManager;
    ImageParkingAdapter imageParkingAdapter;
    private static int RESULT_CAPTURE_IMAGE = 1;
    private static int RESULT_LOAD_IMAGE = 2;
    String picturePath, pictureName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photos_parking);

        Intent intent=getIntent();
        if (intent!=null){
            parkingId=intent.getStringExtra("parking_id");
        }

        imageRecyclerView=findViewById(R.id.image_recycler_view);
        add_image=findViewById(R.id.add_image);
        backPress=findViewById(R.id.back_press);
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        layoutManager=new GridLayoutManager(this,3, LinearLayoutManager.VERTICAL,false);

        callImageHistory();


    }





    private void showPictureDialog() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 0);
//            Toast.makeText(getActivity(), " needs access to your camera.", Toast.LENGTH_LONG).show();
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
//            Toast.makeText(getActivity(), "Car.Go needs access to your storage.", Toast.LENGTH_LONG).show();

        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
//            Toast.makeText(getActivity(), "Car.Go needs access to your storage.", Toast.LENGTH_LONG).show();

        } else {
            selectImage("Upload your photo");
        }
    }

    private void selectImage(String title) {
        
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, RESULT_CAPTURE_IMAGE);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, RESULT_LOAD_IMAGE);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK /*&& null != data*/) {
            if (requestCode == RESULT_CAPTURE_IMAGE) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 70, bytes);

                String partFilename = currentDateFormat();
                picturePath = storeCameraPhotoInSDCard(thumbnail, partFilename);
                pictureName = picturePath.substring(picturePath.lastIndexOf("/") + 1);

                callSavingAPI(picturePath);
              //  iv_image.setImageBitmap(thumbnail);


            } else if (requestCode == RESULT_LOAD_IMAGE) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = this.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                pictureName = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                cursor.close();
                callSavingAPI(picturePath);


               // iv_image.setImageURI(selectedImage);

            }
        }
    }

    private void callSavingAPI(String picturePath) {
        MultipartBody.Part picBody = null;
        if (picturePath!=null){
            File file = new File(picturePath);
            if (file==null){
                Toast.makeText(this, "Please Set your Profile Pic First", Toast.LENGTH_SHORT).show();
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            picBody = MultipartBody.Part.createFormData("parking_picture", file.getName(), requestFile);

        }else{
            Toast.makeText(this, "Please add your dp", Toast.LENGTH_SHORT).show();
          //  progressDialog.dismiss();

            RequestBody parkingID = RequestBody.create(MediaType.parse("text/plain"), parkingId);


            APIService service=APIClient.getClient().create(APIService.class);
            Call<AddImageParking> parkingCall=service.AddParkingImage(parkingID,picBody);
            parkingCall.enqueue(new Callback<AddImageParking>() {
                @Override
                public void onResponse(Call<AddImageParking> call, Response<AddImageParking> response) {
                    AddImageParking imageParking=response.body();
                    if (imageParking.getStatus().equalsIgnoreCase("success")){

                        Toast.makeText(AddPhotosParking.this, imageParking.getParking_data(), Toast.LENGTH_SHORT).show();
                        callImageHistory();
                    }else {
                        Toast.makeText(AddPhotosParking.this, imageParking.getError(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddImageParking> call, Throwable t) {
                    Toast.makeText(AddPhotosParking.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String currentDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    private String storeCameraPhotoInSDCard(Bitmap bitmap, String currentDate) {
        String root = Environment.getExternalStorageDirectory().toString() + "/KOS";

        File myDir = new File(root);
        String storeFilename = "photo_" + currentDate + ".jpg";
        if (!myDir.exists()) {
            boolean f = myDir.mkdirs();
            Log.i("Boolean: ", String.valueOf(f));
        }

        File outputFile = new File(myDir, storeFilename);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
//            picSelection = false;
//            updatedpicSelection = false;
            return "";
        } catch (IOException e) {
            e.printStackTrace();

            return "";
        }

        return root + "/" + storeFilename;
    }



    private void callImageHistory() {
        APIService service= APIClient.getClient().create(APIService.class);
        Call<ImageParkingModel> callModel=service.getImage(parkingId);
        callModel.enqueue(new Callback<ImageParkingModel>() {
            @Override
            public void onResponse(Call<ImageParkingModel> call, Response<ImageParkingModel> response) {
                ImageParkingModel model=response.body();
                if (model.getStatus().equalsIgnoreCase("success")){

                    modelArrayList=model.getArrayList();

                    imageParkingAdapter=new ImageParkingAdapter(AddPhotosParking.this,modelArrayList);
                    imageRecyclerView.setAdapter(imageParkingAdapter);

                }
            }

            @Override
            public void onFailure(Call<ImageParkingModel> call, Throwable t) {
                Toast.makeText(AddPhotosParking.this, "network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
