package com.jadeshop.pro.model;

import java.math.BigDecimal;


public class JadeShopDto {
	//编号
	private String  number;
	
    private Integer id;
    //名称
    private String name;
    //图片
    private byte[] img;
    //材料
    private String stuff;
    //描述
    private String describes;
    //成本价
    private BigDecimal prime;
    //工种
    private String type;
    //售成比
    private BigDecimal primerate;
    //售价
    private BigDecimal price;
    //折扣价
    private BigDecimal disprice;
    //折扣比
    private BigDecimal disrate;
    //利润
    private BigDecimal profit;
    //55利润
    private BigDecimal fiveprofit;
    //利润比
    private BigDecimal profitrate;
    //结账金额
    private BigDecimal closeamount;
    //货主
    private String source;
    //备注
    private String remark;
    //实际结款
    private BigDecimal realamount;
    //合作方
    private String partner;
    //日期
    private String date;
    //状态
    private String status;
    
    private int pageNo;
    
    private int pageSize;   
    

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public String getStuff() {
		return stuff;
	}

	public void setStuff(String stuff) {
		this.stuff = stuff;
	}

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public BigDecimal getPrime() {
		return prime;
	}

	public void setPrime(BigDecimal prime) {
		this.prime = prime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getPrimerate() {
		return primerate;
	}

	public void setPrimerate(BigDecimal primerate) {
		this.primerate = primerate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getDisprice() {
		return disprice;
	}

	public void setDisprice(BigDecimal disprice) {
		this.disprice = disprice;
	}

	public BigDecimal getDisrate() {
		return disrate;
	}

	public void setDisrate(BigDecimal disrate) {
		this.disrate = disrate;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public BigDecimal getFiveprofit() {
		return fiveprofit;
	}

	public void setFiveprofit(BigDecimal fiveprofit) {
		this.fiveprofit = fiveprofit;
	}

	public BigDecimal getProfitrate() {
		return profitrate;
	}

	public void setProfitrate(BigDecimal profitrate) {
		this.profitrate = profitrate;
	}

	public BigDecimal getCloseamount() {
		return closeamount;
	}

	public void setCloseamount(BigDecimal closeamount) {
		this.closeamount = closeamount;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getRealamount() {
		return realamount;
	}

	public void setRealamount(BigDecimal realamount) {
		this.realamount = realamount;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    

}
