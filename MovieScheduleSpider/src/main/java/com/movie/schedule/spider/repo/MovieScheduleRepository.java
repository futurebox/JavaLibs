package com.movie.schedule.spider.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.movie.schedule.spider.domain.MovieSchedule;

@Transactional
public interface MovieScheduleRepository extends JpaRepository<MovieSchedule, String> {

	List<MovieSchedule> findByMovieIdAndCinemaIdAndScheduleDate(String movieId, int cinemaId, String date);

}
