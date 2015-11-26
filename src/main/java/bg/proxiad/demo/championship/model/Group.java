package bg.proxiad.demo.championship.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GROUP")
public class Group {

	
	@Id
    @GeneratedValue
	@Column(name="GROUP_ID")
	private Long id;
	
	@Column(name="GROUP_NAME")
	private String groupName;
	
	@Column(name="STAGE")
	private String stage;
	
	@Column(name="NUMBER_PARTICIPANTS")
	private int numberParticipants;

	@OneToMany(mappedBy = "participantGroup")
	private List<Participant> participants;

	@OneToMany(mappedBy = "quarterParticipantGroup")
	private List<Participant> quarterFinalsParticipants;

	@OneToMany(mappedBy = "semiFinalsGroup")
	private List<Participant> semiFinalsParticipants;

	@OneToMany(mappedBy = "finalsGroup")
	private List<Participant> finalsParticipants;

	@OneToMany(mappedBy = "matchGroup")
	private List<Match> matches;

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public List<Participant> getQuarterFinalsParticipants() {
		return quarterFinalsParticipants;
	}

	public void setQuarterFinalsParticipants(List<Participant> quarterFinalsParticipants) {
		this.quarterFinalsParticipants = quarterFinalsParticipants;
	}

	public List<Participant> getSemiFinalsParticipants() {
		return semiFinalsParticipants;
	}

	public void setSemiFinalsParticipants(List<Participant> semiFinalsParticipants) {
		this.semiFinalsParticipants = semiFinalsParticipants;
	}

	public List<Participant> getFinalsParticipants() {
		return finalsParticipants;
	}

	public void setFinalsParticipants(List<Participant> finalsParticipants) {
		this.finalsParticipants = finalsParticipants;
	}

	public int getNumberParticipants() {
		return numberParticipants;
	}

	public void setNumberParticipants(int numberParticipants) {
		this.numberParticipants = numberParticipants;
	}

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
}
