package bg.proxiad.demo.championship.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SET")
public class Set {

	@Id
    @GeneratedValue
	@Column(name="SET_ID")
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="HOST_POINTS")
	private short hostPoints;
	
	@Column(name="GUEST_POINTS")
	private short guestPoints;
	
	@ManyToOne
	@JoinColumn(name="SET_MATCH_ID")
	private Match match;
	
	
	public short getHostPoints() {
		return hostPoints;
	}

	public void setHostPoints(short hostPoints) {
		this.hostPoints = hostPoints;
	}

	public short getGuestPoints() {
		return guestPoints;
	}

	public void setGuestPoints(short guestPoints) {
		this.guestPoints = guestPoints;
	}


	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}
}
