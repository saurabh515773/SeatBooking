package com.persistent.deskManagement.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.persistent.deskManagement.entity.SeatBooking;

@Repository
public interface SeatBookingRepository extends JpaRepository<SeatBooking, String> {

	@Query("SELECT s FROM SeatBooking s "
			+ "WHERE s.seatNumber = ?1 "
			+ "AND "
			+ "s.bookedFrom BETWEEN ?2 and ?3 "
			+ "OR "
			+ "s.bookedTo BETWEEN ?2 and ?3")
	Optional<SeatBooking> findSeatAvailability(String seatNumber, LocalDateTime bookedFrom, LocalDateTime bookedTo);
	
	@Query("SELECT s FROM SeatBooking s "
			+ "WHERE s.employeeId = ?1 "
			+ "AND "
			+ "s.bookedFrom BETWEEN ?2 and ?3 "
			+ "OR "
			+ "s.bookedTo BETWEEN ?2 and ?3")
	Optional<List<SeatBooking>> findAllEmployeeBookedSeats(Integer employeeId, LocalDateTime bookedFrom, LocalDateTime bookedTo);
	
	@Query("SELECT s FROM SeatBooking s "
			+ "WHERE s.bookedFrom BETWEEN ?1 and ?2 "
			+ "OR "
			+ "s.bookedTo BETWEEN ?1 and ?2")
	Optional<List<SeatBooking>> findAllBookedSeats(LocalDateTime bookedFrom, LocalDateTime bookedTo);

}