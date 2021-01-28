package com.smartgun.shared.file;

public class CsvInitRow {
    private String name;
    private String desc;
    private Integer value;
    private String unit;

    public CsvInitRow(String name, String desc, Integer value, String unit) {
        this.name = name;
        this.desc = desc;
        this.value = value;
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
