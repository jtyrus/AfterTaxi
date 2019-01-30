package com.after.taxi.pricecheck.geocode;

public interface GeocodeService {
	/**
	 * Returns the first found geolocation for a given address.
	 * @param address		For best results address should have zipcode and no apartment/units
	 * @return
	 * @throws Exception
	 */
	GeoCoordinates getCoordinates(String address) throws Exception;
}
