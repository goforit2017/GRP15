package com.example.grp15;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Bluetooth controller / adapter
 */
public class BluetoothController {

    private BluetoothAdapter mAdapter;

    public BluetoothController(){
        mAdapter = BluetoothAdapter.getDefaultAdapter();    //get the instance of bluetooth adapter
    }

    /**
     * Check if the device is supported for bluetooth
     * @return
     */
    public boolean isSupportBluetooth(){
        if(mAdapter != null){
            return true;
        }else {
            return false;
        }
    }

    /**
     * check the current status of bluetooth
     * @return
     */
    public boolean getBluetoothStatus(){
        assert (mAdapter != null);
        return mAdapter.isEnabled();
    }

    /**
     * Turn on the blue and ask user to manually accept.
     * @param activity
     * @param requestCode
     */
    public void turnOnBluetooth(Activity activity, int requestCode){
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(intent, requestCode);
        //mAdapter.enable(); //without user permission which is not recommend
    }

    public void turnOffBluetooth() {
        mAdapter.disable();
    }

    /**
     * set the visibility of bluetooth
     * @param context
     */
    public void enableVisibility(Context context){
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300); // discoverable time is 5 mins
        context.startActivity(discoverableIntent);
    }

    /**
     * find other devices
     */
    public void findDevice(){
        assert (mAdapter != null);
        mAdapter.startDiscovery();
    }

    /**
     * get the list of bonded devices
     * @return
     */
    public List<BluetoothDevice> getBondedDeviceList(){
        return new ArrayList<>(mAdapter.getBondedDevices());
    }
}
