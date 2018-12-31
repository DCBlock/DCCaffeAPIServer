package com.digicap.dcblock.dcblockapiserver.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digicap.dcblock.caffeapiserver.controller.MenuController;
import com.digicap.dcblock.caffeapiserver.dto.MenuDto;
import com.digicap.dcblock.caffeapiserver.service.MenuService;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(MenuController.class)
public class MenuControllerTests {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private MenuService menuService;

  @Test
  public void testGetMenus() throws Exception {
    LinkedHashMap<String, LinkedList<MenuDto>> menus = menuService.getAllMenus();

    given(menuService.getAllMenus()).willReturn(menus);

    mvc.perform(get("/api/caffe/menus"))
        .andExpect(status()
            .isOk()).andExpect(content().json(""));
  }
}
