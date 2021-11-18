package Entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int t_id;                  //ID of the ticket

    @ManyToOne
    @JoinColumn(name = "empl_id")
    private Employee empl;              //Employee making the request

    private double amount;             //Amount of money being requested
    private boolean pending;           //Whether the ticket has been resolved (accept / reject) or is still waiting
    private String resolution_status;  //"rejected" if rejected by the manager, "approved" if approved, otherwise still pending
    private Date resolution_date;      //Date timestamp of when the finance manager made the approval.

    public Ticket(){

    }

    public Ticket(Employee empl, double amount) {
        this.empl = empl;
        this.amount = amount;
        pending = true;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public Employee getEmpl() {
        return empl;
    }

    public void setEmpl(Employee empl) {
        this.empl = empl;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public String getResolution_status() {
        return resolution_status;
    }

    public void setResolution_status(String resolution_status) {
        this.resolution_status = resolution_status;
    }

    public Date getResolution_date() {
        return resolution_date;
    }

    public void setResolution_date(Date resolution_date) {
        this.resolution_date = resolution_date;
    }
}
