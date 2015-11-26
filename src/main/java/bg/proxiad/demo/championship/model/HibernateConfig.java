package bg.proxiad.demo.championship.model;

import static org.hibernate.cfg.AvailableSettings.DEFAULT_BATCH_FETCH_SIZE;
import static org.hibernate.cfg.AvailableSettings.DEFAULT_SCHEMA;
import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.FORMAT_SQL;
import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;
import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;
import static org.hibernate.cfg.AvailableSettings.USE_QUERY_CACHE;
import static org.hibernate.cfg.AvailableSettings.USE_REFLECTION_OPTIMIZER;
import static org.hibernate.cfg.AvailableSettings.USE_SECOND_LEVEL_CACHE;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.dialect.HSQLDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ComponentScan(basePackageClasses = HibernateConfig.class, useDefaultFilters = false, includeFilters = @Filter(Repository.class))
public class HibernateConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(HibernateConfig.class);
	
	@Value("${database.schema}")
	private String dbSchema;
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory());
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}
	
	@Bean
	public SessionFactory sessionFactory() {
		return new LocalSessionFactoryBuilder(dataSource())
			.addPackage(HibernateConfig.class.getPackage().getName())
			.setProperty(SHOW_SQL, "true")
			.setProperty(FORMAT_SQL, "true")
			.setProperty(HBM2DDL_AUTO, "drop-create")
			.setProperty(USE_SECOND_LEVEL_CACHE, "false")
			.setProperty(USE_QUERY_CACHE, "false")
			.setProperty(DEFAULT_SCHEMA, dbSchema)
			.setProperty(DIALECT, HSQLDialect.class.getName())
			.setProperty(USE_REFLECTION_OPTIMIZER, "false")
			.setProperty(DEFAULT_BATCH_FETCH_SIZE, "30")
			.addAnnotatedClass(Participant.class)
			.addAnnotatedClass(User.class)
			.addAnnotatedClass(Group.class)
			.addAnnotatedClass(Score.class)
			.addAnnotatedClass(Set.class)
			.addAnnotatedClass(Match.class)
			.buildSessionFactory();
	}
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:hsql://localhost/championshipdb");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}
}
