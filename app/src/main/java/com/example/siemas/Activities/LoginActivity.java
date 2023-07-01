package com.example.siemas.Activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // disallowed dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // declare variable
        setContentView(R.layout.activity_login);
        ViewModel mviewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModel.class);
        EditText email = findViewById(R.id.emailtext);
        EditText password = findViewById(R.id.passwordtext);
        Button login_btn = findViewById(R.id.login_btn);
        ImageButton showPass = findViewById(R.id.show_pass1);
        showPass.setImageResource(R.drawable.ic_eye_off);
        TextView tvVersion = findViewById(R.id.versionText);

        tvVersion.setText("Version: "+ BuildConfig.VERSION_NAME);

        String TAG = "Permsission : ";
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");

            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA}, 101);

            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");

        }

        List<User> users = mviewModel.getUser();
        if (users != null && users.size() > 0) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Logged",
                    Toast.LENGTH_SHORT);
            toast.show();
            // LEVEL PENCACAH
            if (users.get(0).getRole().equals("PENCACAH")) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }

            // LEVEL PENGAWAS
            if (users.get(0).getRole().equals("PENGAWAS")) {
                Intent intent = new Intent(getApplicationContext(), MainActivityPML.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }

        }

        // show password
        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getInputType() == InputType.TYPE_CLASS_TEXT) {
                    showPass.setImageResource(R.drawable.ic_eye_off);
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                } else {
                    showPass.setImageResource(R.drawable.ic_eye);
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                password.setSelection(password.length());
            }
        });

        // login
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                    alertDialogBuilder.setTitle("SIEMAS");
                    alertDialogBuilder.setMessage("Email  & password harus diisi");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    login(email.getText().toString(), password.getText().toString(), mviewModel);
                }

            }
        });
    }

    private void login(String email, String password, ViewModel viewModel) {
        boolean status_net = getConnectivityStatusString(getApplicationContext());
        if (!status_net) {
            Toast.makeText(getApplicationContext(), "Koneksi terputus, periksa koneksi internet anda.", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("Loging In");
            pd.show();
            InterfaceApi interfaceApi = RetrofitClientInstance.getClient().create(InterfaceApi.class);
            Call<ResponseBody> call = interfaceApi.login_android(email, password);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        try {
                            String result = response.body().string();
                            JSONObject jo = new JSONObject(result);
                            String message = jo.getString("message");
                            if (message.equalsIgnoreCase("success")) {
                                JSONObject objectUser = new JSONObject(jo.getString("body"));
                                String strRole = objectUser.getString("roles");
                                String strRole1 = strRole.replace("[", "");
                                String strRole2 = strRole1.replace("]", "");
                                JSONObject objectRole = new JSONObject(strRole2);
                                User user = new User(
                                        Integer.parseInt(objectUser.getString("id")),
                                        objectUser.getString("email"),
                                        objectUser.getString("name"),
                                        objectUser.getString("kd_kab"),
                                        objectUser.getString("nama_kab"),
                                        objectRole.getString("name"),
                                        objectUser.getString("token")
                                );

                                viewModel.insertUser(user);
                                Log.d(TAG, "onResponse: "+user.getToken());
                                viewModel.getPeriodeFromServer(LoginActivity.this);
                                // intent to main
                                Thread.sleep(5000);
                                String role = objectRole.getString("name");

                                // LEVEL PENCACAH
                                if (role.equals("PENCACAH")) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    pd.dismiss();
                                    finish();
                                }

                                // LEVEL PENGAWAS
                                if (role.equals("PENGAWAS")) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivityPML.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    pd.dismiss();
                                    finish();
                                }


                            } else {
                                pd.dismiss();
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        jo.getString("message"),
                                        Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    pd.dismiss();
                    Toast toast = Toast.makeText(getApplicationContext(),
                            t.toString(),
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    }

    // Cek Koneksi
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
}