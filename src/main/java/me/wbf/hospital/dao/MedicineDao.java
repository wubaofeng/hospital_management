package me.wbf.hospital.dao;

import me.wbf.hospital.entity.Medicine;
import me.wbf.hospital.util.PageUtil;

/**
 * @author Baofeng.Wu
 */
public interface MedicineDao {
    boolean save(Medicine medicine);

    boolean update(Medicine medicine);

    boolean deleteById(int id);

    int deleteByIds(int[] ids);

    Medicine findById(int id);

    PageUtil<Medicine> findAll(PageUtil<Medicine> pageUtil);

    PageUtil<Medicine> findByNameOrType(PageUtil<Medicine> pageUtil, String nameKeyword, String typeKeyword);

    Medicine findByName(String name);
}
