package de.uniweimar.mis.myoctopocus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity ";

    private EditText editText;
    private ClipboardManager clipboardManager;
    private ClipData clip;

    private MenuItem pasteItem;

    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = (TextView)findViewById(R.id.resultoutput);
        resultText.setText("MyOctopocus is ready, please tip with your finger on the screen");
    }

    // set the object text with dollar and the resulting name
    public void writeDollar(Dollar dollar){   // --> not used anymore
        String TAGf = "writeDollar";
        Log.v(TAGf, " entered");

        resultText.setText("Object: " + dollar.result.Name + " Score: " + dollar.result.Score);
        Log.v(TAGf, "Object: " + dollar.result.Name + " Score: " + dollar.result.Score);
    }

}
