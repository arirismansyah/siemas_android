package com.example.siemas.RoomDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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


@Database(entities = {User.class, Dsbs.class, Dsrt.class, StatusRumah.class, Pendidikan.class, Status.class, KegiatanUtama.class, Jadwal212.class, Laporan212.class, Periode.class, Dsart.class, Foto.class}, version = 15, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    private static LocalDatabase localDatabase;
    public static final String databaseName = "siemas";

    public abstract UserDao userDao();

    public abstract DsartDao dsartDao();

    public abstract DsbsDao dsbsDao();

    public abstract DsrtDao dsrtDao();

    public abstract StatusRumahDao statusRumahDao();

    public abstract PendidikanDao pendidikanDao();

    public abstract StatusDao statusDao();

    public abstract KegiatanUtamaDao kegiatanUtamaDao();

    public abstract Jadwal212Dao jadwal212Dao();

    public abstract Laporan212Dao laporan212Dao();

    public abstract PeriodeDao periodeDao();

    public abstract FotoDao fotoDao();

    public static synchronized LocalDatabase getLocalDatabase(Context context) {
        if (localDatabase == null) {
            localDatabase = Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, databaseName)
//                    .setMaxSize(4 * 1024 * 1024)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(mRoomCallBack)
                    .build();
        }
        return localDatabase;
    }

    private static RoomDatabase.Callback mRoomCallBack = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateStatusAsync(localDatabase).execute();
        }

        @Override
        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
            super.onDestructiveMigration(db);
            new PopulateStatusAsync(localDatabase).execute();
        }
    };

    private static class PopulateStatusAsync extends AsyncTask<Void, Void, Void> {
        private StatusDao statusDao;
        private StatusRumahDao statusRumahDao;
        private PendidikanDao pendidikanDao;
        private KegiatanUtamaDao kegiatanUtamaDao;

        private PopulateStatusAsync(LocalDatabase ldb) {
            statusDao = ldb.statusDao();
            statusRumahDao = ldb.statusRumahDao();
            pendidikanDao = ldb.pendidikanDao();
            kegiatanUtamaDao = ldb.kegiatanUtamaDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            statusDao.insertStatus(new com.example.siemas.RoomDatabase.Entities.Status(0, "Belum Cacah"));
            statusDao.insertStatus(new com.example.siemas.RoomDatabase.Entities.Status(1, "Sudah Cacah"));
            statusDao.insertStatus(new com.example.siemas.RoomDatabase.Entities.Status(2, "Sudah Upload Pencacahan"));
            statusDao.insertStatus(new com.example.siemas.RoomDatabase.Entities.Status(3, "Sudah Pemeriksaan Pencacah"));
            statusDao.insertStatus(new com.example.siemas.RoomDatabase.Entities.Status(4, "Sudah Upload Pemeriksaan Pencacah"));
            statusDao.insertStatus(new com.example.siemas.RoomDatabase.Entities.Status(5, "Sudah Pemeriksaan Pengawas"));
            statusDao.insertStatus(new com.example.siemas.RoomDatabase.Entities.Status(6, "Sudah Upload Pemeriksaan Pengawas"));
            statusDao.insertStatus(new com.example.siemas.RoomDatabase.Entities.Status(98, "Non Respon"));
            statusDao.insertStatus(new com.example.siemas.RoomDatabase.Entities.Status(99, "Non Respon Upload"));

            statusRumahDao.insertStatusRumah(new StatusRumah(1, "Milik Sendiri"));
            statusRumahDao.insertStatusRumah(new StatusRumah(2, "Kontrak"));
            statusRumahDao.insertStatusRumah(new StatusRumah(3, "Sewa"));
            statusRumahDao.insertStatusRumah(new StatusRumah(4, "Bebas Sewa"));
            statusRumahDao.insertStatusRumah(new StatusRumah(5, "Dinas"));
            statusRumahDao.insertStatusRumah(new StatusRumah(6, "Lainnya"));
            pendidikanDao.insertPendidikan(new Pendidikan(1, "Paket A"));
            pendidikanDao.insertPendidikan(new Pendidikan(2, "SDLB"));
            pendidikanDao.insertPendidikan(new Pendidikan(3, "SD"));
            pendidikanDao.insertPendidikan(new Pendidikan(4, "MI"));
            pendidikanDao.insertPendidikan(new Pendidikan(5, "Paket B"));
            pendidikanDao.insertPendidikan(new Pendidikan(6, "SMP LB"));
            pendidikanDao.insertPendidikan(new Pendidikan(7, "SMP"));
            pendidikanDao.insertPendidikan(new Pendidikan(8, "MTS"));
            pendidikanDao.insertPendidikan(new Pendidikan(9, "Paket C"));
            pendidikanDao.insertPendidikan(new Pendidikan(10, "SMA LB"));
            pendidikanDao.insertPendidikan(new Pendidikan(11, "SMA"));
            pendidikanDao.insertPendidikan(new Pendidikan(12, "MA"));
            pendidikanDao.insertPendidikan(new Pendidikan(13, "SMK"));
            pendidikanDao.insertPendidikan(new Pendidikan(14, "MAK"));
            pendidikanDao.insertPendidikan(new Pendidikan(15, "D1/D2"));
            pendidikanDao.insertPendidikan(new Pendidikan(16, "D3"));
            pendidikanDao.insertPendidikan(new Pendidikan(17, "D4"));
            pendidikanDao.insertPendidikan(new Pendidikan(18, "S1"));
            pendidikanDao.insertPendidikan(new Pendidikan(19, "Profesi"));
            pendidikanDao.insertPendidikan(new Pendidikan(20, "S2"));
            pendidikanDao.insertPendidikan(new Pendidikan(21, "S3"));
            pendidikanDao.insertPendidikan(new Pendidikan(22, "Tidak Punya Ijazah SD"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(1, "Pertanian tanaman padi dan palawija"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(2, "Holtikultura"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(3, "Perkebunan"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(4, "Perikanan"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(5, "Peternakan"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(6, "Kehutanan dan pertanian lainnya"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(7, "Pertambangan dan penggalian"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(8, "Industri pengolahan"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(9, "Pengadaan listrik, gas, uap/air panas, dan udara dingin"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(10, "Pengelolaan air, pengelolaan air limbah, pengelolaan dan daur ulang sampah, dan aktivitas remediasi"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(11, "Konstruksi"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(12, "Perdagangan besar dan eceran, reparasi dan perawatan mobil dan sepeda motor"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(13, "Pengangkutan dan pergudangan"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(14, "Penyediaan akomodasi dan penyediaan makan minum"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(15, "Informasi dan komunikasi"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(16, "Aktivitas keuangan dan asuransi"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(17, "Real estate"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(18, "Aktivitas profesional, ilmiah, dan teknis"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(19, "Aktivitas penyewaan dan sewa guna tanpa hak opsi, ketenagakerjaan, agen perjalanan, dan penunjang us"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(20, "Administrasi pemerintahan, pertahanan, dan jaminan sosial wajib"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(21, "Pendidikan"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(22, "Aktivitas kesehatan manusia dan aktivitas sosial"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(23, "Kesenian, hiburan, dan rekreasi"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(24, "Aktivitas jasa lainnya"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(25, "Aktivitas rumah tangga sebagai pemberi kerja"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(26, "Aktivitas badan internasional dan badan ekstra internasional lainnya"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(27, "Tidak Bekerja"));
            kegiatanUtamaDao.insertKegiatan(new KegiatanUtama(28, "Lainnya"));
            return null;
        }
    }

}
