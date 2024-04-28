package edu.java.scrapper.domain.repositoty;

import edu.java.scrapper.domain.model.Chat;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class JdbcChatsRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void add(long tgChatId, String name) {
        jdbcTemplate.update("insert into chats (id, name) values (?, ?)", tgChatId, name);
    }

    @Transactional
    public void remove(long tgChatId) {
        jdbcTemplate.update("delete from chats where id = ?", tgChatId);
        jdbcTemplate.update("delete from links where id not in (select distinct linkId from link_chat)");
    }

    public Optional<Chat> findChatById(long tgChatId) {
        String sql = "select * from chats where id = ?";
        List<Chat> chats = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Chat.class), tgChatId);
        return chats.stream().findFirst();
    }
}

