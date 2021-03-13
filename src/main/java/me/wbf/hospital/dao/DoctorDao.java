package me.wbf.hospital.dao;

import me.wbf.hospital.entity.Doctor;
import me.wbf.hospital.util.PageUtil;

import java.util.List;

public interface DoctorDao {
    boolean save(Doctor doctor);
    boolean update(Doctor doctor);
    boolean deleteById(int id);
    int deleteByIds(int[] ids);
    Doctor findById(int id);
    List<Doctor> findByDepartment(int department);
    PageUtil<Doctor> findAll(PageUtil<Doctor> pageUtil);
    PageUtil<Doctor> findByNameOrDepartment(PageUtil<Doctor> pageUtil,String nameKeyword,String departmentKeyword);
    Doctor findByPhone(String phone);
    Doctor findByIdCard(String idCard);
}
