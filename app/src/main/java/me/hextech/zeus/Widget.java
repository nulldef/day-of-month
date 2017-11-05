package me.hextech.zeus;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Widget extends AppWidgetProvider {
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        SharedPreferences sp = context.getSharedPreferences("ZEUS", Context.MODE_PRIVATE);
        for (int id : appWidgetIds) {
            updateWidget(context, appWidgetManager, sp, id);
        }
    }

    // Known android bug
    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, SharedPreferences sp, int id) {
        int month_index = sp.getInt("month" + String.valueOf(id), -1);
        Log.d("ASAS", String.format("Update %d with month index %d", id, month_index));
        if (month_index < 0) return;

        Calendar calNow = Calendar.getInstance();
        calNow.setTime(new Date());
        int current_month = calNow.get(Calendar.MONTH);
        Calendar cal = Calendar.getInstance();
        int current_year = calNow.get(Calendar.YEAR);
        int year = current_month < month_index ? current_year - 1 : current_year;
        cal.set(year, month_index, 0, 0, 0, 0);
        long days = TimeUnit.MILLISECONDS.toDays(calNow.getTimeInMillis() - cal.getTimeInMillis());

        String month = context.getResources().getStringArray(R.array.month_plur)[month_index];
        RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.widget);
        widget.setTextViewText(R.id.day, String.valueOf(days));
        widget.setTextViewText(R.id.month, month);
        appWidgetManager.updateAppWidget(id, widget);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);

        SharedPreferences.Editor editor =
                context.getSharedPreferences("ZEUS", Context.MODE_PRIVATE).edit();

        for (int id : appWidgetIds) {
            editor.remove(String.format(Locale.US, "month%d", id));
        }
        editor.apply();
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}
