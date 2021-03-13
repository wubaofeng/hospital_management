package me.wbf.hospital.service.impl;

import me.wbf.hospital.dao.RegisterDao;
import me.wbf.hospital.dao.impl.RegisterDaoImpl;
import me.wbf.hospital.entity.Register;
import me.wbf.hospital.service.RegisterService;
import me.wbf.hospital.util.PageUtil;

public class RegisterServiceImpl implements RegisterService {

    RegisterDao registerDao = new RegisterDaoImpl();

    @Override
    public boolean save(Register register) {
        return registerDao.save(register);
    }

    @Override
    public boolean update(Register register) {
        return registerDao.update(register);
    }

    @Override
    public boolean deleteById(int id) {
        return registerDao.deleteById(id);
    }

    @Override
    public int deleteByIds(int[] ids) {
        return registerDao.deleteByIds(ids);
    }

    @Override
    public Register findById(int id) {
        return registerDao.findById(id);
    }

    @Override
    public PageUtil<Register> findAll(PageUtil<Register> pageUtil) {
        return registerDao.findAll(pageUtil);
    }

    @Override
    public PageUtil<Register> findByIdOrNameOrType(PageUtil<Register> pageUtil, int idKeyword, String nameKeyword, int departmentKeyword) {
        return registerDao.findByIdOrNameOrDepartment(pageUtil, idKeyword, nameKeyword, departmentKeyword);
    }

}
