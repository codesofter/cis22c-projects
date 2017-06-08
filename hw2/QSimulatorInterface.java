interface QSimulatorInterface
{
	public void runSimulation();
	public Event getEvent();
	public void processEvent(Event event);
}