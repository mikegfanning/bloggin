package org.code_revue.controller;

import org.code_revue.dao.TicketDao;
import org.code_revue.domain.Ticket;
import org.code_revue.domain.TicketStatus;
import org.code_revue.view.TicketPdfView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@Controller
public class HomeController {

    private final TicketDao ticketDao;

    @Autowired
    public HomeController(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

	@RequestMapping(value = "/")
	public String home(Model model) {
        model.addAttribute("statusValues", TicketStatus.values());
        model.addAttribute("tickets", ticketDao.findAllTickets());
        return "home";
	}

    @RequestMapping(value = "/ticket/{ticketId}")
    public String ticket(@PathVariable String ticketId, Model model) throws NoHandlerFoundException {
        Ticket t = ticketDao.findTicketById(ticketId);
        if (null == t) {
            // Exceptionally lazy way of getting a 404, don't do this for real
            throw new NoHandlerFoundException("GET", "blah", null);
        }
        model.addAttribute("ticket", t);
        return "ticket";
    }

    @RequestMapping(value = "/ticket/{ticketId}.pdf")
    public ModelAndView ticketPdf(@PathVariable String ticketId) {
        ModelAndView result = new ModelAndView(new TicketPdfView());
        result.addObject("ticket", ticketDao.findTicketById(ticketId));
        return result;
    }

    @RequestMapping(value = "/ticket", method = RequestMethod.POST)
    public String createTicket(@ModelAttribute Ticket ticket) {
        ticketDao.createTicket(ticket);
        return "ticket";
    }

    @RequestMapping(value = "/barcode", method = RequestMethod.GET)
    public String barcodeHandler(@RequestParam String data, Model model) {
        if (data.startsWith(Ticket.BARCODE_PREFIX)) {
            data = data.substring(Ticket.BARCODE_PREFIX.length());
            return "redirect:/ticket/" + data;
        } else {
            // invalid barcode
            model.addAttribute("errorMessage", "Invalid Barcode");
            return home(model);
        }
    }

    @RequestMapping(value = "/redeem", method = RequestMethod.GET)
    public String redeem() {
        return "redeem";
    }

    @RequestMapping(value = "/redeem", method = RequestMethod.POST)
    public String redeemTicket(@RequestParam String data, Model model) {
        if (!data.startsWith(Ticket.BARCODE_PREFIX)) {
            // invalid barcode data
            model.addAttribute("errorMessage", "Invalid Barcode");
            return "redeem";
        }

        data = data.substring(Ticket.BARCODE_PREFIX.length());
        Ticket t = ticketDao.findTicketById(data);
        if (null == t) {
            // ticket not found
            model.addAttribute("errorMessage", "Ticket Not Found");
        } else if (TicketStatus.CREATED != t.getStatus()) {
            // invalid ticket status
            model.addAttribute("errorMessage", "Invalid Ticket Status");
        } else {
            t.setStatus(TicketStatus.REDEEMED);
            ticketDao.updateTicket(t);
            model.addAttribute("ticket", t);
        }

        return "redeem";
    }

}
