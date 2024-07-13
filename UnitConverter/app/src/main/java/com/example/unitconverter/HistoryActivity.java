package com.example.unitconverter;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private TextView tv_history;
    private Button btn_clear_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        tv_history = findViewById(R.id.tv_history);
        btn_clear_history = findViewById(R.id.btn_clear_history);

        loadHistory();

        btn_clear_history.setOnClickListener(v -> {
            HistoryUtils.clearHistory(this);
            loadHistory();
        });
    }

    private void loadHistory() {
        List<String> historyList = HistoryUtils.getHistory(this);
        StringBuilder historyText = new StringBuilder();
        for (String history : historyList) {
            historyText.append(history).append("\n\n");
        }
        tv_history.setText(historyText.toString());
    }
}
