package com.open.boot.pdf.model;

/**
 * 岩芯钻探地质编录表数据字段
 * 
 * @author Administrator
 *
 */
public class Rock {

	private Integer id; // 钻孔id
	private Integer projectId; // 项目id
	private Double stratumDepth;// 实际深度
	private String stratumName; // 岩土名称
	private String stratumAttr; // 地层属性
	private String stratumDesc; // 地层描述（所含矿物成分）
	private String isChange;	//是否变层
	private String weathering; // 风化程度
	private String rockLength; // 岩芯长度
	private String pitch;	//岩层倾角
	private String hard;	//坚硬程度
	private String rqd; // RQD
	private String adoption; // 采取率
	private String completent; //完整程度

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Double getStratumDepth() {
		return stratumDepth;
	}

	public String getIsChange() {
		return isChange;
	}

	public void setIsChange(String isChange) {
		this.isChange = isChange;
	}

	public String getHard() {
		return hard;
	}

	public void setHard(String hard) {
		this.hard = hard;
	}

	public void setStratumDepth(Double stratumDepth) {
		this.stratumDepth = stratumDepth;
	}

	public String getStratumName() {
		return stratumName;
	}

	public void setStratumName(String stratumName) {
		this.stratumName = stratumName;
	}

	public String getStratumAttr() {
		return stratumAttr;
	}

	public void setStratumAttr(String stratumAttr) {
		this.stratumAttr = stratumAttr;
	}

	public String getStratumDesc() {
		return stratumDesc;
	}

	public void setStratumDesc(String stratumDesc) {
		this.stratumDesc = stratumDesc;
	}

	public String getWeathering() {
		return weathering;
	}

	public void setWeathering(String weathering) {
		this.weathering = weathering;
	}

	public String getRockLength() {
		return rockLength;
	}

	public void setRockLength(String rockLength) {
		this.rockLength = rockLength;
	}

	public String getRqd() {
		return rqd;
	}

	public void setRqd(String rqd) {
		this.rqd = rqd;
	}

	public String getAdoption() {
		return adoption;
	}

	public void setAdoption(String adoption) {
		this.adoption = adoption;
	}

	public String getPitch() {
		return pitch;
	}

	public void setPitch(String pitch) {
		this.pitch = pitch;
	}

	public String getCompletent() {
		return completent;
	}

	public void setCompletent(String completent) {
		this.completent = completent;
	}
	
}
