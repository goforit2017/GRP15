package com.example.grp15;

import java.util.UUID;

public class UUIDManager {
    public static final UUID SERVICE_UUID = UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb");
    public static final UUID READ_UUID = UUID.fromString("00002a38-0000-1000-8000-00805f9b34fb");
    /**
     * 订阅通知的UUID
     */
    public static final UUID NOTIFY_UUID = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb");
    /**
     * 写出数据的UUID
     */
    public static final String WRITE_UUID = "00006a02-0000-1000-8000-00805f9b34fb";

    /**
     * NOTIFY里面的Descriptor UUID
     */
    public static final UUID NOTIFY_DESCRIPTOR = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
}
