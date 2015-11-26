package bg.proxiad.demo.championship.model;

import java.util.Collection;

public interface SetDao {

	void saveOrUpdate(Set set);

	Set load(Long id);
	
	Collection<Set> listAll();
	
	void deleteSet(Long id);
	
}
