package controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import models.Activity;
import models.MonitoredData;

public class Main {
	private static final String DATAPATH = "Activities.txt";
//	private static final String TASK1_PATH = "Activities.txt";
	private static final String TASK2_PATH = "task2.txt";
	private static final String TASK3_PATH = "task3.txt";
	private static final String TASK4_PATH = "task4.txt";
	private static final String TASK5_PATH = "task5.txt";
	
	public static void main(String[] args) {
		List<MonitoredData> monitoredData = ActivityParser.instance().parseActivityFile(DATAPATH);
		task1(monitoredData);
		task2(monitoredData);
		task3(monitoredData);
		task4(monitoredData);
		task5(monitoredData);
	}

	public static void task1(List<MonitoredData> data){
		int distinctDays = Stream.concat(data.stream().map(s -> s.getStartTime().getDate()), 
										data.stream().map(s -> s.getEndTime().getDate())).
										collect(Collectors.toSet()).size();
		
		System.out.println(distinctDays);
	}
	
	public static void task2(List<MonitoredData> data){
		Map<Activity, Long> result = data.stream().collect(Collectors.groupingBy(MonitoredData::getActivity, Collectors.counting()));
	
		writeToFile(TASK2_PATH, result);
		System.out.println(result.toString());
	}
	
	public static void task3(List<MonitoredData> data){
		 Map<Integer, Map<Activity, Long>> result = data.stream().collect(Collectors.groupingBy(MonitoredData::getDay, Collectors.groupingBy(MonitoredData::getActivity, Collectors.counting())));
		writeToFile(TASK3_PATH, result);
		System.out.println(result.toString());
	}


	
	public static void task4(List<MonitoredData> data){
		 Map<Activity, Long> result = data.stream()
				 						  .filter(n -> data.stream()
				 								  		   .filter(d -> d.getActivity() == n.getActivity())
				 								  		   .map(a -> a.getDurationMinsLong())
				 								  		   .collect(Collectors.summingLong(Long::longValue)) 
				 								  		    > (long) 600)
				 						  .collect(Collectors.groupingBy(MonitoredData::getActivity, Collectors.summingLong(MonitoredData::getDurationMinsLong)));
		writeToFile(TASK4_PATH, result);
		System.out.println(result.toString());
	}
	
	public static void task5(List<MonitoredData> data){
		List<Activity> result = data.stream()	
									.map(MonitoredData::getActivity)
									.filter(d ->  data.stream().filter(p -> (p.getActivity() == d) && (p.getDurationMinsLong() < 5)).count() * 100 / data.stream().filter(p -> p.getActivity() == d).count() > 90)
									.distinct()
									.collect(Collectors.toList());
		writeToFile(TASK5_PATH, result);
		System.out.println(result.toString());
	}
	
	public static void writeToFile(String path, Object o){
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
			bw.write(o.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
