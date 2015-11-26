package bg.proxiad.demo.championship.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MATCH")
public class Match {

	@Id
	@GeneratedValue
	@Column(name = "MATCH_ID")
	private Long id;

	@OneToMany(mappedBy = "match")
	private List<Set> sets;

	@OneToOne
	private Participant host;

	@OneToOne
	private Participant guest;
	
	@OneToOne
	private Participant winner;

	@ManyToOne
	@JoinColumn(name = "MATCH_GROUP_ID")
	private Group matchGroup;
	

	public Participant getWinner() {
		return winner;
	}

	public void setWinner(Participant winner) {
		this.winner = winner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Set> getSets() {
		return sets;
	}

	public void setSets(List<Set> sets) {
		this.sets = sets;
	}

	public Participant getHost() {
		return host;
	}

	public void setHost(Participant host) {
		this.host = host;
	}

	public Participant getGuest() {
		return guest;
	}

	public void setGuest(Participant guest) {
		this.guest = guest;
	}

	public Group getMatchGroup() {
		return matchGroup;
	}

	public void setMatchGroup(Group matchGroup) {
		this.matchGroup = matchGroup;
	}

}
