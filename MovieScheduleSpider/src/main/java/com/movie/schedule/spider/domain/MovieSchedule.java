package com.movie.schedule.spider.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name="MSS_MOVIE_SCHEDULE")
@NamedQuery(name="MovieSchedule.findAll", query="SELECT m FROM MovieSchedule m")
public class MovieSchedule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3285057091315021548L;

	@Id
	@GeneratedValue(generator = "system-guid")
	@GenericGenerator(name = "system-guid", strategy = "uuid")
	@Column(name="SCHEDULE_ID")
	private String scheduleId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_AT")
	private Date createdAt;
	
	/**
	 * 排片时间（分钟）
	 */
	@Column(name="SCHEDULE_DATE")
	private String scheduleDate;
	
	/**
	 * 城市CODE
	 */
	@Column(name="CITY_CODE")
	private String cityCode;
	
	/**
	 * 影院ID
	 */
	@Column(name="CINEMA_ID")
	private int cinemaId;
	
	/**
	 * 影片ID
	 */
	@Column(name="MOVIE_ID")
	private String movieId;

	public MovieSchedule() {
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public int getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(int cinemaId) {
		this.cinemaId = cinemaId;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
}