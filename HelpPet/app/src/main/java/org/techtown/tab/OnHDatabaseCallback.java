package org.techtown.tab;

import java.util.ArrayList;

public interface OnHDatabaseCallback {
    public void insert_h(String name, String location, String mobile, String condition);
    public ArrayList<HInfo> selectAll_h();
}


