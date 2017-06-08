/*
	@author So Choi

	Name of Program: 	SingleQueueSimulator
	Description: 		This class tests the LinkedQueue class as well as the teller/customer simulation as given in instructions.
	Date: 				5/8/17
	OS:					Mac OS X
	Compiler: 			terminal (javac)
*/
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SingleQueueSimulator implements QSimulatorInterface {
	/*
		Private instance variables:
		- eventQueue (LinkedQueue<Event>): Series of events for the teller/customer simulation
		- processor (Event): Represents the teller in the simulation
		- idleTimeTotal (double): Accumulator for processor's idle time
		- waitTimeTotal (double): Accumulator for all customers' wait time
		- numEvents (int): Number of "customers"
		- currTime (double): Current time during simulation
		- infile (Scanner): File to read when running simulation
	*/
	private LinkedQueue<Event> eventQueue; 
	private Event processor;  
	private double idleTimeTotal;
	private double waitTimeTotal;
	private int numEvents;
	private double currTime;
	private Scanner infile;
	

	public SingleQueueSimulator(){
		eventQueue = new LinkedQueue<>();
		processor = new Event();
	}

	public SingleQueueSimulator(Scanner file){
		this();
		infile = file;
	}
	
	/*
		Sets local variable as the user designated file
			@param file 	User designated file
	*/
	public void setInputFile(Scanner file){
		infile = file;
	}
	
	/*
		Parses user designated file for the teller/customer simulation
	*/
	private void readFileIntoQueue(){
		//     If the infile instance variable is null, return, otherwise...
		//			Call the clear method for the eventQueue instance variable
		//     		while the infile isn't at the end of file (hasNext)
		//     			read and int and 2 doubles from the infile
		//				instantiate a new Event using the input you just read
		//     			put that new Event into the eventQueue instance variable
		if(infile == null){
			return;
		}else{
			eventQueue.clear();
			while(infile.hasNextLine()){
				String line = infile.nextLine();
				String [] values = line.trim().split("\\s+");
				if(values.length != 3){
					// Event must have 3 values exactly to correctly instantiate.
					System.out.println("Error: Couldn't read all necessary data for an event!");
				}else{
					// Initialize values for temp variables
					int tempId = -999;
					double tempStartTime = -999.;
					double tempDuration = -999.;

					try {
				    	tempId = Integer.parseInt(values[0]);
					} catch (NumberFormatException e) {
						System.out.println("Error: Invalid ID read from file!");
					}

					try {
				    	tempStartTime = Double.parseDouble(values[1]);
					} catch (NumberFormatException e) {
						System.out.println("Error: Invalid Start Time read from file!");
					}

					try {
				    	tempDuration = Double.parseDouble(values[2]);
					} catch (NumberFormatException e) {
						System.out.println("Error: Invalid Duration read from file!");
					}

					Event ev = new Event((int) tempId, (double) tempStartTime, (double) tempDuration);

					if(ev != null){
						eventQueue.enqueue(ev);
					}
				}
			}
		}
	}

	/*
		Runs the teller/customer simulation with the currently user designated file, then displays a summary of the results for the simulation.
	*/
	public void runSimulation(){
		/*  
			INITIALIZE all the numeric member variables to 0
				(to allow running simulation more than once)
			    AND all the processor's member's 0
			Call the readFileIntoQueue() method
			Display "Starting simulation at " and the currTime
			Assign the return value of getEvent() to a local var.
			Loop while the local var. isn't null
				Call the processEvent passing the local var.
				Assign the return value of getEvent() to a local var.
			end loop
			Call the displayStatistics() method 
				(notice this is AFTER the loop ends)
		*/
		idleTimeTotal = 0;
		waitTimeTotal = 0;
		currTime = 0;
		numEvents = 0;
		
		resetProcessor();
		processor.setDuration(0);

		readFileIntoQueue();
		System.out.println("\nStarting simulation at " + currTime + "\n");

		Event currEvent = getEvent();
		while(currEvent != null){
			processEvent(currEvent);
			currEvent = getEvent();
		}

		displayStatistics();
	}

	/*
		Runs the teller/customer simulation with the currently user designated file, then displays a summary of the results for the simulation.
			@return current event for the simulation
	*/
	public Event getEvent(){
		/*
			IF the processor is serving a customer (processor's ID isn't 0)
				RETURN the processor
			ELSE
				IF the eventQueue isn't empty
					Assign the front of the queue to a local var.
							(dequeue from the eventQueue)
					RETURN local var.
				ELSE
					RETURN null (means done with simulation)
		*/
		if(processor.getId() != 0){
			return processor;
		}else{
			if(!eventQueue.isEmpty()){
				Event currEvent = eventQueue.dequeue();
				return currEvent;
			}else{
				return null;
			}
		}
	}

	/*
		Calculates the event times for the processor/customer simulation.
			@param currEvent 	current event for the simulation.
	*/
	public void processEvent(Event currEvent){
		double procStartTime = processor.getStartTime();
		double procDuration = processor.getDuration();
		/*
			IF the currEvent is the processor
				Update the current time to processor's startTime + duration
				Display "Finished processing ", then the processor's ID,
					then the current time
				Reset the processors ID to 0 and the processor's startTime to current time 
					  (to keep track of how long the teller is idle)
		*/
		if(currEvent == processor){
			currTime = procStartTime + currEvent.duration;
			System.out.println("Finished processing " + processor.id + " at " + currTime + "\n");
			resetProcessor();
		}else{
		/*		
			ELSE (assume it's a Customer event from the queue)
				IF the current time < arrivalTime, change the current time to the arrivalTime 
				ENDIF	(otherwise just leave it)
		*/
			double arrivalTime = currEvent.getStartTime();
			if(currTime < arrivalTime){
				currTime = arrivalTime;
			}
			/*
				Calculate the elapsed time between current time and processor's "startTime" (should be >=0)
				Add the elapsed time to the idleTimeTotal
				Calculate how long the "Customer" waited 
					(current time - currEvent's "startTime"
				Add the wait time to the waitTimeTotal
			*/
			if(procStartTime >= 0){
				idleTimeTotal += currTime - procStartTime;
			}
			waitTimeTotal += currTime - arrivalTime;

			/* 
				Increment numEvents
				Indicate that the processor (teller) is serving the currEvent (Customer) by:
				•	displaying "Starting processing ID# ", then currEvent's ID, then " at ", then the currTime
				•	setting the processor's ID to the currEvent's ID
				•	setting the processor's startTime to the currTime
				•	setting the processor's duration to the currEvent's duration
				ENDELSE
			*/
			numEvents++;
			System.out.println("Starting processing ID# " + currEvent.id + " at " + currTime);
			setProcessor(currEvent.id, currTime, currEvent.getDuration());
		}
	}

	/*
		Displays a summary of the processor/customer simulation.
	*/
	public void displayStatistics(){
		/*
			DISPLAY "Ended simulation at ", then the currTime
			DISPLAY idleTimeTotal (with a label like "Total Idle Time:")
			DISPLAY numEvents (with a label like "Number served:")
			DISPLAY waitTimeTotal (with a label like "Total Wait Time:")
			DISPLAY waitTimeTotal/numEvents (with a label like "Average Wait Time:")
		*/
		System.out.println("Ended simulation at " + currTime);
		System.out.println("Total Idle Time: " + idleTimeTotal);
		System.out.println("Number served: " + numEvents);
		System.out.println("Total Wait Time: " + waitTimeTotal);
		if(numEvents > 0){
			System.out.println("Average Wait Time " + waitTimeTotal/numEvents + "\n");
		}else{
			System.out.println("Average Wait Time N/A. No customers served!");
		}
	}

	/*
		Sets the processor with updated values for the ID, start time, and duration.
	*/
	private void setProcessor(int id, double t, double d){
		processor.setId(id);
		processor.setStartTime(t);
		processor.setDuration(d);
	}

	/*
		Sets the processor as ID 0 and the start time as the current time.
	*/
	private void resetProcessor(){
		processor.setId(0);
		processor.setStartTime(currTime);
	}
	
	/*
		Open an input file and initialize a Scanner to read the file. Throw an exception when the file cannot be found.
			@return Scanner variable to help read the file line by line
	*/
	public static Scanner userScanner = new Scanner(System.in);
	public static Scanner openInputFile(){
		String filename;
    	Scanner scanner=null;
        
		System.out.print("Enter the input filename: ");
		filename = userScanner.nextLine();
    	File file= new File(filename);

    	try{
    		scanner = new Scanner(file);
    	}// end try
    	catch(FileNotFoundException fe){
    		System.out.println("Can't open input file\n");
   	    	return null; 
    	} // end catch
    	return scanner;
	}
	
	/*
		Tests the LinkedQueue deep clone.
	*/
	public static void cloneTester(){
		LinkedQueue<Event> theQueue = new LinkedQueue<Event>();
		ArrayList<Event> list = new ArrayList<>(); // for testing later
		Event anEvent;
		Scanner infile;
		int id;
		double start;
		double dur;
		for(id = 10; id < 100; id += 10){
			start = id + 1;
			dur = start + 5;
			anEvent = new Event(id, start, dur);
			theQueue.enqueue(anEvent);
			list.add(anEvent);
		}

		LinkedQueue<Event> copyQueue = theQueue.deepClone();
		System.out.println("After copying, the source queue has");
		System.out.println(theQueue); // calls your toString
		System.out.println("After copying, before clearing the copy queue has");
		System.out.println(copyQueue); // calls your toString
		System.out.println( "\nContents of the copy queue:\n");
		clearAndDisplayQueue(copyQueue);
		System.out.println("After clearing the copy queue has: "+copyQueue);
		System.out.println("\nChecking ability to add back the queue after clearing: ");
		Iterator<Event> iter = list.iterator();
		for( int i=0 ; i < list.size()/2 ; ++i ){ // only copy half
			copyQueue.enqueue( iter.next());
		}
		System.out.println("\nNow the copy queue has: ");
		System.out.println(copyQueue); // calls toString
	} // end cloneTester
	
	/*
		Clears the current queue by emptying all its contents and displays a message for the user.
			@param queue 	Queue to empty
	*/
	public static void clearAndDisplayQueue(LinkedQueue<Event> queue){
		Event event;
		int id;
	 
		while (!queue.isEmpty()){
			event = queue.peekFront();
			id = event.getId();
			System.out.println( event );
			event.setId(id+100); // shouldn't change output anywhere
			queue.dequeue();
		}
		System.out.println("Queue has been emptied\n");
	} // end clearAndDisplayQueue

	/*
		In main, declare Scanner and SingleQueueSimulator variables.  Then
		do the following:
		- instantiate a default SingleQueueSimulator and assign to the main variable
		- open an input file (Scanner) using the same openInputFile from HW#1, and assign to the Scanner variable declared in main
		- LOOP while the Scanner you just assigned isn't null
		- call the setInputFile mutator for the SingleQueueSimulator (passing the Scanner from openInputFile)
		- call runSimulation() for the SingleQueueSimulator you created
		- AGAIN, call the same openInputFile from HW#1, and assign to the Scanner variable declared in main o (end of loop)
		- After the loop, call the cloneTester() method given in the HW2_JavaCodeFile
		
		To stop the loop, enter an empty String for the filename.  See test runs on Catalyst.  Test your programs using the same test input files to see if you get the same results.  Turn in with the output using both of the test input files (one at a time), and then the HW2 Input.txt file.
		
		More hints will be given in class (SEE the Class Notes in Weeks 3
		&4).
		
		DON'T USE RECURSION.
	*/
	public static void main(String[] args){
		SingleQueueSimulator sim = new SingleQueueSimulator();
		Scanner file = openInputFile();
		while(file != null){
			sim.setInputFile(file);
			sim.runSimulation();
			file.close();

			file = openInputFile();
		}
		cloneTester();
	}
} // end SingleQueueSimulation class

