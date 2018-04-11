package com.excilys.formation.cdb.servlets;

import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.dto.paginator.PageDTO;
import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.mapper.page.PageMapper;
import com.excilys.formation.cdb.mapper.request.DashboardRequestMapper;
import com.excilys.formation.cdb.paginator.ComputerSortedPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.servlets.constants.Paths;
import com.excilys.formation.cdb.servlets.constants.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.excilys.formation.cdb.servlets.constants.ServletParameter.CURRENT_PATH;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.IS_ASCENDING;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.LIMIT_VALUES;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.ORDER_BY;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.PAGE_DTO;
import static com.excilys.formation.cdb.servlets.constants.ServletParameter.SELECTION;

@Controller
public class ServletDashboard extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ServletDashboard.class);

    @Autowired
    private ComputerService computerService;

    public ServletDashboard() {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("doGet");
        try {
            ComputerSortedPage computerSortedPage = DashboardRequestMapper.mapDoGet(request, computerService);
            request = setRequest(request, computerSortedPage);
        } catch (ServiceException e) {
            LOG.error("{}", e);
            throw new ServletException(e);
        }
        this.getServletContext().getRequestDispatcher(Views.DASHBOARD).forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("doPost");
        String selection = request.getParameter(SELECTION);

        if (selection != null && !selection.isEmpty()) {
            String[] stringToDelete = selection.split(",");
            List<Long> idList = new ArrayList<>(stringToDelete.length);
            Arrays.stream(stringToDelete)
                    .filter(s -> s.matches("[0-9]+"))
                    .map(Long::valueOf)
                    .forEach(idList::add);
            try {
                computerService.deleteComputers(idList);
            } catch (ServiceException e) {
                LOG.error("{}", e);
                throw new ServletException(e);
            }
        }
        this.doGet(request, response);
    }

    private HttpServletRequest setRequest(HttpServletRequest request, ComputerSortedPage computerSortedPage) throws ServiceException {
        LOG.debug("setRequest");
        // Setting the paths
        request.setAttribute(CURRENT_PATH, Paths.PATH_DASHBOARD);

        PageDTO<ComputerDTO> computerPageDTO = PageMapper.toPageDTO(computerSortedPage, computerService.getNumberOfComputers());
        request.setAttribute(PAGE_DTO, computerPageDTO);
        request.setAttribute(ORDER_BY, computerSortedPage.getOrderBy().getValue());
        request.setAttribute(IS_ASCENDING, computerSortedPage.isAscending());
        request.setAttribute(LIMIT_VALUES, LimitValue.toLongList());

        return request;
    }
}