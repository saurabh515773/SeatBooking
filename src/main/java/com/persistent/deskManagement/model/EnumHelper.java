package com.persistent.deskManagement.model;

public class EnumHelper {

	public enum PreferencesEnum{
		WINDOW, AC, PANTRY
	}
	
	public enum GenderEnum{
		MALE, FEMALE, OTHER
	}
	
	public enum CityEnum{
		PUNE, NAGPUR, HYDERABAD, BANGALORE, GOA
	}
	
	public enum StatusEnum{
		OCCUPIED, BOOKED, RETURNED
	}
	
	public enum CRUDEnum {
		CREATE, UPDATE, QUERY, DELETE, LIST

	}
	
	public enum ResponseStatusEnum{
		SUCCESS, FAILURE
	}
}
