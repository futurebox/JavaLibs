package com.movie.schedule.spider.service;

import com.movie.schedule.spider.modal.SpiderModal;

/**
 * 
 * 爬虫服务接口
 * 
 * @author futurebox
 *
 */
public interface SpiderService {
	
	/**
	 * 
	 * 抓取影片信息
	 * 
	 * @return
	 */
	SpiderModal processMovies();
}
