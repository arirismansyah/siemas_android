package com.example.siemas.RoomDatabase;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.siemas.InterfaceApi;
import com.example.siemas.RetrofitClientInstance;
import com.example.siemas.RoomDatabase.Dao.DsartDao;
import com.example.siemas.RoomDatabase.Dao.DsbsDao;
import com.example.siemas.RoomDatabase.Dao.DsrtDao;
import com.example.siemas.RoomDatabase.Dao.FotoDao;
import com.example.siemas.RoomDatabase.Dao.Jadwal212Dao;
import com.example.siemas.RoomDatabase.Dao.KegiatanUtamaDao;
import com.example.siemas.RoomDatabase.Dao.Laporan212Dao;
import com.example.siemas.RoomDatabase.Dao.PendidikanDao;
import com.example.siemas.RoomDatabase.Dao.PeriodeDao;
import com.example.siemas.RoomDatabase.Dao.StatusDao;
import com.example.siemas.RoomDatabase.Dao.StatusRumahDao;
import com.example.siemas.RoomDatabase.Dao.UserDao;
import com.example.siemas.RoomDatabase.Entities.Dsart;
import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.Foto;
import com.example.siemas.RoomDatabase.Entities.Jadwal212;
import com.example.siemas.RoomDatabase.Entities.KegiatanUtama;
import com.example.siemas.RoomDatabase.Entities.Laporan212;
import com.example.siemas.RoomDatabase.Entities.Pendidikan;
import com.example.siemas.RoomDatabase.Entities.Periode;
import com.example.siemas.RoomDatabase.Entities.Status;
import com.example.siemas.RoomDatabase.Entities.StatusRumah;
import com.example.siemas.RoomDatabase.Entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private UserDao userDao;
    private DsartDao dsartDao;
    private DsbsDao dsbsDao;
    private DsrtDao dsrtDao;
    private FotoDao fotoDao;
    private StatusRumahDao statusRumahDao;
    private PendidikanDao pendidikanDao;
    private StatusDao statusDao;
    private KegiatanUtamaDao kegiatanUtamaDao;
    private Jadwal212Dao jadwal212Dao;
    private Laporan212Dao laporan212Dao;
    private PeriodeDao periodeDao;

    public Repository(Application application) {
        LocalDatabase ldb = LocalDatabase.getLocalDatabase(application);
        this.userDao = ldb.userDao();
        this.dsbsDao = ldb.dsbsDao();
        this.dsartDao = ldb.dsartDao();
        this.dsrtDao = ldb.dsrtDao();
        this.statusRumahDao = ldb.statusRumahDao();
        this.pendidikanDao = ldb.pendidikanDao();
        this.statusDao = ldb.statusDao();
        this.kegiatanUtamaDao = ldb.kegiatanUtamaDao();
        this.jadwal212Dao = ldb.jadwal212Dao();
        this.laporan212Dao = ldb.laporan212Dao();
        this.periodeDao = ldb.periodeDao();
        this.fotoDao = ldb.fotoDao();
    }


    // insert user
    private static class insertUserAsync extends AsyncTask<User, Void, Void> {
        private UserDao userDao;
        private User user;

        public insertUserAsync(UserDao userDao, User user) {
            this.userDao = userDao;
            this.user = user;
        }


        @Override
        protected Void doInBackground(User... users) {
            userDao.insertUser(user);
            return null;
        }
    }

    public void insertUser(User user) {
        new insertUserAsync(userDao, user).execute();
    }

    // get user
    public static class getListUser extends AsyncTask<Void, Void, List<User>> {
        private UserDao userDao;

        public getListUser(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            return userDao.getAllUser();
        }
    }

    public List<User> getUser() {
        try {
            return new getListUser(userDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // nuke user
    private static class nukeUserAsync extends AsyncTask<Void, Void, Integer> {
        private UserDao userDao;

        public nukeUserAsync(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return userDao.nukeUser();
        }
    }

    public void nukeUser() {
        new nukeUserAsync(userDao).execute();
    }


    // get dsbs from api
    public void getDsbsPclFromApi(Context context, String email, String token) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Mengambil Data DSBS");
        pd.show();

        InterfaceApi interfaceApi = RetrofitClientInstance.getClient().create(InterfaceApi.class);
        Call<ResponseBody> call = interfaceApi.getDsbsPcl(email, "Bearer " + token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    String message = jo.getString("message");

                    if (message.equals("success")) {
                        JSONArray joArray = new JSONArray(jo.getString("body"));
                        ArrayList<Dsbs> dsbsList = new ArrayList<Dsbs>(joArray.length());
                        for (int i = 0; i < joArray.length(); i++) {
                            JSONObject ob = new JSONObject(joArray.get(i).toString());

                            Dsbs dsbs = new Dsbs(
                                    ob.getInt("id"),
                                    ob.getString("tahun"),
                                    ob.getInt("semester"),
                                    ob.getString("kd_kab"),
                                    ob.getString("nama_kab"),
                                    ob.getString("kd_kec"),
                                    ob.getString("nama_kec"),
                                    ob.getString("kd_desa"),
                                    ob.getString("nama_desa"),
                                    ob.getString("kd_bs"),
                                    ob.getString("nks"),
                                    ob.getInt("jml_rt"),
                                    ob.getString("pencacah"),
                                    ob.getString("pengawas")
                            );
                            dsbsList.add(dsbs);
                        }
                        insertDsbsList(dsbsList);

                    } else {
                        Toast.makeText(context, "Kesalahan pada server", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "Sync DSBS Success", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });

    }

    public void getDsbsPmlFromApi(Context context, String email, String token) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Mengambil Data DSBS");
        pd.show();

        InterfaceApi interfaceApi = RetrofitClientInstance.getClient().create(InterfaceApi.class);
        Call<ResponseBody> call = interfaceApi.getDsbsPml(email, "Bearer " + token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    String message = jo.getString("message");

                    if (message.equals("success")) {
                        JSONArray joArray = new JSONArray(jo.getString("body"));
                        ArrayList<Dsbs> dsbsList = new ArrayList<Dsbs>(joArray.length());
                        for (int i = 0; i < joArray.length(); i++) {
                            JSONObject ob = new JSONObject(joArray.get(i).toString());
                            int status = 0;
                            if (!ob.getString("status").equals("null")) {
                                status = ob.getInt("status");
                            }
                            Dsbs dsbs = new Dsbs(
                                    ob.getInt("id"),
                                    ob.getString("tahun"),
                                    ob.getInt("semester"),
                                    ob.getString("kd_kab"),
                                    ob.getString("nama_kab"),
                                    ob.getString("kd_kec"),
                                    ob.getString("nama_kec"),
                                    ob.getString("kd_desa"),
                                    ob.getString("nama_desa"),
                                    ob.getString("kd_bs"),
                                    ob.getString("nks"),
                                    ob.getInt("jml_rt"),
                                    ob.getString("pencacah"),
                                    ob.getString("pengawas")
                            );
                            dsbsList.add(dsbs);
                        }
                        insertDsbsList(dsbsList);

                    } else {
                        Toast.makeText(context, "Kesalahan pada server", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "Sync DSBS Success", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });

    }

    public void insertDsbsList(List<Dsbs> dsbsList) {
        Dsbs[] arrayDsbs = new Dsbs[dsbsList.size()];
        arrayDsbs = dsbsList.toArray(arrayDsbs);
        //asynctask
        new insertDsbsListAsync(dsbsDao).execute(arrayDsbs);
    }

    private static class insertDsbsListAsync extends AsyncTask<Dsbs, Void, Void> {
        private DsbsDao dsbsDao;

        public insertDsbsListAsync(DsbsDao dsbsDao) {
            this.dsbsDao = dsbsDao;
        }

        @Override
        protected Void doInBackground(Dsbs... dsbs) {
            dsbsDao.insertListDsbs(Arrays.asList(dsbs));
            return null;
        }

    }

    // get list dsbs
    public static class getListDsbs extends AsyncTask<Void, Void, List<Dsbs>> {
        private DsbsDao dsbsDao;
        private String tahun;
        private int semester;

        public getListDsbs(DsbsDao dsbsDao, String tahun, int semester) {
            this.dsbsDao = dsbsDao;
            this.tahun = tahun;
            this.semester = semester;
        }

        @Override
        protected List<Dsbs> doInBackground(Void... voids) {
            return dsbsDao.getDsbsList(tahun, semester);
        }
    }

    public List<Dsbs> getDsbs(String tahun, int semester) {
        try {
            return new getListDsbs(dsbsDao, tahun, semester).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get jadwal 212 from api
    private static class insertJadwalListAsync extends AsyncTask<Jadwal212, Void, Void> {
        private Jadwal212Dao jadwal212Dao;

        public insertJadwalListAsync(Jadwal212Dao jadwal212Dao) {
            this.jadwal212Dao = jadwal212Dao;
        }


        @Override
        protected Void doInBackground(Jadwal212... jadwal212s) {
            jadwal212Dao.insertListJadwal(Arrays.asList(jadwal212s));
            return null;
        }
    }

    public void insertJadwalList(List<Jadwal212> jadwal212List) {
        Jadwal212[] arrayJadwal = new Jadwal212[jadwal212List.size()];
        arrayJadwal = jadwal212List.toArray(arrayJadwal);
        //asynctask
        new insertJadwalListAsync(jadwal212Dao).execute(arrayJadwal);
    }

    public void getJadwal212FromApi(Context context, String email, String token) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Mengambil Data DSBS");
        pd.show();

        InterfaceApi interfaceApi = RetrofitClientInstance.getClient().create(InterfaceApi.class);
        Call<ResponseBody> call3 = interfaceApi.getJadwal(email,"Bearer " + token);
        call3.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    String message = jo.getString("message");

                    if (message.equals("success")) {
                        JSONArray joArray = new JSONArray(jo.getString("body"));
                        ArrayList<Jadwal212> jadwal212ArrayList = new ArrayList<Jadwal212>(joArray.length());
                        for (int i = 0; i < joArray.length(); i++) {
                            JSONObject ob = new JSONObject(joArray.get(i).toString());

                            Jadwal212 jadwal212 = new Jadwal212(
                                    ob.getInt("id"),
                                    ob.getString("tanggal")
                            );
                            jadwal212ArrayList.add(jadwal212);
                        }
                        insertJadwalList(jadwal212ArrayList);

                    } else {
                        Toast.makeText(context, "Kesalahan pada server", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "Get Jadwal 212 Success", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    // get jadwal by tanggal
    private static class getJadwalByTanggalAsync extends AsyncTask<String, Void, Jadwal212> {
        private Jadwal212Dao jadwal212Dao;

        public getJadwalByTanggalAsync(Jadwal212Dao jadwal212Dao) {
            this.jadwal212Dao = jadwal212Dao;
        }

        @Override
        protected Jadwal212 doInBackground(String... strings) {
            return jadwal212Dao.getJadwalByTanggal(strings[0]);
        }
    }

    public Jadwal212 getJadwalByTanggal(String tanggal) {
        try {
            return new getJadwalByTanggalAsync(jadwal212Dao).execute(tanggal).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    // get dsrt from api
    public void getDsrtPclFromApi(Context context, String email, String token) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Mengambil Data DSRT");
        pd.show();

        InterfaceApi interfaceApi = RetrofitClientInstance.getClient().create(InterfaceApi.class);
        Call<ResponseBody> call = interfaceApi.getDsrtPcl(email, "Bearer " + token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    String message = jo.getString("message");
                    if (message.equals("success")) {
                        JSONArray joArray = new JSONArray(jo.getString("body"));
                        ArrayList<Dsrt> dsrtList = new ArrayList<Dsrt>(joArray.length());

                        for (int i = 0; i < joArray.length(); i++) {
                            JSONObject ob = new JSONObject(joArray.get(i).toString());

                            int jml_art_prelist = 0;
                            if (!ob.getString("jml_art_prelist").equals("null")) {
                                jml_art_prelist = ob.getInt("jml_art_prelist");
                            }

                            int jml_art_cacah = 0;
                            if (!ob.getString("jml_art_cacah").equals("null")) {
                                jml_art_cacah = ob.getInt("jml_art_cacah");
                            }

                            int art_sekolah = 0;
                            if (!ob.getString("art_sekolah").equals("null")) {
                                art_sekolah = ob.getInt("art_sekolah");
                            }

                            int art_bpjs = 0;
                            if (!ob.getString("art_bpjs").equals("null")) {
                                art_bpjs = ob.getInt("art_bpjs");
                            }

                            int luas_lantai = 0;
                            if (!ob.getString("luas_lantai").equals("null")) {
                                luas_lantai = ob.getInt("luas_lantai");
                            }

                            int status_pencacahan = 0;
                            if (!ob.getString("status_pencacahan").equals("null")) {
                                status_pencacahan = ob.getInt("status_pencacahan");
                            }

                            int gsmp = 0;
                            if (!ob.getString("gsmp").equals("null")) {
                                gsmp = ob.getInt("gsmp");
                            }
                            int status_res = 0 ;
                            if (!ob.getString("status_res").equals("null")) {
                                gsmp = ob.getInt("status_res");
                            }
                            int jml_komoditas_makanan = 0 ;
                            if (!ob.getString("jml_komoditas_makanan").equals("null")) {
                                gsmp = ob.getInt("jml_komoditas_makanan");
                            }
                            int jml_komoditas_nonmakanan = 0 ;
                            if (!ob.getString("jml_komoditas_nonmakanan").equals("null")) {
                                gsmp = ob.getInt("jml_komoditas_nonmakanan");
                            }

                            byte [] foto = new byte[0];
                            Dsrt dsrt = new Dsrt(
                                    ob.getInt("id"),
                                    ob.getString("tahun"),
                                    ob.getInt("semester"),
                                    ob.getString("kd_kab"),
                                    ob.getString("nama_kab"),
                                    ob.getString("kd_kec"),
                                    ob.getString("nama_kec"),
                                    ob.getString("kd_desa"),
                                    ob.getString("nama_desa"),
                                    ob.getString("kd_bs"),
                                    ob.getInt("nu_rt"),
                                    ob.getString("nks"),
                                    status_pencacahan,
                                    ob.getString("nama_krt_prelist"),
                                    jml_art_prelist,
                                    ob.getString("nama_krt_cacah"),
                                    jml_art_cacah,
                                    ob.getString("status_rumah"),
                                    jml_komoditas_makanan,
                                    jml_komoditas_nonmakanan,
                                    ob.getString("makanan_sebulan"),
                                    ob.getString("nonmakanan_sebulan"),
                                    ob.getString("makanan_sebulan_bypml"),
                                    ob.getString("nonmakanan_sebulan_bypml"),
                                    ob.getString("transportasi"),
                                    ob.getString("peliharaan"),
                                    art_sekolah,
                                    art_bpjs,
                                    ob.getString("ijazah_krt"),
                                    ob.getString("kegiatan_seminggu"),
                                    ob.getString("deskripsi_kegiatan"),
                                    luas_lantai,
                                    gsmp,
                                    ob.getString("gsmp_desk"),
                                    ob.getString("latitude"),
                                    ob.getString("longitude"),
                                    ob.getString("latitude_selesai"),
                                    ob.getString("longitude_selesai"),
                                    ob.getString("jam_mulai"),
                                    ob.getString("jam_selesai"),
                                    ob.getString("durasi_pencacahan"),
                                    ob.getString("pencacah"),
                                    ob.getString("pengawas")
                            );
                            dsrtList.add(dsrt);
                        }
                        insertDsrtList(dsrtList);
                    } else {
                        Toast.makeText(context, "Ada kesalahan di server", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "Sync DSRT Success", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });

    }

    public void getDsrtPmlFromApi(Context context, String email, String token) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Mengambil Data DSRT");
        pd.show();

        InterfaceApi interfaceApi = RetrofitClientInstance.getClient().create(InterfaceApi.class);
        Call<ResponseBody> call = interfaceApi.getDsrtPml(email, "Bearer " + token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    String message = jo.getString("message");

                    if (message.equals("success")) {
                        JSONArray joArray = new JSONArray(jo.getString("body"));
                        ArrayList<Dsrt> dsrtList = new ArrayList<Dsrt>(joArray.length());

                        for (int i = 0; i < joArray.length(); i++) {
                            JSONObject ob = new JSONObject(joArray.get(i).toString());


                            int jml_art_prelist = 0;
                            if (!ob.getString("jml_art_prelist").equals("null")) {
                                jml_art_prelist = ob.getInt("jml_art_prelist");
                            }

                            int jml_art_cacah = 0;
                            if (!ob.getString("jml_art_cacah").equals("null")) {
                                jml_art_cacah = ob.getInt("jml_art_cacah");
                            }

                            int art_sekolah = 0;
                            if (!ob.getString("art_sekolah").equals("null")) {
                                art_sekolah = ob.getInt("art_sekolah");
                            }

                            int art_bpjs = 0;
                            if (!ob.getString("art_bpjs").equals("null")) {
                                art_bpjs = ob.getInt("art_bpjs");
                            }

                            int luas_lantai = 0;
                            if (!ob.getString("luas_lantai").equals("null")) {
                                luas_lantai = ob.getInt("luas_lantai");
                            }

                            int status_pencacahan = 0;
                            if (!ob.getString("status_pencacahan").equals("null")) {
                                status_pencacahan = ob.getInt("status_pencacahan");
                            }

                            int gsmp = 0;
                            if (!ob.getString("gsmp").equals("null")) {
                                gsmp = ob.getInt("gsmp");
                            }
                            int status_res = 0 ;
                            if (!ob.getString("status_res").equals("null")) {
                                gsmp = ob.getInt("status_res");
                            }
                            int jml_komoditas_makanan = 0 ;
                            if (!ob.getString("jml_komoditas_makanan").equals("null")) {
                                jml_komoditas_makanan = ob.getInt("jml_komoditas_makanan");
                            }
                            int jml_komoditas_nonmakanan = 0 ;
                            if (!ob.getString("jml_komoditas_nonmakanan").equals("null")) {
                                jml_komoditas_nonmakanan = ob.getInt("jml_komoditas_nonmakanan");
                            }

                            byte[] foto = new byte[0];
                            Dsrt dsrt = new Dsrt(
                                    ob.getInt("id"),
                                    ob.getString("tahun"),
                                    ob.getInt("semester"),
                                    ob.getString("kd_kab"),
                                    ob.getString("nama_kab"),
                                    ob.getString("kd_kec"),
                                    ob.getString("nama_kec"),
                                    ob.getString("kd_desa"),
                                    ob.getString("nama_desa"),
                                    ob.getString("kd_bs"),
                                    ob.getInt("nu_rt"),
                                    ob.getString("nks"),
                                    status_pencacahan,
                                    ob.getString("nama_krt_prelist"),
                                    jml_art_prelist,
                                    ob.getString("nama_krt_cacah"),
                                    jml_art_cacah,
                                    ob.getString("status_rumah"),
                                    jml_komoditas_makanan,
                                    jml_komoditas_nonmakanan,
                                    ob.getString("makanan_sebulan"),
                                    ob.getString("nonmakanan_sebulan"),
                                    ob.getString("makanan_sebulan_bypml"),
                                    ob.getString("nonmakanan_sebulan_bypml"),
                                    ob.getString("transportasi"),
                                    ob.getString("peliharaan"),
                                    art_sekolah,
                                    art_bpjs,
                                    ob.getString("ijazah_krt"),
                                    ob.getString("kegiatan_seminggu"),
                                    ob.getString("deskripsi_kegiatan"),
                                    luas_lantai,
                                    gsmp,
                                    ob.getString("gsmp_desk"),
                                    ob.getString("latitude"),
                                    ob.getString("longitude"),
                                    ob.getString("latitude_selesai"),
                                    ob.getString("longitude_selesai"),
                                    ob.getString("jam_mulai"),
                                    ob.getString("jam_selesai"),
                                    ob.getString("durasi_pencacahan"),
                                    ob.getString("pencacah"),
                                    ob.getString("pengawas")
                            );
                            dsrtList.add(dsrt);
                        }

                        insertDsrtList(dsrtList);
                    } else {
                        Toast.makeText(context, "Ada kesalahan di server", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "Sync DSRT Success", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });

    }

    public void insertDsrtList(List<Dsrt> dsrtList) {
        Dsrt[] arrayDsrt = new Dsrt[dsrtList.size()];
        arrayDsrt = dsrtList.toArray(arrayDsrt);
        //asynctask
        new insertDsrtListAsync(dsrtDao).execute(arrayDsrt);
    }

    private static class insertDsrtListAsync extends AsyncTask<Dsrt, Void, Void> {
        private DsrtDao dsrtDao;

        public insertDsrtListAsync(DsrtDao dsrtDao) {
            this.dsrtDao = dsrtDao;
        }

        @Override
        protected Void doInBackground(Dsrt... dsrts) {
            dsrtDao.insertListDsrt(Arrays.asList(dsrts));
            return null;
        }

    }

    // get list dsrt
    public static class getListDsrt extends AsyncTask<Void, Void, List<Dsrt>> {
        private DsrtDao dsrtDao;
        private String tahun;
        private int semester;

        public getListDsrt(DsrtDao dsrtDao, String tahun, int semester) {
            this.dsrtDao = dsrtDao;
            this.tahun = tahun;
            this.semester = semester;
        }

        @Override
        protected List<Dsrt> doInBackground(Void... voids) {
            return dsrtDao.getDsrtList(tahun, semester);
        }
    }

    public List<Dsrt> getDsrt(String tahun, int semester) {
        try {
            return new getListDsrt(dsrtDao, tahun, semester).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }



    // get list jadwal 212
    public static class getListJadwal extends AsyncTask<Void, Void, List<Jadwal212>> {
        private Jadwal212Dao jadwal212Dao;

        public getListJadwal(Jadwal212Dao jadwal212Dao) {
            this.jadwal212Dao = jadwal212Dao;
        }

        @Override
        protected List<Jadwal212> doInBackground(Void... voids) {
            return jadwal212Dao.getListJadwal212();
        }
    }

    public List<Jadwal212> getListJadwal() {
        try {
            return new getListJadwal(jadwal212Dao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get list dsrt by idBs
    public static class getListDsrtByIdBsAsync extends AsyncTask<Object, Void, List<Dsrt>>{
        private DsrtDao dsrtDao;

        public getListDsrtByIdBsAsync(DsrtDao dsrtDao) {
            this.dsrtDao = dsrtDao;
        }
        @Override
        protected List<Dsrt> doInBackground(Object... objects) {
            String tahun = (String) objects[0];
            int semester = (int) objects[1];
            String kd_kab = (String) objects[2];
            String kd_kec = (String) objects[3];
            String kd_desa = (String) objects[4];
            String kd_bs = (String) objects[5];
            return dsrtDao.getListDsrtByIdBs(tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs);
        }
    }

    public List<Dsrt> getListDsrtByIdBs(String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs ){
        try {
            return new getListDsrtByIdBsAsync(dsrtDao).execute(tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get list dsrt by idBs and status pencacahan lower
    public static class getListDsrtByIdBsStatusLwAsync extends AsyncTask<Object, Void, List<Dsrt>> {
        private DsrtDao dsrtDao;
        private String tahun;
        private int semester;

        public getListDsrtByIdBsStatusLwAsync(DsrtDao dsrtDao) {
            this.dsrtDao = dsrtDao;
        }

        @Override
        protected List<Dsrt> doInBackground(Object... objects) {
            int status = (int) objects[0];
            String tahun = (String) objects[1];
            int semester = (int) objects[2];
            String kd_kab = (String) objects[3];
            String kd_kec = (String) objects[4];
            String kd_desa = (String) objects[5];
            String kd_bs = (String) objects[6];
            return dsrtDao.getListDsrtByIdBsStatusLw(status, tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs);
        }
    }
    public List<Dsrt> getListDsrtByIdBsStatusLw(int status_pencacahan, String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs) {
        try {
            return new getListDsrtByIdBsStatusLwAsync(dsrtDao).execute(status_pencacahan, tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get list dsrt by idBs and status pencacahan upper
    public static class getListDsrtByIdBsStatusUpAsync extends AsyncTask<Object, Void, List<Dsrt>> {
        private DsrtDao dsrtDao;

        public getListDsrtByIdBsStatusUpAsync(DsrtDao dsrtDao) {
            this.dsrtDao = dsrtDao;
        }

        @Override
        protected List<Dsrt> doInBackground(Object... objects) {
            int status = (int) objects[0];
            String tahun = (String) objects[1];
            int semester = (int) objects[2];
            String kd_kab = (String) objects[3];
            String kd_kec = (String) objects[4];
            String kd_desa = (String) objects[5];
            String kd_bs = (String) objects[6];
            return dsrtDao.getListDsrtByIdBsStatusUp(status, tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs);
        }
    }

    public List<Dsrt> getListDsrtByIdBsStatusUp(int status_pencacahan, String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs) {
        try {
            return new getListDsrtByIdBsStatusUpAsync(dsrtDao).execute(status_pencacahan, tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get live data dsbs
    public LiveData<List<Dsbs>> getLiveDataDsbs(String tahun , int semester) {
        return dsbsDao.getDsbsLive(tahun, semester);
    }

    // get live data dsrt by idbs
    public LiveData<List<Dsrt>> getLiveDataDsrtByIdBs(String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs ) {
        return dsrtDao.getLiveDsrtByIdBs(tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs);
    }

    // get live data dsrt
    public LiveData<List<Dsrt>> getLiveDataDsrt(String tahun, int semester){
        return dsrtDao.getDsrtLive(tahun, semester);
    }

    public LiveData<List<Foto>> getLiveDataFotoByIdBs(String idBs, String tahun, int semester ) {
        return fotoDao.getLiveDataFotoByIdBs(idBs,tahun,semester);
    }


    private static class updateStatusFotoAsync extends AsyncTask<Object, Void, Void>{
        private FotoDao fotoDao;

        public updateStatusFotoAsync(FotoDao fotoDao) {
            this.fotoDao = fotoDao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            int idDsrt = (int) objects[0];
            int statusPencacahan = (int) objects[1];
            fotoDao.updateStatusFoto(idDsrt, statusPencacahan);
            return null;
        }
    }

    public void updateStatusFoto(int idDsrt, int status){
        new updateStatusFotoAsync(fotoDao).execute(idDsrt, status);
    }


    // get dsrt by id
    private static class getDsrtByIdAsync extends AsyncTask<Integer, Void, Dsrt> {
        private DsrtDao dsrtDao;

        public getDsrtByIdAsync(DsrtDao dsrtDao) {
            this.dsrtDao = dsrtDao;
        }
        @Override
        protected Dsrt doInBackground(Integer... integers) {
            return dsrtDao.getDsrtById(integers[0]);
        }
    }

    public Dsrt getDsrtById(Integer idDsrt) {
        try {
            return new getDsrtByIdAsync(dsrtDao).execute(idDsrt).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

//    private static class getDsrtFotoAsycn extends AsyncTask<Integer, Void, Dsrt> {
//        private DsrtDao dsrtDao;
//
//        public getDsrtFotoAsycn(DsrtDao dsrtDao) {
//            this.dsrtDao = dsrtDao;
//        }
//        @Override
//        protected Dsrt doInBackground(Integer... integers) {
//            return dsrtDao.getfotobyid(integers[0]);
//        }
//    }
//    public Dsrt getDsrtFoto(Integer idDsrt) {
//        try {
//            return new getDsrtFotoAsycn(dsrtDao).execute(idDsrt).get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    // get dsrt by id bs and nu rt
    private static class getDsrtByIdBsNuRTAsync extends AsyncTask<Object, Void, Dsrt> {
        private DsrtDao dsrtDao;

        public getDsrtByIdBsNuRTAsync(DsrtDao dsrtDao) {
            this.dsrtDao = dsrtDao;
        }

        @Override
        protected Dsrt doInBackground(Object... objects) {
            String tahun = (String) objects[0];
            int semester = (int) objects[1] ;
            String kd_kab = (String) objects[2];
            String kd_kec = (String) objects[3];
            String kd_desa = (String) objects[4];
            String kd_bs = (String) objects[5];
            int nu_rt = (int) objects[6];
            return dsrtDao.getDsrtByIdBSNuRt(tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs, nu_rt);
        }
    }

    public Dsrt getDsrtByIdBsNuRt(String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs, int nu_rt) {
        try {
            return new getDsrtByIdBsNuRTAsync(dsrtDao).execute(tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs, nu_rt).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // update foto rumah
//    private static class updateFotoRumahAsync extends AsyncTask<Object, Void, Void>{
//        private DsrtDao dsrtDao;
//
//        public updateFotoRumahAsync(DsrtDao dsrtDao) {
//            this.dsrtDao = dsrtDao;
//        }
//
//        @Override
//        protected Void doInBackground(Object... objects) {
//            int idDsrt = (int) objects[0];
//            byte[] fileFoto = (byte[]) objects[1];
//            dsrtDao.updateFotoRumah(idDsrt, fileFoto);
//            return null;
//        }
//    }

//    public void updateFotoRumah(int idDsrt, byte[] fileFoto){
//        new updateFotoRumahAsync(dsrtDao).execute(idDsrt, fileFoto);
//    }

    // update status pencacahan
    private static class updateStatusPencacahanAsync extends AsyncTask<Object, Void, Void>{
        private DsrtDao dsrtDao;

        public updateStatusPencacahanAsync(DsrtDao dsrtDao) {
            this.dsrtDao = dsrtDao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            int idDsrt = (int) objects[0];
            int statusPencacahan = (int) objects[1];
            dsrtDao.updateStatusPencacahan(idDsrt, statusPencacahan);
            return null;
        }
    }

    public void updateStatusPencacahan(int idDsrt, int statusPencacahan){
        new updateStatusPencacahanAsync(dsrtDao).execute(idDsrt, statusPencacahan);
    }

    // update pencacahan pcl
    private static class updatePencacahanAsync extends AsyncTask<Object, Void, Void>{
        private DsrtDao dsrtDao;

        public updatePencacahanAsync(DsrtDao dsrtDao) {
            this.dsrtDao = dsrtDao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            int idDsrt = (int) objects[0];
            String namaKrt = (String) objects[1];
            int jmlArt = (int) objects[2];
            String statusRumah = (String) objects[3];
            String makananSebulan = (String) objects[4];
            String nonMakananSebulan = (String) objects[5];
            int gsmp = (int) objects[6];
            String gsmp_desk = (String) objects[7];
            String latitude = (String) objects[8];
            String longitude = (String) objects[9];
            String durasi = (String) objects[10];
            int statusPencacahan = (int) objects[11];
            dsrtDao.updatePencacahan(idDsrt, namaKrt, jmlArt, statusRumah, makananSebulan, nonMakananSebulan, gsmp,gsmp_desk, latitude, longitude, durasi, statusPencacahan);
            return null;
        }
    }

    public void updatePencacahan(int idDsrt, String namaKrt, int jmlArt, String statusRumah, String makananSebulan, String nonMakananSebulan, int
            gsmp,String gsmp_desk, String latitude, String longitude, String durasi, int statusPencacahan){
        new updatePencacahanAsync(dsrtDao).execute(idDsrt, namaKrt, jmlArt, statusRumah, makananSebulan, nonMakananSebulan, gsmp,gsmp_desk, latitude, longitude, durasi, statusPencacahan);
    }

    // update pemeriksaan pcl
    private static class updatePemeriksaanAsync extends AsyncTask<Object, Void, Void>{
        private DsrtDao dsrtDao;

        public updatePemeriksaanAsync(DsrtDao dsrtDao) {
            this.dsrtDao = dsrtDao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            int idDsrt = (int) objects[0];
            String namaKrt = (String) objects[1];
            int jmlArt = (int) objects[2];
            int jml_komoditas_makanan = (int) objects[3];
            int jml_komoditas_nonmkanan = (int) objects[4];
            String makananSebulan = (String) objects[5];
            String nonMakananSebulan = (String) objects[6];
            String makananSebulanbypml = (String) objects[7];
            String nonMakananSebulanbypml = (String) objects[8];
            String transportasi = (String) objects[9];
            String peliharaan = (String) objects[10];
            int artSekolah = (int) objects[11];
            int artBpjs = (int) objects[12];
            String ijazahKrt = (String) objects[13];
            String kegiatanKrt = (String) objects[14];
            String deskripsiKegiatan = (String) objects[15];
            int luasLantai = (int) objects[16];
            int statusPencacahan = (int) objects[17];
            dsrtDao.updatePemeriksaan(
                    idDsrt,
                    namaKrt,
                    jmlArt,
                    jml_komoditas_makanan,
                    jml_komoditas_nonmkanan,
                    makananSebulan,
                    nonMakananSebulan,
                    makananSebulanbypml,
                    nonMakananSebulanbypml,
                    transportasi,
                    peliharaan,
                    artSekolah,
                    artBpjs,
                    ijazahKrt,
                    kegiatanKrt,
                    deskripsiKegiatan,
                    luasLantai,
                    statusPencacahan
                    );
            return null;
        }
    }

    public void updatePemeriksaan(
            int idDsrt,
            String namaKrt,
            int jmlArt,
            int  jml_komoditas_makanan,
            int jml_komoditas_nonmkanan,
            String  makananSebulan,
            String  nonMakananSebulan,
            String makananSebulanbypml,
            String nonMakananSebulanbypml,
            String transportasi,
            String peliharaan,
            int artSekolah,
            int artBpjs,
            String ijazahKrt,
            String kegiatanKrt,
            String deskripsiKegiatan,
            int luasLantai,
            int statusPencacahan){
        new updatePemeriksaanAsync(dsrtDao).execute(
                idDsrt,
                namaKrt,
                jmlArt,
                jml_komoditas_makanan,
                jml_komoditas_nonmkanan,
                makananSebulan,
                nonMakananSebulan,
                makananSebulanbypml,
                nonMakananSebulanbypml,
                transportasi,
                peliharaan,
                artSekolah,
                artBpjs,
                ijazahKrt,
                kegiatanKrt,
                deskripsiKegiatan,
                luasLantai,
                statusPencacahan);
    }


    // insert status rumah
    private static class insertStatusRumahAsync extends AsyncTask<StatusRumah, Void, Void> {
        private StatusRumahDao statusRumahDao;
        private StatusRumah statusRumah;

        public insertStatusRumahAsync(StatusRumahDao statusRumahDao, StatusRumah statusRumah) {
            this.statusRumahDao = statusRumahDao;
            this.statusRumah = statusRumah;
        }

        @Override
        protected Void doInBackground(StatusRumah... statusRumahs) {
            statusRumahDao.insertStatusRumah(statusRumah);
            return null;
        }

    }

    public void insertStatusRumah(StatusRumah statusRumah) {
        new insertStatusRumahAsync(statusRumahDao, statusRumah).execute();
    }

    // get status rumah
    public static class getListStatusRumah extends AsyncTask<Void, Void, List<StatusRumah>> {
        private StatusRumahDao statusRumahDao;

        public getListStatusRumah(StatusRumahDao statusRumahDao) {
            this.statusRumahDao = statusRumahDao;
        }

        @Override
        protected List<StatusRumah> doInBackground(Void... voids) {
            return statusRumahDao.getAllStatusRumah();
        }
    }

    public List<StatusRumah> getAllStatusRumah() {
        try {
            return new getListStatusRumah(statusRumahDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    // nuke status
    private static class nukeStatusRumahAsync extends AsyncTask<Void, Void, Integer> {
        private StatusRumahDao statusRumahDao;

        public nukeStatusRumahAsync(StatusRumahDao statusRumahDao) {
            this.statusRumahDao = statusRumahDao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return statusRumahDao.nukeStatusRumah();
        }
    }

    public void nukeStatusRumah() {
        new nukeStatusRumahAsync(statusRumahDao).execute();
    }

    // insert pendidikan
    private static class insertPendidikanAsync extends AsyncTask<Pendidikan, Void, Void> {
        private PendidikanDao pendidikanDao;
        private Pendidikan pendidikan;

        public insertPendidikanAsync(PendidikanDao pendidikanDao, Pendidikan pendidikan) {
            this.pendidikanDao = pendidikanDao;
            this.pendidikan = pendidikan;
        }

        @Override
        protected Void doInBackground(Pendidikan... pendidikans) {
            pendidikanDao.insertPendidikan(pendidikan);
            return null;
        }

    }

    public void insertPendidikan(Pendidikan pendidikan) {
        new insertPendidikanAsync(pendidikanDao, pendidikan).execute();
    }


    // get pendidikan
    public static class getListPendidikan extends AsyncTask<Void, Void, List<Pendidikan>> {
        private PendidikanDao pendidikanDao;

        public getListPendidikan(PendidikanDao pendidikanDao) {
            this.pendidikanDao = pendidikanDao;
        }

        @Override
        protected List<Pendidikan> doInBackground(Void... voids) {
            return pendidikanDao.getAllPendidikan();
        }
    }

    public List<Pendidikan> getAllPendidikan() {
        try {
            return new getListPendidikan(pendidikanDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // nuke pendidikan
    private static class nukePendidikanAsync extends AsyncTask<Void, Void, Integer> {
        private PendidikanDao pendidikanDao;

        public nukePendidikanAsync(PendidikanDao pendidikanDao) {
            this.pendidikanDao = pendidikanDao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return pendidikanDao.nukePendidikan();
        }
    }

    public void nukePendidikan() {
        new nukePendidikanAsync(pendidikanDao).execute();
    }

    // insert status
    private static class insertStatusAsync extends AsyncTask<Status, Void, Void> {
        private StatusDao statusDao;
        private com.example.siemas.RoomDatabase.Entities.Status status;

        public insertStatusAsync(StatusDao statusDao, com.example.siemas.RoomDatabase.Entities.Status status) {
            this.statusDao = statusDao;
            this.status = status;
        }

        @Override
        protected Void doInBackground(com.example.siemas.RoomDatabase.Entities.Status... statuses) {
            statusDao.insertStatus(status);
            return null;
        }


    }

    public void insertStatus(Status status) {
        new insertStatusAsync(statusDao, status).execute();
    }

    // get status
    public static class getListStatus extends AsyncTask<Void, Void, List<Status>> {
        private StatusDao statusDao;

        public getListStatus(StatusDao statusDao) {
            this.statusDao = statusDao;
        }

        @Override
        protected List<com.example.siemas.RoomDatabase.Entities.Status> doInBackground(Void... voids) {
            return statusDao.getAllStatus();
        }
    }

    public List<Status> getAllStatus() {
        try {
            return new getListStatus(statusDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // nuke status
    private static class nukeStatusAsync extends AsyncTask<Void, Void, Integer> {
        private StatusDao statusDao;

        public nukeStatusAsync(StatusDao statusDao) {
            this.statusDao = statusDao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return statusDao.nukeStatus();
        }
    }

    public void nukeStatus() {
        new nukeStatusAsync(statusDao).execute();
    }


    // insert kegiatan
    private static class insertKegiatanAsync extends AsyncTask<KegiatanUtama, Void, Void> {
        private KegiatanUtamaDao kegiatanUtamaDao;
        private KegiatanUtama kegiatanUtama;

        public insertKegiatanAsync(KegiatanUtamaDao kegiatanUtamaDao, KegiatanUtama kegiatanUtama) {
            this.kegiatanUtamaDao = kegiatanUtamaDao;
            this.kegiatanUtama = kegiatanUtama;
        }


        @Override
        protected Void doInBackground(KegiatanUtama... kegiatanUtamas) {
            kegiatanUtamaDao.insertKegiatan(kegiatanUtama);
            return null;
        }
    }

    public void insertKegiatan(KegiatanUtama kegiatanUtama) {
        new insertKegiatanAsync(kegiatanUtamaDao, kegiatanUtama).execute();
    }

    // get kegiatan
    public static class getListKegiatan extends AsyncTask<Void, Void, List<KegiatanUtama>> {
        private KegiatanUtamaDao kegiatanUtamaDao;

        public getListKegiatan(KegiatanUtamaDao kegiatanUtamaDao) {
            this.kegiatanUtamaDao = kegiatanUtamaDao;
        }


        @Override
        protected List<KegiatanUtama> doInBackground(Void... voids) {
            return kegiatanUtamaDao.getAllKegiatan();
        }
    }

    public List<KegiatanUtama> getAllKegiatan() {
        try {
            return new getListKegiatan(kegiatanUtamaDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // nuke kegiatan
    private static class nukeKegiatanAsync extends AsyncTask<Void, Void, Integer> {
        private KegiatanUtamaDao kegiatanUtamaDao;

        public nukeKegiatanAsync(KegiatanUtamaDao kegiatanUtamaDao) {
            this.kegiatanUtamaDao = kegiatanUtamaDao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return kegiatanUtamaDao.nukeKegiatan();
        }
    }

    public void nukeKegiatan() {
        new nukeKegiatanAsync(kegiatanUtamaDao).execute();
    }

    // nuke dsbs
    private static class nukeDsbsAsync extends AsyncTask<Void, Void, Integer> {
        private DsbsDao dsbsDao;

        public nukeDsbsAsync(DsbsDao dsbsDao) {
            this.dsbsDao = dsbsDao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return dsbsDao.nukeDsbs();
        }
    }

    public void nukeDsbs() {
        new nukeDsbsAsync(dsbsDao).execute();
    }

    // nuke dsrt
    private static class nukeDsrtAsync extends AsyncTask<Void, Void, Integer>{
        private DsrtDao dsrtDao;
        public nukeDsrtAsync(DsrtDao dsrtDao) {
            this.dsrtDao = dsrtDao;
        }


        @Override
        protected Integer doInBackground(Void... voids) {
            return dsrtDao.nukeDsrt();
        }
    }

    public void nukeDsrt(){new nukeDsrtAsync(dsrtDao).execute();}

    // insert laporan
    private static class insertLaporanAsync extends AsyncTask<Laporan212, Void, Void> {
        private Laporan212Dao laporan212Dao;
        private Laporan212 laporan212;

        public insertLaporanAsync(Laporan212Dao laporan212Dao, Laporan212 laporan212) {
            this.laporan212Dao = laporan212Dao;
            this.laporan212 = laporan212;
        }

        @Override
        protected Void doInBackground(Laporan212... laporan212s) {
            laporan212Dao.insertlaporan(laporan212);
            return null;
        }
    }

    public void insertLaporan(Laporan212 laporan212) {
        new insertLaporanAsync(laporan212Dao, laporan212).execute();
    }

    // get live data laporan
    public LiveData<List<Laporan212>> getLiveDataLaporan(){
        return laporan212Dao.getLiveDataLaporan212();
    }

    // nuke laporan
    private static class nukeLaporanAsync extends AsyncTask<Void, Void, Integer>{
        private Laporan212Dao laporan212Dao;

        public nukeLaporanAsync(Laporan212Dao laporan212Dao) {
            this.laporan212Dao = laporan212Dao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return laporan212Dao.nukeLaporan();
        }
    }

    public void nukeLaporan(){new nukeLaporanAsync(laporan212Dao).execute();}

    // delete laporan
    private static class deleteLaporanAsync extends AsyncTask<Object, Void, Integer>{
        private Laporan212Dao laporan212Dao;

        public deleteLaporanAsync(Laporan212Dao laporan212Dao) {
            this.laporan212Dao = laporan212Dao;
        }

        @Override
        protected Integer doInBackground(Object... objects) {
            String tahun = (String) objects[0];
            int semester = (int) objects[1];
            String kd_kab = (String) objects[2];
            String kd_kec = (String) objects[3];
            String kd_desa = (String) objects[4];
            String kd_bs = (String) objects[5];
            int nuRT = (int) objects[6];
            return laporan212Dao.deleteLaporan(tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs, nuRT);
        }
    }

    public void deleteLaporan(String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs, int nuRT){
        new deleteLaporanAsync(laporan212Dao).execute(tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs, nuRT);
    }

    // update status pencacahan
    private static class updateStatusLaporanAsync extends AsyncTask<Object, Void, Void>{
        private Laporan212Dao laporan212Dao;

        public updateStatusLaporanAsync(Laporan212Dao laporan212Dao) {
            this.laporan212Dao = laporan212Dao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            int status = (int) objects[0];
            String tahun = (String) objects[1];
            int semester = (int) objects[2];
            String kd_kab = (String) objects[3];
            String kd_kec = (String) objects[4];
            String kd_desa = (String) objects[5];
            String kd_bs = (String) objects[6];
            int nuRT = (int) objects[7];
            laporan212Dao.updateStatus(status, tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs, nuRT);
            return null;
        }
    }

    public void updateStatusLaporan(int status, String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs, int nuRT){
        new updateStatusLaporanAsync(laporan212Dao).execute(status,tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs, nuRT);
    }

    // get list laporan by id bs and status
    public static class getListLaporanByIdBsStatusAsync extends AsyncTask<Object, Void, List<Laporan212>>{
        private Laporan212Dao laporan212Dao;

        public getListLaporanByIdBsStatusAsync(Laporan212Dao laporan212Dao) {
            this.laporan212Dao = laporan212Dao;
        }

        @Override
        protected List<Laporan212> doInBackground(Object... objects) {
            int status = (int) objects[0];
            String tahun = (String) objects[1];
            int semester = (int) objects[2];
            String kd_kab = (String) objects[3];
            String kd_kec = (String) objects[4];
            String kd_desa = (String) objects[5];
            String kd_bs = (String) objects[6];
            return laporan212Dao.getListLaporan212ByIdBsStatus(status,tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs);
        }
    }

    public List<Laporan212> getLaporanByIdBsStatus(int status, String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs){
        try {
            return new getListLaporanByIdBsStatusAsync(laporan212Dao).execute(status,tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get laporan from api
    public void getLaporanFromApi(Context context, String email, String token) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Mengambil Data Laporan 212");
        pd.show();

        InterfaceApi interfaceApi = RetrofitClientInstance.getClient().create(InterfaceApi.class);
        Call<ResponseBody> call = interfaceApi.getLaporan(email, "Bearer " + token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    String message = jo.getString("message");

                    if (message.equals("success")) {
                        JSONArray joArray = new JSONArray(jo.getString("body"));
                        ArrayList<Laporan212> laporanList = new ArrayList<Laporan212>(joArray.length());
                        for (int i = 0; i < joArray.length(); i++) {
                            JSONObject ob = new JSONObject(joArray.get(i).toString());
                            Laporan212 laporan212 = new Laporan212(
                                    ob.getString("tahun"),
                                    ob.getInt("semester"),
                                    ob.getString("kd_kab"),
                                    ob.getString("kd_kec"),
                                    ob.getString("kd_desa"),
                                    ob.getString("kd_bs"),
                                    ob.getInt("nu_rt"),
                                    ob.getString("nks"),
                                    ob.getString("nama_krt"),
                                    ob.getString("pengawas"),
                                    ob.getString("tanggal"),
                                    ob.getInt("status")
                            );
                            laporanList.add(laporan212);
                        }
                        insertLaporanList(laporanList);

                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "Sync Laporan 212 Success", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });

    }

    // insert list laporan
    public void insertLaporanList(List<Laporan212> laporan212List) {
        Laporan212[] arrayLaporan = new Laporan212[laporan212List.size()];
        arrayLaporan = laporan212List.toArray(arrayLaporan);
        //asynctask
        new insertLapranListAsync(laporan212Dao).execute(arrayLaporan);
    }

    private static class insertLapranListAsync extends AsyncTask<Laporan212, Void, Void> {
        private Laporan212Dao laporan212Dao;

        public insertLapranListAsync(Laporan212Dao laporan212Dao) {
            this.laporan212Dao = laporan212Dao;
        }

        @Override
        protected Void doInBackground(Laporan212... laporan212s) {
            laporan212Dao.insertListLaporan(Arrays.asList(laporan212s));
            return null;
        }
    }

    // get list laporan by id bs and status up
    public static class getListLaporanByIdBsStatusUpAsync extends AsyncTask<Object, Void, List<Laporan212>>{
        private Laporan212Dao laporan212Dao;
        public getListLaporanByIdBsStatusUpAsync(Laporan212Dao laporan212Dao) {
            this.laporan212Dao = laporan212Dao;
        }
        @Override
        protected List<Laporan212> doInBackground(Object... objects) {
            int status = (int) objects[0];
            String tahun = (String) objects[1];
            int semester = (int) objects[2];
            String kd_kab = (String) objects[3];
            String kd_kec = (String) objects[4];
            String kd_desa = (String) objects[5];
            String kd_bs = (String) objects[6];
            return laporan212Dao.getListLaporan212ByIdBsStatusUp(status,tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs);
        }
    }

    public List<Laporan212> getLaporanByIdBsStatusUp(int status, String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs){
        try {
            return new getListLaporanByIdBsStatusUpAsync(laporan212Dao).execute(status,tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // update lokasi selesai
    private static class updateLokasiSelesaiAsync extends AsyncTask<Object, Void, Void>{
        private DsrtDao dsrtDao;

        public updateLokasiSelesaiAsync(DsrtDao dsrtDao) {
            this.dsrtDao = dsrtDao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            int idDsrt = (int) objects[0];
            String latitudeSelesai = (String) objects[1];
            String longitudeSelesai = (String) objects[2];
            dsrtDao.updateDoneLocation(idDsrt, latitudeSelesai, longitudeSelesai);
            return null;
        }
    }

    public void updateLokasiSelesai(int idDsrt, String latitudeSelesai, String longitudeSelesai){
        new updateLokasiSelesaiAsync(dsrtDao).execute(idDsrt, latitudeSelesai, longitudeSelesai);
    }

    private  static class updateDurasiPencacahanAsycn extends AsyncTask<Object, Void, Void>{
        private DsrtDao dsrtdao;
        public updateDurasiPencacahanAsycn(DsrtDao dsrtDao){
            this.dsrtdao = dsrtDao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            int idDsrt = (int) objects[0];
            String durasi = (String) objects[1];
            dsrtdao.updateDurasiPencacahan(idDsrt, durasi);
            return null;
        }
    }
    public void updateDurasiPencacahan(int idDsrt, String durasi){
        new updateDurasiPencacahanAsycn(dsrtDao).execute(idDsrt, durasi);
    }

    // update jam mulai
    private static class updateJamMulaiAsync extends AsyncTask<Object, Void, Void>{
        private DsrtDao dsrtDao;

        public updateJamMulaiAsync(DsrtDao dsrtDao) {
            this.dsrtDao = dsrtDao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            int idDsrt = (int) objects[0];
            String jamMulai = (String) objects[1];
            dsrtDao.updateJamMulai(idDsrt, jamMulai);
            return null;
        }
    }

    public void updateJamMulai(int idDsrt, String jamMulai){
        new updateJamMulaiAsync(dsrtDao).execute(idDsrt, jamMulai);
    }

    // update jam selesai
    private static class updateJamSelesaiAsync extends AsyncTask<Object, Void, Void>{
        private DsrtDao dsrtDao;

        public updateJamSelesaiAsync(DsrtDao dsrtDao) {
            this.dsrtDao = dsrtDao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            int idDsrt = (int) objects[0];
            String jamSelesai = (String) objects[1];
            dsrtDao.updateJamSelesai(idDsrt, jamSelesai);
            return null;
        }
    }

    public void updateJamSelesai(int idDsrt, String jamSelesai){
        new updateJamSelesaiAsync(dsrtDao).execute(idDsrt, jamSelesai);
    }

    public void getPeriodeFromAPI(Context context){
        InterfaceApi interfaceApi = RetrofitClientInstance.getClient().create(InterfaceApi.class);
        Call<ResponseBody> call = interfaceApi.getPeriodeFromApi();
            call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    String message = jo.getString("message");
                    if (message.equals("success")) {
                        JSONArray joArray = new JSONArray(jo.getString("body"));
                        ArrayList<Periode> periodes = new ArrayList<Periode>(joArray.length());
                        for (int i = 0; i < joArray.length(); i++) {
                            JSONObject ob = new JSONObject(joArray.get(i).toString());
                                Periode periode = new Periode(
                                        ob.getInt("id"),
                                        ob.getString("tahun"),
                                        ob.getInt("semester"),
                                        ob.getString("bulan")
                                );

                            periodes.add(periode );
                        }
                        insertPeriodeList(periodes);
                    }
                    Toast.makeText(context, "Sync Periode Success", Toast.LENGTH_SHORT).show();
                }catch (JSONException | IOException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void insertPeriodeList(List<Periode> periodeList) {
        Periode[] arrayPeriode = new Periode[periodeList.size()];
        arrayPeriode = periodeList.toArray(arrayPeriode);
        new insertPeriodeListAsync(periodeDao).execute(arrayPeriode);
    }

    private static class insertPeriodeListAsync extends AsyncTask<Periode, Void, Void> {
        private PeriodeDao periodeDao;
        public insertPeriodeListAsync(PeriodeDao periodeDao) {
            this.periodeDao = periodeDao;
        }
        @Override
        protected Void doInBackground(Periode... periodes) {
            periodeDao.insert_periode(Arrays.asList(periodes));
            return null;
        }
    }

    public List<Periode> getPeriode() {
        try {
            return new getperiodeAsync(periodeDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class getperiodeAsync extends AsyncTask<Object, Void, List<Periode>>{
        private PeriodeDao periodeDao;
        public getperiodeAsync(PeriodeDao periodeDao){
            this.periodeDao = periodeDao;
        }
        @Override
        protected List<Periode> doInBackground(Object... objects) {
            return periodeDao.getPeriode();
        }
    }

    public void insertDsartList(List<Dsart> dsartList) {
        Dsart[] dsarts = new Dsart[dsartList.size()];
        dsarts = dsartList.toArray(dsarts);
        //asynctask
        new insertDsartListAsync(dsartDao).execute(dsarts);
    }

    private static class insertDsartListAsync extends AsyncTask<Dsart, Void, Void> {
        private DsartDao dsartDao;
        public insertDsartListAsync(DsartDao dsartDao) {
            this.dsartDao = dsartDao;
        }
        @Override
        protected Void doInBackground(Dsart... dsarts) {
            dsartDao.insert_listdsart(Arrays.asList(dsarts));
            return null;
        }
    }

    private static class nukeDsartbyIdAsync extends AsyncTask<Void, Void, Integer> {
        private DsartDao dsartDao;
        String kd_kab, kd_kec, kd_desa, kd_bs, tahun;
        int semester, nu_rt;
        public nukeDsartbyIdAsync(DsartDao dsartDao, String tahun, int semester,
                                  String kd_kab, String kd_kec, String kd_desa, String kd_bs, int nu_rt){
            this.tahun =tahun;
            this.semester = semester;
            this.kd_kab=kd_kab;
            this.kd_kec=kd_kec;
            this.kd_desa=kd_desa;
            this.kd_bs=kd_bs;
            this.nu_rt = nu_rt;
        }
        @Override
        protected Integer doInBackground(Void... voids) {
            return dsartDao.nukeDsartbyid(tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs, nu_rt);
        }
    }
    public void nukeDsartbyId(String tahun, int semester,String kd_kab, String kd_kec, String kd_desa, String kd_bs, int nu_rt) {
        new nukeDsartbyIdAsync(dsartDao, tahun, semester, kd_kab,kd_kec, kd_desa, kd_bs, nu_rt).execute();
    }
    private static class getDsartbyIdAsync extends AsyncTask<Void, Void, List<Dsart>>{
        private DsartDao dsartDao;
        String kd_kab, kd_kec, kd_desa, kd_bs, tahun;
        int semester, nu_rt;
        public getDsartbyIdAsync(DsartDao dsartDao, String tahun, int semester, String kd_kab,
                                 String kd_kec, String kd_desa, String kd_bs, int nu_rt){
            this.dsartDao = dsartDao;
            this.tahun =tahun;
            this.semester = semester;
            this.kd_kab=kd_kab;
            this.kd_kec=kd_kec;
            this.kd_desa=kd_desa;
            this.kd_bs=kd_bs;
            this.nu_rt = nu_rt;
        }
        @Override
        protected List<Dsart> doInBackground(Void... voids) {
            return dsartDao.getDsartListbyid(tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs, nu_rt);
        }
    }
    public List<Dsart> getDsartbyId(String tahun, int semester, String kd_kab,String kd_kec, String kd_desa, String kd_bs,  int nu_rt){
        try {
            return new getDsartbyIdAsync(dsartDao, tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs, nu_rt).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

//    private static class getDsartlamabyIdAsync extends AsyncTask<Void, Void, List<Dsart>>{
//        private DsartDao dsartDao;
//        String id_bs, tahun;
//        int semester, nu_rt;
//        public getDsartlamabyIdAsync(DsartDao dsartDao,String id_bs, String tahun, int semester, int nu_rt){
//            this.dsartDao = dsartDao;
//            this.id_bs=id_bs;
//            this.tahun =tahun;
//            this.semester = semester;
//            this.nu_rt = nu_rt;
//        }
//        @Override
//        protected List<Dsart> doInBackground(Void... voids) {
//            return dsartDao.getDsartListbyid(id_bs, tahun, semester, nu_rt);
//        }
//    }
//    public List<Dsart> getDsartlamabyId(String id_bs, String tahun, int semester, int nu_rt){
//        try {
//            return new getDsartbyIdAsync(dsartDao, id_bs, tahun, semester, nu_rt).execute().get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    public void getDsartPclFromAPI(Context context, String email, String token) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Mengambil Data DSART");
        pd.show();

        InterfaceApi interfaceApi = RetrofitClientInstance.getClient().create(InterfaceApi.class);
        Call<ResponseBody> call = interfaceApi.getDsartPcl(email, "Bearer " + token);
        call.enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    String message = jo.getString("message");
                    if (message.equals("success")) {
                        JSONArray joArray = new JSONArray(jo.getString("body"));
                        ArrayList<Dsart> dsarts = new ArrayList<Dsart>(joArray.length());
                        for (int i = 0; i < joArray.length(); i++) {
                            JSONObject ob = new JSONObject(joArray.get(i).toString());
                            Dsart dsart = new Dsart(
                                    ob.getString("tahun"),
                                    ob.getInt("semester"),
                                    ob.getString("kd_kab"),
                                    ob.getString("kd_kec"),
                                    ob.getString("kd_desa"),
                                    ob.getString("kd_bs"),
                                    ob.getInt("nu_rt"),
                                    ob.getInt("nu_art"),
                                    ob.getString("nks"),
                                    ob.getString("nama_art"),
                                    ob.getString("pekerjaan"),
                                    ob.getString("pendapatan"),
                                    ob.getString("pendidikan")
                            );
                            dsarts.add(dsart);
                        }
                        insertDsartList(dsarts);

                    }
                    Toast.makeText(context, "Sync DSART Success", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }catch (JSONException | IOException e){
                    e.printStackTrace();
                    pd.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }

    public void getDsartPmlFromAPI(Context context, String email, String token) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Mengambil Data DSART");
        pd.show();

        InterfaceApi interfaceApi = RetrofitClientInstance.getClient().create(InterfaceApi.class);
        Call<ResponseBody> call = interfaceApi.getDsartPml(email, "Bearer " + token);
        call.enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    String message = jo.getString("message");
                    if (message.equals("success")) {
                        JSONArray joArray = new JSONArray(jo.getString("body"));
                        ArrayList<Dsart> dsarts = new ArrayList<Dsart>(joArray.length());
                        for (int i = 0; i < joArray.length(); i++) {
                            JSONObject ob = new JSONObject(joArray.get(i).toString());
                            Dsart dsart = new Dsart(
                                    ob.getString("tahun"),
                                    ob.getInt("semester"),
                                    ob.getString("kd_kab"),
                                    ob.getString("kd_kec"),
                                    ob.getString("kd_desa"),
                                    ob.getString("kd_bs"),
                                    ob.getInt("nu_rt"),
                                    ob.getInt("nu_art"),
                                    ob.getString("nks"),
                                    ob.getString("nama_art"),
                                    ob.getString("pekerjaan"),
                                    ob.getString("pendapatan"),
                                    ob.getString("pendidikan")
                            );
                            dsarts.add(dsart);
                        }
                        insertDsartList(dsarts);
                    }
                    Toast.makeText(context, "Sync DSART Success", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }catch (JSONException | IOException e){
                    e.printStackTrace();
                    pd.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
}
