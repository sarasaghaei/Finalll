package ir.sarasaghaei.final_project.Ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import ir.sarasaghaei.final_project.Const;
import ir.sarasaghaei.final_project.R;
import ir.sarasaghaei.final_project.adapter.RecyclerAdapter;
import ir.sarasaghaei.final_project.broadcast.CheckInternet;
import ir.sarasaghaei.final_project.entity.Biyab;
import ir.sarasaghaei.final_project.entity.database.BiyabDBHelper;

public class DetialsActivity extends AppCompatActivity {
    TextView btnback, sharing;
    TextView etTitle, etDescription, etPrice;
    int id;
    String Tell;
    Button btnMessage, btncall;
    Intent intent;
    Biyab biyab;
    SliderLayout mDemoSlider;
    RatingBar ratingBar;
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView_biyab;
    String subcat = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detials);

        Check_user();

        //---- for exit app
        exit.activityList.add(this);

        CheckInternet checkInternet = new CheckInternet();
        registerReceiver(checkInternet, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));

        intent = getIntent();
        init();
        Intent intent = getIntent();
        if (intent == null) {
            fileList();
            return;
        }
        id = intent.getIntExtra("id", 0);
        set_data(id);
        DataSlider();
        setsetting();

    }

    private void init() {
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        ratingBar = findViewById(R.id.ratingBar);
        etTitle = findViewById(R.id.etTitle);
        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });
        etDescription = findViewById(R.id.etDescription);

        etPrice = findViewById(R.id.etPrice);

        btnMessage = findViewById(R.id.btnMessage);
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendmessage = "sms:" + Tell;
                Intent sendintent = new Intent(Intent.ACTION_VIEW, Uri.parse(sendmessage));
                startActivity(sendintent);
            }
        });
        btncall = findViewById(R.id.btncall);
        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendCall = "tel:" + Tell;
                Toast.makeText(DetialsActivity.this, sendCall, Toast.LENGTH_SHORT).show();
                Intent callintent = new Intent(Intent.ACTION_VIEW, Uri.parse(sendCall));
                startActivity(callintent);
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


        //----------- sharing  --------------
        sharing = findViewById(R.id.sharing);
        sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Url = "http://Biyab.com";
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, Url);
                startActivity(Intent.createChooser(intent, "Share is"));
            }
        });
        recyclerView_biyab = findViewById(R.id.recylerview);
        recyclerView_biyab.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(DetialsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_biyab.setLayoutManager(layoutManager);

    }

    private void set_data(int id) {
        biyab = new BiyabDBHelper(this).select(id);
        ratingBar.setRating(Float.valueOf(biyab.getRank()));
        etTitle.setText(biyab.getTitle());
        etDescription.setText(biyab.getDescription());
        subcat = biyab.getSub_category();
        recyclerAdapter = new RecyclerAdapter(this, new BiyabDBHelper(this).select_subcat(subcat));
        recyclerView_biyab.setAdapter(recyclerAdapter);
        etPrice.setText(biyab.getPrice());
        Tell = biyab.getTell();

    }

    private void DataSlider() {
        TextSliderView textSliderView;
        TextSliderView textSliderView1;
        TextSliderView textSliderView2;


        if (biyab.getImage1() != null && !biyab.getImage1().equals("")) {
            textSliderView = new TextSliderView(this);
            textSliderView.image(biyab.getImage1());
            mDemoSlider.addSlider(textSliderView);
        }
        if (biyab.getImage2() != null && !biyab.getImage2().equals("")) {
            textSliderView1 = new TextSliderView(this);
            textSliderView1.image(biyab.getImage2());
            mDemoSlider.addSlider(textSliderView1);
        }
        if (biyab.getImage3() != null && !biyab.getImage3().equals("")) {
            textSliderView2 = new TextSliderView(this);
            textSliderView2.image(biyab.getImage3());
            mDemoSlider.addSlider(textSliderView2);
        }


        mDemoSlider.setDuration(4000);
    }

    private void Check_user() {
        SharedPreferences sharedPreferences = getSharedPreferences(Const.SHEAREDPRF_NAME, MODE_PRIVATE);
        if (!sharedPreferences.contains(Const.USER)) {
            Toast.makeText(this, "کابر گرامی شما ثبت نام نکرده اید", Toast.LENGTH_SHORT).show();
            View view_dialog_Checkuser = LayoutInflater.from(this).inflate(R.layout.alertdialog_checkuser, null);
            final AlertDialog dialog_Checkuser;
            AlertDialog.Builder bilder = new AlertDialog.Builder(this);
            bilder.setView(view_dialog_Checkuser);
            dialog_Checkuser = bilder.create();
            dialog_Checkuser.show();
            Button btndialog_login = (Button) view_dialog_Checkuser.findViewById(R.id.btndialog_login);
            Button btndialog_cancel = (Button) view_dialog_Checkuser.findViewById(R.id.btndialog_cancel);
            final View layout_view = (View) view_dialog_Checkuser.findViewById(R.id.l_login);
            btndialog_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout_view.setVisibility(View.VISIBLE);
                }
            });

            btndialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_Checkuser.dismiss();
                    finish();
                }
            });
            Button btnlogin = (Button) view_dialog_Checkuser.findViewById(R.id.btnlogin);
            final EditText etphone = (EditText) view_dialog_Checkuser.findViewById(R.id.etphone);
            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone_number = etphone.getText().toString();
                    if (!phone_number.matches("(\\+98|0)?9\\d{9}")) {
                        Toast.makeText(getApplicationContext(), "شماره وارد شده معتبر نمی باشد دوباره بررسی کنید", Toast.LENGTH_SHORT).show();
                    } else {
                        if (phone_number != null && !phone_number.equals("")) {
                            SharedPreferences sharedPreferences = getSharedPreferences(Const.SHEAREDPRF_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Const.USER, phone_number);
                            Log.e(Const.TAG, "SharedPreferences :           creat user");
                            editor.apply();
                            Const.CHECK_USER = true;
                            dialog_Checkuser.dismiss();
                            Toast.makeText(getApplicationContext(), "ثبت نام شما با موفقیت انجام شد", Toast.LENGTH_LONG).show();
                        }
                    }

                }

            });


            Log.e(Const.TAG, "SharedPreferences :      Not   exist user");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Biyab biyab_update = new Biyab(id, String.valueOf(ratingBar.getRating()));
        int result = new BiyabDBHelper(DetialsActivity.this).update_rank(biyab_update);

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
        if (sharedPreferences.contains("Fontsize")) {
            String Fontsize = sharedPreferences.getString("Fontsize", "");
            if (Fontsize != "") {
                etTitle.setTextSize(Float.parseFloat(Fontsize));
                etDescription.setTextSize(Float.parseFloat(Fontsize));

            }
        }

    }
}