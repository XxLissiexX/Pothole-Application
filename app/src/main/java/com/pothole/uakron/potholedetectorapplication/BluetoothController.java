package com.pothole.uakron.potholedetectorapplication;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.IntentFilter;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import com.pothole.uakron.potholedetectorapplication.MainActivity;

/**
 * Created by Alissa on 11/30/2015.
 */
public class BluetoothController
{
   BluetoothAdapter btAdapter;
    private BroadcastReceiver bluetoothState =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String prevStateExtra =BluetoothAdapter.EXTRA_PREVIOUS_STATE;
            String stateExtra = BluetoothAdapter.EXTRA_STATE;
            int state =intent.getIntExtra(stateExtra, -1);
            int previousState =intent.getIntExtra(prevStateExtra, -1);
            String toastText ="";
            switch(state)
            {
                case(BluetoothAdapter.STATE_TURNING_ON):
                {
                    toastText="Bluetooth turning on";
                    break;
                }
                case(BluetoothAdapter.STATE_TURNING_OFF):
                {
                    toastText="Bluetooth turning off";
                    break;
                }
                case(BluetoothAdapter.STATE_OFF):
                {
                    toastText="Bluetooth Off";
                    break;
                }
                case(BluetoothAdapter.STATE_ON):
                {
                    toastText="Bluetooth On";
                    break;
                }

            }

        }
    };

    public void Turn_on_Bluetooth(Context con)
    {
        btAdapter =BluetoothAdapter.getDefaultAdapter();
        if(btAdapter.isEnabled())
        {
            String address =btAdapter.getAddress();
            String name =btAdapter.getName();
            String statusText = name+ " : " +address;
        }
        String actionStateChanged =BluetoothAdapter.ACTION_STATE_CHANGED;
        String actionRequestEnable =BluetoothAdapter.ACTION_REQUEST_ENABLE;
        IntentFilter filter = new IntentFilter(actionStateChanged);
        //registerReceiver(bluetoothState, filter);

       // startActivityForResult(new Intent(actionRequestEnable),0);

    }

}