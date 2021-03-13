package me.wbf.hospital.dao;

import me.wbf.hospital.entity.Register;
import me.wbf.hospital.util.PageUtil;

/**
 * @author Baofeng.Wu
 */
public interface RegisterDao {
    boolean save(Register register);

    boolean update(Register register);

    boolean deleteById(int id);

    int deleteByIds(int[] ids);

    Register findById(int id);

    PageUtil<Register> findAll(PageUtil<Register> pageUtil);

    PageUtil<Register> findByIdOrNameOrDepartment(PageUtil<Register> pageUtil, int registeridKeyword, String nameKeyword, int departmentKeyword);
}
