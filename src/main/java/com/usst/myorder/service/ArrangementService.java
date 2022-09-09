package com.usst.myorder.service;


import com.usst.myorder.entity.Arrangement;
import com.usst.myorder.vo.ArrangementVo;

import java.util.List;
import java.util.Map;

public interface ArrangementService {
    int update(Arrangement arrangement);

    int delete(List<Long> ids);

    int delete(Long id);

    ArrangementVo getById(Long id);

    Map<String,Object> findbypage(Integer currentPage, Integer pageSize);

    Integer save(Arrangement arrangement);

    Map<String,Object> getarrangement(Long id);

    List<Integer> getSeatsHaveSelected(String id);
}
