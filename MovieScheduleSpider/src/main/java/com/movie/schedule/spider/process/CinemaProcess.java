package com.movie.schedule.spider.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.movie.schedule.spider.domain.CinemaInfo;

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
public class CinemaProcess implements PageProcessor {

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
		Map<String, Object> returnMapCinemaPage = new HashMap<String, Object>();
		
		String date = page.getHtml().xpath("//div[@class='col-xs-18']//div[@class='panel-heading']/text()").get();
		
		List<CinemaInfo> cinemas = new ArrayList<CinemaInfo>();

		if(!StringUtils.isEmpty(date)) {
			{
				String[] splitArray = date.split("日");
				returnMapCinemaPage.put("date", splitArray[0].replaceAll("年", "").replaceAll("月", ""));
			}
			
			/**
			 * 抓取影院信息
			 */
			{
				List<String> names = page.getHtml().xpath("//table[@class='table table-bordered table-condensed']/tbody/tr/td/a/text()").all();
				List<String> links = page.getHtml().xpath("//table[@class='table table-bordered table-condensed']/tbody/tr/td/a").links().replace("http://pp.58921.com/cinema/", "").all();
				
				int index = 0;
				for(String name : names) {
					if(StringUtils.isEmpty(links)) {
						
					}else {
						String[] splitArray = links.get(index).split("\\/");
	
						CinemaInfo cinemaInfo = new CinemaInfo();
						cinemaInfo.setCinemaId3rd(splitArray[0]);
						cinemaInfo.setCinemaName(name);
						cinemaInfo.setUpdatedAt(new Date());
						
						cinemas.add(cinemaInfo);
						
					}
					index++;
				}
			}
		}
		
		returnMapCinemaPage.put("cinemas", cinemas);
		page.putField("returnMapCinemaPage", returnMapCinemaPage);
	}
	
}
