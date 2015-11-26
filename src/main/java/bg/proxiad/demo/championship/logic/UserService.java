package bg.proxiad.demo.championship.logic;

import java.util.Collection;

import bg.proxiad.demo.championship.model.User;


public interface UserService {

	
	User loadUser(Long id);
	
	void saveOrUpdateUser(User user);
	
	Collection<User> listAllUsers();
	
	void deleteUser(Long id);
}
