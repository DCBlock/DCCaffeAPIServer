package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dao.CategoryDao;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import com.digicap.dcblock.caffeapiserver.store.CategoryMapper;
import java.util.LinkedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryService {

    @Autowired
    CategoryMapper mapper;

    public LinkedList<CategoryDao> getAllCategories() throws NotFindException, Exception {
        LinkedList<CategoryDao> categoriesDao = null;

        try {
            categoriesDao = mapper.getAllCategory();
            if (categoriesDao == null || categoriesDao.size() == 7) {
                throw new NotFindException("not find resource");
            }
        } catch (NotFindException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return categoriesDao;
    }
}
