package com.example.siemas.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siemas.AlarmReceiver;
import com.example.siemas.BuildConfig;
import com.example.siemas.InterfaceApi;
import com.example.siemas.R;
import com.example.siemas.RetrofitClientInstance;
import com.example.siemas.RoomDatabase.Entities.Jadwal212;
import com.example.siemas.RoomDatabase.Entities.User;
import com.example.siemas.RoomDatabase.ViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPML extends AppCompatActivity {
    private ViewModel mviewModel;
    private ImageButton logoutBtn;
    private User user;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Calendar calendar;
    private List<Jadwal212> jadwal212List;
    private TextView tvVersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.siemas.R.layout.activity_main_pml);

        // disallowed dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        mviewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModel.class);
        logoutBtn = findViewById(R.id.logout_btn);
        user = mviewModel.getUser().get(0);
        createNotificationChannel();

        if (mviewModel.getUser() == null && mviewModel.getUser().size()==0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Login Terlebih Dahulu", Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        tvVersion = findViewById(R.id.textVersion);
        tvVersion.setText("Version: "+ BuildConfig.VERSION_NAME);



        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivityPML.this);
                alertDialogBuilder.setTitle("SIEMAS 2022");
                alertDialogBuilder.setMessage("Anda yakin ingin logout? Data yang tersimpan di local akan terhapus.");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        logout(mviewModel);
                    }
                });
                alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        // MENU PROFILE
        CardView profileMenu = findViewById(R.id.profileMenu);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        // MENU SYNC
        CardView syncMenu = findViewById(R.id.syncMenu);
        syncMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivityPML.this);
                alertDialogBuilder.setTitle("SIEMAS 2022");
                alertDialogBuilder.setMessage("Anda yakin ingin melakukan Sync Data? Jika ini bukan pertama kali anda melakukan sync data, data yang tersimpan di local dan belum diupload akan terganti dengan data dari server.");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mviewModel.getDsbsPmlFromApi(MainActivityPML.this, user.getEmail(), user.getToken());
                        mviewModel.getDsrtPmlFromApi(MainActivityPML.this, user.getEmail(), user.getToken());
                        mviewModel.getJadwalFromApi(MainActivityPML.this, user.getEmail(),user.getToken());
                        mviewModel.getLaporanFromApi(MainActivityPML.this, user.getEmail(),user.getToken());
                        mviewModel.getDsartPmlFromApi(MainActivityPML.this, user.getEmail(),user.getToken());
                    }
                });
                alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        // MENU ALARM
        CardView notifMenu = findViewById(R.id.notifMenu);
        notifMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jadwal212List = mviewModel.getListJadwal();

                if (jadwal212List.size()>0){
                    for (int i = 0; i < jadwal212List.size(); i++) {
                        String stringDate = jadwal212List.get(i).getTanggal();
                        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                        setAlarm(stringDate);
                    }
                } else {
                    Toast.makeText(MainActivityPML.this, "Silakan sync data terlebih dahulu untuk mendapatkan jadwal 212", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // MENU PEMERIKSAAN
        CardView pemeriksaanMenu = findViewById(R.id.pemeriksaanMenu);
        pemeriksaanMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PemeriksaanPmlActivity.class);
                startActivity(intent);
            }
        });

        // MENU INPUT 212
        CardView input212Menu = findViewById(R.id.input212Menu);
        input212Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Input212Activity.class);
                startActivity(intent);
            }
        });

        // MENU REPORT
        CardView reportMenu = findViewById(R.id.reportMenu);
        reportMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReportPmlActivity.class);
                startActivity(intent);
            }
        });


    }

    private void setAlarm(String stringDate){
        final int idPending = (int) System.currentTimeMillis();
        stringDate = stringDate+" 08:00:00";


        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");

        try {
            Date date1 = sdf.parse(stringDate);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date1);
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, idPending, intent, PendingIntent.FLAG_ONE_SHOT);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent);
            Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CharSequence name = "siemas";
            String desc = "Channel alarm manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("siemas", name, importance);
            channel.setDescription(desc);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    public Boolean getConnectivityStatusString(Context context) {
        String status = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = "Wifi enabled";
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = "Mobile data enabled";
                return true;
            }
        } else {
            status = "No internet is available";
            return false;
        }
        return false;
    }

    //  Fungsi Logout
    private void logout(ViewModel mviewModel) {
        boolean status_net = getConnectivityStatusString(getApplicationContext());
        if (!status_net){
            Toast.makeText(getApplicationContext(), "Koneksi terputus, periksa koneksi internet anda.", Toast.LENGTH_SHORT).show();
        } else {
            String email = mviewModel.getUser().get(0).getEmail();
            InterfaceApi interfaceApi = RetrofitClientInstance.getClient().create(InterfaceApi.class);
            final ProgressDialog pd= new ProgressDialog(MainActivityPML.this);
            pd.setMessage("Loging Out");
            pd.show();

            Call<ResponseBody> call = interfaceApi.logout_android(email, "Bearer " + mviewModel.getUser().get(0).getToken());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        String result = response.body().string();
                        JSONObject object = new JSONObject(result);
                        String message = object.getString("message");
                        if (message.equals("success")){
                            // destroy data
                            mviewModel.nukeUser();
                            mviewModel.nukeDsbs();
                            mviewModel.nukeDsrt();
                            mviewModel.nukeLaporan();

                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Berhasil logout.", Toast.LENGTH_SHORT).show();
                        } else {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Gagal logout.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Gagal logout.", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
}