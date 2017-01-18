package controller.util.observer;

public abstract class Observer {
	protected Object subject;

	public abstract void update();
}