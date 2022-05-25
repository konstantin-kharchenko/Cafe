package by.kharchenko.cafe.model.dao;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Menu;

import java.util.List;

public interface MenuDao {
    List<Menu> findMenuListByIdList(List<Integer> idList) throws DaoException;
}
