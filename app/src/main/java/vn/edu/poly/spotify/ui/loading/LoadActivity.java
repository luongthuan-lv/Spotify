package vn.edu.poly.spotify.ui.loading;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import vn.edu.poly.spotify.MainActivity;
import vn.edu.poly.spotify.PermisstionPresenter;
import vn.edu.poly.spotify.PermisstionView;
import vn.edu.poly.spotify.R;
import vn.edu.poly.spotify.ui.music.MusicFragment;

public class LoadActivity extends AppCompatActivity implements PermisstionView {
    public MusicFragment musicFragment = new MusicFragment();
    public PermisstionPresenter permisstionPresenter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        window.setNavigationBarColor(this.getResources().getColor(R.color.colorPrimary));

        permisstionPresenter=new PermisstionPresenter(this);
        permisstionPresenter.runtimePermisstion();

    }

    @Override
    public void PermisstionGranted() {
        Toast.makeText(LoadActivity.this, "Bạn đã cấp quyền tìm kiếm dữ liệu", Toast.LENGTH_SHORT).show();
        musicFragment.getAllAudioFromDevice1(LoadActivity.this);
        startActivity(new Intent(LoadActivity.this,MainActivity.class));
    }

    @Override
    public void PermisstionDenied() {
        Toast.makeText(LoadActivity.this, "Bạn đã từ chối tìm kiếm dữ liệu", Toast.LENGTH_SHORT).show();
        finish();
    }
}
