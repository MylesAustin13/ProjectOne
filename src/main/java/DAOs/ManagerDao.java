package DAOs;

import Entities.Manager;

public interface ManagerDao {
    void addManager(Manager m);
    Manager getManagerByUsername(String uname);

}
