package com.example.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.model.Dish;

/**
 * This program is to find the maximum satisfaction that Gordon can get from
 * eating at the restaurant on basis of amount of time to eat.
 * 
 * @author DharmendraPandit
 * @version 1.0
 */
@Service
public class RestaurantService {

	// Define the log object for this class
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	final static private String TOTAL_TIME_FOR_DISH = "TOTAL_TIME_FOR_DISH";
	final static private String SORTED_DISH = "SORTED_DISH";
	final static private String fileName = "data.txt";// data.txt file should be
														// in classpath

	/**
	 * To get the maximum satisfaction result
	 */
	public void getSatisfactionResult() {

		Map<Object, Object> diskMap = getSortedDiskInMap(fileName);
		int totalTimeForDish = (Integer) diskMap.get(TOTAL_TIME_FOR_DISH);
		Set<Dish> sortedDisk = (TreeSet<Dish>) diskMap.get(SORTED_DISH);
		log.info("INPUT totalTimeTakenForDish : " + totalTimeForDish);
		List<Dish> satisfactionList = new ArrayList<>();
		long satisfactionAmount = 0L;
		for (Dish dish : sortedDisk) {
			if (totalTimeForDish >= dish.getTimeTaken()) {
				totalTimeForDish -= dish.getTimeTaken();
				satisfactionList.add(dish);
				satisfactionAmount += dish.getSatisfactionAmount();
			} else {
				continue;
			}
		}

		log.info("OUTPUT satisfactionAmount: " + satisfactionAmount);
		log.info("Remaining timeTakenForDish: " + totalTimeForDish);
		log.info("LIST OF SELECTED DISH : " + satisfactionList);
	}

	/**
	 * 
	 * @param fileName
	 * @return Sorted map on basis of Satisfaction lavel
	 */
	public Map<Object, Object> getSortedDiskInMap(String fileName) {
		Map<Object, Object> returnMap = new HashMap<>();
		Set<Dish> diskMap = new TreeSet(new ObjectSorting());
		int noOfDish = 0;
		int totalTimeForDish = 0;
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			Iterator<String> iterator = stream.iterator();
			Dish disk = null;
			int rowCount = 0;
			boolean isFirstRow = false;
			while (iterator.hasNext()) {
				String[] item = iterator.next().split(" ");
				try {
					int satisfactionAmount = Integer.parseInt(item[0]);
					int timeTaken = Integer.parseInt(item[1]);
					if (isFirstRow == false) {
						// First row of text file would be input
						noOfDish = timeTaken;
						totalTimeForDish = satisfactionAmount;
						isFirstRow = true;
					} else {
						// Calculating the satisfaction ratio of each item
						float satisfaction = (timeTaken > 0) ? ((float) satisfactionAmount / timeTaken) : 0;
						if (item != null) {
							disk = new Dish(satisfactionAmount, timeTaken, satisfaction);
							// Map with sorted order on basis of maximum
							// satisfaction of each item
							diskMap.add(disk);
						}
					}
					if (rowCount == noOfDish) {
						break;
					}
					rowCount++;
				} catch (NumberFormatException e) {
					continue;
				}
			}
			log.info("INPUT Total number of Dish: " + noOfDish);
		} catch (IOException e) {
			e.printStackTrace();
		}

		returnMap.put(TOTAL_TIME_FOR_DISH, totalTimeForDish);
		returnMap.put(SORTED_DISH, diskMap);

		return returnMap;
	}

}

class ObjectSorting implements Comparator<Dish> {

	/**
	 * Sorting on basis of satisfaction satisfaction = satisfactionAmount /
	 * timeTaken
	 */
	@Override
	public int compare(Dish disk1, Dish disk2) {

		if (disk1.getSatisfaction() < disk2.getSatisfaction()) {
			return 1;
		} else if (disk1.getSatisfaction() == disk2.getSatisfaction()) {
			return 0;
		} else {
			return -1;
		}
	}
}
