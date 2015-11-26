package bg.proxiad.demo.championship.model;

import java.util.Collection;

public interface GroupDao {

	void saveOrUpdate(Group group);

	Group load(Long id);
	
	Collection<Group> listAll();
	
	void deleteGroup(Long id);
	
}
