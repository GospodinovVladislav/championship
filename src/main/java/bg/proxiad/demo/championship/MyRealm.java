package bg.proxiad.demo.championship;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import antlr.Token;
import bg.proxiad.demo.championship.logic.UserService;
import bg.proxiad.demo.championship.model.User;

public class MyRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		List<User> listUser = arg0.asList();
		
		for(User u : listUser){
			if(u.getIsAdmin()){
				info.addRole("admin");
			}
		}
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {

		List<User> listUsers = (List<User>) userService.listAllUsers();
		UsernamePasswordToken token = (UsernamePasswordToken) arg0;

		String mail = token.getUsername();
		String password = new String(token.getPassword());

		System.out.println("Name: " + mail);
		System.out.println("Password " + token.getPassword());
		
		for(User u : listUsers){
			System.out.println(u.getFirstName());
			System.out.println(u.getPassword());
			
			
			if(u.getEmail().equals(mail) && u.getPassword().equals(password)){
				System.out.println("FOUND!");
				return new SimpleAuthenticationInfo(u, token.getCredentials(), "myRealm");
			} else {
				System.out.println("NOT FOUND!");
			}
		}
		throw new AuthenticationException("Wrong username or password!");
	}
}
