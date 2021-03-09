package com.persistent.deskManagement.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.persistent.deskManagement.dao.SeatBookingRepository;
import com.persistent.deskManagement.entity.SeatBooking;
import com.persistent.deskManagement.model.EnumHelper.StatusEnum;
import com.persistent.deskManagement.service.SeatBookingService;

@Service("SeatBookingServiceImpl")
public class SeatBookingServiceImpl implements SeatBookingService {

	@Autowired
	SeatBookingRepository seatBookingRepository;

	@Override
	public Boolean findSeatAvailability(String seatNumber, LocalDateTime bookedFrom, LocalDateTime bookedTo) {

		Optional<List<SeatBooking>> seat = seatBookingRepository.findSeatAvailability(seatNumber, bookedFrom, bookedTo);
		if(seat.isPresent() && !seat.get().isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public SeatBooking seatBooking(String seatNumber, LocalDateTime bookedFrom, LocalDateTime bookedTo, Integer employeeId) {
		SeatBooking seatBookingObj = new SeatBooking();
		Random random = new Random();
		int rand_int1 = random.nextInt(999999);
		seatBookingObj.setBookingId("BNG".concat(String.valueOf(employeeId)).concat("-").concat(String.valueOf(rand_int1)));
		seatBookingObj.setSeatNumber(seatNumber);
		seatBookingObj.setStatus(StatusEnum.BOOKED);
		seatBookingObj.setBookedFrom(bookedFrom);
		seatBookingObj.setBookedTo(bookedTo);
		seatBookingObj.setIsTimeElapsed(false);
		seatBookingObj.setEmployeeId(employeeId);
		seatBookingObj = seatBookingRepository.save(seatBookingObj);
		return seatBookingObj;
	}

	@Override
	public Optional<List<SeatBooking>> allEmployeeBookedSeats(Integer employeeId, LocalDateTime bookedFrom, LocalDateTime bookedTo) {

		Optional<List<SeatBooking>> bookedSeatList = seatBookingRepository.findAllEmployeeBookedSeats(employeeId, bookedFrom, bookedTo);
		if(bookedSeatList.isPresent() && !bookedSeatList.get().isEmpty()) {
			return bookedSeatList;
		}
		return Optional.empty();
	}

	@Override
	public Optional<SeatBooking> occupySeat(String bookingId) {
		Optional<SeatBooking> seatObj = seatBookingRepository.findById(bookingId);
		if(seatObj.isPresent()) {
			SeatBooking seatBookingObj = seatObj.get();
			seatBookingObj.setStatus(StatusEnum.OCCUPIED);
			seatBookingObj = seatBookingRepository.save(seatBookingObj);
			return Optional.of(seatBookingObj);
		}else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<SeatBooking> returnSeat(String bookingId) {
		Optional<SeatBooking> seatObj = seatBookingRepository.findById(bookingId);
		if(seatObj.isPresent()) {
			SeatBooking seatBookingObj = seatObj.get();
			seatBookingObj.setStatus(StatusEnum.RETURNED);
			seatBookingObj = seatBookingRepository.save(seatBookingObj);
			return Optional.of(seatBookingObj);
		}else {
			return Optional.empty();
		}
	}

	@Override
	public ArrayList<String> allBookedSeats(LocalDateTime bookedFrom, LocalDateTime bookedTo) {
		ArrayList<String> allBookedSeats = new ArrayList<>();

		Optional<List<SeatBooking>> bookedSeatList = seatBookingRepository.findAllBookedSeats(bookedFrom, bookedTo);
		if(bookedSeatList.isPresent() && !bookedSeatList.get().isEmpty()) {
			for (SeatBooking seatBooking : bookedSeatList.get()) {
				allBookedSeats.add(seatBooking.getSeatNumber());
			}
		}
		return allBookedSeats;
	}

}
