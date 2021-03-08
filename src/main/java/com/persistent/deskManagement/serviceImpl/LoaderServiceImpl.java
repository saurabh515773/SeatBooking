package com.persistent.deskManagement.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.persistent.deskManagement.entity.Employee;
import com.persistent.deskManagement.entity.FloorPlan;
import com.persistent.deskManagement.entity.SeatInformation;
import com.persistent.deskManagement.model.EnumHelper.CityEnum;
import com.persistent.deskManagement.model.EnumHelper.GenderEnum;
import com.persistent.deskManagement.model.EnumHelper.PreferencesEnum;
import com.persistent.deskManagement.service.EmployeeService;
import com.persistent.deskManagement.service.FloorPlanService;
import com.persistent.deskManagement.service.LoaderService;
import com.persistent.deskManagement.service.SeatInformationService;

@Service("LoaderServiceImpl")
public class LoaderServiceImpl implements LoaderService{

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	FloorPlanService floorPlanService;
	
	@Autowired
	SeatInformationService seatInformationService;
	
	@Override
	public Boolean loadAllEmployees() {
		List<Employee> empList = new ArrayList<>();
		Faker faker = new Faker();
		Employee orgEmpObj = new Employee();
		orgEmpObj.setId(34607);
		orgEmpObj.setUsername("saurabh_awasthi");
		orgEmpObj.setActive(true);
		orgEmpObj.setFirstName("Saurabh");
		orgEmpObj.setLastName("Awasthi");
		orgEmpObj.setGender(GenderEnum.MALE);
		orgEmpObj.setMobileNumber(8808578559L);
		orgEmpObj.setEmailId("saurabh_awasthi@persistent.com");
		orgEmpObj.setIsUserLoggedIn(false);
		orgEmpObj.setPassword("saurabh_awasthi");
		
		empList.add(orgEmpObj);

		for(int i=0 ;i<9 ;i++) {
			Employee empObj = new Employee();
			empObj.setId(faker.random().nextInt(20000, 50000));
			empObj.setFirstName(faker.name().firstName());
			empObj.setLastName(faker.name().lastName());
			empObj.setUsername(empObj.getFirstName().concat("_")
					.concat(empObj.getLastName()));
			empObj.setActive(true);
			empObj.setGender(GenderEnum.MALE);
			empObj.setMobileNumber(Long.valueOf(faker.random().nextLong(999999999)));
			empObj.setEmailId(empObj.getFirstName().concat("_")
					.concat(empObj.getLastName().concat("@persistent.com")));
			empObj.setIsUserLoggedIn(false);
			empObj.setPassword(empObj.getUsername());
			
			empList.add(empObj);
		}

		empList = employeeService.insertAll(empList);
		if(empList.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean loadAllFloor() {
		List<FloorPlan> floorPlanList = new ArrayList<>();
		Faker faker = new Faker();
		FloorPlan OrgFloorPlanObj = new FloorPlan();
		OrgFloorPlanObj.setCity(CityEnum.PUNE);
		OrgFloorPlanObj.setBuilding("SDB1");
		OrgFloorPlanObj.setFloorNumber(4);
		OrgFloorPlanObj.setNoOfCubicle(5);
		OrgFloorPlanObj.setFloorId("PUNE_SDB1_4");
		
		floorPlanList.add(OrgFloorPlanObj);

		for(int i=2 ;i<=5 ;i++) {
			for(CityEnum city : CityEnum.values()) {
				FloorPlan floorPlanObj = new FloorPlan();
				floorPlanObj.setCity(city);
				floorPlanObj.setBuilding("SDB".concat(String.valueOf(i)));
				floorPlanObj.setFloorNumber(faker.random().nextInt(2, 10));
				floorPlanObj.setNoOfCubicle(faker.random().nextInt(4, 10));
				floorPlanObj.setFloorId(floorPlanObj.getCity().name().concat("_")
						.concat(floorPlanObj.getBuilding()).concat("_")
						.concat(String.valueOf(floorPlanObj.getFloorNumber())));
				
				floorPlanList.add(floorPlanObj);
			}
		}
		
		floorPlanList = floorPlanService.insertAll(floorPlanList);
		if(floorPlanList.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean loadAllSeat() {
		List<SeatInformation> seatInformationList = new ArrayList<>();
		Faker faker = new Faker();
		SeatInformation orgSeatInformationObj = new SeatInformation();
		orgSeatInformationObj.setSeatPosition("S1");
		orgSeatInformationObj.setTotalTimeOccupied(5);
		orgSeatInformationObj.setTotalTimeBooked(10);
		orgSeatInformationObj.setPreference(PreferencesEnum.AC);
		orgSeatInformationObj.setLastBookedDate(LocalDate.now());
		orgSeatInformationObj.setIsAvailable(true);
		Optional<FloorPlan> orgFloorPlanObj = floorPlanService.findById("PUNE_SDB1_4");
		if(orgFloorPlanObj.isPresent()) {
			orgSeatInformationObj.setFloorDetail(orgFloorPlanObj.get());
			orgSeatInformationObj.setCubicleNumber("C1");
			orgSeatInformationObj.setAvgRating(4.2);
			orgSeatInformationObj.setSeatNumber(orgFloorPlanObj.get().getFloorId().concat("_")
					.concat(orgSeatInformationObj.getCubicleNumber().concat("_")
							.concat(orgSeatInformationObj.getSeatPosition())));
			
			seatInformationList.add(orgSeatInformationObj);
		}else {
			System.out.println("floorPlanObj is NOT FOUND");
		}
				
		for(int i=2 ;i<=35 ;i++) {
			for(CityEnum city : CityEnum.values()) {
				SeatInformation seatInformationObj = new SeatInformation();
				seatInformationObj.setSeatPosition("S".concat(String.valueOf(i)));
				seatInformationObj.setTotalTimeOccupied(faker.random().nextInt(1, 10));
				seatInformationObj.setTotalTimeBooked(faker.random().nextInt(10, 20));
				seatInformationObj.setPreference(PreferencesEnum.WINDOW);
				seatInformationObj.setLastBookedDate(LocalDate.now());
				seatInformationObj.setIsAvailable(true);
				Optional<List<FloorPlan>> floorPlanList = floorPlanService.FindFloorPlanByCity(city);
				if(floorPlanList.isPresent() && floorPlanList.get()!=null && floorPlanList.get().size()>0) {
					Random random = new Random();
					seatInformationObj.setFloorDetail(floorPlanList.get().get(random.nextInt(floorPlanList.get().size())));
					seatInformationObj.setCubicleNumber("C".concat(String.valueOf(i)));
					seatInformationObj.setAvgRating(Double.valueOf(faker.random().nextInt(2, 5)));
					seatInformationObj.setSeatNumber(seatInformationObj.getFloorDetail().getFloorId().concat("_")
							.concat(seatInformationObj.getCubicleNumber().concat("_")
									.concat(seatInformationObj.getSeatPosition())));
					
					seatInformationList.add(seatInformationObj);
				}else {
					System.out.println("floorPlanObj is NOT FOUND");
				}
				
				seatInformationList.add(seatInformationObj);
			}
		}
		
		seatInformationList = seatInformationService.insertAll(seatInformationList);
		
		return true;
	}
}
