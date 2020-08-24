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

    public void executeCommand(String name) {
        String TAGf = "executeCommand";
        Log.v(TAGf, "executionCommand entered");

        String text = editText.getText().toString();

        if (name.equals("Copy")) {
            if (text != null && text != "") {
                clip = ClipData.newPlainText("simple text", text); // saving the text on the clipboard
                clipboardManager.setPrimaryClip(clip);
            }
            // instructions
        } else if (name.equals("Paste")) {
            ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
            String pasteData = item.getText().toString();
            if (pasteData != null || pasteData == "") {

                String newText = "";

                if (editText.hasSelection()) {
                    int start = editText.getSelectionStart();
                    int end = editText.getSelectionEnd();
                    newText = text.substring(0, start);
                    newText += pasteData;
                    newText += text.substring(end, text.length());
                    editText.setText(newText);
                    editText.setSelection(start + pasteData.length()); // setting the cursor to the end of the text

                } else {
                    int cursor_pos = editText.getSelectionStart();
                    newText = text.substring(0, cursor_pos);
                    newText += pasteData;
                    newText += text.substring(cursor_pos, text.length()); // fitting the stored text into the apparent text
                    editText.setText(newText);
                    editText.setSelection(cursor_pos + (pasteData.length())); // setting the cursor after the inserted text
                }

            }

        } else if (name.equals("Select")) {
            editText.setSelection(0, text.length()); // selecting the whole text

        } else if (name.equals("Cut")){

            if(editText.hasSelection()){
                if (text != null && text != "") {
                    System.out.println("Copy " + text);
                    clip = ClipData.newPlainText("simple text", text); // saving the text on the clipboard
                    clipboardManager.setPrimaryClip(clip);
                }

                editText.setText(""); // Deleting text, if selected
            }
        }
    }

}
