package privateapp.statobr.com.privateapp.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import privateapp.statobr.com.privateapp.Menus;
import privateapp.statobr.com.privateapp.R;
import privateapp.statobr.com.privateapp.bd.BancoDeDados;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    // UI references.
    public EditText mEmailView;
    public EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button registro;
    public String email, senha;
    public boolean entrar;
    public BancoDeDados bancoDeDados;
    public AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        try {
            inicializar();
        } catch (Exception e2) {
            e2.printStackTrace();
        }

        registro = (Button) findViewById(R.id.registro);

        registro.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                voltar();
            }

        });

        final Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                entrar = login();
                if (entrar == true) {
                    Log.i("Login", "Entrou");
                    Intent i = new Intent(Login.this, Menus.class);
                    startActivity(i);
                } else {
                    Log.i("Login", "Não Entrou");
                    mEmailView.setError("Verifique o email informado");
                    mPasswordView.setError("Verifique a senha informado");
                }

            }
        });

        mProgressView = findViewById(R.id.login_progress);
    }

    private void inicializar() {
        try {
            bancoDeDados = new BancoDeDados();
            bancoDeDados.init();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    public boolean login() {
        Boolean resultado = false;
        Log.i("Login", "Login");
        email = mEmailView.getText().toString();
        senha = mPasswordView.getText().toString();
        System.out.print(email + senha);
        Boolean em = bancoDeDados.QuerySelect(email, senha);

        final ProgressDialog progressDialog = new ProgressDialog(Login.this, R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        }, 200);
        if(email.isEmpty() || senha.isEmpty()){
            builder = new AlertDialog.Builder(Login.this);
            builder.setMessage("Verifique as informações e tente novamente").setTitle("Erro").setPositiveButton("OK", null);
            builder.show();
        }else {

            if (em == true) {
                resultado = true;
            } else {
                resultado = false;
            }
        }
        return resultado;
    }

    public void voltar() {
        Intent a = new Intent(Login.this, Registrar.class);
        startActivity(a);
    }
}