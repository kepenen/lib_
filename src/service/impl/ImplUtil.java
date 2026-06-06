package service.impl;

import dbframe.core.BaseQuery;

import java.util.ArrayList;

public class ImplUtil {
    static int deleteIds(Long[] ids, StringBuilder sql, BaseQuery base) {
        for (Long id : ids) {
            sql.append("'").append(id).append("',");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");
        return base.update(sql.toString(), new ArrayList<>());
    }
}
