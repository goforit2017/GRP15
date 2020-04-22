/* Created by GRP team 15 XIN LIN(20030603)*/
package com.example.grp15;

import java.util.UUID;

public class UUIDManager {
    public static final UUID SERVICE_UUID = UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb");
    /**
     * UUID of reading
     */
    public static final UUID READ_UUID = UUID.fromString("00002a38-0000-1000-8000-00805f9b34fb");
    /**
     * UUID of NOTIFY
     */
    public static final UUID NOTIFY_UUID = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb");
    /**
     * UUID of writing
     */
    public static final String WRITE_UUID = "00006a02-0000-1000-8000-00805f9b34fb";
    /**
     * Descriptor UUID in NOTIFY
     */
    public static final UUID NOTIFY_DESCRIPTOR = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
}
