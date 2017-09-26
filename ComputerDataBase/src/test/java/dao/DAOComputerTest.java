package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

import dao.impl.DAOComputer;
import junit.framework.TestCase;
import model.Computer;

@RunWith(Parameterized.class)
public class DAOComputerTest extends TestCase {
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

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            {1, 0, "bob", "1989-08-30 00:00:00", "2017-09-20 00:00:00"},
            {null, null, null, null, null},
            {1, 0, "bob", null, null},
            {null, null, null, "1989-08-30 00:00:00", "2017-09-20 00:00:00"},
        });
    }

    @Parameter
    public Integer id;
    @Parameter(1)
    public Integer companyId;
    @Parameter(2)
    public String name;
    @Parameter(3)
    public String introduced;
    @Parameter(4)
    public String discontinued;

    @Test
    public void testMapComputer() throws SQLException {
        Mockito.doReturn(id instanceof Integer ? id : 0).when(resultSet).getInt("id");
        if (!(id instanceof Integer)) {
            Mockito.doReturn(true).when(resultSet).wasNull();
        }
        Mockito.doReturn(name).when(resultSet).getString("name");
        Mockito.doReturn(introduced == null ? null : Timestamp.valueOf(introduced)).when(resultSet)
                .getTimestamp("introduced");
        Mockito.doReturn(discontinued == null ? null : Timestamp.valueOf(discontinued)).when(resultSet)
                .getTimestamp("discontinued");
        Mockito.doReturn(companyId instanceof Integer ? companyId : 0).when(resultSet).getInt("company_id");
        if (!(companyId instanceof Integer)) {
            Mockito.doReturn(true).when(resultSet).wasNull();
        }

        Computer computer = DAOComputer.map(resultSet);
        assertEquals(computer.getId(), id);
        assertEquals(computer.getName(), name);
        assertEquals(computer.getIntroduced() == null ? null : Timestamp.valueOf(computer.getIntroduced()),
                introduced == null ? null : Timestamp.valueOf(introduced));
        assertEquals(computer.getDiscontinued() == null ? null : Timestamp.valueOf(computer.getDiscontinued()),
                discontinued == null ? null : Timestamp.valueOf(discontinued));
        assertEquals(computer.getCompanyId(), companyId);
    }
}
