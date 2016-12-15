package com.theodinspire.codeadvent.RoomKeys;

/**
 * Created by ecormack on 12/15/2016.
 */
public class RoomKey {
    private String key;
    private int sector;
    private String checksum;

    public RoomKey(String input) {
        int checkSumStart = input.indexOf("[");
        int lastDashIndex = input.lastIndexOf("-");

        checksum = input.substring(checkSumStart + 1, checkSumStart + 6);
        key = input.substring(0, lastDashIndex);
        sector = Integer.parseInt(input.substring(lastDashIndex + 1, checkSumStart));
    }

    public String getKey()      { return key; }
    public int getSector()      { return sector; }
    public String getChecksum() { return checksum; }

    @Override
    public String toString() {
        return key + "-" + sector + "[" + checksum + "]";
    }
}
