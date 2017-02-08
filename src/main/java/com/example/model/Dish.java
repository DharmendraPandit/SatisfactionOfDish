package com.example.model;

import java.io.Serializable;
/**
 * 
 * @author DharmendraPandit
 *
 */
public class Dish implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3711356234012864888L;

	final private int satisfactionAmount;
	final private int timeTaken;
	final private float satisfaction;

	public Dish(int satisfactionAmount, int timeTaken, float satisfaction) {
		this.satisfactionAmount = satisfactionAmount;
		this.timeTaken = timeTaken;
		this.satisfaction = satisfaction;
	}

	public int getSatisfactionAmount() {
		return satisfactionAmount;
	}

	public int getTimeTaken() {
		return timeTaken;
	}

	public float getSatisfaction() {
		return satisfaction;
	}

	@Override
	public String toString() {
		return new StringBuilder("[satisfactionAmount: ").append(satisfactionAmount).append(" ,timeTaken: ")
				.append(timeTaken).append(" ,satisfaction: ").append(satisfaction).append("]").toString();
	}

	@Override
	public int hashCode() {

		int result = 17;
		result = 31 * result + this.getSatisfactionAmount();
		result = 31 * result + this.getTimeTaken();
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		} else if (!(obj instanceof Dish)) {
			return false;
		} else {
			Dish disk = (Dish) obj;
			return disk.getSatisfactionAmount() == this.getSatisfactionAmount()
					&& disk.getTimeTaken() == this.getTimeTaken();
		}

	}
}
