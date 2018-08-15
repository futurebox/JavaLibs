package com.movie.schedule.spider.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.movie.schedule.spider.domain.MovieInfo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 
 * 爬虫实现
 * 
 * @author futurebox
 *
 */
@Component
public class MoviesProcess implements PageProcessor {

    private Site site = Site
    		.me()
    		.setRetryTimes(3)
    		.setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
    
	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		List<MovieInfo> movies = new ArrayList<MovieInfo>();
		
		/**
		 * 先抓取热门影片名称及ID
		 */
		{
			List<String> movieNames = page.getHtml().xpath("//table[@class='table table-bordered table-condensed']/tbody/tr/td/a/text()").all();
		
			movieNames.stream().forEach(movieName -> {
				List<String> links = page.getHtml().xpath("//table[@class='table table-bordered table-condensed']/tbody/tr/td/a[@title='"+movieName+"']").links().replace("http://pp.58921.com/film/", "").all();
				
				if(StringUtils.isEmpty(links)) {
					
				}else {
					MovieInfo movieInfo = new MovieInfo();
					movieInfo.setMovieId(links.get(0));
					movieInfo.setMovieName(movieName);
					movieInfo.setUpdatedAt(new Date());
					
					movies.add(movieInfo);
				}
			});
		}
		
		/**
		 * 再抓取其他正在上映的影片信息
		 */
		{
			List<String> linkTitles = page.getHtml().xpath("//div[@class='panel panel-default pp-home-other-film-list']//div[@class='panel-body']//div[@class='item-list']/ul/li/a/@title").all();
			linkTitles.stream().forEach(linkTitle ->{
				if(linkTitle.indexOf("排片")>0) {
					List<String> links = page.getHtml().xpath("//div[@class='panel panel-default pp-home-other-film-list']//div[@class='panel-body']//div[@class='item-list']/ul/li/a[@title='"+linkTitle+"']").links().replace("http://pp.58921.com/film/", "").all();
					
					if(StringUtils.isEmpty(links)) {
						
					}else {
						MovieInfo movieInfo = new MovieInfo();
						movieInfo.setMovieId(links.get(0));
						movieInfo.setMovieName(linkTitle.replaceAll("排片", "").trim());
						movieInfo.setUpdatedAt(new Date());
						
						movies.add(movieInfo);
					}
				}
			});
			
		}
		
		page.putField("movies", movies);
	}
	
}
