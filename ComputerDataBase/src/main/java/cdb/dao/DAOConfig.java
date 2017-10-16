package cdb.dao;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DAOConfig {
//    private static final String CONFIG_FILE = "db.properties";

    /**
     * .
     * @return a
     * @throws IOException a
     */
    @Bean
    public DataSource driverManagerDataSource() throws IOException {
//        Properties p = new Properties();
//        InputStream in = getClass().getResourceAsStream(CONFIG_FILE);
//        p.load(in);
//        in.close();
//        return new DriverManagerDataSource(p.getProperty("url"), p.getProperty("username"), p.getProperty("password"));
        return new DriverManagerDataSource("jdbc:mysql://localhost:3306/computer-database-db?autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull",
                "admincdb", "qwerty1234");
    }

//    /**
//     * .
//     * @return a
//     */
//    @Bean
//    public ConnectionManager connectionManager() {
//        return new ConnectionManager();
//    }
//
//    /**
//     * .
//     * @return a
//     */
//    @Bean
//    public DAOCompany daoCompany() {
//        return new DAOCompanyImpl();
//    }
//
//    /**
//     * .
//     * @return a
//     */
//    @Bean
//    public DAOComputer daoComputer() {
//        return new DAOComputerImpl();
//    }
//
//    /**
//     * .
//     * @return a
//     */
//    @Bean
//    public ServiceCompany serviceCompany() {
//        return new ServiceCompany();
//    }
//
//    /**
//     * .
//     * @return a
//     */
//    @Bean
//    public ServiceComputer serviceComputer() {
//        return new ServiceComputer();
//    }
}
