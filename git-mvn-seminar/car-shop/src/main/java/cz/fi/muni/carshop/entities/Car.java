package cz.fi.muni.carshop.entities;

import java.awt.Color;

import cz.fi.muni.carshop.enums.CarTypes;

public class Car {

	private Color color;
	private CarTypes type;
	private int constructionYear;
	private int price;
        private double engineVolume;

	public Car(Color color, CarTypes type, int constructionYear, int price) {
		super();
		this.color = color;
		this.type = type;
		this.constructionYear = constructionYear;
		this.price = price;
	}

	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public CarTypes getType() {
		return type;
	}
	
	public int getConstructionYear() {
		return constructionYear;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

        /**
         * @return the engineVolume
         */
        public double getEngineVolume() {
            return engineVolume;
        }

        /**
         * @param engineVolume the engineVolume to set
         */
        public void setEngineVolume(double engineVolume) {
            this.engineVolume = engineVolume;
        }
	
	
	
	
}
