package org.code_revue.dao;

import org.apache.commons.codec.binary.Base32;
import org.code_revue.domain.Ticket;
import org.code_revue.domain.TicketStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Concrete {@link org.code_revue.dao.TicketDao} that uses a JDBC data store to CRUD @{link Ticket}s.
 * @author Mike Fanning
 */
@Repository
public class JdbcTicketDao implements TicketDao {

    private JdbcTemplate template;

    private static final TicketMapper ticketMapper = new TicketMapper();

    /**
     * Set the {@link javax.sql.DataSource} that this object will use for JDBC communications.
     * @param dataSource
     */
    @Autowired
    public void setDataSource(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public Ticket createTicket(Ticket ticket) {

        // This is almost certainly a terrible way to generate a UID for a ticket, please don't use this for real.
        if (null == ticket.getTicketId()) {
            UUID uuid = UUID.randomUUID();
            ByteBuffer buffer = ByteBuffer.allocate(8);
            byte[] data = buffer.putLong(uuid.getMostSignificantBits()).array();
            Base32 encoder = new Base32();
            String id = encoder.encodeAsString(data).substring(0, 5);
            ticket.setTicketId(id);
        }

        template.update("insert into ticket (ticket_id, ticketholder_name, status, created_date) " +
                "values (?, ?, ?, ?)", ticket.getTicketId(), ticket.getTicketholderName(),
                ticket.getStatus().toString(), ticket.getCreatedDate().getTime());
        return ticket;
    }

    @Override
    public int updateTicket(Ticket ticket) {
        int rows = template.update("update ticket set ticketholder_name = ?, status = ? where ticket_id = ?",
                ticket.getTicketholderName(), ticket.getStatus().toString(), ticket.getTicketId());
        return rows;
    }

    @Override
    public Ticket findTicketById(String ticketId) {
        Ticket ticket = null;
        try {
            ticket = template.queryForObject("select ticket_id, ticketholder_name, status, created_date from ticket " +
                    "where ticket_id = ?", ticketMapper, ticketId);
        } catch (EmptyResultDataAccessException e) {
            // Ticket was not found, do nothing.
        }
        return ticket;
    }

    @Override
    public List<Ticket> findAllTickets() {
        return template.query("select ticket_id, ticketholder_name, status, created_date from ticket", ticketMapper);
    }

    private static class TicketMapper implements RowMapper<Ticket> {
        @Override
        public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ticket ticket = new Ticket();
            ticket.setTicketId(rs.getString("ticket_id"));
            ticket.setTicketholderName(rs.getString("ticketholder_name"));
            ticket.setStatus(TicketStatus.valueOf(rs.getString("status")));
            ticket.setCreatedDate(new Date(rs.getLong("created_date")));
            return ticket;
        }
    }

}
