package org.code_revue.domain;

import java.util.Date;

/**
 * Represents a ticket to some kind of event.
 * @author Mike Fanning
 */
public class Ticket {

    public static final String BARCODE_PREFIX = "TIK";

    private String ticketId;

    private String ticketholderName;

    private TicketStatus status = TicketStatus.CREATED;

    private Date createdDate = new Date();

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketholderName() {
        return ticketholderName;
    }

    public void setTicketholderName(String ticketholderName) {
        this.ticketholderName = ticketholderName;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
