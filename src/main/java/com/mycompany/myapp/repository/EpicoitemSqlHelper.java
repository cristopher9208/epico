package com.mycompany.myapp.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class EpicoitemSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("name", table, columnPrefix + "_name"));
        columns.add(Column.aliased("category", table, columnPrefix + "_category"));
        columns.add(Column.aliased("cost_price", table, columnPrefix + "_cost_price"));
        columns.add(Column.aliased("unit_price", table, columnPrefix + "_unit_price"));
        columns.add(Column.aliased("pic_filename", table, columnPrefix + "_pic_filename"));

        return columns;
    }
}
