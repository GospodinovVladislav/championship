package championship;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.ThreadContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import bg.proxiad.demo.championship.ApplicationConfig;
import bg.proxiad.demo.championship.front.UsersController;
import bg.proxiad.demo.championship.logic.UserService;
import bg.proxiad.demo.championship.model.User;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
public class StandAloneUserControllerTest {

	
	private MockMvc mockMvc;
	
	@Mock
	private UserService userServiceMock;
	
	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders.standaloneSetup(new UsersController(userServiceMock))
				.setViewResolvers(viewResolver())
				.build();
		
		SecurityManager securityManager = mock(org.apache.shiro.mgt.SecurityManager.class,RETURNS_DEEP_STUBS);
		ThreadContext.bind(securityManager);
	}

	
	private ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	
	@SuppressWarnings({ "unchecked"})
	@Test
	public void testUserList() throws Exception{
		User user1 = new User();
		user1.setId(1L);
		user1.setAdmin(false);
		user1.setEmail("some@email.com");
		user1.setFirstName("Vlado");
		user1.setLastName("Gospodinov");
		user1.setPassword("vlad");
		user1.setPhotoFileName("default.jpeg");
		
		User user2 = new User();
		user2.setId(2L);
		user2.setAdmin(true);
		user2.setEmail("second@email.com");
		user2.setFirstName("Vladislav");
		user2.setLastName("Ivanov");
		user2.setPassword("vladikata");
		user2.setPhotoFileName("default.jpeg");
		
		
		when(userServiceMock.listAllUsers()).thenReturn(Arrays.asList(user1,user2));
		
		mockMvc.perform(get(("/users")))
			.andExpect(status().isOk())
			.andExpect(view().name("list-users"))
			.andExpect(forwardedUrl("/WEB-INF/jsp/list-users.jsp"))
			.andExpect(model().attribute("users", hasSize(2)))
			.andExpect(model().attribute("users", hasItem(
					allOf(hasProperty("id",equalTo(1L)),
							hasProperty("isAdmin",equalTo(false)),
							hasProperty("email",equalTo("some@email.com")),
							hasProperty("firstName",equalTo("Vlado")),
							hasProperty("lastName",equalTo("Gospodinov")),
							hasProperty("password",equalTo("vlad")),
							hasProperty("photoFileName",equalTo("default.jpeg"))
						)
					)))
			.andExpect(model().attribute("users", hasItem(
					allOf(hasProperty("id",equalTo(2L)),
							hasProperty("isAdmin",equalTo(true)),
							hasProperty("email",equalTo("second@email.com")),
							hasProperty("firstName",equalTo("Vladislav")),
							hasProperty("lastName",equalTo("Ivanov")),
							hasProperty("password",equalTo("vladikata")),
							hasProperty("photoFileName",equalTo("default.jpeg"))
						)
					)));
		
		verify(userServiceMock,times(1)).listAllUsers();
		verifyNoMoreInteractions(userServiceMock);
			
		
		
		
	}
	
	
	
}
