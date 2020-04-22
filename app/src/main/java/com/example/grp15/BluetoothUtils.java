/* Created by GRP team 15 XIN LIN(20030603)*/
package com.example.grp15;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.util.Log;

import java.util.UUID;

public class BluetoothUtils {
    /**
     * if the bluetooth notification is enabled
     * @param enable
     * @param characteristic
     * @return
     */
    //@SuppressLint("NewApi")
    public static boolean enableNotification(BluetoothGatt bluetoothGatt, boolean enable, BluetoothGattCharacteristic characteristic) {
        if (bluetoothGatt == null || characteristic == null) {
            return false;
        }
        if (!bluetoothGatt.setCharacteristicNotification(characteristic, enable)) {
            return false;
        }
        //get the Descriptor from Notify then do the register
        BluetoothGattDescriptor clientConfig = characteristic.getDescriptor(UUIDManager.NOTIFY_DESCRIPTOR);
        if (clientConfig == null) {
            return false;
        }
        if (enable) {
            clientConfig.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        } else {
            clientConfig.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
        }
        return bluetoothGatt.writeDescriptor(clientConfig);
    }

    /**
     * transfer the byte into string
     * @param src the byte array needed to transfer
     * @return the string transferred by byte array
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;// &0xFF make the value 0-255 no sign
            //String hv = Integer.toHexString(v);
            String hv = Integer.toString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * transfer 4 bytes into a flat value
     * since the method will not estimate the length of byte array
     * make sure the correctness of parameter!
     *
     * @param b the byte array
     * @return the float value after transfer
     */
    public static float bytesToFloat(byte[] b, char option) {
        if(option=='x'){
            return Float.intBitsToFloat(bytesToInt(b,3));
        }else if (option == 'y'){
            return Float.intBitsToFloat(bytesToInt(b,7));
        }else{
            return Float.intBitsToFloat(bytesToInt(b,11));
        }

    }
    /**
     * transfer 4 bytes into 4 integer
     * since the method will not estimate the length of byte array
     * make sure the correctness of parameter!
     * @param b byte array
     * @return int value after transfer
     */
    public static int bytesToInt(byte[] b, int n) {
        int i = (b[n] << 24) & 0xFF000000;
        i |= (b[n-1] << 16) & 0xFF0000;
        i |= (b[n-2] << 8) & 0xFF00;
        i |= b[n-3] & 0xFF;
        return i;
    }

    /**
     * transfer the string into byte array for message sending
     * @param message the string needed to be transfer
     * @return the byte array after transfer
     */
    public static byte[] getHexBytes(String message) {
        int len = message.length() / 2;
        char[] chars = message.toCharArray();

        String[] hexStr = new String[len];

        byte[] bytes = new byte[len];

        for (int i = 0, j = 0; j < len; i += 2, j++) {
            hexStr[j] = "" + chars[i] + chars[i + 1];
            bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
        }
        return bytes;
    }
}
