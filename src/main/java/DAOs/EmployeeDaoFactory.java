package DAOs;

public class EmployeeDaoFactory {
    private static EmployeeDao dao;

    public EmployeeDaoFactory(){

    }

    public static EmployeeDao getEmployeeDao(){
        if(dao == null){
            dao = new EmployeeDaoImpl();
        }
        return dao;
    }
}
