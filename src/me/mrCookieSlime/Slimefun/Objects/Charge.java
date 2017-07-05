package me.mrCookieSlime.Slimefun.Objects;

public class Charge {
	
	private double storedEnergy, capacity;
	
	public Charge(double storedEnergy, double capacity) {
		this.storedEnergy = storedEnergy;
		this.capacity = capacity;
	}
	
	public double getStoredEnergy() {
		return storedEnergy;
	}
	
	public double getCapacity() {
		return capacity;
	}

}
