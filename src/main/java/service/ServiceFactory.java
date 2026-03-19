package service;

import dao.CatDao;
import dao.NutritionDao;
import dao.OwnerDao;
import dao.jdbc.JdbcCatDao;
import dao.jdbc.JdbcNutritionDao;
import dao.jdbc.JdbcOwnerDao;

public class ServiceFactory {
    private final String url;
    private final String user = "root";
    private final String pass = "root";

    public ServiceFactory() {
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("mac")) {
            url = "jdbc:mysql://localhost:8889/CatnOwner?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        } else if (OS.contains("win")) {
            url = "jdbc:mysql://localhost:3306/CatnOwner?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        } else {
            throw new RuntimeException("Unsupported OS");
        }
    }

    public CatService createCatService() {
        CatDao dao = new JdbcCatDao(url, user, pass);
        return new CatService(dao);
    }
    public OwnerService createOwnerService() {
        OwnerDao dao = new JdbcOwnerDao(url, user, pass);
        return new OwnerService(dao);
    }

    public NutritionService createNutritionService(){
        NutritionDao dao = new JdbcNutritionDao(url, user, pass);
        return new NutritionService(dao);
    }
}
