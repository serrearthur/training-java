package cdb.test.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

import cdb.dao.impl.DAOCompanyImpl;
import cdb.model.Company;
import junit.framework.TestCase;

/**
 * Test class for {@link DAOCompanyImpl}.
 * @author aserre
 */
@RunWith(Parameterized.class)
public class DAOCompanyTest extends TestCase {

    private ResultSet resultSet;

    @Override
    @Before
    public void setUp() {
        resultSet = Mockito.mock(ResultSet.class);
    }

    @Override
    @After
    public void tearDown() {

    }

    /**
     * Sample data used for this test case.
     * @return an array of values to be used for the test
     */
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {{1, "Apple"}, {0, null}, {1, null}, {null, "a"}});
    }

    @Parameter
    public Integer id;
    @Parameter(1)
    public String name;

    /**
     * Test of the {@link DAOCompanyImpl#map(ResultSet)} method.
     * @throws SQLException thrown by the {@link ResultSet}
     */
    @Test
    public void testMapComputer() throws SQLException {
        Mockito.doReturn(id instanceof Integer ? id : 0).when(resultSet).getInt("id");
        if (!(id instanceof Integer)) {
            Mockito.doReturn(true).when(resultSet).wasNull();
        }
        Mockito.doReturn(name).when(resultSet).getString("name");

        Company company = DAOCompanyImpl.map(resultSet);
        assertEquals(company.getId(), id);
        assertEquals(company.getName(), name);
    }
}
