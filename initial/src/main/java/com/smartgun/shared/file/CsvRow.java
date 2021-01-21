package com.smartgun.shared.file;

public class CsvRow {
    private String name;
    private String desc;
    private String point;
    private String time;

    public CsvRow(String name, String desc, String point, String time) {
        this.name = name;
        this.desc = desc;
        this.point = point;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CsvRow{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", point='" + point + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
