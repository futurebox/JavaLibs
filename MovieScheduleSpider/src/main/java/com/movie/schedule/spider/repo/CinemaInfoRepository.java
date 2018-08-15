package com.movie.schedule.spider.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.movie.schedule.spider.domain.CinemaInfo;

@Transactional
public interface CinemaInfoRepository extends JpaRepository<CinemaInfo, Integer> {

	CinemaInfo findByCinemaName(String cinemaName);

}
