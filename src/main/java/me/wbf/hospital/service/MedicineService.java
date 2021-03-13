package me.wbf.hospital.service;

import me.wbf.hospital.entity.Medicine;
import me.wbf.hospital.util.PageUtil;

public interface MedicineService {
    boolean save(Medicine medicine);

    boolean update(Medicine medicine);

    boolean deleteById(int id);

    int deleteByIds(int[] ids);

    Medicine findById(int id);

    PageUtil<Medicine> findAll(PageUtil<Medicine> pageUtil);

    PageUtil<Medicine> findByNameOrType(PageUtil<Medicine> pageUtil, String nameKeyword, String typeKeyword);

    boolean existByName(String name);
}
