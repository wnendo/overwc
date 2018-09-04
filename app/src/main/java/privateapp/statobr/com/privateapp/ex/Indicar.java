package privateapp.statobr.com.privateapp.ex;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import privateapp.statobr.com.privateapp.Menus;
import privateapp.statobr.com.privateapp.R;
import privateapp.statobr.com.privateapp.bd.BancoDeDados;
import privateapp.statobr.com.privateapp.login.Login;
import privateapp.statobr.com.privateapp.login.Registrar;
import privateapp.statobr.com.privateapp.mask.Mask;

import static java.lang.System.exit;

public class Indicar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public EditText nomeCad, telefoneCad, emailCad, cargoCad, empresaCad;
    private Button confirmar, voltarReg;
    public String nomeCadConf, telefoneCadConf, emailCadConf, cargoCadConf, empresaCadConf,comm;
    public  AlertDialog dialog;
    public AlertDialog.Builder builder;
    public BancoDeDados bancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.logo_peq);
        setSupportActionBar(toolbar);

        confirmar = (Button) findViewById(R.id.indicar);
        voltarReg = (Button) findViewById(R.id.voltarCad);

        nomeCad = (EditText) findViewById(R.id.nomeInd);
        telefoneCad  = (EditText) findViewById(R.id.telefoneInd);
        emailCad = (EditText) findViewById(R.id.emailInd);
        cargoCad = (EditText) findViewById(R.id.cargo);
        empresaCad = (EditText) findViewById(R.id.empresa);

        Mask maskTEL = new Mask("(##)#####-####", telefoneCad);

        telefoneCad.addTextChangedListener(maskTEL);

        inicializar();

        nomeCad.setFocusable(true);
        nomeCad.requestFocus();

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indicar();
            }
        });

        voltarReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltar();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void inicializar () {
        try {
            bancoDeDados = new BancoDeDados();
            bancoDeDados.init();

        }catch (Exception e1){
            e1.printStackTrace();
        }
    }


    public boolean emailVal(String email){
        String regEx =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence emailValidacao = email;

        Pattern pattern = Pattern.compile(regEx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailValidacao);

        if(matcher.matches()) {
            return true;
        }else {
            return false;
        }
    }

    public void indicar() {
        Log.i("Registrar", "Pegando Dados");

        boolean validacaoEmail;

        nomeCadConf = nomeCad.getText().toString();
        telefoneCadConf = telefoneCad.getText().toString();
        emailCadConf = emailCad.getText().toString();
        cargoCadConf = cargoCad.getText().toString();
        empresaCadConf = empresaCad.getText().toString();


        validacaoEmail = emailVal(emailCadConf);

        if (validacaoEmail == true) {
            if (nomeCadConf.isEmpty() || telefoneCadConf.isEmpty() ||
                    emailCadConf.isEmpty()) {

                builder = new AlertDialog.Builder(Indicar.this);
                builder.setMessage("Verifique as informações e tente novamente").setTitle("Erro").setPositiveButton("OK", null);
                builder.show();
            } else {
                Log.i("Registrar", "Realizando Insert" + nomeCadConf + telefoneCadConf + emailCadConf);
                comm = "insert into tbIndicacoes values ('" + nomeCadConf.toString() + "', " +
                        "'" + telefoneCadConf.toString() + "', " +
                        "'" + emailCadConf.toString() + "', " +
                        "'" + cargoCadConf.toString() + "', " +
                        "'" + empresaCadConf.toString() + "')";
                bancoDeDados.QuerySQL(comm);
                Toast.makeText(Indicar.this, "Cadastro efetuado com sucesso", Toast.LENGTH_SHORT).show();
                voltar();
            }
        } else {
            emailCad.setError("Email digitado inválido");
        }
    }

    public void voltar(){
        Intent a = new Intent(getApplicationContext(), Menus.class);
        startActivity(a);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.perfil) {
            // Handle the camera action
        } else if (id == R.id.indicacoes) {
            Intent i = new Intent(Indicar.this, Indicar.class);
            startActivity(i);
        } else if (id == R.id.premio) {
            Intent i = new Intent(Indicar.this, Premios.class);
            startActivity(i);
        } else if (id == R.id.status) {
            Intent a = new Intent(Indicar.this, StatusIndicacoes.class);
            startActivity(a);
        } else if (id == R.id.configuracoes) {
            Intent e = new Intent(Indicar.this, FaleConosc.class);
            startActivity(e);
        }else if (id == R.id.sair) {
            finishAffinity();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
