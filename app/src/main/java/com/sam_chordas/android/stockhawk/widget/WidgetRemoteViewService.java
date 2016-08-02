package com.sam_chordas.android.stockhawk.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.rest.Utils;

/**
 * Created by jhani on 7/13/2016.
 */
public class WidgetRemoteViewService extends RemoteViewsService {
    public final String LOG_TAG = WidgetRemoteViewService.class.getSimpleName();
    private static final String[] QUOTE_COLUMNS = {
            QuoteColumns._ID,
            QuoteColumns.BIDPRICE,
            QuoteColumns.CHANGE,
            QuoteColumns.CREATED,
            QuoteColumns.ISCURRENT,
            QuoteColumns.ISUP,
            QuoteColumns.PERCENT_CHANGE,
            QuoteColumns.SYMBOL
    };
    // these indices must match the projection
    static final int INDEX_QUOTE_ID = 0;
    static final int INDEX_QUOTE_BIDPRICE = 1;
    static final int INDEX_QUOTE_CHANGE = 2;
    static final int INDEX_QUOTE_CREATED = 3;
    static final int INDEX_QUOTE_ISCURRENT = 4;
    static final int INDEX_QUOTE_ISUP = 5;
    static final int INDEX_QUOTE_PERCENT_CHANGE = 6;
    static final int INDEX_QUOTE_SYMBOL = 7;


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor cursor = null;

            @Override
            public void onCreate() {
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {
                if (cursor != null) {
                    cursor.close();
                }

                final long identityToken = Binder.clearCallingIdentity();

                // get the data from the database
                cursor = getContentResolver().query(
                        QuoteProvider.Quotes.CONTENT_URI,
                       QUOTE_COLUMNS,
                        QuoteColumns.ISCURRENT + " = ?",
                        new String[]{"1"},
                        null);
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        cursor == null || !cursor.moveToPosition(position)) {
                    return null;
                }

                // Get the layout
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_detail_list_item);

                // Bind data to the views
                views.setTextViewText(R.id.stock_symbol, cursor.getString(cursor.getColumnIndex
                        (getResources().getString(R.string.string_symbol))));

                if (cursor.getInt(cursor.getColumnIndex(QuoteColumns.ISUP)) == 1) {
                    views.setInt(R.id.change, getResources().getString(R.string.string_set_background_resource), R.drawable.percent_change_pill_green);
                } else {
                    views.setInt(R.id.change, getResources().getString(R.string.string_set_background_resource), R.drawable.percent_change_pill_red);
                }

                if (Utils.showPercent) {
                    views.setTextViewText(R.id.change, cursor.getString(cursor.getColumnIndex(QuoteColumns.PERCENT_CHANGE)));
                } else {
                    views.setTextViewText(R.id.change, cursor.getString(cursor.getColumnIndex(QuoteColumns.CHANGE)));
                }

                final Intent fillInIntent = new Intent();
                fillInIntent.putExtra(getResources().getString(R.string.string_symbol), cursor.getString(cursor.getColumnIndex(QuoteColumns.SYMBOL)));
                views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);

                return views;
            }


            @Override
            public void onDestroy() {

            }

            @Override
            public int getCount() {
                return cursor == null ? 0 : cursor.getCount();
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (cursor.moveToPosition(position))
                    return cursor.getLong(INDEX_QUOTE_ID);
                return position;
            }

            @Override
            public RemoteViews getLoadingView(){ return  null;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }


        };
    }
}