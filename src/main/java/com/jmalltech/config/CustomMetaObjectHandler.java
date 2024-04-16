package com.jmalltech.config;


import com.jmalltech.core.config.MyMetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@Primary
public class CustomMetaObjectHandler extends MyMetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createdDate", Date.class, new Date());
        //this.strictInsertFill(metaObject, "updatedDate", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updatedDate", Date.class, new Date());
    }
}
