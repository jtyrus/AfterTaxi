package com.after.taxi.pricecheck.lyft;

public interface LyftClient {
	/**
	 * Returns the Lyft response for a given trip. The Lyft price is returned in cents.
	 * @param startLat
	 * @param startLon
	 * @param endLat
	 * @param endLon
	 * @return
	 */
	LyftResponse getPrice(double startLat, double startLon, double endLat, double endLon);
}
