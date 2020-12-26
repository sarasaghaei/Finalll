package ir.sarasaghaei.final_project.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import ir.sarasaghaei.final_project.Const;
import ir.sarasaghaei.final_project.R;
import ir.sarasaghaei.final_project.broadcast.CheckInternet;
import ir.sarasaghaei.final_project.entity.Biyab;
import ir.sarasaghaei.final_project.entity.database.BiyabDBHelper;

public class AddAdvActivity extends AppCompatActivity {

    Spinner spinner, sub_spinner;
    String category, sub_category, offer;
    Button btn_insert, btn_selectimage;
    TextView btnback,et_tell;
    EditText et_title, et_details, tv_imagepath, et_price;
    CheckBox chboffer;
    String[] sub_categoreis;
    String[] categories = {"کالای دیجیتال", "لوازم خانه", "سوپرمارکتی"};
    String[] subcat_digi = {"موبایل و تبلت", "رایانه", "کنسول بازی", "ساعت هوشمند", "لوازم جانبی"};
    String[] subcat_homekala = {"لوازم برقی", "آشپزخانه", "لوازم خواب", "دکوراسیون"};
    String[] subcat_market = {"پروتین", "بهداشتی", "آشامیدنی", "خوراکی"};
    Boolean allgranted = false;
    private final int READ_EXTRA = 100;
    String thePath = "no-path-found";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addadv);

        Check_user();

        //---- for exit app
        exit.activityList.add(this);

        CheckInternet checkInternet = new CheckInternet();
        registerReceiver(checkInternet, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));

        init();
        setsetting();
    }

    private void init() {
        spinner_category();
        btn_insert = findViewById(R.id.btn_insert);
        et_title = findViewById(R.id.et_title);
        et_details = findViewById(R.id.et_details);
        et_price = findViewById(R.id.et_price);
        tv_imagepath = findViewById(R.id.tv_imagepath);
        et_tell = findViewById(R.id.et_tell);

        et_title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if(et_title.length() == 0)
                    et_title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.error_edittext, 0, 0, 0);

                }else{
                    et_title.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                }

            }
        });

        et_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if(et_price.length() == 0)
                        et_price.setCompoundDrawablesWithIntrinsicBounds(R.drawable.error_edittext, 0, 0, 0);

                }else{
                    et_price.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                }

            }
        });


        chboffer = findViewById(R.id.chboffer);
        if (chboffer.isChecked()) {
            offer = "offer";
        } else {
            offer = "0";
        }

        btn_selectimage = findViewById(R.id.btn_selectimage);
        btn_selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean BeenPermission = askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READ_EXTRA);
                if (BeenPermission) {
                    Log.e(Const.TAG, "Allow Permission  allgranted...............");
                    Intent imageIntent = new Intent(Intent.ACTION_PICK);
                    imageIntent.setType("image/*");
                    imageIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    imageIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(imageIntent, "select multiple images"), READ_EXTRA);
                }
            }
        });


        //----------------- back and exit in activity-------------
        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void spinner_category() {
        spinner = findViewById(R.id.spinner_category);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddAdvActivity.this,
                android.R.layout.simple_spinner_dropdown_item, categories);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = categories[i];
                spinner_subcategpry();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void spinner_subcategpry() {
        sub_spinner = findViewById(R.id.spinner_subcategory);

        switch (category) {
            case "کالای دیجیتال": {
                sub_categoreis = subcat_digi;
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddAdvActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, subcat_digi);
                sub_spinner.setAdapter(adapter);
            }
            break;
            case "لوازم خانه": {
                sub_categoreis = subcat_homekala;
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddAdvActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, subcat_homekala);
                sub_spinner.setAdapter(adapter);
            }
            break;
            case "سوپرمارکتی": {
                sub_categoreis = subcat_market;
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddAdvActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, subcat_market);
                sub_spinner.setAdapter(adapter);
            }
            break;

        }
        sub_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sub_category = sub_categoreis[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void Insert_record(View view) {
        String title = et_title.getText().toString();
        String description = et_details.getText().toString();
        String Tell = et_tell.getText().toString();
        String price = et_price.getText().toString();
        if ((!title.equals("") && title != null) &&
                (!Tell.equals("") && Tell != null) &&
                (!price.equals("") && price != null)) {
            Biyab new_record = new Biyab(title, description, category, sub_category, Tell, price, thePath, "", "", offer, "0");

            try {
                long result = new BiyabDBHelper(this).insert(new_record);
                Toast.makeText(AddAdvActivity.this, "آگهی شما به شماره" + String.valueOf(result) + " ثبت شد", Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK);

            } catch (Exception ex) {

            }
            finish();
        }else {
            et_tell.setCompoundDrawablesWithIntrinsicBounds(R.drawable.error_edittext, 0, 0, 0);
        }
    }

    private boolean askForPermission(String permission, Integer requestCode) {
        Boolean BeenPermission = false;
        Log.e(Const.TAG, "checkSelfPermission ...............");
        if (ActivityCompat.checkSelfPermission(this, permission) != 0) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            Log.e(Const.TAG, "requestPermissions ...............");
        } else {
            BeenPermission = true;
        }
        return BeenPermission;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_EXTRA) {
            //check if all permissions are granted

            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults.length > 0
                        && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(Const.TAG, "PERMISSION Allow ...............");
                    allgranted = true;
                    Intent imageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    imageIntent.setType("image/*");
                    imageIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(imageIntent, READ_EXTRA);
                } else {
                    Log.e(Const.TAG, "PERMISSION NOT ALLOW ...............");
                    allgranted = false;
                    break;
                }

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_EXTRA && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();

            String[] filePathColumn = {MediaStore.Images.Media.DISPLAY_NAME};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                thePath = cursor.getString(columnIndex);
                tv_imagepath.setText(thePath);
            }
            cursor.close();
        }

    }

    private void Check_user() {
        SharedPreferences sharedPreferences = getSharedPreferences(Const.SHEAREDPRF_NAME, MODE_PRIVATE);
        if (sharedPreferences.contains(Const.USER)) {
            String user = sharedPreferences.getString("user", "");
            if (!(user != "")) {
                et_tell.setText(user);
                Toast.makeText(this, "کابر گرامی شما ثبت نام نکرده اید", Toast.LENGTH_SHORT).show();
                View view = LayoutInflater.from(this).inflate(R.layout.alertdialog, null);
                AlertDialog alertDialog;
                AlertDialog.Builder bilder = new AlertDialog.Builder(this);
                bilder.setView(view);
                alertDialog = bilder.create();
                alertDialog.show();
                Log.e(Const.TAG, "SharedPreferences :           exist user");
            }
        } else {
            Const.CHECK_USER = true;
        }
    }
    private void setsetting() {
        SharedPreferences sharedPreferences = getSharedPreferences(Const.SHEAREDPRF_NAME, MODE_PRIVATE);
        if (sharedPreferences.contains("Backgroundcolor")) {
            String Backgroundcolor = sharedPreferences.getString("Backgroundcolor", "");
            if (Backgroundcolor != "") {
                View whole_layout = findViewById(R.id.base_layout);
                whole_layout.setBackgroundColor(Color.parseColor(Backgroundcolor));
            }
        }
        if (sharedPreferences.contains("Toolbarcolor")) {
            String Toolbarcolor = sharedPreferences.getString("Toolbarcolor", "");
            if (Toolbarcolor != "") {
                View drawer_layout = findViewById(R.id.layou_toolbar);
                drawer_layout.setBackgroundColor(Color.parseColor(Toolbarcolor));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = this.getWindow();
                    window.setStatusBarColor(Color.parseColor(Toolbarcolor));
                    window.setNavigationBarColor(Color.parseColor(Toolbarcolor));
                }
            }
        }

    }
}