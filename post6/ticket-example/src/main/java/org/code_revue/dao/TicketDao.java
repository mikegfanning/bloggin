package org.code_revue.dao;

import org.code_revue.domain.Ticket;

import java.util.List;

/**
 * Data Access Object for CRUDing {@link org.code_revue.domain.Ticket}s.
 * @author Mike Fanning
 */
public interface TicketDao {

    /**
     * Make a new Ticket
     * @param ticket
     * @return Ticket with updated id field
     */
    public Ticket createTicket(Ticket ticket);

    /**
     * Updates a Ticket.
     * @param ticket
     * @return Number of rows that were updated
     */
    public int updateTicket(Ticket ticket);

    /**
     * Look up a Ticket from the backing data store.
     * @param ticketId
     * @return Ticket or null
     */
    public Ticket findTicketById(String ticketId);

    /**
     * Get all the Tickets in the data store.
     * @return
     */
    public List<Ticket> findAllTickets();

}
