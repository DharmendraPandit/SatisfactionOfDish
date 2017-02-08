package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.model.Dish;
import com.example.service.RestaurantService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootAssignmentApplicationTests {

	final static private String TOTAL_TIME_FOR_DISH = "TOTAL_TIME_FOR_DISH";
	final static private String SORTED_DISH = "SORTED_DISH";
	final static private String fileName = "data.txt";// data.txt file should be in classpath
	
	@InjectMocks
	RestaurantService service;
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void test(){
		
		Map<Object, Object> satisfactionResults = service.getSortedDiskInMap(fileName);
		int expectedTotalTimeForDish = 10000;
		// First row and first column of data.txt i.e. total satisfaction amount (10000)
		int actualTotalTimeForDish = (Integer) satisfactionResults.get(TOTAL_TIME_FOR_DISH);
		assertThat(expectedTotalTimeForDish).isEqualTo(actualTotalTimeForDish);
		
		Set<Dish> sortedDisk = (TreeSet<Dish>) satisfactionResults.get(SORTED_DISH);
		long expectedSatisfactionAmount = 2490901;
		long actualSatisfactionAmount = 0L;
		for (Dish dish : sortedDisk) {
			if (actualTotalTimeForDish >= dish.getTimeTaken()) {
				actualTotalTimeForDish -= dish.getTimeTaken();
				actualSatisfactionAmount += dish.getSatisfactionAmount();
			} else {
				continue;
			}
		}
		//assertThat(actualSatisfactionAmount).isLessThanOrEqualTo(expectedTotalSatisfactionAmount);
		assertThat(expectedSatisfactionAmount).isEqualTo(actualSatisfactionAmount);
	}
}
