package com.excilys.formation.cdb.paginator;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.paginator.core.OFFSET_VALUE;
import com.excilys.formation.cdb.service.CompanyService;

import java.util.List;

public class CompanySearchPage extends CompanyPage {

    protected String search = "";

    public CompanySearchPage(String search) {
        super();
        this.search = search;
    }

    public CompanySearchPage(String search, OFFSET_VALUE offset) {
        super();
        this.search = search;
    }

    public List<Company> goToPage(Long pageNumber) {
        this.checkValidPageNumber(pageNumber, currentLastPageNumber());

        Integer start = this.pageNumber * this.offset.getValue();
        this.page = CompanyService.INSTANCE.getCompany(search, start, this.offset.getValue());
        return this.page;
    }

    public List<Company> previous() {
        this.checkPreviousPageNumber();

        Integer start = this.pageNumber * this.offset.getValue();
        this.page = CompanyService.INSTANCE.getCompany(search, start, this.offset.getValue());
        return this.page;
    }

    public List<Company> next() {
        this.checkNextPageNumber(this.currentLastPageNumber());

        Integer start = this.pageNumber * this.offset.getValue();
        this.page = CompanyService.INSTANCE.getCompany(search, start, this.offset.getValue());
        return this.page;
    }

    public List<Company> first() {
        this.page = CompanyService.INSTANCE.getCompany(search, FIRST_PAGE, this.offset.getValue());
        return this.page;
    }

    public List<Company> last() {
        Integer start = this.pageNumber * this.offset.getValue();
        this.page = CompanyService.INSTANCE.getCompany(search, start, this.offset.getValue());
        return this.page;
    }

    public Long currentLastPageNumber() {
        Long numberOfComputer = CompanyService.INSTANCE.getNumberOfCompaniesWithName(this.search);
        return numberOfComputer / this.offset.getValue() + 1;
    }
}