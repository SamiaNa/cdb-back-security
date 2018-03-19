package com.excilys.formation.cdb.mapper;

import com.excilys.formation.cdb.exceptions.UnauthorizedLimitValueException;
import com.excilys.formation.cdb.paginator.core.LimitValue;

import java.util.Arrays;

public class LimitValueMapper {
    public static LimitValue toLimitValue(Long value) throws UnauthorizedLimitValueException {
        for (LimitValue val : LimitValue.values()) {
            if (value.equals(val.getValue())) {
                return val;
            }
        }
        throw new UnauthorizedLimitValueException(value + " is not a valid limit value");
    }
}