/*
	OUTPUT
*/
/*
	Enter the input filename: HW2 Test Input1.txt

	Starting simulation at 0.0

	Starting processing ID# 1 at 5.7
	Finished processing 1 at 9.8

	Starting processing ID# 2 at 9.8
	Finished processing 2 at 13.600000000000001

	Starting processing ID# 3 at 13.600000000000001
	Finished processing 3 at 15.8

	Starting processing ID# 4 at 23.4
	Finished processing 4 at 29.0

	Starting processing ID# 5 at 29.0
	Finished processing 5 at 32.3

	Ended simulation at 32.3
	Total Idle Time: 13.299999999999997
	Number served: 5
	Total Wait Time: 10.100000000000005
	Average Wait Time 2.020000000000001

	Enter the input filename: HW2 Test Input2.txt

	Starting simulation at 0.0

	Starting processing ID# 1 at 3.2
	Finished processing 1 at 7.0

	Starting processing ID# 2 at 10.9
	Finished processing 2 at 14.4

	Starting processing ID# 3 at 14.4
	Finished processing 3 at 18.6

	Starting processing ID# 4 at 18.6
	Finished processing 4 at 21.700000000000003

	Starting processing ID# 5 at 21.700000000000003
	Finished processing 5 at 24.1

	Starting processing ID# 6 at 24.1
	Finished processing 6 at 28.400000000000002

	Starting processing ID# 7 at 28.400000000000002
	Finished processing 7 at 31.1

	Starting processing ID# 8 at 31.1
	Finished processing 8 at 33.2

	Starting processing ID# 9 at 33.2
	Finished processing 9 at 35.7

	Starting processing ID# 10 at 36.6
	Finished processing 10 at 40.0

	Ended simulation at 40.0
	Total Idle Time: 7.999999999999999
	Number served: 10
	Total Wait Time: 26.100000000000012
	Average Wait Time 2.610000000000001

	Enter the input filename: HW2 Input.txt

	Starting simulation at 0.0

	Starting processing ID# 1 at 8.2
	Finished processing 1 at 11.299999999999999

	Starting processing ID# 2 at 11.299999999999999
	Finished processing 2 at 15.799999999999999

	Starting processing ID# 3 at 23.1
	Finished processing 3 at 27.400000000000002

	Starting processing ID# 4 at 27.400000000000002
	Finished processing 4 at 31.1

	Starting processing ID# 5 at 31.1
	Finished processing 5 at 34.0

	Starting processing ID# 6 at 34.0
	Finished processing 6 at 37.3

	Starting processing ID# 7 at 45.5
	Finished processing 7 at 49.2

	Starting processing ID# 8 at 49.2
	Finished processing 8 at 51.300000000000004

	Starting processing ID# 9 at 52.0
	Finished processing 9 at 56.5

	Starting processing ID# 10 at 56.6
	Finished processing 10 at 60.4

	Starting processing ID# 11 at 60.4
	Finished processing 11 at 64.6

	Starting processing ID# 12 at 64.6
	Finished processing 12 at 67.19999999999999

	Starting processing ID# 13 at 67.19999999999999
	Finished processing 13 at 71.39999999999999

	Starting processing ID# 14 at 76.5
	Finished processing 14 at 80.0

	Starting processing ID# 15 at 82.6
	Finished processing 15 at 85.39999999999999

	Starting processing ID# 16 at 85.39999999999999
	Finished processing 16 at 90.69999999999999

	Starting processing ID# 17 at 90.69999999999999
	Finished processing 17 at 93.89999999999999

	Starting processing ID# 18 at 98.6
	Finished processing 18 at 103.39999999999999

	Starting processing ID# 19 at 103.39999999999999
	Finished processing 19 at 106.3

	Starting processing ID# 20 at 106.3
	Finished processing 20 at 109.6

	Starting processing ID# 21 at 111.1
	Finished processing 21 at 116.6

	Ended simulation at 116.6
	Total Idle Time: 38.400000000000006
	Number served: 21
	Total Wait Time: 41.29999999999995
	Average Wait Time 1.9666666666666641

	Enter the input filename: 
	Can't open input file

	After copying, the source queue has
	Event: 10, 11.0, 16.0 -> 
	Event: 20, 21.0, 26.0 -> 
	Event: 30, 31.0, 36.0 -> 
	Event: 40, 41.0, 46.0 -> 
	Event: 50, 51.0, 56.0 -> 
	Event: 60, 61.0, 66.0 -> 
	Event: 70, 71.0, 76.0 -> 
	Event: 80, 81.0, 86.0 -> 
	Event: 90, 91.0, 96.0 
	After copying, before clearing the copy queue has
	Event: 10, 11.0, 16.0 -> 
	Event: 20, 21.0, 26.0 -> 
	Event: 30, 31.0, 36.0 -> 
	Event: 40, 41.0, 46.0 -> 
	Event: 50, 51.0, 56.0 -> 
	Event: 60, 61.0, 66.0 -> 
	Event: 70, 71.0, 76.0 -> 
	Event: 80, 81.0, 86.0 -> 
	Event: 90, 91.0, 96.0 

	Contents of the copy queue:

	Event: 10, 11.0, 16.0
	Event: 20, 21.0, 26.0
	Event: 30, 31.0, 36.0
	Event: 40, 41.0, 46.0
	Event: 50, 51.0, 56.0
	Event: 60, 61.0, 66.0
	Event: 70, 71.0, 76.0
	Event: 80, 81.0, 86.0
	Event: 90, 91.0, 96.0
	Queue has been emptied

	After clearing the copy queue has: 

	Checking ability to add back the queue after clearing: 

	Now the copy queue has: 
	Event: 10, 11.0, 16.0 -> 
	Event: 20, 21.0, 26.0 -> 
	Event: 30, 31.0, 36.0 -> 
	Event: 40, 41.0, 46.0 
*/