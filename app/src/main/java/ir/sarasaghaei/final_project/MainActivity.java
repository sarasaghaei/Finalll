package ir.sarasaghaei.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import ir.sarasaghaei.final_project.Ui.AboutActivity;
import ir.sarasaghaei.final_project.Ui.AddAdvActivity;
import ir.sarasaghaei.final_project.Ui.DetialsActivity;
import ir.sarasaghaei.final_project.Ui.Map_Location;
import ir.sarasaghaei.final_project.Ui.DownMusicActivity;
import ir.sarasaghaei.final_project.Ui.ResultSearchActivity;
import ir.sarasaghaei.final_project.Ui.SettingActivity;
import ir.sarasaghaei.final_project.Ui.exit;
import ir.sarasaghaei.final_project.adapter.BiyabAdapter;
import ir.sarasaghaei.final_project.adapter.RecyclerAdapter;
import ir.sarasaghaei.final_project.broadcast.CheckInternet;
import ir.sarasaghaei.final_project.entity.Biyab;
import ir.sarasaghaei.final_project.entity.database.BiyabDBHelper;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton floatingbtnadd;
    SliderLayout mDemoSlider;
    EditText etSearch;
    ListView listview_biyab;
    GridView gridView;
    BiyabAdapter adapter;
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView_biyab;
    BottomNavigationView bottom_menu;
    Dialog bottomsheet;
    View view_bottomsheet;
    LocationManager locationManager;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //---- for exit app
        exit.activityList.add(this);
     //   ((SplashActivity)this.getApplicationContext()).finish();

        Check_user();

        //-----Check internet statuse
        CheckInternet checkInternet = new CheckInternet();
        registerReceiver(checkInternet, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        set_toolbar();
        set_NavigationView();
        init();
        DataSlider();
        setsetting();
    }

    private void set_toolbar() {
        DrawerLayout drawer_layout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.layou_toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colotTitle));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawer_layout, toolbar, 0, 0);

        drawer_layout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }

    private void set_NavigationView() {
        navigationView = findViewById(R.id.nav_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_menu_MyBiyab:
                        //Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_menu_setting:
                        Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_menu_location:
                        GPS_Statuse();
                        break;
                    case R.id.nav_menu_about:
                        Intent intent1 = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent1);
                        break;
                }
                return false;
            }
        });
    }

    private void init() {


        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        etSearch = findViewById(R.id.etSearch);
        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                etSearch.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        });
        //---------- search in database
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search = etSearch.getText().toString();
                Toast.makeText(MainActivity.this, search, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ResultSearchActivity.class);
                intent.putExtra("Textsearch", search);
                startActivity(intent);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        listview_biyab = findViewById(R.id.listview);
        adapter = new BiyabAdapter(this, new BiyabDBHelper(this).select("کالای دیجیتال"), 1);
        listview_biyab.setAdapter(adapter);

        listview_biyab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Biyab biyab_selected = (Biyab) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, DetialsActivity.class);
                intent.putExtra("id", biyab_selected.getId());
                startActivity(intent);
            }
        });

        recyclerView_biyab = findViewById(R.id.recylerview);
        recyclerAdapter = new RecyclerAdapter(this, new BiyabDBHelper(this).select("سوپرمارکتی", "offer"));
        recyclerView_biyab.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_biyab.setLayoutManager(layoutManager);
        recyclerView_biyab.setAdapter(recyclerAdapter);
        recyclerView_biyab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView imgmarkety = findViewById(R.id.imgmarkety);
                imgmarkety.setVisibility(View.GONE);
                return false;
            }
        });
       /* recyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {

            }
        });*/

       /* recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                *//*Intent intent = new Intent(MainActivity.this, DetialsActivity.class);
                intent.putExtra("id", position);
                startActivity(intent);*//*
                Log.e(Const.TAG, "onItemClick position: " + position);
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.e(Const.TAG, "onItemClick position: " + position);
            }
        });*/


        gridView = findViewById(R.id.gridview);
        adapter = new BiyabAdapter(this, new BiyabDBHelper(this).select("لوازم خانه"), 2);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Biyab biyab_selected = (Biyab) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, DetialsActivity.class);
                intent.putExtra("id", biyab_selected.getId());
                startActivity(intent);
            }
        });

        floatingbtnadd = findViewById(R.id.flotingadd);
        floatingbtnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddAdvActivity.class);
                startActivity(intent);

            }
        });
        bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home: {
                        ScrollView scrollView = findViewById(R.id.scrollView);
                        scrollView.scrollTo(0, 0);
                        return true;
                    }
                    case R.id.menu_downlode: {
                        Intent intent = new Intent(MainActivity.this, DownMusicActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.menu_category: {
                        bottomsheet();
                        return true;
                    }
                    case R.id.menu_setting: {
                        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(intent);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void DataSlider() {
        TextSliderView textSliderView = new TextSliderView(this);
        TextSliderView textSliderView1 = new TextSliderView(this);
        TextSliderView textSliderView2 = new TextSliderView(this);

        textSliderView.image(R.drawable.slideshow);
        textSliderView1.image(R.drawable.slideshow1);
        textSliderView2.image(R.drawable.slideshow2);

        mDemoSlider.addSlider(textSliderView);
        mDemoSlider.addSlider(textSliderView1);
        mDemoSlider.addSlider(textSliderView2);
        mDemoSlider.setDuration(4000);
    }

    public void onclick_imgv(View view) {
        TextView imgv = (TextView) view;
        String view_name = imgv.getTag().toString();
        switch (view_name) {
            case "imgv1": {
                Toast.makeText(this, "click imgv1", Toast.LENGTH_SHORT).show();
                break;
            }
            case "imgv2": {
                Toast.makeText(this, "click imgv2", Toast.LENGTH_SHORT).show();
                break;
            }
            case "imgv3": {
                Toast.makeText(this, "click imgv3", Toast.LENGTH_SHORT).show();
                break;
            }
            case "imgv4": {
                Toast.makeText(this, "click imgv4", Toast.LENGTH_SHORT).show();
                break;
            }

        }
    }

    private void bottomsheet() {
        view_bottomsheet = getLayoutInflater().inflate(R.layout.category, null);

        bottomsheet = new Dialog(MainActivity.this, R.style.MaterialDialogSheet);
        bottomsheet.setContentView(view_bottomsheet);
        bottomsheet.setCancelable(true);
        bottomsheet.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        bottomsheet.getWindow().setGravity(Gravity.BOTTOM);
        bottomsheet.show();
    }

    public void onclick_viewcategory(View view) {
        TextView submenu = (TextView) view;
        String view_name = submenu.getResources().getResourceEntryName(view.getId());
        String submenu_tag = submenu.getTag().toString();
        switch (view_name) {
            case "sub_cat_digi": {
                View submenu_digi = view_bottomsheet.findViewById(R.id.submenu_digi);
                if (submenu_tag.equals("menu")) {
                    submenu.setBackground(getResources().getDrawable(R.drawable.arrow_left));
                    submenu.setTag("submenu");
                    submenu_digi.setVisibility(View.VISIBLE);
                } else {
                    submenu.setBackground(getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_24));
                    submenu.setTag("menu");
                    submenu_digi.setVisibility(View.GONE);
                }
                break;
            }
            case "sub_cat_home_device": {
                View submenu_home_device = view_bottomsheet.findViewById(R.id.submenu_home_device);
                if (submenu_tag.equals("menu")) {
                    submenu.setBackground(getResources().getDrawable(R.drawable.arrow_left));
                    submenu.setTag("submenu");
                    submenu_home_device.setVisibility(View.VISIBLE);
                } else {
                    submenu.setBackground(getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_24));
                    submenu.setTag("menu");
                    submenu_home_device.setVisibility(View.GONE);
                }
                break;
            }
            case "sub_cat_market": {
                View submenu_market = view_bottomsheet.findViewById(R.id.submenu_market);
                if (submenu_tag.equals("menu")) {
                    submenu.setBackground(getResources().getDrawable(R.drawable.arrow_left));
                    submenu.setTag("submenu");
                    submenu_market.setVisibility(View.VISIBLE);
                } else {
                    submenu.setBackground(getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_24));
                    submenu.setTag("menu");
                    submenu_market.setVisibility(View.GONE);
                }
                break;
            }
        }
    }

    public void onclick_subcategory(View view) {
        TextView cat = (TextView) view;
        Intent intent = new Intent(MainActivity.this, ResultSearchActivity.class);
        intent.putExtra("type", "subcategory");
        intent.putExtra("cat", cat.getText().toString());
        startActivity(intent);
    }

    public void onclick_category(View view) {
        TextView cat = (TextView) view;
        Intent intent = new Intent(MainActivity.this, ResultSearchActivity.class);
        intent.putExtra("type", "category");
        intent.putExtra("cat", cat.getText().toString());
        startActivity(intent);
    }

    private void Check_user() {
        SharedPreferences sharedPreferences = getSharedPreferences(Const.SHEAREDPRF_NAME, MODE_PRIVATE);
        if (!sharedPreferences.contains(Const.USER)) {
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

    private void GPS_Statuse() {
        Boolean isenableGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isenableGPS) {
            Toast.makeText(this, "GPS_Statuse is Enable", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, Map_Location.class);
            startActivity(intent);

        } else {
            AlertDialog bilder = new AlertDialog.Builder(this, R.style.AlertDialogCustom)
                    .setTitle(R.string.nogps)
                    .setMessage("برای نمایش موقعیت خود لطفا GPS خود را روشن کنید")
                    .setIcon(R.drawable.gps)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("cancel", null)
                    .show();
            Toast.makeText(this, "GPS_Statuse is Disable", Toast.LENGTH_SHORT).show();
        }
    }

    private void setsetting() {
        SharedPreferences sharedPreferences = getSharedPreferences(Const.SHEAREDPRF_NAME, MODE_PRIVATE);
        if (sharedPreferences.contains("Backgroundcolor")) {
            String Backgroundcolor = sharedPreferences.getString("Backgroundcolor", "");
            if (Backgroundcolor != "") {
                View whole_layout = findViewById(R.id.base_layout);
                navigationView.setBackgroundColor(Color.parseColor(Backgroundcolor));
                whole_layout.setBackgroundColor(Color.parseColor(Backgroundcolor));
            }
        }
        if (sharedPreferences.contains("Toolbarcolor")) {
            String Toolbarcolor = sharedPreferences.getString("Toolbarcolor", "");
            if (Toolbarcolor != "") {
                View drawer_layout = findViewById(R.id.layou_toolbar);

                View header_menu = getLayoutInflater().from(this).inflate(R.layout.header_menu,null);
                View layou_toolbar1 = header_menu.findViewById(R.id.layou_toolbar);

                layou_toolbar1.setBackgroundColor(Color.parseColor(Toolbarcolor));
               /* View bottom_sheet = view_bottomsheet.findViewById(R.id.bottom_sheet);
                bottom_sheet.setBackgroundColor(Color.parseColor(Toolbarcolor));*/

                bottom_menu.setBackgroundColor(Color.parseColor(Toolbarcolor));
                floatingbtnadd.setBackgroundColor(Color.parseColor(Toolbarcolor));
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