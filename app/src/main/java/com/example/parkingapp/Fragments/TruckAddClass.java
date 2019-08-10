package com.example.parkingapp.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.TruckModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TruckAddClass extends AppCompatActivity {
    Preferences preferences;
    ProgressDialog dialog;
    LinearLayout item,item2;
    TextView text_confirm,text_confirm2;
  AppCompatEditText edit_text_truck_number,edit_text_truck_make,edit_text_truck_color;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.truck_details_add);

        preferences=new Preferences(this);
        edit_text_truck_number=findViewById(R.id.edit_text_truck_number);
        edit_text_truck_make=findViewById(R.id.edit_text_truck_make);
        edit_text_truck_color=findViewById(R.id.edit_text_truck_color);

        item=findViewById(R.id.item);
        text_confirm=findViewById(R.id.text_confirm);
        text_confirm=findViewById(R.id.text_confirm2);
        item2=findViewById(R.id.item2);

        edit_text_truck_number.setText(preferences.getTruckNum());
        edit_text_truck_color.setText(preferences.getTruckColor());
        edit_text_truck_color.setText(preferences.getTruckName());

        Button save_truck=findViewById(R.id.save_truck);
        save_truck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTrackAddAPI();
            }
        });

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog2();
            }
        });

    }

    private void showPictureDialog2() {
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
            selectImage2("Upload your photo");
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
                    startActivityForResult(intent, 100);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 101);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void selectImage2(String title) {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 200);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 201);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
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


    private void callTrackAddAPI() {
        try {
            String truckNum = edit_text_truck_number.getText().toString();
            String truckName = edit_text_truck_make.getText().toString();
            String truckColor = edit_text_truck_color.getText().toString();

            if (TextUtils.isEmpty(truckName)) {
                edit_text_truck_make.setError("Please enter Manufacture");
            } else if (TextUtils.isEmpty(truckColor)) {
                edit_text_truck_color.setError("Please enter truck color");
            } else if (TextUtils.isEmpty(truckNum)) {
                edit_text_truck_number.setError("Please enter truck number");
            } else if (pictureMainPath.equalsIgnoreCase("") || pictureMainPath == null) {
                Toast.makeText(this, "No Picture Added", Toast.LENGTH_SHORT).show();
            } else if (pictureMainPath2.equalsIgnoreCase("") || pictureMainPath2 == null) {
                Toast.makeText(this, "No Picture Added", Toast.LENGTH_SHORT).show();
            } else {
                callAPITruck(truckNum, truckColor, truckName);
            }

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Images are not available", Toast.LENGTH_SHORT).show();
        }
    }
    String picturePath, pictureName;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK /*&& null != data*/) {
            if (requestCode == 100) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 70, bytes);

                String partFilename = currentDateFormat();
                pictureMainPath = storeCameraPhotoInSDCard(thumbnail, partFilename);

                Toast.makeText(this, "First document added", Toast.LENGTH_SHORT).show();
                text_confirm2.setVisibility(View.VISIBLE);
              //  pictureName = picturePath.substring(picturePath.lastIndexOf("/") + 1);

              //  callSavingAPI(picturePath);
                //  iv_image.setImageBitmap(thumbnail);


            } else if (requestCode == 101) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = this.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                pictureMainPath = cursor.getString(columnIndex);
                Toast.makeText(this, "First document added", Toast.LENGTH_SHORT).show();
                text_confirm2.setVisibility(View.VISIBLE);
              //  pictureName = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                cursor.close();
              //  callSavingAPI(picturePath);


                // iv_image.setImageURI(selectedImage);

            }if (requestCode == 200) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 70, bytes);

                String partFilename = currentDateFormat();
                pictureMainPath2 = storeCameraPhotoInSDCard(thumbnail, partFilename);
                Toast.makeText(this, "second document added", Toast.LENGTH_SHORT).show();
                text_confirm.setVisibility(View.VISIBLE);
              //  pictureName = picturePath.substring(picturePath.lastIndexOf("/") + 1);

              //  callSavingAPI(picturePath);
                //  iv_image.setImageBitmap(thumbnail);


            } else if (requestCode == 201) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = this.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                pictureMainPath2 = cursor.getString(columnIndex);
                Toast.makeText(this, "second document added", Toast.LENGTH_SHORT).show();
                text_confirm.setVisibility(View.VISIBLE);
              //  pictureName = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                cursor.close();
              //  callSavingAPI(picturePath);


                // iv_image.setImageURI(selectedImage);

            }
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


    String pictureMainPath=null;
    String pictureMainPath2=null;
    private void callAPITruck(String truckNum, String truckColor, String truckName) {

        dialog=new ProgressDialog(TruckAddClass.this);
        dialog.setTitle("updating truck data");
        dialog.setMessage("Please wait we are uploading information");
        dialog.show();

        preferences.setTruckNum(truckNum);
        preferences.setTruckColor(truckColor);
        preferences.setTruckName(truckName);


        MultipartBody.Part picBody = null;
        MultipartBody.Part picBody2 = null;
        try {
            File file = new File(pictureMainPath);
            if (file == null) {
                Toast.makeText(TruckAddClass.this, "Registration Image is missing", Toast.LENGTH_SHORT).show();
            }
            // pDialog.show();
            RequestBody requestFileRegistration = RequestBody.create(MediaType.parse("image/*"), file);
            picBody = MultipartBody.Part.createFormData("truck_insurance_image", file.getName(), requestFileRegistration);

            if (picBody == null) {
                Toast.makeText(TruckAddClass.this, "registration is missing", Toast.LENGTH_SHORT).show();
            }

            File file2 = new File(pictureMainPath2);
            if (file2 == null) {
                Toast.makeText(TruckAddClass.this, "License Image is missing", Toast.LENGTH_SHORT).show();
            }
            // pDialog.show();
            final RequestBody requestFileLicense = RequestBody.create(MediaType.parse("image/*"), file);
            picBody2 = MultipartBody.Part.createFormData("truck_registration_image", file.getName(), requestFileLicense);

            if (picBody2 == null) {
                Toast.makeText(TruckAddClass.this, "registration is missing", Toast.LENGTH_SHORT).show();
            }

            RequestBody truck_owner_id = RequestBody.create(MediaType.parse("text/plain"), preferences.getUserId());
            RequestBody track_number = RequestBody.create(MediaType.parse("text/plain"), truckNum);
            RequestBody manufacturing_name = RequestBody.create(MediaType.parse("text/plain"), truckName);
            RequestBody truck_color = RequestBody.create(MediaType.parse("text/plain"), truckColor);

            APIService service= APIClient.getClient().create(APIService.class);
            Call<TruckModel> modelCall= service.modelTruck(truck_owner_id,track_number,manufacturing_name,truck_color,picBody,picBody2);
            modelCall.enqueue(new Callback<TruckModel>() {
                @Override
                public void onResponse(Call<TruckModel> call, Response<TruckModel> response) {
                    dialog.dismiss();
                    TruckModel model=response.body();
                    if (model.getStatus().equalsIgnoreCase("success")){
                        Toast.makeText(TruckAddClass.this, model.getParkingData(), Toast.LENGTH_SHORT).show();
                        preferences.setTruckID(model.getTruck_id()) ;
                        finish();
                    }else{
                        Toast.makeText(TruckAddClass.this, "Error while uploading", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TruckModel> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(TruckAddClass.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            });




        }catch (Exception e){
            e.printStackTrace();
            dialog.dismiss();
        }
    }
}
