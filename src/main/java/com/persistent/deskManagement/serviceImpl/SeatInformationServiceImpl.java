package com.persistent.deskManagement.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.persistent.deskManagement.dao.SeatInformationRepository;
import com.persistent.deskManagement.entity.SeatInformation;
import com.persistent.deskManagement.model.EnumHelper.CityEnum;
import com.persistent.deskManagement.service.SeatInformationService;

@Service
public class SeatInformationServiceImpl implements SeatInformationService{

	@Autowired
	SeatInformationRepository seatInfoRepository;
	
	@Override
	public SeatInformation findAndReplaceSeatInformation(SeatInformation seatInformation) {
		SeatInformation seatInfoObj = new SeatInformation();
		//if(true) {
//			seatInfoObj = seatInfoRepository.getSeatInformationById(seatInformation.getSeatNumber());
//		}else {
			seatInfoObj = seatInfoRepository.save(seatInformation);
		//}
		return seatInfoObj;
	}

	@Override
	public List<SeatInformation> insertAll(List<SeatInformation> seatInformationList) {
		return seatInfoRepository.saveAll(seatInformationList);
	}

	@Override
	public Optional<List<SeatInformation>> findAllSeatsByCityAndBuildingAndFloor(CityEnum cityName, String buildingName,
			Integer floorNumber) {
		return seatInfoRepository.findAllSeatsByCityAndBuildingAndFloor(cityName, buildingName, floorNumber);
	}

}
