package com.example.siemas.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siemas.BuildConfig;
import com.example.siemas.InterfaceApi;
import com.example.siemas.R;
import com.example.siemas.RetrofitClientInstance;
import com.example.siemas.RoomDatabase.Entities.User;
import com.example.siemas.RoomDatabase.ViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ViewModel mviewModel;
    private ImageButton logoutBtn;
    private TextView tvVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mviewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModel.class);
        logoutBtn = findViewById(R.id.logout_btn);
        tvVersion = findViewById(R.id.textVersion);
        // disallowed dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (mviewModel.getUser() == null && mviewModel.getUser().size()==0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Login Terlebih Dahulu", Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        tvVersion.setText("Version: "+ BuildConfig.VERSION_NAME);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
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
        // MENU DSBS
        CardView dsbsMenu = findViewById(R.id.dsbsMenu);
        dsbsMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DsbsActivity.class);
                startActivity(intent);
            }
        });

        // MENU PENCACAHAN
        CardView pencacahanMenu = findViewById(R.id.pencacahanMenu);
        pencacahanMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PencacahanActivity.class);
                startActivity(intent);
            }
        });

        // MENU PEMERIKSAAN
        CardView pemeriksaanMenu = findViewById(R.id.pemeriksaanMenu);
        pemeriksaanMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PemeriksaanPclActivity.class);
                startActivity(intent);
            }
        });

        // MENU DIREKTORY
        CardView direktoriMenu = findViewById(R.id.direktoriMenu);
        direktoriMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DirectoryActivity.class);
                startActivity(intent);
            }
        });

        // MENU REPORT
        CardView reportMenu = findViewById(R.id.reportMenu);
        reportMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReportPclActivity.class);
                startActivity(intent);
            }
        });

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
            final ProgressDialog pd= new ProgressDialog(MainActivity.this);
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