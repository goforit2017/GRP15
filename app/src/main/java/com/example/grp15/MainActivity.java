package com.example.grp15;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 0;
    private BluetoothController mController = new BluetoothController();
    private BluetoothDevice targetDevice;
    private BluetoothGattCallback mCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                    //super.onConnectionStateChange(gatt, status, newState);???
            Log.d("BLE","If gatt success?: "+ status);
            if(status == 133){
                gatt.close();
                gatt.connect();
                Log.d("BLE","Oops, I'm here again");
                return;
            }
                    //String intentAction;
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                //intentAction = ACTION_GATT_CONNECTED;
                //broadcastUpdate(intentAction);
                Log.i("CONNECT", "Connected to GATT server.");
                // Attempts to discover services after successful connection.
                Log.i("CONNECT", "Attempting to start service discovery:" + gatt.discoverServices());
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                //intentAction = ACTION_GATT_DISCONNECTED;
                Log.i("CONNECT", "Disconnected from GATT server.");
                //broadcastUpdate(intentAction);
            } else if (newState == BluetoothProfile.STATE_CONNECTING) {
                        //intentAction = ACTION_GATT_CONNECTING;
                Log.i("CONNECT", "connecting from GATT server.");
                        //broadcastUpdate(intentAction);
            } else {
                        //intentAction = ACTION_GATT_DISCONNECTING;
                Log.i("CONNECT", "Disconnecting from GATT server.");
                        //broadcastUpdate(intentAction);
                gatt.close();
            }
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                   Log.e("SERVER", "GATT_connect_succeed");
                   List<BluetoothGattService> supportedGattServices = gatt.getServices();
                   if(supportedGattServices==null){
                       Log.e("SERVER","No Server Found");
                   }else{
                       int i=0;
                       int p = 0;
                       for (BluetoothGattService gattService : supportedGattServices) {
                           if(gattService==null){
                               p=p+1;
                           }else{
                               i=1+i;
                               Log.d("123","FUCK!");
                           }
                       }
                       Log.e("ricardo","Server Found : i = "+i+" p = "+p); //i = 0; p = 0
                   }
            }
        }
    };
    private Toast mToast;

    /**
     * Use the broadcast to monitor the state of bluetooth
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)){
                // get the device
                BluetoothDevice scanDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (scanDevice == null || scanDevice.getName() == null){
                    return;
                }
                Log.d("Found", "name="+scanDevice.getName()+" address="+scanDevice.getAddress());
                String name = scanDevice.getName();
                if(name != null && name.equals("GRP15")){   // find the pi
                    mController.mAdapter.cancelDiscovery();
                    targetDevice = scanDevice;
                    if(scanDevice.getBondState()==BluetoothDevice.BOND_NONE){
                         // if not bonded then create bond.
                        Log.d("WTF","yes you gotta here");
                        scanDevice.createBond();
                    }else{
                        BluetoothGatt mGatt = scanDevice.connectGatt(getApplicationContext(), false, mCallback);

                    }
                   
                }

            }else if(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)){
                int scanMode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE,0);
                if (scanMode == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE){
                    // when the scan mode is discoverable show some interactive GUI scine it takes 12s
                    //TODO GUI design
                }else{
                    //TODO GUI design
                }
            }else{
                //TODO
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter();
        // add some action into the filter
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);

        //IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(receiver, filter);
    }

    public void isSupportBluetooth(View view){
        boolean ret = mController.isSupportBluetooth();
        showToast("Support bluetooth? "+ret);
        mController.checkInfo();
    }

    public void isBluetoothEnable(View view){
        boolean ret = mController.getBluetoothStatus();
        showToast("Bluetooth enable? "+ret);
    }

    public void turnOnBluetooth(View view){
        //mController.turnOnBluetooth(this, REQUEST_CODE);
        mController.enableVisibility(this); // both will ask for permission but only one time needed
    }

    public void startSearch(View view){
        if(mController.mAdapter.startDiscovery()){
            Log.d("TEST","discovery start successfully");
        }else{
            Log.e("TEST","Can't start discovery");
        }
    }

    public void trunOffBluetooth(View view){
        mController.turnOffBluetooth();
    }

    private void showToast(String text){
        if (mToast == null){
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(text);
        }
        mToast.show();
    }

    /**
     * check if the activity is done successfully
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            showToast("Open Bluetooth Successfully");
        }else{
            showToast("Fail to Open Bluetooth");
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        unregisterReceiver(receiver);
    }
}
