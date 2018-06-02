package kk.techbytecare.sqlsavebitmap;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import kk.techbytecare.sqlsavebitmap.DBHelper.DBHelper;
import kk.techbytecare.sqlsavebitmap.Utils.Utils;

public class SelectActivity extends AppCompatActivity {

    private static final int SELECT_PHOTO = 7777;

    ImageView imageView;
    Button btnChoose,btnSave;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        dbHelper = new DBHelper(this);

        imageView = findViewById(R.id.imageView);
        btnChoose = findViewById(R.id.select_image);
        btnSave = findViewById(R.id.save_image);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,SELECT_PHOTO);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

                AlertDialog.Builder builder = new AlertDialog.Builder(SelectActivity.this);
                builder.setTitle("Enter Image Name");

                final EditText editText = new EditText(SelectActivity.this);
                builder.setView(editText);

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        if (!TextUtils.isEmpty(editText.getText().toString()))  {

                            dbHelper.addBitmap(editText.getText().toString(), Utils.getBytes(bitmap));

                            Toast.makeText(SelectActivity.this, "Saved Successful..", Toast.LENGTH_SHORT).show();

                        }
                        else    {
                            Toast.makeText(SelectActivity.this, "Plz enter the name of uploaded image...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {

            Uri pickedImage = data.getData();

            imageView.setImageURI(pickedImage);

            btnSave.setEnabled(true);
        }
    }
}
