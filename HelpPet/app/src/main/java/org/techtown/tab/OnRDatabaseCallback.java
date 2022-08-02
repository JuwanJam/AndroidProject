package org.techtown.tab;

import java.util.ArrayList;

public interface OnRDatabaseCallback {
    public void insert_r(String name, String review, float rating, int hid);
    public ArrayList<RInfo> selectAll_r();

}


