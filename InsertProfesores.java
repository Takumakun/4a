/**EL AUTOR CONSIDERA QUE LOS METODOS AQUÍ IMPLEMENTADOS SON LOS SUFICIENTEMENTE DESCRIPTIVOS
 * COMO PARA NO TENER QUE COMENTARLOS UNO POR UNO, SI TIENE ALGUNA DUDA PONGASE EN CONTACTO EN :
 * TAKUMAKUN@GMAIL.COM
 */

package com.example.takuma.dbadaptertest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertProfesores extends AppCompatActivity {

    private DbHelper myDb;

    EditText editNombreP, editApellidosP, editEdadP, editTutor, editCicloP, editDespacho;
    Button btnAñadir, btnVerInfo, btnBorrarProfesor;
    public static String profesorDespedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_profesores);
        editNombreP = (EditText)findViewById(R.id.editNombreP);
        editApellidosP = (EditText)findViewById(R.id.editApellidosP);
        editEdadP = (EditText)findViewById(R.id.editEdadP);
        editTutor = (EditText)findViewById(R.id.editTutorP);
        editCicloP = (EditText)findViewById(R.id.editCicloP);
        editDespacho = (EditText)findViewById(R.id.editDespacho);
        btnAñadir = (Button)findViewById(R.id.btnAñadirProfesor);
        btnVerInfo = (Button)findViewById(R.id.btnVerInfoProfesor);
        btnBorrarProfesor = (Button)findViewById(R.id.btnBorrarProfesor);
        myDb = new DbHelper(this);
        addDatosProfesores();
        verInfoProfesores();
        borrarInfoProfesores();
    }

    public void addDatosProfesores() {
        btnAñadir.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean estaDentro = myDb.insertarDatosProfesores(
                        editNombreP.getText().toString(),
                        editApellidosP.getText().toString(),
                        editEdadP.getText().toString(),
                        editTutor.getText().toString(),
                        editCicloP.getText().toString(),
                        editDespacho.getText().toString());
                    if (estaDentro = true) {
                        Toast.makeText(InsertProfesores.this, "Info Añadida", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(InsertProfesores.this, "Info no Añadida", Toast.LENGTH_SHORT).show();
                    }
                    //VACIAR LOS CAMPOS
                    editNombreP.setText("");
                    editApellidosP.setText("");
                    editEdadP.setText("");
                    editTutor.setText("");
                    editCicloP.setText("");
                    editDespacho.setText("");
                }
            });
    }

    public void verInfoProfesores(){
        btnVerInfo.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursorDB = myDb.devolverDatosProfesores();
                    StringBuffer buffer = new StringBuffer();
                    if(cursorDB.getCount() == -1){
                        showMessage("Error","No se ha encontrado ningún dato");
                    }
                    while (cursorDB.moveToNext()){
                        buffer.append("id :" + cursorDB.getString(0) + "\n");
                        buffer.append("Nombre :" + cursorDB.getString(1) + "\n");
                        buffer.append("Apellidos :" + cursorDB.getString(2) + "\n");
                        buffer.append("Edad :" + cursorDB.getString(3) + "\n");
                        buffer.append("Tutor :" + cursorDB.getString(4) + "\n");
                        buffer.append("Ciclo :" + cursorDB.getString(5) + "\n");
                        buffer.append("Despacho :" + cursorDB.getString(6) + "\n\n");
                    }
                    //Show all data
                    showMessage("PROFESORES",buffer.toString());
                }
            }
        );
    }

    public void showMessage(String Title, String Message){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle(Title);
        alertBuilder.setMessage(Message);
        alertBuilder.show();
    }

    public void borrarInfoProfesores(){
        btnBorrarProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            despedirProfesor("Despedir Profesor", "Pon la ID del profesor que quieres despedir");
            }
        });
    }

    public String despedirProfesor(String Title, String Message) {
        final EditText aQuienBorro = new EditText(this);
        aQuienBorro.setSingleLine();
        aQuienBorro.setPadding(50, 10, 50, 10);
        AlertDialog.Builder jefeDeEstudios = new AlertDialog.Builder(this);
        jefeDeEstudios.setTitle(Title);
        jefeDeEstudios.setMessage(Message);
        jefeDeEstudios.setView(aQuienBorro);
        jefeDeEstudios.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            profesorDespedido = aQuienBorro.getText().toString();
            Toast.makeText(InsertProfesores.this, "he de borrar la id : " + profesorDespedido, Toast.LENGTH_SHORT).show();
            if (myDb.borrarProfesor(Integer.parseInt(profesorDespedido))) {
                Toast.makeText(InsertProfesores.this, "Se ha despedido al profesor " + profesorDespedido + "", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(InsertProfesores.this, "No se ha podido despedir al profesor " + profesorDespedido + "", Toast.LENGTH_SHORT).show();
            }
            }
        });
        jefeDeEstudios.show();
        return profesorDespedido;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_insert_profesores, menu);
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
}
