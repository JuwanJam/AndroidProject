package org.techtown.tab;

public class PetInfo {

    String name;
    String location;
    String mobile;
    String condition;

    public PetInfo(String name, String location, String mobile, String condition) {
        this.name = name;
        this.location = location;
        this.mobile = mobile;
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location){ this.location = location; }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "PetInfo{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", mobile='" + mobile + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
