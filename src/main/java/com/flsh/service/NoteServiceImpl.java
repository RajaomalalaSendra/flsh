package com.flsh.service;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.flsh.interfaces.NoteService;
import com.flsh.model.Course;

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

	@Override
	public List<Course> getECProfessor(int idProf) {
		String sql = "SELECT Element_Constitutif.*  FROM Element_Constitutif WHERE prof_id = "+idProf
					+ " UNION SELECT Element_Constitutif.*  FROM Element_Constitutif WHERE ue_id in (select ue_id FROM ";
		List<Course> courses = jdbcTemplate.query(sql, new CourseMapper());
		return courses;
	}
	
	
}
