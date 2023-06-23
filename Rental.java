import java.util.Date;

public class Rental {
	private Video video ;
	// magic number
	private int status ; // 0 for Rented, 1 for Returned
	private Date rentDate ;
	private Date returnDate ;

	public Rental(Video video) {
		this.video = video ;
		status = 0 ;
		rentDate = new Date() ;
	}

	public Video getVideo() {
		return video;
	}

	public int getStatus() {
		return status;
	}

	public void returnVideo() {
		if ( status == 1 ) {
			this.status = 1;
			returnDate = new Date() ;
		}
	}
	public Date getRentDate() {
		return rentDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	// Feature Envy
	public int getDaysRentedLimit() {
		int limit = 0 ;
		int daysRented ;
		// duplication
		if (getStatus() == 1) { // returned Video, status = magic number
			long diff = returnDate.getTime() - rentDate.getTime();
			daysRented = getDaysRented(diff);
		} else { // not yet returned
			long diff = new Date().getTime() - rentDate.getTime();
			daysRented = getDaysRented(diff);
		}
		if ( daysRented <= 2) return limit ;
		// type 정리
		switch ( video.getVideoType() ) {
			case Video.VHS: limit = 5 ; break ;
			case Video.CD: limit = 3 ; break ;
			case Video.DVD: limit = 2 ; break ;
		}
		return limit ;
	}

	private int getDaysRented(long diff) {
		int daysRented;
		daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		return daysRented;
	}

	public int getDaysRented(Rental each) {
		long diff = getDiff(each);
		return getRented(diff);
	}

	private long getDiff(Rental each) {
		long diff;
		if (each.getStatus() == 1) { // returned Video
			diff = each.getReturnDate().getTime() - each.getRentDate().getTime();
		} else { // not yet returned
			diff = new Date().getTime() - each.getRentDate().getTime();
		}
		return diff;
	}

	private int getRented(long diff) {
		int daysRented;
		daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		return daysRented;
	}

	public int getEachPoint(Rental each, int daysRented) {
		int eachPoint = 1;

		if ((each.getVideo().getPriceCode() == Video.NEW_RELEASE) )
			eachPoint++;

		if ( daysRented > each.getDaysRentedLimit() )
			eachPoint -= Math.min(eachPoint, each.getVideo().getLateReturnPointPenalty()) ;
		return eachPoint;
	}

	public double getEachCharge(Rental each, int daysRented) {
		double eachCharge = 0.0;

		switch (each.getVideo().getPriceCode()) {
			case Video.REGULAR:
				eachCharge += 2;
				if (daysRented > 2)
					eachCharge += (daysRented - 2) * 1.5;
				break;
			case Video.NEW_RELEASE:
				eachCharge = daysRented * 3;
				break;
		}
		return eachCharge;
	}
}
