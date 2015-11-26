package bg.proxiad.demo.championship.logic;

import java.util.Collection;

import bg.proxiad.demo.championship.model.Set;


public interface SetService {

	Set loadSet(Long id);
	
	void saveOrUpdateSet(Set set);
	
	Collection<Set> listAllSets();
	
	void deleteSet(Long id);
	
}
