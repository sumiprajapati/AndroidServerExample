package com.example.sumi.serverexample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity{
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    //1
    NavigationView navigationView;
    //2
    FragmentTransaction ft;
    //3
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homelayout);
        drawerLayout=findViewById(R.id.drawerlayout);
        toolbar=findViewById(R.id.toolbarid);
        navigationView=findViewById(R.id.navigationview);
        setSupportActionBar(toolbar);
        //hamburger button lyaua lai ra toggle garauna lai
        actionBarDrawerToggle=new ActionBarDrawerToggle(HomeActivity.this,
                drawerLayout,toolbar,R.string.open,R.string.close);


        getSupportActionBar().setTitle("My Example");//hamle banako toolbar ko naam rakheko
        ft=getSupportFragmentManager().beginTransaction();


        ft.add(R.id.framelayout,new AboutUsFragment());//about us fragment add gareko
        // login garne bitikai bydefault ma kholne page
        ft.commit();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.drawer_home:
                        ft=getSupportFragmentManager().beginTransaction();

                        ft.replace(R.id.framelayout,new HomeFragment());//homelayout ma bhako fragementlayout
//(bhanne layout id ma) naya homefragment replace gareko

                        ft.commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.drawer_aboutus:
                        ft=getSupportFragmentManager().beginTransaction();

                        ft.replace(R.id.framelayout,new AboutUsFragment());//fragmentlayout ma about us fragment thapeko
                        ft.commit();
                        drawerLayout.closeDrawers();



                        break;
                    case R.id.drawer_swipabletab:
                        ft=getSupportFragmentManager().beginTransaction();

                        ft.replace(R.id.framelayout,new SwipableFragment());//fragment layout ma swipablefragment thapeko
                        ft.commit();
                        drawerLayout.closeDrawers();


                        break;
                }



                return false;


            }
        });

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
//logout garne button ko lagi menufile inflate garera state define gareko ani
// intent halera logoout bhayesi mainactivity mai intend gareko
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout,menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menuLogout){
            SharedPreferences dp=getSharedPreferences("yourfile",MODE_PRIVATE);
            SharedPreferences.Editor editor=dp.edit();
            editor.remove("state");
            editor.apply();
            Intent i=new Intent(HomeActivity.this,MainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
