package com.sam_chordas.android.stockhawk.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.TaskParams;

/**
 * Created by sam_chordas on 10/1/15.
 */
public class StockIntentService extends IntentService {

  public StockIntentService(){
    super(StockIntentService.class.getName());
  }

  public StockIntentService(String name) {
    super(name);
  }
  int service_result;

  @Override protected void onHandleIntent(Intent intent) {
    Log.d(StockIntentService.class.getSimpleName(), "Stock Intent Service");
    StockTaskService stockTaskService = new StockTaskService(this);
    Bundle args = new Bundle();
    if (intent.getStringExtra("tag").equals("add")){
      args.putString("symbol", intent.getStringExtra("symbol"));
    }
    // We can call OnRunTask from the intent service to force it to run immediately instead of
    // scheduling a task.
    service_result = stockTaskService.onRunTask(new TaskParams(intent.getStringExtra("tag"), args));
    if(service_result == GcmNetworkManager.RESULT_FAILURE)
    {
      // since this service is running on worker thread we cannot display a toast on this thread hence we create a new main thread to display the toast.
      Handler mHandler = new Handler(getMainLooper());
      mHandler.post(new Runnable() {
        @Override
        public void run() {
         // Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_no_symbol), Toast.LENGTH_LONG).show();
          Toast.makeText(getApplicationContext(), "We didn't find this stock listed", Toast.LENGTH_LONG).show();
        }
      });

    }

  }
}
