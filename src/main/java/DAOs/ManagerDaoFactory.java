package DAOs;

public class ManagerDaoFactory {
    private static ManagerDao dao;

    public ManagerDaoFactory(){

    }

    public static ManagerDao getManagerDao(){
        if(dao == null){
            dao = new ManagerDaoImpl();
        }
        return dao;
    }
}
