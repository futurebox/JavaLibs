package com.movie.schedule.spider.domain;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name="MSS_MOVIE_INFO")
@NamedQuery(name="MovieInfo.findAll", query="SELECT m FROM MovieInfo m")
public class MovieInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3652083235390248242L;

	@Id
	@Column(name="MOVIE_ID")
	private String movieId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_AT")
	private Date updatedAt;

	/**
	 * 影片名称
	 */
	@Column(name="MOVIE_NAME")
	private String movieName;

	@Transient
	private int tempId;

	public MovieInfo() {
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public int getTempId() {
		return tempId;
	}

	public void setTempId(int tempId) {
		this.tempId = tempId;
	}

}