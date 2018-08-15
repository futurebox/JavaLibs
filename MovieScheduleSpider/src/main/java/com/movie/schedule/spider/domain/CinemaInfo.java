package com.movie.schedule.spider.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name="MSS_CINEMA_INFO")
@NamedQuery(name="CinemaInfo.findAll", query="SELECT c FROM CinemaInfo c")
public class CinemaInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8347920334374389471L;

	@Id
	@GeneratedValue(generator = "system-native")
	@GenericGenerator(name = "system-native", strategy = "native")
	@Column(name="CINEMA_ID")
	private int cinemaId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_AT")
	private Date updatedAt;

	@Column(name="CITY_CODE")
	private String cityCode;
	/**
	 * 影院名称
	 */
	@Column(name="CINEMA_NAME")
	private String cinemaName;

	/**
	 * 经度坐标
	 */
	@Column(name="POI_LNG", columnDefinition="decimal(10,7)")
	private BigDecimal poiLng;

	/**
	 * 纬度坐标
	 */
	@Column(name="POI_LAT", columnDefinition="decimal(10,7)")
	private BigDecimal poiLat;
	
	@Column(name="CINEMA_ID_3RD")
	private String cinemaId3rd;

	public CinemaInfo() {
	}

	public int getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(int cinemaId) {
		this.cinemaId = cinemaId;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCinemaName() {
		return cinemaName;
	}

	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}

	public BigDecimal getPoiLng() {
		return poiLng;
	}

	public void setPoiLng(BigDecimal poiLng) {
		this.poiLng = poiLng;
	}

	public BigDecimal getPoiLat() {
		return poiLat;
	}

	public void setPoiLat(BigDecimal poiLat) {
		this.poiLat = poiLat;
	}

	public String getCinemaId3rd() {
		return cinemaId3rd;
	}

	public void setCinemaId3rd(String cinemaId3rd) {
		this.cinemaId3rd = cinemaId3rd;
	}

}