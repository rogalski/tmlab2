package lab.tm.rogalski.lab2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MyActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setListeners();
    }

    @Override
    public void onResume() {
        EditText editText = (EditText) findViewById(R.id.TextToSend);
        editText.setText("");
        super.onResume();
    }

    private void setListeners() {
        Button sendButton = (Button) findViewById(R.id.SendBtn);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.TextToSend);
                String data = editText.getText().toString();
                sendIntent(data);
            }
        });
    }

    private void sendIntent(String data) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);
    }


}
