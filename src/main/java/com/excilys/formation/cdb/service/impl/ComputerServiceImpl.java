package com.excilys.formation.cdb.service.impl;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.paginator.ComputerPage;
import com.excilys.formation.cdb.paginator.ComputerSearchPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.persistence.DatabaseField;
import com.excilys.formation.cdb.persistence.dao.ComputerDAO;
import com.excilys.formation.cdb.persistence.dao.impl.ComputerDAOImpl;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.validators.ComputerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public enum ComputerServiceImpl implements ComputerService {
    INSTANCE;
    private static final Logger LOG = LoggerFactory.getLogger(ComputerServiceImpl.class);
    private static ComputerDAO computerDAO = ComputerDAOImpl.INSTANCE;

    ComputerServiceImpl() {

    }

    @Override
    public Long getNumberOfComputers() throws ServiceException {
        try {
            return computerDAO.getNumberOfComputers();
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the number of computers!", e);
        }
    }

    @Override
    public Long getNumberOfComputersWithName(String name) throws ServiceException {
        try {
            return computerDAO.getNumberOfComputersWithName(name);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the number of computers WITH NAME LIKE \"" + name + "\"!", e);
        }
    }

    @Override
    public Computer getComputer(Long id) throws ServiceException {
        try {
            return computerDAO.getComputer(id);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve computer with ID " + id + "!", e);
        }
    }

    @Override
    public List<Computer> getComputer(String name, long index, Long limit) throws ServiceException {
        try {
            return computerDAO.getComputersWithName(name, index, limit);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the computers WITH NAME LIKE \"" + name + "\"!", e);
        }
    }

    @Override
    public List<Computer> getComputerOrderedBy(String name, long index, Long limit, DatabaseField computerField, boolean ascending) throws ServiceException {
        try {
            return computerDAO.getComputersWithNameOrderedBy(name, index, limit, computerField, ascending);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't retrieve the computers WITH NAME LIKE \"" + name + "\"!", e);
        }
    }


    @Override
    public List<Computer> getComputers(long index, Long limit) throws ServiceException {
        try {
            return computerDAO.getComputerList(index, limit);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't get list of computers from " + index + " to " + limit + "!", e);
        }
    }

    @Override
    public List<Computer> getComputersOrderedBy(long index, Long limit, DatabaseField computerField, boolean ascending) throws ServiceException {
        try {
            return computerDAO.getComputerListOrderedBy(index, limit, computerField, ascending);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't get list of computers from " + index + " to " + limit + "!", e);
        }
    }


    @Override
    public Long persistComputer(Computer c) throws ValidationException, ServiceException {
        ComputerValidator.INSTANCE.validate(c);
        try {
            return computerDAO.persistComputer(c);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't store the computer!", e);
        }
    }

    @Override
    public void updateComputer(Computer c) throws ValidationException, ServiceException {
        ComputerValidator.INSTANCE.validate(c);
        try {
            computerDAO.updateComputer(c);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't update the computer!", e);
        }
    }

    @Override
    public void deleteComputer(Long id) throws ServiceException {
        try {
            computerDAO.deleteComputer(id);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't delete the computer with ID \"" + id + "\" !", e);
        }
    }

    @Override
    public void deleteComputers(List<Long> idList) throws ServiceException {
        try {
            computerDAO.deleteComputers(idList);
        } catch (DAOException e) {
            LOG.error("{}", e);
            throw new ServiceException("Couldn't delete the list of computers!", e);
        }
    }

    @Override
    public ComputerPage getComputers(LimitValue limit) {
        return new ComputerPage(limit);
    }

    @Override
    public ComputerSearchPage getComputer(String name, LimitValue limit) {
        return new ComputerSearchPage(name, limit);
    }


}
