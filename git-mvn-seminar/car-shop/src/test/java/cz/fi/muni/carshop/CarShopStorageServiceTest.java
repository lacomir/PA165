package cz.fi.muni.carshop;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import org.junit.rules.ExpectedException;
import org.junit.Rule;

import cz.fi.muni.carshop.entities.Car;
import cz.fi.muni.carshop.enums.CarTypes;
import cz.fi.muni.carshop.exceptions.RequestedCarNotFoundException;
import cz.fi.muni.carshop.services.CarShopStorageService;
import cz.fi.muni.carshop.services.CarShopStorageServiceImpl;
import org.junit.Assert;

public class CarShopStorageServiceTest {

	private CarShopStorageService service = new CarShopStorageServiceImpl();

	@Rule
	public ExpectedException thrown = ExpectedException.none();
                
        @Test
	public void testSellCar() {
                     
            CarShopStorage.getInstancce().getCars().clear();
            Car newCar = new Car(Color.BLACK, CarTypes.AUDI, 2016, 899000);
            service.addCarToStorage(newCar);
            
            try{
                service.sellCar(newCar);
            } catch (RequestedCarNotFoundException e) {
                Assert.fail("RequestedCarNotFoundException thrown");
            }
            
            Assert.assertEquals(0, CarShopStorage.getInstancce().getCars().size());
	}
        
        @Test
	public void testSellCarEmptyList() throws RequestedCarNotFoundException {
                     
            thrown.reportMissingExceptionWithMessage("We expect exception on empty car list").expect(RequestedCarNotFoundException.class);
            
            CarShopStorage.getInstancce().getCars().clear();
            Car newCar = new Car(Color.BLACK, CarTypes.AUDI, 2016, 899000);
            
            service.sellCar(newCar);
	}
        
        @Test
	public void testSellCarEmptyCarCategory() throws RequestedCarNotFoundException {
                     
            thrown.reportMissingExceptionWithMessage("We expect exception on empty car category").expect(RequestedCarNotFoundException.class);
            
            CarShopStorage.getInstancce().getCars().clear();
            Car newCar = new Car(Color.BLACK, CarTypes.BMW, 2016, 899000);
            service.addCarToStorage(newCar);
            
            Car sellCar = new Car(Color.BLACK, CarTypes.AUDI, 2016, 20000);           
            service.sellCar(sellCar);
	}
        
        
        @Test
	public void testSellCarNotSuccessful() throws RequestedCarNotFoundException {
                     
            CarShopStorage.getInstancce().getCars().clear();
            Car newCar = new Car(Color.WHITE, CarTypes.AUDI, 2014, 100000);
            service.addCarToStorage(newCar);

            thrown.reportMissingExceptionWithMessage("We expect exception when not existing in list").expect(RequestedCarNotFoundException.class);
            
            Car sellCar = new Car(Color.BLACK, CarTypes.AUDI, 2016, 20000);           
            service.sellCar(sellCar);

	}
        
	@Test()
	public void testPriceCantBeNegative() {
		// JUnit 4.11
		//thrown.expect(IllegalArgumentException.class);
		// JUnit 4.12
		 thrown.reportMissingExceptionWithMessage("We expect exception on negative price").expect(IllegalArgumentException.class);

		service.addCarToStorage(new Car(Color.BLACK, CarTypes.AUDI, 2016, -1));
	}
        
        @Test()
	public void testaddCarCannotBeNull() {
		// JUnit 4.11
		//thrown.expect(IllegalArgumentException.class);
		// JUnit 4.12
		 thrown.reportMissingExceptionWithMessage("We expect exception on null added car").expect(IllegalArgumentException.class);

		service.addCarToStorage(null);
	}

	@Test
	public void testGetCar() {
		service.addCarToStorage(new Car(Color.BLACK, CarTypes.AUDI, 2016, 899000));

		assertTrue(service.isCarAvailable(Color.BLACK, CarTypes.AUDI).isPresent());
	}

	@Test
	public void testCarShopStorage_containsTypeForExistingCar() {
		service.addCarToStorage(new Car(Color.BLACK, CarTypes.AUDI, 2016, 899000));
		Map<CarTypes, List<Car>> cars = CarShopStorage.getInstancce().getCars();

		assertThat(cars, hasKey(CarTypes.AUDI));
	}

	// expected to fail with JUnit < 4.11
	@Test
	public void testGetCheaperCars_returnsCorrectResult() {
		service.addCarToStorage(new Car(Color.BLACK, CarTypes.AUDI, 2016, 899000));
		service.addCarToStorage(new Car(Color.BLACK, CarTypes.AUDI, 2016, 889000));
		service.addCarToStorage(new Car(Color.WHITE, CarTypes.AUDI, 2016, 859000));
		service.addCarToStorage(new Car(Color.BLUE, CarTypes.AUDI, 2016, 909000));

		assertThat(service.getCheaperCarsOfSameTypeAndYear(new Car(Color.BLACK, CarTypes.AUDI, 2016, 900000)),
				hasSize(3));

	}

}
