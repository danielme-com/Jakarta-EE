package com.danielme.jakartaee.jpa.hibernate.dialects;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class PostgreCustomDialect extends PostgreSQL95Dialect {

    public PostgreCustomDialect() {
        super();
        registerFunction("WEEK",
                new SQLFunctionTemplate(StandardBasicTypes.STRING,
                        "DATE_PART('week', ?1)"));
    }

}
