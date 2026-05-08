package service;

import dao.CatDao;
import dao.NutritionDao;
import dao.OwnerDao;
import dao.jdbc.JdbcCatDao;
import dao.jdbc.JdbcNutritionDao;
import dao.jdbc.JdbcOwnerDao;

/**
 * Factory class responsible for creating service-layer instances.
 *
 * <p>This factory centralizes the creation of services and their corresponding
 * DAO dependencies. It also configures the database connection parameters
 * based on the operating system.</p>
 *
 * <p>Currently supports MySQL connections on macOS and Windows environments.</p>
 * @author Michal Salabura
 * @author Bernard Joyce
 */
public class ServiceFactory {
    private final String url;
    private final String user = "root";
    private final String pass = "root";

    /**
     * Initializes the factory and configures the database connection URL
     * based on the underlying operating system.
     * @throws RuntimeException if the operating system is not supported
     */
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

    /**
     * Creates a {@link CatService} instance with its required DAO dependency.
     * @return a configured {@link CatService}
     */
    public CatService createCatService() {
        CatDao dao = new JdbcCatDao(url, user, pass);
        return new CatService(dao);
    }

    /**
     * Creates an {@link OwnerService} instance with its required DAO dependency.
     * @return a configured {@link OwnerService}
     */
    public OwnerService createOwnerService() {
        OwnerDao dao = new JdbcOwnerDao(url, user, pass);
        return new OwnerService(dao);
    }

    /**
     * Creates a {@link NutritionService} instance with its required DAO dependency.
     * @return a configured {@link NutritionService}
     */
    public NutritionService createNutritionService(){
        NutritionDao dao = new JdbcNutritionDao(url, user, pass);
        return new NutritionService(dao);
    }
}
