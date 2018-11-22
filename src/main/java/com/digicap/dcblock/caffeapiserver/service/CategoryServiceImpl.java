package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.CategoryVo;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;
import com.digicap.dcblock.caffeapiserver.store.CategoryMapper;
import java.util.LinkedList;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private CategoryMapper mapper;

    @Autowired
    public CategoryServiceImpl(CategoryMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public LinkedList<CategoryVo> getAllCategories() {
        LinkedList<CategoryVo> categoriesVo = null;

        try {
            categoriesVo = mapper.selectAllCategory();
            if (categoriesVo == null || categoriesVo.size() == 0) {
                throw new NotFindException("not find resource");
            }
        } catch (NotFindException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return categoriesVo;
    }

    @Override
    public CategoryVo postCategory(String name) {
        CategoryVo categoryVo = null;

        try {
            categoryVo = mapper.insertCategory(name);
        } catch (MyBatisSystemException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new UnknownException(e.getMessage());
        }

        return categoryVo;
    }
}
