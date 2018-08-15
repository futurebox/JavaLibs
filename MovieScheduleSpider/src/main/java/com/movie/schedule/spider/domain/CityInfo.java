package com.movie.schedule.spider.domain;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name="MSS_CITY_INFO")
@NamedQuery(name="CityInfo.findAll", query="SELECT c FROM CityInfo c")
public class CityInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7625430154409139175L;

	@Id
	@Column(name="CITY_CODE")
	private String cityCode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_AT")
	private Date updatedAt;

	/**
	 * 城市名
	 */
	@Column(name="CITY_NAME")
	private String cityName;

	public CityInfo() {
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}