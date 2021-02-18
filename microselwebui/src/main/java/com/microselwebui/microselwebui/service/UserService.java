package com.microselwebui.microselwebui.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.microselwebui.microselwebui.beans.UserBean;
import com.microselwebui.microselwebui.proxies.IMicroselAdherentsProxy;

@Service
public class UserService {
	
	
	  @Autowired private IMicroselAdherentsProxy adherentsProxy;
	 

	List<UserBean> adherents =  new ArrayList<UserBean>();
	
	public Page<UserBean> findPaginated(List<UserBean> adherents, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<UserBean> list;

        if (adherents.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, adherents.size());
            list = adherents.subList(startItem, toIndex);
        }

        Page<UserBean> adherentsPage
          = new PageImpl<UserBean>(list, PageRequest.of(currentPage, pageSize), adherents.size());

        return adherentsPage;

	}
}
