package com.movie.schedule.spider.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.movie.schedule.spider.domain.CinemaInfo;
import com.movie.schedule.spider.domain.CityInfo;
import com.movie.schedule.spider.domain.MovieInfo;
import com.movie.schedule.spider.domain.MovieSchedule;
import com.movie.schedule.spider.modal.SpiderModal;
import com.movie.schedule.spider.process.CinemaProcess;
import com.movie.schedule.spider.process.CitiesProcess;
import com.movie.schedule.spider.process.MoviesProcess;
import com.movie.schedule.spider.repo.CinemaInfoRepository;
import com.movie.schedule.spider.repo.CityInfoRepository;
import com.movie.schedule.spider.repo.MovieInfoRepository;
import com.movie.schedule.spider.repo.MovieScheduleRepository;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;

/**
 * 
 * 服务接口实现
 * 
 * @author futurebox
 *
 */
@Service
public class SpiderServiceImpl implements SpiderService {
	
	@Autowired
	MovieInfoRepository movieRepo;
	
	@Autowired
	CityInfoRepository cityRepo;
	
	@Autowired
	CinemaInfoRepository cinemaRepo;
	
	@Autowired
	MovieScheduleRepository scheduleRepo;

	@SuppressWarnings("unchecked")
	@Override
	public SpiderModal processMovies() {
		SpiderModal spiderModal = new SpiderModal();
		
		try {
			
			Spider spider = Spider.create(new MoviesProcess());

			Spider spiderCity = Spider.create(new CitiesProcess());

			Spider spiderCinema = Spider.create(new CinemaProcess());
			
			ResultItems resultMovies = spider.get("http://pp.58921.com/");

			List<MovieInfo> movies = resultMovies.get("movies");

			if (movies != null && !movies.isEmpty()) {
				movies.stream().forEach(movie -> {
					movie.setUpdatedAt(new Date());
					movieRepo.save(movie);
					ResultItems resultCities = spiderCity.get("http://pp.58921.com/film/"+movie.getMovieId());


					Map<String, Object> citiesMap = resultCities.get("returnMap");
					List<String> dates = (List<String>) citiesMap.get("dates");
					List<CityInfo> cities = (List<CityInfo>) citiesMap.get("cities");

					if (cities != null && !cities.isEmpty()) {
						
						int cityIndex = 0;
						/**
						 * 只抓前15个城市的
						 * TODO
						 */
						for(CityInfo city: cities) {
							if(cityIndex>=15){
								continue;
							}
							city.setUpdatedAt(new Date());
							cityRepo.save(city);
							/**
							 * 只爬最近三天的
							 * TODO
							 */
							int index = 0;
							for(String date: dates) {
								if(index>=3){
									continue;
								}
								
								/**
								 * 查看当天该城市是否已经抓取过
								 * TODO
								 */

								ResultItems resultCinemas = spiderCinema.get("http://pp.58921.com/film/"+movie.getMovieId()+"/"+city.getCityCode()+"/"+date);
								

								if(!StringUtils.isEmpty(resultCinemas)) {
									Map<String, Object> returnMapCinemaPage = resultCinemas.get("returnMapCinemaPage");
									String currentDate = (String) returnMapCinemaPage.get("date");
									
									if(!StringUtils.isEmpty(currentDate)) {

										List<CinemaInfo> cinemas = (List<CinemaInfo>) returnMapCinemaPage.get("cinemas");

										if (cinemas != null && !cinemas.isEmpty()) {
											cinemas.stream().forEach(cinema -> {
												
												/**
												 * 如果不在库里，则新建
												 */
												CinemaInfo cinemaExist = cinemaRepo.findByCinemaName(cinema.getCinemaName());
												if(StringUtils.isEmpty(cinemaExist)) {
													cinema.setCityCode(city.getCityCode());
													cinemaExist = cinemaRepo.save(cinema);
												}else {
													cinemaExist.setCityCode(city.getCityCode());
													cinemaExist.setCinemaName(cinema.getCinemaName());
													cinemaExist.setCinemaId3rd(cinema.getCinemaId3rd());
													cinemaExist.setUpdatedAt(new Date());
													
													cinemaRepo.save(cinemaExist);
												}
												
												/**
												 * 保存排片数据
												 * 1. 先查询是否已经存在
												 * 2. 再判断是否保存
												 */
												List<MovieSchedule> movieSchedules = scheduleRepo.findByMovieIdAndCinemaIdAndScheduleDate(movie.getMovieId(), cinemaExist.getCinemaId(), currentDate);
												if(movieSchedules.size()==0) {
													MovieSchedule movieSchedule = new MovieSchedule();
													movieSchedule.setCinemaId(cinemaExist.getCinemaId());
													movieSchedule.setCityCode(city.getCityCode());
													movieSchedule.setCreatedAt(new Date());
													movieSchedule.setMovieId(movie.getMovieId());
													movieSchedule.setScheduleDate(currentDate);
													
													scheduleRepo.save(movieSchedule);
													
												}
											});
										}
										
										/**
										 * 抓取完成标识
										 */
									}
								}
								index++;
							}
							cityIndex++;
						}
					}
				});
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return spiderModal;
	}
	
}
