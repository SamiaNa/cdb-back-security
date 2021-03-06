package com.excilys.formation.cdb.dto.model;

import com.excilys.formation.cdb.dto.ModelDTO;
import com.excilys.formation.cdb.dto.model.constraints.DateConstraints;
import com.excilys.formation.cdb.dto.model.constraints.NameConstraints;
import org.springframework.stereotype.Component;

import java.util.Objects;

@DateConstraints(
        date1 = "introduced",
        date2 = "discontinued"
)
@Component
public class ComputerDTO implements ModelDTO {
    public static final long NO_ID = -1L;

    private long id;

    @NameConstraints
    private String name;
    private String introduced;
    private String discontinued;
    private String companyName;
    private Long companyId;

    public ComputerDTO() {
        this.id = NO_ID;
    }

    public ComputerDTO(long id, String name, String introduced, String discontinued, String companyName, Long companyId) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.companyName = companyName;
        this.companyId = companyId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String shortToString() {
        return String.format("ID: %d, name: %s", this.id, this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ComputerDTO computerDTO = (ComputerDTO) o;
        return Objects.equals(id, computerDTO.id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ID: ").append(this.id).append("\n");
        sb.append("Name: ").append(this.name).append("\n");
        sb.append("Company ID: ").append(this.companyName).append("\n");
        sb.append("Introduced in: ");
        if (introduced != null) {
            sb.append(introduced);
        } else {
            sb.append("N/A");
        }
        sb.append("\n");
        sb.append("Discontinued in: ");
        if (discontinued != null) {
            sb.append(discontinued);
        } else {
            sb.append("N/A");
        }
        sb.append("\n");
        return sb.toString();
    }
}
