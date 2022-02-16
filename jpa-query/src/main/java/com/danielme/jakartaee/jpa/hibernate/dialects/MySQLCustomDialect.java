package com.danielme.jakartaee.jpa.hibernate.dialects;

import org.hibernate.dialect.MySQL8Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MySQLCustomDialect extends MySQL8Dialect {

    public MySQLCustomDialect() {
        super();
        registerFunction("WEEK",
                new StandardSQLFunction("weekofyear", StandardBasicTypes.INTEGER));
    }

}
