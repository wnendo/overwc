package privateapp.statobr.com.privateapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import privateapp.statobr.com.privateapp.bd.BancoDeDados;
import privateapp.statobr.com.privateapp.ex.FaleConosc;
import privateapp.statobr.com.privateapp.ex.Indicar;
import privateapp.statobr.com.privateapp.ex.Premios;
import privateapp.statobr.com.privateapp.ex.StatusIndicacoes;

import static java.lang.System.exit;

public class Menus extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public TextView nomeUsu, emailUsu;
    public BancoDeDados bancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.logo_peq);
        setSupportActionBar(toolbar);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Adria Grotesk Bold.ttf");
        TextView tv = (TextView) findViewById(R.id.textCadastro);
        TextView tv2 = (TextView) findViewById(R.id.textIndicar);
        TextView tv3 = (TextView) findViewById(R.id.textStatus);

        tv.setTypeface(font);
        tv2.setTypeface(font);
        tv3.setTypeface(font);

        nomeUsu = (TextView) findViewById(R.id.nomeUsuario);
        emailUsu = (TextView) findViewById(R.id.emailUsuario);

        inicializar();

        FloatingActionButton indicar = (FloatingActionButton) findViewById(R.id.indicarUsuarios);
        indicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Menus.this, Indicar.class);
                startActivity(i);
            }
        });

        FloatingActionButton status = (FloatingActionButton) findViewById(R.id.statusUsuario);
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Menus.this, StatusIndicacoes.class);
                startActivity(i);
            }
        });

        FloatingActionButton premios = (FloatingActionButton) findViewById(R.id.premiosUsuario);
        premios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Menus.this, Premios.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void inicializar() {
        try {
            bancoDeDados = new BancoDeDados();
            bancoDeDados.init();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.perfil) {
            // Handle the camera action
        } else if (id == R.id.indicacoes) {
            Intent i = new Intent(Menus.this, Indicar.class);
            startActivity(i);
        } else if (id == R.id.premio) {

        } else if (id == R.id.status) {
            Intent a = new Intent(Menus.this, StatusIndicacoes.class);
            startActivity(a);
        } else if (id == R.id.configuracoes) {
            Intent e = new Intent(Menus.this, FaleConosc.class);
            startActivity(e);
        }else if (id == R.id.sair) {
            finishAffinity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
