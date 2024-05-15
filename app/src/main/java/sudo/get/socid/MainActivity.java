package sudo.get.socid;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button button = findViewById(R.id.button);
        TextView textView = findViewById(R.id.textView);
        button.setOnClickListener(v -> {
            if (button.getText().equals(getString(R.string.button2))) {
                ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("label", textView.getText()));
                button.setText(R.string.button3);
            } else if (button.getText().equals(getString(R.string.button3)))
                finish();
            else if (button.getText().equals(getString(R.string.button))) {
                Process process = null;
                try {
                    process = Runtime.getRuntime().exec("su");
                } catch (IOException e) {
                    textView.setText(R.string.text2);
                    button.setText(R.string.button3);
                }
                if (process != null) {
                    DataOutputStream dataOutputStream = new DataOutputStream(process.getOutputStream());
                    DataInputStream dataInputStream = new DataInputStream(process.getInputStream());
                    try {
                        dataOutputStream.writeBytes("chmod +r /sys/devices/soc0/serial_number \n");
                        dataOutputStream.flush();
                        dataOutputStream.writeBytes("cat /sys/devices/soc0/serial_number \n");
                        dataOutputStream.flush();
                        String result = dataInputStream.readLine();
                        if (result == null) {
                            textView.setText(R.string.text3);
                            button.setText(R.string.button3);
                        } else if (result.matches("\\d+")) {
                            textView.setText(result);
                            textView.setTextIsSelectable(true);
                            button.setText(R.string.button2);
                        } else if (result.matches("0x[0-9a-fA-F]+")) {
                            String cover = result.substring(2);
                            textView.setText(Integer.parseInt(cover, 16));
                            textView.setTextIsSelectable(true);
                            button.setText(R.string.button2);
                        }
                    } catch (IOException ignored) {
                    }
                }
            }
        });

        ImageButton imageButton = findViewById(R.id.github);
        imageButton.setOnClickListener(v -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.github.com/EX3124/get-socid")));
            } catch (ActivityNotFoundException ignored) {
            }
        });
    }
}