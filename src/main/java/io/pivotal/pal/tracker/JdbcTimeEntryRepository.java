package io.pivotal.pal.tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;


public class JdbcTimeEntryRepository implements TimeEntryRepository {


    Logger logger = LoggerFactory.getLogger(JdbcTimeEntryRepository.class);

    private final JdbcTemplate jdbcTemplate;

    private final String TIME_ENTRY_INSERT_SQL = "insert into time_entries (project_id, " +
            "user_id, " +
            "date, " +
            "hours) values (?,?,?,?)";
    private final String TIME_ENTRY_SELECT_SQL = "select * from time_entries where id = ?";
    private final String TIME_ENTRY_SELECT_ALL_SQL = "select * from time_entries";
    private final String TIME_ENTRY_UPDATE_ID_SQL = "update time_entries set project_id = ?, user_id = ?, " +
            "date = ?, hours = ? where id = ?";
    private final String TIME_ENTRY_DELETE_ID_SQL = "delete from time_entries where id = ?";

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        KeyHolder generatedKey = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(TIME_ENTRY_INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, timeEntry.getProjectId());
            ps.setLong(2, timeEntry.getUserId());
            ps.setDate(3, java.sql.Date.valueOf(timeEntry.getDate()));
            ps.setInt(4, timeEntry.getHours());

            return ps;
        }, generatedKey);

        return this.find(generatedKey.getKey().longValue());
    }

    @Override
    public TimeEntry find(long id) {

        try {
            return jdbcTemplate.queryForObject(TIME_ENTRY_SELECT_SQL, new Object[]{id}, (rs, rowNum) -> {

                TimeEntry timeEntry1 = new TimeEntry();

                timeEntry1.setId(rs.getLong("id"));
                timeEntry1.setProjectId(rs.getLong("project_id"));
                timeEntry1.setUserId(rs.getLong("user_id"));
                timeEntry1.setDate(rs.getDate("date").toLocalDate());
                timeEntry1.setHours(rs.getInt("hours"));

                return timeEntry1;
            });
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {

        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(TIME_ENTRY_UPDATE_ID_SQL);
                ps.setLong(1, timeEntry.getProjectId());
                ps.setLong(2, timeEntry.getUserId());
                ps.setDate(3, java.sql.Date.valueOf(timeEntry.getDate()));
                ps.setInt(4, timeEntry.getHours());
                ps.setLong(5, id);
                return ps;
            });

            return this.find(id);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(TIME_ENTRY_DELETE_ID_SQL, id);
    }

    @Override
    public List list() {
        return jdbcTemplate.query(TIME_ENTRY_SELECT_ALL_SQL, (rs, rowNum) ->
            new TimeEntry(
                rs.getLong("id"),
                rs.getLong("project_id"),
                rs.getLong("user_id"),
                rs.getDate("date").toLocalDate(),
                rs.getInt("hours")
            )
        );
    }
}
