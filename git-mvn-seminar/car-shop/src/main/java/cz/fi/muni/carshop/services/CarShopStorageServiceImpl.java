package cz.fi.muni.carshop.services;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import cz.fi.muni.carshop.CarShopStorage;
import cz.fi.muni.carshop.entities.Car;
import cz.fi.muni.carshop.enums.CarTypes;
import cz.fi.muni.carshop.exceptions.RequestedCarNotFoundException;
import java.util.Iterator;

public class CarShopStorageServiceImpl implements CarShopStorageService {

	@Override
	public Optional<Car> isCarAvailable(Color color, CarTypes type) {
		Map<CarTypes, List<Car>> allCars = CarShopStorage.getInstancce().getCars();
		List<Car> carsOfSameType = allCars.get(type);
		return carsOfSameType.stream().filter(car -> car.getColor().equals(color)).findAny();
	}

	@Override
	public List<Car> getCheaperCarsOfSameTypeAndYear(Car referenceCar) {
		Map<CarTypes, List<Car>> allCars = CarShopStorage.getInstancce().getCars();
		List<Car> carsOfSameType = allCars.get(referenceCar.getType());
		return carsOfSameType.stream().filter(car -> referenceCar.getConstructionYear() == car.getConstructionYear()
				&& car.getPrice() < referenceCar.getPrice()).collect(Collectors.toList());
	}

	@Override
	public void addCarToStorage(Car car) {
            
            if(car == null || car.getPrice() < 0) {
                throw new IllegalArgumentException();
            }
            
            CarShopStorage.getInstancce().getCars().computeIfAbsent(car.getType(), x -> new ArrayList<>()).add(car);
	}

        @Override
        public void sellCar(Car car) throws RequestedCarNotFoundException {
//            boolean carFound = false;
//            Iterator<Map.Entry<CarTypes, List<Car>>> carMapIterator = CarShopStorage.getInstancce().getCars().entrySet().iterator();
//            
//            while (carMapIterator.hasNext()) {
//                
//                Map.Entry<CarTypes, List<Car>> entry = carMapIterator.next();
//                
//                for (Iterator<Car> carListIterator = entry.getValue().listIterator(); carListIterator.hasNext(); ) {
//                    Car nextCar = carListIterator.next();
//                    if (car.equals(nextCar)) {
//                        carListIterator.remove();
//                        carFound = true;
//                    }
//                }
//                
//                if(entry.getValue().isEmpty()){
//                    carMapIterator.remove();
//                }
//            }
//            
//            if(!carFound) {
//                throw new RequestedCarNotFoundException(car.toString());
//            }  

//            boolean carFound = false;
//            
//            for (Map.Entry<CarTypes, List<Car>> entry : CarShopStorage.getInstancce().getCars().entrySet()) {
//                if(entry.getValue() != null && !entry.getValue().isEmpty())
//                    carFound = entry.getValue().remove(car);
//            }
            
            Map<CarTypes, List<Car>> allCars = CarShopStorage.getInstancce().getCars();
            List<Car> carsOfSameType = allCars.get(car.getType());
            if(carsOfSameType == null || carsOfSameType.isEmpty()) {
                throw new RequestedCarNotFoundException(car.toString());
            }

            if(!carsOfSameType.remove(car)) {
                throw new RequestedCarNotFoundException(car.toString());
            } else if(carsOfSameType.isEmpty()) {
                    allCars.remove(car.getType());
            }
            
        }   

}
