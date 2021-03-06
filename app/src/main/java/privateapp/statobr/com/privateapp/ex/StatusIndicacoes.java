package privateapp.statobr.com.privateapp.ex;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import privateapp.statobr.com.privateapp.R;
import privateapp.statobr.com.privateapp.bd.BancoDeDados;

import static java.lang.System.exit;

public class StatusIndicacoes extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context;
    private SimpleCursorAdapter adaptador;
    private ListView lista;
    String comm;
    public BancoDeDados bancoDeDados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_indicacoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.logo_peq);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        context = this;

        lista = (ListView) findViewById(R.id.listando);
        inicializar();
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
    private void inicializar () {
        try {
            bancoDeDados = new BancoDeDados();
            bancoDeDados.init();

        }catch (Exception e1){
            e1.printStackTrace();
        }
    }
    /*
    public void consulta() {

        bancoDeDados.QueryIndic();
        comm = "SELECT NOME FROM  tbIndicacoes ORDER BY NOME";
        bancoDeDados.QuerySQL(comm);

        final Cursor c;

        if (c.moveToFirst()) {
            String[] nomeCampos = new String[]{"Nome"};
            int[] idViews = new int[]{R.id.nomes};
            adaptador = new SimpleCursorAdapter(this, R.layout.activity_lista, c, nomeCampos, idViews, 0);
            lista.setAdapter(adaptador);
        }
        lista.setAdapter(adaptador);

    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.status_indicacoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.perfil) {
            // Handle the camera action
        } else if (id == R.id.indicacoes) {
            Intent i = new Intent(StatusIndicacoes.this, Indicar.class);
            startActivity(i);
        } else if (id == R.id.premio) {

            Intent i = new Intent(StatusIndicacoes.this, Premios.class);
            startActivity(i);
        } else if (id == R.id.status) {
            Intent a = new Intent(StatusIndicacoes.this, StatusIndicacoes.class);
            startActivity(a);
        } else if (id == R.id.configuracoes) {
            Intent e = new Intent(StatusIndicacoes.this, FaleConosc.class);
            startActivity(e);
        }else if (id == R.id.sair) {
            finishAffinity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
