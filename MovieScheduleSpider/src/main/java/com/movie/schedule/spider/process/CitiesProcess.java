package com.movie.schedule.spider.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.movie.schedule.spider.domain.CityInfo;

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
public class CitiesProcess implements PageProcessor {

    private Site site = Site
    		.me()
    		.setRetryTimes(3)
    		.setTimeOut(30000)
    		.setSleepTime(500)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
    
	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<String> dates = new ArrayList<String>();
		List<CityInfo> cities = new ArrayList<CityInfo>();
		
		{
			List<String> hrefs = page.getHtml().xpath("//table[@class='table table-bordered table-condensed']/tbody").links().all();

			for(String href: hrefs) {
				if(!StringUtils.isEmpty(href)) {
					String[] hrefArray = href.split("_");
					dates.add(hrefArray[hrefArray.length-1]);
				}
			}
		}
		
		
		
		/**
		 * 先抓取热门城市信息
		 */
		{
			List<String> names = page.getHtml().xpath("//table[@class='table table-bordered table-condensed']/thead/tr/th/a/text()").all();
			List<String> links = page.getHtml().xpath("//table[@class='table table-bordered table-condensed']/thead/tr/th/a").links().replace("http://pp.58921.com/film/", "").all();
			
			int index = 0;
			for(String name : names) {
				if(StringUtils.isEmpty(links)) {
					
				}else {
					String[] splitArray = links.get(index).split("\\/");

					CityInfo cityInfo = new CityInfo();
					cityInfo.setCityCode(splitArray[splitArray.length-1]);
					cityInfo.setCityName(name);
					cityInfo.setUpdatedAt(new Date());
					
					cities.add(cityInfo);
					
				}
				index++;
			}
		}
		
		/**
		 * 再抓取其他城市信息
		 * 暂时关闭
		 */
		
		{
			List<String> linkTitles = page.getHtml().xpath("//div[@class='panel panel-default pp-home-other-film-list']//div[@class='panel-body']//div[@class='item-list']/ul/li/a/text()").all();
			List<String> links = page.getHtml().xpath("//div[@class='panel panel-default pp-home-other-film-list']//div[@class='panel-body']//div[@class='item-list']/ul/li/a").links().all();
			
			int index = 0;
			for(String linkTitle : linkTitles) {
				if(StringUtils.isEmpty(links)) {
					
				}else {
					String[] titleArray = linkTitle.split(" ");
					String[] splitArray = links.get(index).split("/");
					
					CityInfo cityInfo = new CityInfo();
					cityInfo.setCityCode(splitArray[splitArray.length-1]);
					cityInfo.setCityName(titleArray[0]);
					cityInfo.setUpdatedAt(new Date());
					
					cities.add(cityInfo);
				}
				index++;
			}
			
		}
		
		returnMap.put("dates", dates);
		returnMap.put("cities", cities);
		page.putField("returnMap", returnMap);
	}
	
}
