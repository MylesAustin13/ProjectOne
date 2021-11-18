package DAOs;

public class TicketDaoFactory {
    private static TicketDao dao;

    public TicketDaoFactory(){

    }

    public static TicketDao getTicketDao(){
        if(dao == null){
            dao = new TicketDaoImpl();
        }
        return dao;
    }
}
