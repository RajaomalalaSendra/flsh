package com.flsh.service;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.flsh.interfaces.NoteService;

public class NoteServiceImpl implements NoteService {
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	NamedParameterJdbcTemplate namedJdbcTemplate;  
	
	private static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);
	
	@Autowired
	public NoteServiceImpl(DataSource dsrc) {
		this.dataSource = dsrc;
		jdbcTemplate = new JdbcTemplate(dsrc);
		namedJdbcTemplate = new NamedParameterJdbcTemplate(dsrc);
		logger.info("Init period service");
	}
	
	
}
