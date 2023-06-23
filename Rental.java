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

	// dead code
	public void setVideo(Video video) {
		this.video = video;
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

	// dead code
	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	// dead code
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
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
}
