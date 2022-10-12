package light_sensor_thing.impl;

import common.*;

public class LightLevelChanged extends Event {

	private double newLevel;
	
	public LightLevelChanged(long timestamp, double newLevel) {
		super(timestamp);
		this.newLevel = newLevel;
	}
	
	public double getNewLevel() {
		return this.newLevel;
	}
	
}
