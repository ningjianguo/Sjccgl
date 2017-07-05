package com.wonders.main.model;

/**
 * TbCheckMattersType entity. @author MyEclipse Persistence Tools
 */

public class TbCheckMattersType implements java.io.Serializable {

	// Fields

	private String checkId;
	private String checkXuhao;
	private String checkMatters;
	private String checkYj;
	private String checkBody;
	private String checkObject;
	private String checkContent;
	private String checkBl;
	private String checkPc;

	// Constructors

	/** default constructor */
	public TbCheckMattersType() {
	}

	/** full constructor */
	public TbCheckMattersType(String checkXuhao, String checkMatters,
			String checkYj, String checkBody, String checkObject,
			String checkContent, String checkBl, String checkPc) {
		this.checkXuhao = checkXuhao;
		this.checkMatters = checkMatters;
		this.checkYj = checkYj;
		this.checkBody = checkBody;
		this.checkObject = checkObject;
		this.checkContent = checkContent;
		this.checkBl = checkBl;
		this.checkPc = checkPc;
	}

	// Property accessors

	public String getCheckId() {
		return this.checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getCheckXuhao() {
		return this.checkXuhao;
	}

	public void setCheckXuhao(String checkXuhao) {
		this.checkXuhao = checkXuhao;
	}

	public String getCheckMatters() {
		return this.checkMatters;
	}

	public void setCheckMatters(String checkMatters) {
		this.checkMatters = checkMatters;
	}

	public String getCheckYj() {
		return this.checkYj;
	}

	public void setCheckYj(String checkYj) {
		this.checkYj = checkYj;
	}

	public String getCheckBody() {
		return this.checkBody;
	}

	public void setCheckBody(String checkBody) {
		this.checkBody = checkBody;
	}

	public String getCheckObject() {
		return this.checkObject;
	}

	public void setCheckObject(String checkObject) {
		this.checkObject = checkObject;
	}

	public String getCheckContent() {
		return this.checkContent;
	}

	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}

	public String getCheckBl() {
		return this.checkBl;
	}

	public void setCheckBl(String checkBl) {
		this.checkBl = checkBl;
	}

	public String getCheckPc() {
		return this.checkPc;
	}

	public void setCheckPc(String checkPc) {
		this.checkPc = checkPc;
	}

	@Override
	public String toString() {
		return "TbCheckMattersType [checkId=" + checkId + ", checkXuhao="
				+ checkXuhao + ", checkMatters=" + checkMatters + ", checkYj="
				+ checkYj + ", checkBody=" + checkBody + ", checkObject="
				+ checkObject + ", checkContent=" + checkContent + ", checkBl="
				+ checkBl + ", checkPc=" + checkPc + "]";
	}
	
}