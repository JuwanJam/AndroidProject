package org.techtown.tab;

import java.util.ArrayList;

public interface OnDatabaseCallback {
    public void insert(String name, String location, String mobile, String condition);
    public ArrayList<PetInfo> selectAll();
}


