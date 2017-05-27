package models;

import java.util.Date;

public class MonitoredData {
	private Activity activity;
	private Date startTime;
	private Date endTime;
	
	public Activity getActivity() {
		return activity;
	}
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public MonitoredData(Activity activity, Date startTime, Date endTime) {
		super();
		this.activity = activity;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public Integer getDay(){
		return startTime.getDay();
	}
	
	@Override
	public String toString(){
		return (startTime.toString() + " \t\t" + endTime.toString() + "\t\t" + activity.toString());
	}
	
	public Date getDurationDate(){
		return new Date(endTime.getTime() - startTime.getTime());
	}
	
	public Long getDurationLong(){
		return endTime.getTime() - startTime.getTime();
	}
	
	public Long getDurationMinsLong(){
		return (long) getDurationDate().getMinutes();
	}
}
