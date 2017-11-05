package me.hextech.zeus;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class ConfigActivity extends AppCompatActivity {
    int widget_id = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            widget_id = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (widget_id == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        final Intent result = new Intent();
        result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widget_id);

        setResult(RESULT_CANCELED, result);

        setContentView(R.layout.activity_config);

        final SharedPreferences sp = getSharedPreferences("ZEUS", MODE_PRIVATE);

        RecyclerView months = findViewById(R.id.months);
        months.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        MonthsAdapter adapter = new MonthsAdapter();
        adapter.setOnClickListener(new MonthsAdapter.MonthClickListener() {
            @Override
            public void onClick(int position) {
                sp.edit().putInt("month" + String.valueOf(widget_id), position).apply();
                setResult(RESULT_OK, result);
                Log.d("ASAS", String.format("Selected month position %d", position));

                Context context = ConfigActivity.this;
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                Widget.updateWidget(context, appWidgetManager, sp, widget_id);
                finish();
            }
        });

        months.setAdapter(adapter);
    }
}
