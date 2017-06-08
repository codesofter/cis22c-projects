/*
	The Event class represents either the Teller or a Customer in our simulation:

	•	If it's the Teller (called processor in the SingleQueueSimulator class), the id will be 0 if it's "idle", but will be assigned the id of the Customer (Event from the queue), the startTime will be the current time, and the duration will be the Customer's duration when it's serving a customer.

	•	If it's a Customer (Event from the queue), the id, startTime and duration are read from the file, but the startTime will be the Customer's arrival time.
*/
import java.util.*;
import java.io.*;


class Event implements DeepCloneable<Event>{
	protected int id;
	protected double startTime;
	protected double duration;

	public Event(){ id = 0; startTime = 0; duration = 0; }
	public Event(int i, double t, double d)
	{
		id = 0;
		startTime = 0;
		duration = 0;
		setId(i);
		setStartTime(t);
		setDuration(d);
	}
	

	public void setId(int i){ id = i; }
	
	public boolean setStartTime(double t)
	{
		if (t >= 0)
		{
			startTime = t;
			return true;
		}
		return false;
	}
	
	public boolean setDuration(double d)
	{
		if (d >= 0)
		{
			duration = d;
			return true;
		}
		return false;
	}
	
	public int getId() { return id;  }
	
	public double getStartTime()  { return startTime; }
	
	public double getDuration()  { return duration;  }
	
	public String toString()
	{
		return "Event: "+id +", "+startTime + ", "+duration;
	}
	@Override
	public Event deepClone() {
		Event cloneEvent;
		
		cloneEvent = new Event(id, startTime, duration);
		
		return cloneEvent;
	}

}
