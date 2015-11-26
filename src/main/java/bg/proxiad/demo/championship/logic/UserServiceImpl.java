package bg.proxiad.demo.championship.logic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.proxiad.demo.championship.model.User;
import bg.proxiad.demo.championship.model.UserDao;


@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public User loadUser(Long id) {
		return userDao.load(id);
	}

	@Override
	public void saveOrUpdateUser(User user) {
		userDao.saveOrUpdate(user);
	}

	@Override
	public Collection<User> listAllUsers() {
		return userDao.listAll();
	}

	@Override
	public void deleteUser(Long id) {
		userDao.deleteUser(id);
	}

	
	
}
