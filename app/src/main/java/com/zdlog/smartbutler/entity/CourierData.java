package com.zdlog.smartbutler.entity;

/**
 * 项目名：SmartButler
 * 包名 ： com.zdlog.smartbutler.entity
 * 文件名：CourierData
 * 创建者：jiushaba
 * 创建时间：2017/4/12 0012下午 12:16
 * 描述： 快递查询实体
 */
public class CourierData {

    //时间
    private String datatime;
    //状态
    private String remark;
    //城市
    private String zone;

    public String getDatatime() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "CourierData{" +
                "datatime='" + datatime + '\'' +
                ", remark='" + remark + '\'' +
                ", zone='" + zone + '\'' +
                '}';
    }
}
