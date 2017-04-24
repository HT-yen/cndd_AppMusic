package rimoka.com.navigationgoogleplay;
//lưu ý: phần note là để làm theo cách 2(dùng recycle view thay cho navigation)
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Toolbar toobar;
    private DrawerLayout drawerLayout;
    NavigationView mNavigationView;
    FrameLayout mContentFrame;
    public static SearchView searchView;



    int SELECTED_POSITION;
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cài action bar hoạt động như toolbar
        toobar=(Toolbar) findViewById((R.id.toolbar));
        if(toobar!=null) {
            setSupportActionBar(toobar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        drawerLayout=(DrawerLayout) findViewById(R.id.nav_drawer);
        mContentFrame=(FrameLayout) findViewById(R.id.nav_contentframe);

        if(savedInstanceState!=null){
            //vị trí fragment hiện tại
            SELECTED_POSITION=savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }
        //set cho navigation
        setupNavigation();


        NavigationView navigationView=(NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch(id){
                    case R.id.navigation_song:
                        SELECTED_POSITION=0;
                        MyListSong.setViewfollow(null);
                        MyListSong.setStringcompare(null);
                        startFragment(new ItemTwoFragment());
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.navigation_artist:
                        SELECTED_POSITION=1;
                        startFragment(new ArtistFragment());
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.navigation_album:
                        SELECTED_POSITION=2;
                        startFragment(new AlbumFragment());
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.Cancel:
                        SELECTED_POSITION=3;
                        Intent myIntent =new Intent(MainActivity.this,MyService.class);
                        stopService(myIntent);
                        System.exit(0);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.Star:
                        SELECTED_POSITION=4;
                        Toast.makeText(MainActivity.this,"You have to create before remark! Update Later!",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        return true;
                    default:return true;
                }

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        MenuItem itemSearch = menu.findItem(R.id.mnsearch);
        searchView = (SearchView) itemSearch.getActionView();
        startFragment(new ItemTwoFragment());
        return super.onCreateOptionsMenu(menu);
    }

    public void setupNavigation(){
        if(toobar!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toobar.setNavigationIcon(R.drawable.ic_drawer);
            toobar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(Gravity.START);
                }
            });
        }


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) { //hàm lấy trạng thái của activity(bao gồm cả lúc bát đầu app) hay lức phục hồi app
        super.onRestoreInstanceState(savedInstanceState);
        SELECTED_POSITION=savedInstanceState.getInt(STATE_SELECTED_POSITION,0);
        Menu menu=mNavigationView.getMenu();
        menu.getItem(SELECTED_POSITION).setChecked(true);
    }
    //luu lại vị trí của navi trước khi rời activity
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION,SELECTED_POSITION);
    }
    public void startFragment(Fragment fragment){
        String backstate=fragment.getClass().getName();
        String fragmenttag=backstate;
//        Toast.makeText(getApplicationContext()," "+backstate,Toast.LENGTH_LONG).show();


        FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction transaction=manager.beginTransaction();
            transaction.replace(R.id.nav_contentframe,fragment,fragmenttag);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN); //Fragment is being added onto the stack
            transaction.commit();
    }

}
