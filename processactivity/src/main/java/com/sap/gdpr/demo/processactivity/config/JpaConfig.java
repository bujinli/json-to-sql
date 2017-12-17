package com.sap.gdpr.demo.processactivity.config;

import static org.eclipse.persistence.config.PersistenceUnitProperties.ALLOW_NATIVE_SQL_QUERIES;
import static org.eclipse.persistence.config.PersistenceUnitProperties.CACHE_SHARED_DEFAULT;
import static org.eclipse.persistence.config.PersistenceUnitProperties.DDL_BOTH_GENERATION;
import static org.eclipse.persistence.config.PersistenceUnitProperties.DDL_GENERATION;
import static org.eclipse.persistence.config.PersistenceUnitProperties.DDL_GENERATION_MODE;
import static org.eclipse.persistence.config.PersistenceUnitProperties.DROP_AND_CREATE;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_LEVEL;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
public class JpaConfig extends JpaBaseConfiguration {

	protected final JpaProperties properties;

	protected JpaConfig(DataSource dataSource, JpaProperties properties,
			ObjectProvider<JtaTransactionManager> jtaTransactionManagerProvider,
			ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
		super(dataSource, properties, jtaTransactionManagerProvider, transactionManagerCustomizers);
		this.properties = properties;
	}

	protected Map<String, Object> getVendorProperties() {
		Map<String, Object> vendorProperties = new HashMap<>();
		vendorProperties.putAll(properties.getProperties());

//		vendorProperties.put(WEAVING, "static");

		// vendorProperties.put(DDL_GENERATION, CREATE_OR_EXTEND);
		vendorProperties.put(DDL_GENERATION, DROP_AND_CREATE);
		vendorProperties.put(DDL_GENERATION_MODE, DDL_BOTH_GENERATION);

		vendorProperties.put(CACHE_SHARED_DEFAULT, "false");
		vendorProperties.put(ALLOW_NATIVE_SQL_QUERIES, "true");

		// "FINE" provides more details
		vendorProperties.put(LOGGING_LEVEL, "INFO");
//		vendorProperties.put(LOGGING_LOGGER, EclipseLinkLogger.class.getCanonicalName());

		// set hana as db
		// vendorProperties.put(TARGET_DATABASE, TargetDatabase.HANA);

		// show sql
		vendorProperties.put("eclipselink.logging.level.sql", "FINER");
		vendorProperties.put("eclipselink.jdbc.cache-statements", "true");
		vendorProperties.put("eclipselink.logging.parameters", "true");

//		vendorProperties.put(SESSION_CUSTOMIZER, "com.sap.grc.pm.config.DefaultSessionCustomizer");

		return vendorProperties;
	}

	protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
		return new EclipseLinkJpaVendorAdapter();
	}
}
