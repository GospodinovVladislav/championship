package bg.proxiad.demo.championship.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SCORE")
public class Score {

	
	@Id
    @GeneratedValue
	@Column(name="SCORE_ID")
	private Long id;
	
	@Column(name="MATCHES_WON")
	private int matchesWon;
	
	@Column(name="MATCHES_LOST")
	private int matchesLost;
	
	@Column(name="POINTS_MADE")
	private int pointsMade;
	
	@Column(name="POINTS_TAKEN")
	private int pointsTaken;
	
	@Column(name="SCORE")
	private int score;
	
	@OneToOne
	@JoinColumn(name="SCORE_PARTICIPANT_ID")
	private Participant participant;
	

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getMatchesWon() {
		return matchesWon;
	}

	public void setMatchesWon(int matchesWon) {
		this.matchesWon = matchesWon;
	}

	public int getMatchesLost() {
		return matchesLost;
	}

	public void setMatchesLost(int matchesLost) {
		this.matchesLost = matchesLost;
	}

	public int getPointsMade() {
		return pointsMade;
	}

	public void setPointsMade(int pointsMade) {
		this.pointsMade = pointsMade;
	}

	public int getPointsTaken() {
		return pointsTaken;
	}

	public void setPointsTaken(int pointsTaken) {
		this.pointsTaken = pointsTaken;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}
