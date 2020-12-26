package ir.sarasaghaei.final_project.Ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import ir.sarasaghaei.final_project.Const;
import ir.sarasaghaei.final_project.R;
import ir.sarasaghaei.final_project.broadcast.CheckInternet;

public class SettingActivity extends AppCompatActivity {
    Button btn_sc_background, btn_sc_toolbar, btn_changed,btn_reset;
    SeekBar seekbar;
    String selectcolor,backgroundcolor,toolbarcolor;
    int fontsize=10;
    AlertDialog dialog;


    TextView btnback, sc_background, sc_toolbar, tv_fontsiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Check_user();


        //---- for exit app
        exit.activityList.add(this);

        CheckInternet checkInternet = new CheckInternet();
        registerReceiver(checkInternet, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));


        init();
        setsetting();
    }


    private void init() {
        btn_sc_background = findViewById(R.id.btn_sc_background);
        btn_sc_toolbar = findViewById(R.id.btn_sc_toolbar);
        btn_changed = findViewById(R.id.btn_changed);
        btn_reset=findViewById(R.id.btn_reset);
        seekbar = findViewById(R.id.seekbar);
        btnback = findViewById(R.id.btnback);
        sc_background = findViewById(R.id.sc_background);
        sc_toolbar = findViewById(R.id.sc_toolbar);
        tv_fontsiz = findViewById(R.id.tv_fontsiz);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_fontsiz.setTextSize(progress);
                fontsize = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        btn_changed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(Const.SHEAREDPRF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Backgroundcolor", backgroundcolor);
                editor.putString("Toolbarcolor", toolbarcolor);
                editor.putString("Fontsize", String.valueOf(fontsize));
                editor.apply();
                dialogshow();
            }
        });
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(Const.SHEAREDPRF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("Backgroundcolor");
                editor.remove("Toolbarcolor");
                editor.remove("Fontsize");
                editor.apply();
                dialogshow();
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

    public void Check_user() {
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

    public void onclick(View view) {
        Button select = (Button) view;
        final String view_name = select.getResources().getResourceEntryName(view.getId());
        ColorPickerDialogBuilder
                .with(SettingActivity.this)
                .setTitle("Choose color")
                .initialColor(0xffffffff)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                        selectcolor = String.valueOf(selectedColor);
                        String colorint = "#" + selectcolor;
                        if (view_name.equals("btn_sc_background")) {
                            sc_background.setBackgroundColor(Color.parseColor(colorint));
                            backgroundcolor = colorint;
                        } else {
                            sc_toolbar.setBackgroundColor(Color.parseColor(colorint));
                            toolbarcolor = colorint;
                        }

                        Toast.makeText(SettingActivity.this, "" + Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();


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

    private void dialogshow(){
        View view_dialog = LayoutInflater.from(SettingActivity.this).inflate(R.layout.alertdialog, null);
        TextView dialogicon =(TextView) view_dialog.findViewById(R.id.dialogicon);
        dialogicon.setBackground(getResources().getDrawable(R.drawable.exitapp));

        TextView dialogtitle =(TextView) view_dialog.findViewById(R.id.dialogtitle);
        dialogtitle.setText("خروج از برنامه");

        TextView dialogmessage =(TextView) view_dialog.findViewById(R.id.dialogmessage);
        dialogmessage.setText("برای اعمال تغییرات برنامه را مجداا راه اجرا کنید");

        Button btn_ok =(Button) view_dialog.findViewById(R.id.btn_ok);
        btn_ok.setText("خروج");

        Button btn_exit =(Button)view_dialog.findViewById(R.id.btn_exit);
        btn_exit.setText("ادامه");

        AlertDialog.Builder bilder = new AlertDialog.Builder(SettingActivity.this);
        bilder.setView(view_dialog);
        dialog = bilder.create();
        dialog.show();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Activity activity : exit.activityList) {
                    activity.finish();


                }
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
