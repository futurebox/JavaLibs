package com.movie.schedule.spider.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.movie.schedule.spider.domain.MovieInfo;

@Transactional
public interface MovieInfoRepository extends JpaRepository<MovieInfo, Integer> {

}
