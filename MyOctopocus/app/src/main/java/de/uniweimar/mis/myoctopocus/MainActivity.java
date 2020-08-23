package de.uniweimar.mis.myoctopocus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    private ClipboardManager clipboardManager;
    private ClipData clip;

    private MenuItem pasteItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void executeCommand(String name) {
        String TAG = "executeCommand";
        Log.v(TAG, "executionCommand entered");

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
