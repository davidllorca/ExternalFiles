package com.davidllorca.externalfiles;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by David Llorca <davidllorcabaron@gmail.com> on 7/12/14.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSave = (Button) findViewById(R.id.save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        Button btnLoad = (Button) findViewById(R.id.load);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
    }

    private void save() {
        EditText editor = (EditText) findViewById(R.id.editor);
        EditText filename = (EditText) findViewById(R.id.filename);
        OutputStreamWriter out;
        try {
            // open outputstream
            out = new OutputStreamWriter(openFileOutput(filename.getText().toString(), 0));
            out.write(editor.getText().toString());
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        } catch (IOException e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    private void saveInExternalStorage() {
        EditText editor = (EditText) findViewById(R.id.editor);
        EditText filename = (EditText) findViewById(R.id.filename);
        OutputStreamWriter out;
        try {
            File file = Environment.getExternalStorageDirectory();
            FileOutputStream fos = new FileOutputStream(new File(file, filename.getText().toString()));
            fos.write(editor.getText().toString().getBytes());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        } catch (IOException e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    private void load() {
        EditText editor = (EditText) findViewById(R.id.editor);
        EditText filename = (EditText) findViewById(R.id.filename);
        InputStreamReader in;
        try {
            in = new InputStreamReader(openFileInput(filename.getText().toString()));
            BufferedReader bufferedReader = new BufferedReader(in);
            String strTmp = null;
            StringBuffer strBuff = new StringBuffer();
            while ((strTmp = bufferedReader.readLine()) != null) {
                strBuff.append(strTmp + "\n");
            }
            in.close();
            editor.setText(strBuff.toString());
            showMessage("Document has been read");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        } catch (IOException e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    /**
     * Show toast message.
     *
     * @param message
     */
    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
