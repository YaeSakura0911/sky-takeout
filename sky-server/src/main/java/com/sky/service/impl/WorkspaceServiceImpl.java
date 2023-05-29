package com.sky.service.impl;

import com.sky.mapper.WorkspaceMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    private WorkspaceMapper workspaceMapper;

    @Override
    public DishOverViewVO getDishesOverview() {

        DishOverViewVO dishOverViewVO = new DishOverViewVO();

        dishOverViewVO.setSold(workspaceMapper.selectEnableDish());

        dishOverViewVO.setDiscontinued(workspaceMapper.selectDisabledDish());

        return dishOverViewVO;

    }

    @Override
    public SetmealOverViewVO getSetmealOverview() {

        SetmealOverViewVO setmealOverViewVO = new SetmealOverViewVO();

        setmealOverViewVO.setSold(workspaceMapper.selectEnableSetmeal());

        setmealOverViewVO.setDiscontinued(workspaceMapper.selectDisabledSetmeal());

        return setmealOverViewVO;
    }

}
