package org.techtown.tab;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;


public class Fragment6 extends Fragment {

    EditText editText15;
    EditText editText16;
    EditText editText17;
    EditText editText18;
    private ImageView mImageView;
    private static final int REQUEST_UPLOAD = 100;
    private static final int REQUEST_PERMISSION_UPLOAD = 100;


    OnHDatabaseCallback callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callback = (OnHDatabaseCallback) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment6, container, false);

        editText15 = (EditText) rootView.findViewById(R.id.editText15);
        editText16 = (EditText) rootView.findViewById(R.id.editText16);
        editText17 = (EditText) rootView.findViewById(R.id.editText17);
        editText18 = (EditText) rootView.findViewById(R.id.editText18);

        Button button12 = (Button) rootView.findViewById(R.id.button12);
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText15.getText().toString();
                String location = editText16.getText().toString();
                String mobile = editText17.getText().toString();
                String condition = editText18.getText().toString();

                callback.insert_h(name, location, mobile, condition);

            }
        });

        mImageView = rootView.findViewById(R.id.imgView);

        // 버튼에 클릭 리스너 지정
        Button uploadButton = rootView.findViewById(R.id.btnUploadPicture);
        uploadButton.setOnClickListener(v -> {

            // EXTERNAL_STORAGE 권한 검사
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                showUploadDialog();
            } else {
                requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                        REQUEST_PERMISSION_UPLOAD);
            }
        });

        return rootView;
    }

    public void showUploadDialog() {

        // 업로드 방법 선택 대화상자 보이기
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent chooser = Intent.createChooser(galleryIntent, "사진 업로드");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});
        startActivityForResult(chooser, REQUEST_UPLOAD);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EXTERNAL STORAGE 읽기 권한 허용됨
        if (requestCode == REQUEST_PERMISSION_UPLOAD) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showUploadDialog();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 이미지 업로드 결과
        if (requestCode == REQUEST_UPLOAD) {
            if (resultCode == RESULT_OK && data != null) {
                // 비트맵 획득
                Bitmap bitmap = null;
                if (data.getExtras() != null) {
                    // 카메라 결과 획득
                    bitmap = (Bitmap) data.getExtras().get("data");
                } else {
                    // 갤러리(포토) 결과 획득
                    Uri uri = data.getData();
                    if (uri != null) {
                        String path = BitmapHelper.getRealPathFromUri(getActivity(), uri);
                        bitmap = BitmapHelper.getBitmapFromPath(path);
                    }
                }
                if (bitmap != null) {
                    mImageView.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(getActivity(), "업로드에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
