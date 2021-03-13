package me.wbf.hospital.service.impl;

import me.wbf.hospital.dao.MedicineDao;
import me.wbf.hospital.dao.impl.MedicineDaoImpl;
import me.wbf.hospital.entity.Medicine;
import me.wbf.hospital.service.MedicineService;
import me.wbf.hospital.util.PageUtil;

public class MedicineServiceImpl implements MedicineService {
    MedicineDao medicineDao = new MedicineDaoImpl();

    @Override
    public boolean save(Medicine medicine) {
        return medicineDao.save(medicine);
    }

    @Override
    public boolean update(Medicine medicine) {
        return medicineDao.update(medicine);
    }

    @Override
    public boolean deleteById(int id) {
        return medicineDao.deleteById(id);
    }

    @Override
    public int deleteByIds(int[] ids) {
        return medicineDao.deleteByIds(ids);
    }

    @Override
    public Medicine findById(int id) {
        return medicineDao.findById(id);
    }

    @Override
    public PageUtil<Medicine> findAll(PageUtil<Medicine> pageUtil) {
        return medicineDao.findAll(pageUtil);
    }

    @Override
    public PageUtil<Medicine> findByNameOrType(PageUtil<Medicine> pageUtil, String nameKeyword, String typeKeyword) {
        return medicineDao.findByNameOrType(pageUtil, nameKeyword, typeKeyword);
    }

    @Override
    public boolean existByName(String name) {
        return medicineDao.findByName(name) == null;
    }
}
