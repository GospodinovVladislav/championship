package bg.proxiad.demo.championship.logic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.proxiad.demo.championship.model.Set;
import bg.proxiad.demo.championship.model.SetDao;
import bg.proxiad.demo.championship.model.UserDao;

@Service
@Transactional
public class SetServiceImpl implements SetService{

	@Autowired
	private SetDao setDao;
	
	
	@Override
	public Set loadSet(Long id) {
		return setDao.load(id);
	}

	@Override
	public void saveOrUpdateSet(Set set) {
		setDao.saveOrUpdate(set);
		
	}

	@Override
	public Collection<Set> listAllSets() {
		return setDao.listAll();
	}

	@Override
	public void deleteSet(Long id) {
		setDao.deleteSet(id);
	}

}
