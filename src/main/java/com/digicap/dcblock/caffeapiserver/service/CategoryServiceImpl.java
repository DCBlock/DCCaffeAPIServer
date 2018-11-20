package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.CategoryVo;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.store.CategoryMapper;
import java.util.LinkedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    CategoryMapper mapper;

    @Autowired
    public CategoryServiceImpl(CategoryMapper mapper) {
        this.mapper = mapper;
    }

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
}
