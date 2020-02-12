package com.example.uber_navidad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class RegistroRepartidor extends AppCompatActivity {
    EditText edtCedula, edtNombre, edtApellido,
            edtCorreo, edtPassword, edtVerifPass,
            edtFechaNac,edtDireccion;
    Button btnAceptar;
    BigInteger valorMd5 = null;
    ArrayList<String> listaCondcutor;

    public static final String REGEX_NUMEROS = "^[0-9]+$"; //validar numeros
    public static final String REGEX_LETRAS = "^[a-zA-ZáÁéÉíÍóÓúÚñÑüÜ\\s]+$"; //validar letras
    public static final String REGEX_EMAIL ="^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]{2,}[.][a-zA-Z]{2,4}$"; //validar correo electronico
    public static final String REGEX_FECHA ="^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
            + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
            + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
            + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$";//validar fecha de nacimiento

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_repartidor);

        edtNombre = findViewById(R.id.nombresRegistro);
        edtApellido = findViewById(R.id.apellidosRegistro);
        edtCedula = findViewById(R.id.cedulaRegistro);
        edtFechaNac= findViewById(R.id.fechaRegistro);
        edtDireccion = findViewById(R.id.direccionRegistro);
        edtCorreo = findViewById(R.id.mailRegistro);
        edtPassword = findViewById(R.id.passRegistro);
        edtVerifPass = findViewById(R.id.ValpassRegistro);

        btnAceptar = findViewById(R.id.btn_siguiente);
        listaCondcutor = new ArrayList<>();
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validar();
            }
        });
    }

    private void validar(){
        String stNombre = edtNombre.getText().toString();
        String stApellido = edtApellido.getText().toString();
        String stCedula = edtCedula.getText().toString();
        String stFechaNac = edtFechaNac.getText().toString();
       // String stTelefono = edtTelefono.getText().toString();
        String stDireccion = edtDireccion.getText().toString();
        String stCorreo = edtCorreo.getText().toString();
        String stPassword = edtPassword.getText().toString();
        String stVeriPass = edtVerifPass.getText().toString();

        if(!stNombre.trim().isEmpty() && !stApellido.trim().isEmpty() && !stCedula.isEmpty() && !stFechaNac.isEmpty()&&!stDireccion.isEmpty() && !stCorreo.trim().isEmpty() && !stPassword.isEmpty() && !stVeriPass.isEmpty()){
            int i=0;
            Pattern patron = Pattern.compile(REGEX_LETRAS);
            if(patron.matcher(stNombre).matches()){
                listaCondcutor.add(stNombre);
                i++;
            }else{
                edtNombre.setError("Ingrese correctamente el nombre");
                edtNombre.requestFocus();
            }

            if(patron.matcher(stApellido).matches()){
                listaCondcutor.add(stApellido);
                i++;
            }else{
                edtApellido.setError("Ingrese correctamente el apellido");
                edtApellido.requestFocus();
            }
            if(stCedula.length()<10 || stCedula.length()>11 ||!validaCedula(stCedula)){

                edtCedula.setError("Ingrese una cédula válida");
                edtCedula.requestFocus();
            }else{
                listaCondcutor.add(stCedula);
                i++;
            }
            Pattern patron3 = Pattern.compile(REGEX_FECHA);
            if(patron3.matcher(stFechaNac).matches()){
                listaCondcutor.add(stFechaNac);
                i++;
            }else{
                edtFechaNac.setError("Ingrese una fecha correcta");
                edtFechaNac.requestFocus();
            }
            if(stDireccion.length()<50){
                listaCondcutor.add(stDireccion);
                i++;
            }else{
                edtDireccion.setError("Direccion ingresada muy extensa");
                edtDireccion.requestFocus();
            }
            Pattern patron1 = Pattern.compile(REGEX_EMAIL);
            if(patron1.matcher(stCorreo).matches()){
                listaCondcutor.add(stCorreo);
                i++;
            }else{
                edtCorreo.setError("Ingrese correctamente el correo");
                edtCorreo.requestFocus();
            }
            if(stPassword.compareTo(stVeriPass) == 0){
                i++;
                byte[] md5Password = stPassword.getBytes();
                try {
                    valorMd5 = new BigInteger(1,MD5.cryptMD5(md5Password));
                    listaCondcutor.add(valorMd5.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else{
                edtVerifPass.setError("Las contraseñas no son iguales");
                edtVerifPass.requestFocus();
            }
            if(i==7){

                //registrar("http://192.168.0.102/ConexionWebServices/insert.php",valorMd5.toString());
                Intent siguiente = new Intent(this, SplashScreemMain.class);
                siguiente.putExtra("Datos", listaCondcutor);
                startActivity(siguiente);
                Toast.makeText(this, "Registro Correcto", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Falta llenar campos, vuelva a intentarlo", Toast.LENGTH_SHORT).show();
        }
    }


    public static boolean validaCedula(String x){
        int suma=0;
        int[] a = new int[x.length() / 2];
        int[] b = new int[(x.length() / 2)];
        int c=0;
        int d=1;
        for (int i = 0; i < x.length()/2; i++) {
            a[i]=Integer.parseInt(String.valueOf(x.charAt(c)));
            c=c+2;
            if (i < (x.length()/2)-1) {
                b[i]=Integer.parseInt(String.valueOf(x.charAt(d)));
                d=d+2;
            }
        }

        for (int i = 0; i < a.length; i++) {
            a[i]=a[i]*2;
            if (a[i] >9){
                a[i]=a[i]-9;
            }
            suma=suma+a[i]+b[i];
        }
        int aux=suma/10;
        int dec=(aux+1)*10;
        if ((dec - suma) == Integer.parseInt(String.valueOf(x.charAt(x.length()-1))))
            return true;
        else
            return suma % 10 == 0 && x.charAt(x.length() - 1) == '0';
    }
}

