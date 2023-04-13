package com.yejian.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Customer {

    private Integer id; // 主键ID

    private String khno; // 客户编号

    private String name; // 客户名称

    private String area;    //区域

    private String cusManager;  //客户经理

    private String level; // 客户级别

    private String myd;     //满意度

    private String xyd;     //信用度

    private String address;  //地址

    private String postCode;    //邮编

    private String phone; // 手机号码

    private String fax;     //传真

    private String webSite; //网站

    private String yyzzzch; //营业执照注册号

    private String fr; // 法人代表

    private String zczj; //注册资金

    private String nyye;    //年营业额

    private String khyh;    //开户银行

    private String khzh;    //开户账号

    private String dsdjh;   //地税登记表

    private String gsdjh;   //国税登记表

    private Integer state; // 流失状态  0=正常 1=流失

    private Integer isValid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKhno() {
        return khno;
    }

    public void setKhno(String khno) {
        this.khno = khno == null ? null : khno.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getCusManager() {
        return cusManager;
    }

    public void setCusManager(String cusManager) {
        this.cusManager = cusManager == null ? null : cusManager.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getMyd() {
        return myd;
    }

    public void setMyd(String myd) {
        this.myd = myd == null ? null : myd.trim();
    }

    public String getXyd() {
        return xyd;
    }

    public void setXyd(String xyd) {
        this.xyd = xyd == null ? null : xyd.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite == null ? null : webSite.trim();
    }

    public String getYyzzzch() {
        return yyzzzch;
    }

    public void setYyzzzch(String yyzzzch) {
        this.yyzzzch = yyzzzch == null ? null : yyzzzch.trim();
    }

    public String getFr() {
        return fr;
    }

    public void setFr(String fr) {
        this.fr = fr == null ? null : fr.trim();
    }

    public String getZczj() {
        return zczj;
    }

    public void setZczj(String zczj) {
        this.zczj = zczj == null ? null : zczj.trim();
    }

    public String getNyye() {
        return nyye;
    }

    public void setNyye(String nyye) {
        this.nyye = nyye == null ? null : nyye.trim();
    }

    public String getKhyh() {
        return khyh;
    }

    public void setKhyh(String khyh) {
        this.khyh = khyh == null ? null : khyh.trim();
    }

    public String getKhzh() {
        return khzh;
    }

    public void setKhzh(String khzh) {
        this.khzh = khzh == null ? null : khzh.trim();
    }

    public String getDsdjh() {
        return dsdjh;
    }

    public void setDsdjh(String dsdjh) {
        this.dsdjh = dsdjh == null ? null : dsdjh.trim();
    }

    public String getGsdjh() {
        return gsdjh;
    }

    public void setGsdjh(String gsdjh) {
        this.gsdjh = gsdjh == null ? null : gsdjh.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}