package com.example.andro.android_hid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.andro.android_hid.browser.FileChooser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    Button btnRun, btnBrowse;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupOS);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        setScriptName(prefs);
        checkRadio(prefs);

        btnRun = (Button) findViewById(R.id.button);
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String path = getScriptPath(prefs);
                    RunAsRoot(path);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("Run Button", e.getMessage());
                }
            }
        });

        btnBrowse = (Button) findViewById(R.id.button_browse);
        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileExplorer();
            }
        });
    }

    public void openFileExplorer() {
        String system = "";
        if (radioGroup.getCheckedRadioButtonId() == R.id.radioWindows) {
            system = "windows";
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.radioLinux) {
            system = "linux";
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.radioMac) {
            system = "mac";
        }

        Intent intent = new Intent(this, FileChooser.class);
        intent.putExtra("OS", system);
        startActivity(intent);
    }

    public static void RunAsRoot(String path) throws IOException {
        File file = new File(path);
        String cmds = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {

                cmds += AndroidKeyboardCommandHelper.processRow(line) + " ";
            }
            Log.d("LINE", cmds);
            br.close();
        } catch (IOException e) {
            Log.d("FileIO", "File IO exception");
        }

        Process p = Runtime.getRuntime().exec("su");
        DataOutputStream os = new DataOutputStream(p.getOutputStream());
        os.writeBytes(cmds + "\n");
        os.writeBytes("exit\n");
        os.flush();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setScriptName(prefs);
        checkRadio(prefs);
    }

    public static String getScriptPath(SharedPreferences prefs) {
        return prefs.getString("filepath", "");
    }

    private void setScriptName(SharedPreferences prefs) {
        TextView selectedScript = (TextView) findViewById(R.id.text_selected_script);

        String filename = prefs.getString("filename", "");
        if (!filename.equals("")) {
            selectedScript.setText("Script: " + filename);
        }
    }

    private void checkRadio(SharedPreferences prefs) {
        String system = prefs.getString("system", "");
        switch (system) {
            case "windows":
                radioGroup.check(R.id.radioWindows);
                break;
            case "linux":
                radioGroup.check(R.id.radioLinux);
                break;
            case "mac":
                radioGroup.check(R.id.radioMac);
                break;
        }
    }
}
