/* Created by GRP team 15 XIN LIN(20030603)*/
package com.example.grp15;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Bluetooth controller / adapter
 */
public class BluetoothController {

    public BluetoothAdapter mAdapter;   // may change the public back to private
    public String name; // need to be fixed latter. find a better way
    public String address;

    public BluetoothController(){
        mAdapter = BluetoothAdapter.getDefaultAdapter();    //get the instance of bluetooth adapter
        name = mAdapter.getName();
        address = mAdapter.getAddress();
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

    /**
     * check some information
     */
    public void checkInfo() {
        Log.d("WTF", "bluetooth name =" + name + " address =" + address);
        Set<BluetoothDevice> devices = mAdapter.getBondedDevices();
        Log.d("WTF", "bonded device size =" + devices.size());
        for (BluetoothDevice bondDevice : devices) {
            Log.d("WTF", "bonded device name =" + bondDevice.getName() + " address" + bondDevice.getAddress());
        }
    }
}
