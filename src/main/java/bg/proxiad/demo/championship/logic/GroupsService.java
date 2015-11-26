package bg.proxiad.demo.championship.logic;

import java.util.Collection;

import bg.proxiad.demo.championship.model.Group;


public interface GroupsService {

	Group loadGroup(Long id);
	
	void saveOrUpdateGroup(Group group);
	
	Collection<Group> listAllGroups();
	
	void deleteGroup(Long id);
	
	void generateQuarterFinalGroups();
	
	void generateSemiFinalsGroups();
	
	void generateFinalsGroups();
	
}
