package me.com.loganalysis;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        findViewById(R.id.startanalsis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ScrollingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ScrollingActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0x0001);
                } else {
                    LogAnalysis();
                }
            }
        });

        resulttext = ((TextView) findViewById(R.id.analysisresult));
        filename = ((EditText) findViewById(R.id.analysisname));
        resultedit = ((EditText) findViewById(R.id.analysisresultedit));
        dialog = new ProgressDialog(ScrollingActivity.this);
        dialog.setMessage("分析中...");
    }

    ProgressDialog dialog;
    TextView resulttext;
    EditText filename;
    EditText resultedit;

    private void LogAnalysis() {
        dialog.show();
        if (TextUtils.isEmpty(filename.getText().toString())) {
            Toast.makeText(ScrollingActivity.this, "请输入有效的文件名！", Toast.LENGTH_LONG).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                final AnalysisResult reslut = FileUtils.LogAnalysis(ScrollingActivity.this, filename.getText().toString().trim());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        if (reslut.getResults().contains("异常")) {
                            Toast.makeText(ScrollingActivity.this, reslut.getResults(), Toast.LENGTH_LONG).show();
                            return;
                        }
                        String txtfail = "";
                        for (int i = 0; i < reslut.Fails.size(); i++) {
                            txtfail += reslut.Fails.get(i) + "\n";
                        }
                        String txt = "";
                        for (int i = 0; i < reslut.TimeSpan1s.size(); i++) {
                            txt += reslut.TimeSpan1s.get(i) + "\n";
                        }
                        resultedit.setText(reslut.getResults() + "\n" +txtfail + "\n");
                        resulttext.setText(txt);
                    }
                });
            }
        }).start();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0x1001:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LogAnalysis();
                } else {
                    Toast.makeText(ScrollingActivity.this, "您拒绝了读文件权限，无法执行操作！", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
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
