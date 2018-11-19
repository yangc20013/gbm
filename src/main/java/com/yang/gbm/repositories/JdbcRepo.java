package com.yang.gbm.repositories;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRepo extends JdbcTemplate {
	public JdbcRepo(DataSource dataSource) {
		super(dataSource);
	}

	protected NamedParameterJdbcTemplate getNamedTemplate() {
		return new NamedParameterJdbcTemplate(this.getDataSource());
	}
}
