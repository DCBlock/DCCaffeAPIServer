package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dao.CategoryDao;
import java.util.LinkedList;

public interface CategoryService {

    public LinkedList<CategoryDao> getAllCategories() throws Exception;
}
