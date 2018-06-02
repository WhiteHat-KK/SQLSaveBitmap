package kk.techbytecare.sqlsavebitmap;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import kk.techbytecare.sqlsavebitmap.DBHelper.DBHelper;
import kk.techbytecare.sqlsavebitmap.Utils.Utils;

public class ShowBitmapActivity extends AppCompatActivity {

    EditText edt_name;
    Button btn_select;
    ImageView imageView;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bitmap);

        dbHelper = new DBHelper(this);

        edt_name = findViewById(R.id.edt_name);
        btn_select = findViewById(R.id.btn_select);
        imageView = findViewById(R.id.imageView);

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                byte[] data = dbHelper.getBitmapByName(edt_name.getText().toString());

                if (data != null)   {

                    Bitmap bitmap = Utils.getImage(data);
                    imageView.setImageBitmap(bitmap);

                }
                else    {
                    Toast.makeText(ShowBitmapActivity.this, edt_name.getText().toString()+" not found!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
