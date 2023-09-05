package com.example.myproducts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproducts.Common.Common;
import com.example.myproducts.Interface.ItemClickListener;
import com.example.myproducts.Model.Category;
import com.example.myproducts.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    TextView tv_fullname;
    FirebaseDatabase db;
    DatabaseReference categ;
    RecyclerView rv_menu;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    SharedPreferences preferences;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

         drawerLayout = findViewById(R.id.drawer_layout);
         navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Categories");
        setSupportActionBar(toolbar);



        db=FirebaseDatabase.getInstance();
        categ=db.getReference("Category");

        toolbar.setNavigationIcon(R.drawable.ic_drawer);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                 R.id.nav_cart, R.id.nav_orders, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

                 if(id==R.id.nav_cart){
                     Intent intent=new Intent(HomeActivity.this,CartActivity.class);
                     startActivity(intent);
                }
                else if(id==R.id.nav_orders){
                     Intent intent=new Intent(HomeActivity.this,OrderStatuesActivity.class);
                     startActivity(intent);
                }
                else if(id==R.id.nav_logout){

                    preferences=getSharedPreferences("pref1",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();

                    editor.clear();
                    editor.commit();

                    Intent intent2=new Intent(HomeActivity.this,SigninActivity.class);
                    intent2.addFlags(intent2.FLAG_ACTIVITY_NEW_TASK | intent2.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent2);
                }
                else if(id==R.id.nav_about){
                     Intent intent=new Intent(HomeActivity.this,AboutActivity.class);
                     startActivity(intent);
                 }
                 else if(id==R.id.nav_contact){
                     Intent intent=new Intent(HomeActivity.this, ContactActivity.class);
                     startActivity(intent);
                 }
                return false;
            }


        });


        View headrView=navigationView.getHeaderView(0);
        TextView tv_fullname=(TextView)headrView.findViewById(R.id.tv_fullname);
        tv_fullname.setText(Common.currentUser.getName());

        rv_menu= findViewById(R.id.rv_menu);
        rv_menu.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        rv_menu.setLayoutManager(layoutManager);


        if(Common.isConnectedToInterner(getBaseContext())) {
            loadMenu();
        }
        else {
            Toast.makeText(HomeActivity.this, "Check your connecing to internet !", Toast.LENGTH_LONG).show();
            return;
        }



    }



    private void loadMenu() {
        adapter=new
                FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.item_menu,MenuViewHolder.class,categ) {
                    @Override
                    protected void populateViewHolder(MenuViewHolder menuViewHolder, Category category, int i) {
                        menuViewHolder.tv_namemenu.setText(category.getName());
                        Picasso.with(getBaseContext()).load(category.getImage())
                                .into(menuViewHolder.iv_menu);
                        Category clickItem = category;
                        menuViewHolder.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int pos, boolean isLongClick) {
                                Intent intentfood=new Intent(HomeActivity.this, ProductsActivity.class);
                                intentfood.putExtra("categid",adapter.getRef(pos).getKey());
                                startActivity(intentfood);
                            }
                        });
                    }
                };
        adapter.notifyDataSetChanged();
        rv_menu.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public void onBackPressed() {

        if(this.drawerLayout.isDrawerOpen(GravityCompat.START)){
            this.drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
}