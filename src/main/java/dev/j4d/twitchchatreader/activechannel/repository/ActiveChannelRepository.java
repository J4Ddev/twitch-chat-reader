package dev.j4d.twitchchatreader.activechannel.repository;

import dev.j4d.twitchchatreader.activechannel.domain.ActiveChannel;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Repository
public class ActiveChannelRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ActiveChannelRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(String channel, String timeOfJoin) {
        jdbcTemplate.update("INSERT INTO active_channel (channel, time_of_join) VALUES (:channel, :time_of_join)", toParams(channel, timeOfJoin));
    }

    public List<ActiveChannel> getAll() {
        return jdbcTemplate.query("SELECT id, channel, time_of_join FROM active_channel ORDER BY id", this::mapRow);
    }

    public List<ActiveChannel> getAll(List<String> channels) {
        return jdbcTemplate.query("SELECT id, channel, time_of_join FROM active_channel WHERE channel IN (:channels)", Map.of("channels", channels), this::mapRow);
    }

    private MapSqlParameterSource toParams(String channel, String timeOfJoin) {
        return new MapSqlParameterSource().addValue("channel", channel).addValue("time_of_join", timeOfJoin);
    }

    private ActiveChannel mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new ActiveChannel(resultSet.getLong("id"), resultSet.getString("channel"), Instant.parse(resultSet.getString("time_of_join")));
    }
}
