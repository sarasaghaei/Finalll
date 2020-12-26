package ir.sarasaghaei.final_project.Ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ir.sarasaghaei.final_project.Const;
import ir.sarasaghaei.final_project.R;
import ir.sarasaghaei.final_project.adapter.BiyabAdapter;
import ir.sarasaghaei.final_project.broadcast.CheckInternet;
import ir.sarasaghaei.final_project.entity.Biyab;
import ir.sarasaghaei.final_project.entity.database.BiyabDBHelper;

public class ResultSearchActivity extends AppCompatActivity {
    ListView listview_result;
    BiyabAdapter adapter;
    String name, cat, search;
    TextView etSearch,btn_sortgrid,btn_sortlist;
    GridView gridView_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resut_search);

        Toolbar toolbar = findViewById(R.id.layou_toolbar);
        setSupportActionBar(toolbar);

        Check_user();

        //---- for exit app
        exit.activityList.add(this);


        CheckInternet checkInternet = new CheckInternet();
        registerReceiver(checkInternet, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        Intent intent = getIntent();
        name = intent.getStringExtra("type");
        cat = intent.getStringExtra("cat");
        search = intent.getStringExtra("Textsearch");
        init();
        setsetting();

    }

    private void init() {
        btn_sortlist = findViewById(R.id.btn_sortlist);
        btn_sortgrid = findViewById(R.id.btn_sortgrid);

        etSearch = findViewById(R.id.etSearch);
        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                if (event.getX() >= (etSearch.getWidth() - etSearch
                        .getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                    //Toast.makeText(ResutSearchActivity.this, "clicl baaaaaaaaaack", Toast.LENGTH_SHORT).show();
                    finish();
                    return true;
                }
                return false;
            }
        });
        etSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_arrow_back_24, 0, 0, 0);

        //---------- search in database
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search = etSearch.getText().toString();
                adapter = new BiyabAdapter(ResultSearchActivity.this, new BiyabDBHelper(ResultSearchActivity.this).select_search(search), 0);
                listview_result.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listview_result = findViewById(R.id.listview);
        gridView_result = findViewById(R.id.gridview);
        if (name != null && name.equals("category")) {
            adapter = new BiyabAdapter(this, new BiyabDBHelper(this).select(cat), 0);
            listview_result.setAdapter(adapter);
            gridView_result.setAdapter(adapter);
        } else if (name != null && name.equals("subcategory")) {
            adapter = new BiyabAdapter(this, new BiyabDBHelper(this).select_subcat(cat), 0);
            listview_result.setAdapter(adapter);
            gridView_result.setAdapter(adapter);
        }
        if (search != null) {
            etSearch.setText(search);
            etSearch.setFocusable(true);
            etSearch.setClickable(true);
            adapter = new BiyabAdapter(this, new BiyabDBHelper(this).select_search(search), 0);
            listview_result.setAdapter(adapter);
            gridView_result.setAdapter(adapter);
        }

        listview_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Biyab biyab_selected = (Biyab) parent.getItemAtPosition(position);
                Intent intent = new Intent(ResultSearchActivity.this, DetialsActivity.class);
                intent.putExtra("id", biyab_selected.getId());
                startActivity(intent);
            }
        });

        gridView_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Biyab biyab_selected = (Biyab) parent.getItemAtPosition(position);
                Intent intent = new Intent(ResultSearchActivity.this, DetialsActivity.class);
                intent.putExtra("id", biyab_selected.getId());
                startActivity(intent);
            }
        });

        btn_sortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridView_result.setVisibility(View.GONE);
                listview_result.setVisibility(View.VISIBLE);
            }
        });

        btn_sortgrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listview_result.setVisibility(View.GONE);
                gridView_result.setVisibility(View.VISIBLE);
            }
        });
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
                View viewgrid = getLayoutInflater().from(this).inflate(R.layout.gridview_biyab,null);
                TextView etTitle =(TextView) viewgrid.findViewById(R.id.etTitle);
                etTitle.setTextSize(Float.parseFloat(Fontsize));

                TextView etPrice =(TextView) viewgrid.findViewById(R.id.etPrice);
                etPrice.setTextSize(Float.parseFloat(Fontsize));

                View viewlist = getLayoutInflater().from(this).inflate(R.layout.gridview_biyab,null);
                etTitle =(TextView) viewlist.findViewById(R.id.etTitle);
                etTitle.setTextSize(Float.parseFloat(Fontsize));

                etPrice =(TextView) viewlist.findViewById(R.id.etPrice);
                etPrice.setTextSize(Float.parseFloat(Fontsize));
            }
        }

    }
}