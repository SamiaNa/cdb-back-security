package com.excilys.formation.cdb.persistence.dao.impl;

import com.excilys.formation.cdb.exceptions.ConnectionException;
import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.persistence.dao.CompanyDAO;
import com.excilys.formation.cdb.utils.HSQLDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CompanyDAOImplTest {
    private CompanyDAO companyDAO = CompanyDAOImpl.INSTANCE;
    private static final Long NUMBER_OF_COMPANIES = 3L;
    private static final Long NUMBER_OF_COMPANIES_WITH_NAME_COMPANY = 3L;

    private static final Company COMPANY_1 = new Company(1L, "Company 1");
    private static final Company COMPANY_2 = new Company(2L, "Company 2");
    private static final Company COMPANY_3 = new Company(3L, "Company 3");

    private static final List<Company> COMPANY_LIST = Arrays.asList(COMPANY_1, COMPANY_2, COMPANY_3);

    @Before
    public void setUp() throws SQLException, IOException, ConnectionException {
        HSQLDatabase.initDatabase();
    }

    @After
    public void cleanUp() throws SQLException, ConnectionException {
        HSQLDatabase.destroy();
    }

    @Test
    public void getNumberOfCompanies() throws DAOException {
        assertEquals(NUMBER_OF_COMPANIES, companyDAO.getNumberOfCompanies());
    }

    @Test
    public void getNumberOfCompaniesWithName() throws DAOException {
        assertEquals(NUMBER_OF_COMPANIES_WITH_NAME_COMPANY, companyDAO.getNumberOfCompaniesWithName("Company"));
    }

    @Test
    public void getCompany() throws DAOException {
        assertEquals(COMPANY_1, companyDAO.getCompany(1L));
    }

    @Test
    public void getCompanyWithName() throws DAOException {
        assertEquals(COMPANY_LIST, companyDAO.getCompaniesWithName("Company", 0L, LimitValue.TEN.getValue()));
    }

    @Test
    public void getCompanies() throws DAOException {
        assertEquals(COMPANY_LIST, companyDAO.getCompanies());
    }

    @Test
    public void getCompaniesFromTo() throws DAOException {
        assertEquals(COMPANY_LIST, companyDAO.getCompanies(0L, LimitValue.TEN.getValue()));
    }

    @Test
    public void deleteCompany() throws DAOException {
        final Long ID = 2L;
        final Long EXPECTED_LENGTH = 2L;
        companyDAO.deleteCompany(ID);

        assertNull(companyDAO.getCompany(ID));
        assertEquals(EXPECTED_LENGTH, companyDAO.getNumberOfCompanies());
    }

    @Test
    public void getComputerIDsWithCompanyID() throws DAOException {
        final Long ID = 1L;
        final List<Long> EXPECTED_LIST = Collections.singletonList(ID);

        List<Long> result = CompanyDAOImpl.INSTANCE.getComputerIDsWithCompanyID(ID);

        assertEquals(EXPECTED_LIST, result);

    }
}