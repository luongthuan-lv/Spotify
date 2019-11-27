package vn.edu.poly.spotify;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import vn.edu.poly.spotify.ui.music.MusicFragment;

public class MainActivity extends AppCompatActivity implements PermisstionView {
    public MusicFragment musicFragment = new MusicFragment();
    public PermisstionPresenter permisstionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        permisstionPresenter=new PermisstionPresenter(this);
        permisstionPresenter.runtimePermisstion();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_music, R.id.navigation_favorite, R.id.navigation_search)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

    @Override
    public void PermisstionGranted() {
        Toast.makeText(MainActivity.this, "Bạn đã cấp quyền tìm kiếm dữ liệu", Toast.LENGTH_SHORT).show();
        musicFragment.getAllAudioFromDevice(MainActivity.this);
    }

    @Override
    public void PermisstionDenied() {
        Toast.makeText(MainActivity.this, "Bạn đã từ chối tìm kiếm dữ liệu", Toast.LENGTH_SHORT).show();
        finish();
    }

}
