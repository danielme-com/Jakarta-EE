package com.danielme.jakartaee.jpa.hibernate;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

//set this class as the property "hibernate.metadata_builder_contributor" in persistence.xml
public class CustomFunctionsMySQLMetadataBuilderContributor implements MetadataBuilderContributor {

    @Override
    public void contribute(MetadataBuilder metadataBuilder) {
        metadataBuilder.applySqlFunction(
                "WEEK",
                new StandardSQLFunction("WEEKOFYEAR", StandardBasicTypes.INTEGER));
    }

}
