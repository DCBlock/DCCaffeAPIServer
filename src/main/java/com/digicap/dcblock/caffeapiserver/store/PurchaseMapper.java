package com.digicap.dcblock.caffeapiserver.store;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * purchase table을 사용하는 query들.
 */
public interface PurchaseMapper {

    @Select("SELECT setval('purchase_recept_id', 1)")
    int initReceptId();

    @Select("SELECT nextval('purchase_recept_id')")
    int getReceptId();
}
