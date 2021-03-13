package me.wbf.hospital.service;

import me.wbf.hospital.entity.Medicine;
import me.wbf.hospital.entity.Register;
import me.wbf.hospital.util.PageUtil;

/**
 * @author Baofeng.Wu
 */
public interface RegisterService {

    boolean save(Register register);

    boolean update(Register register);

    boolean deleteById(int id);

    int deleteByIds(int[] ids);

    Register findById(int id);

    PageUtil<Register> findAll(PageUtil<Register> pageUtil);

    PageUtil<Register> findByIdOrNameOrType(PageUtil<Register> pageUtil, int idKeyword, String nameKeyword, int departmentKeyword);

}
