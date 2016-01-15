package com.pothole.uakron.potholedetectorapplication;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
{

    public void goToPotholeMarkers(View view)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        this.startActivity ( intent );
    }
    public void goToPotholeWarnings(View view)
    {
        //change the "Maps Activity" to the new activity.
        // Intent intent = new Intent(this, MapsActivity.class);
        //this.startActivity ( intent );
    }
    public void goToTestGPS(View view)
    {
        Intent intent = new Intent(this, ShowLocationActivity.class);
        this.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //turn on the bluetooth
        BluetoothController btC =new BluetoothController();
        btC.Turn_on_Bluetooth();

    }

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
                        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
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

        public void Turn_on_Bluetooth()
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
            registerReceiver(bluetoothState, filter);

            startActivityForResult(new Intent(actionRequestEnable),0);

        }

    }
}
