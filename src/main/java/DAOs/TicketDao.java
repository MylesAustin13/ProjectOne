package DAOs;

import Entities.Ticket;

import java.util.List;

public interface TicketDao {
    void addTicket(Ticket t);        //Add an unresolved Ticket to the table
    void approveTicket(int t_id);    //Approve an unresolved Ticket
    void rejectTicket(int t_id);     //Reject an unresolved Ticket
    Ticket getTicketByID(int t_id);  //Get a ticket with the specified ID
    List<Ticket> getAllTicketsByOwner(int empl_id); //Get all the tickets made by a certain employee
    List<Ticket> getAllPendingTicketsByOwner(int empl_id); //Get all the unresolved tickets made by an employee
    List<Ticket> getAllResolvedTicketsByOwner(int empl_id); //Get all the resolved tickets made by an employee
    List<Ticket> getAllPendingTickets(); //Get all the unresolved tickets
}
