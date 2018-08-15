package com.movie.schedule.spider.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.movie.schedule.spider.domain.CityInfo;

@Transactional
public interface CityInfoRepository extends JpaRepository<CityInfo, String> {

}
