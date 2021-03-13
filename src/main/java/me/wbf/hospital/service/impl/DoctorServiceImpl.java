package me.wbf.hospital.service.impl;

import me.wbf.hospital.dao.DoctorDao;
import me.wbf.hospital.dao.impl.DoctorDaoImpl;
import me.wbf.hospital.entity.Doctor;
import me.wbf.hospital.service.DoctorService;
import me.wbf.hospital.util.PageUtil;

import java.util.List;

/**
 * @author Baofeng.Wu
 */
public class DoctorServiceImpl implements DoctorService {
    private DoctorDao doctorDao = new DoctorDaoImpl();

    @Override
    public boolean save(Doctor doctor) {
        return doctorDao.save(doctor);
    }

    @Override
    public boolean update(Doctor doctor) {
        return doctorDao.update(doctor);
    }

    @Override
    public boolean deleteById(int id) {
        return doctorDao.deleteById(id);
    }

    @Override
    public int deleteByIds(int[] ids) {
        return doctorDao.deleteByIds(ids);
    }

    @Override
    public Doctor findById(int id) {
        return doctorDao.findById(id);
    }

    @Override
    public List<Doctor> findByDepartment(int department) {
        return doctorDao.findByDepartment(department);
    }

    @Override
    public PageUtil<Doctor> findAll(PageUtil<Doctor> pageUtil) {
        return doctorDao.findAll(pageUtil);
    }

    @Override
    public PageUtil<Doctor> findByNameOrDepartment(PageUtil<Doctor> pageUtil, String nameKeyword, String departmentKeyword) {
        return doctorDao.findByNameOrDepartment(pageUtil, nameKeyword, departmentKeyword);
    }

    @Override
    public boolean existByPhone(String phone) {
        return doctorDao.findByPhone(phone) == null;
    }

    @Override
    public boolean existByIdCard(String idCard) {
        return doctorDao.findByIdCard(idCard) == null;
    }
}
