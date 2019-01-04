package com.digicap.dcblock.caffeapiserver.service;

import com.digicap.dcblock.caffeapiserver.dto.CategoryVo;
import com.digicap.dcblock.caffeapiserver.dto.MenusInCategoryDto;
import com.digicap.dcblock.caffeapiserver.exception.InvalidParameterException;
import com.digicap.dcblock.caffeapiserver.exception.NotFindException;
import com.digicap.dcblock.caffeapiserver.exception.UnknownException;

import java.util.LinkedList;

import org.mybatis.spring.MyBatisSystemException;

public interface CategoryService {

  LinkedList<CategoryVo> getAllCategories() throws MyBatisSystemException, NotFindException;

  CategoryVo postCategory(String name) throws MyBatisSystemException;

  MenusInCategoryDto deleteCategory(int code) throws MyBatisSystemException, UnknownException, NotFindException;

  void updateAll(LinkedList<CategoryVo> categories) throws MyBatisSystemException, InvalidParameterException;
}
