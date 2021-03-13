package me.wbf.hospital.dao.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import me.wbf.hospital.dao.DoctorDao;
import me.wbf.hospital.entity.Doctor;
import me.wbf.hospital.util.PageUtil;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Baofeng.Wu
 */
public class DoctorDaoImpl implements DoctorDao {
    static QueryRunner queryRunner = new QueryRunner(new ComboPooledDataSource());
    static BeanProcessor bean = new GenerousBeanProcessor();
    static RowProcessor processor = new BasicRowProcessor(bean);

    @Override
    public boolean save(Doctor doctor) {
        boolean result = false;
        String insertSql = "INSERT INTO tb_doctor(name,id_card,phone,sex,birthday,age,email,department,education,remark)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            int affectedRows = queryRunner.update(insertSql, doctor.getName(), doctor.getIdCard(), doctor.getPhone(), doctor.getSex(), doctor.getBirthday(),
                    doctor.getAge(), doctor.getEmail(), doctor.getDepartment(), doctor.getEducation(), doctor.getRemark());
            result = affectedRows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Doctor doctor) {
        boolean result = false;
        String updateSql = "UPDATE tb_doctor set name=?, sex=?, id_card=?, phone=?, sex=?, age=?," +
                " birthday=?, email=?, department=?, education=?, remark=? where id=?";
        try {
            int affectedRows = queryRunner.update(updateSql, doctor.getName(), doctor.getSex(), doctor.getIdCard(), doctor.getPhone(), doctor.getSex(), doctor.getAge(),
                    doctor.getBirthday(), doctor.getEmail(), doctor.getDepartment(), doctor.getEducation(), doctor.getRemark(), doctor.getId());
            result = affectedRows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean deleteById(int id) {
        boolean result = false;
        String deleteSql = "DELETE FROM tb_doctor WHERE id = ?";
        try {
            result = queryRunner.update(deleteSql, id) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int deleteByIds(int[] ids) {
        int result = 0;
        String deleteSql = "DELETE FROM tb_doctor WHERE id IN ";
        String[] idsStr = Arrays.stream(ids).mapToObj(String::valueOf).toArray(String[]::new);
        String idsSql = "(" + String.join(",", idsStr) + ")";
        try {
            result = queryRunner.update(deleteSql + idsSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Doctor findById(int id) {
        String querySql = "SELECT * FROM tb_doctor WHERE id=?";
        Doctor doctor = null;
        try {
            doctor = queryRunner.query(querySql, new BeanHandler<>(Doctor.class, processor), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctor;
    }

    @Override
    public List<Doctor> findByDepartment(int department) {
        String querySql = "SELECT * FROM tb_doctor WHERE department=?";
        List<Doctor> doctorList = null;
        try {
            doctorList = queryRunner.query(querySql, new BeanListHandler<>(Doctor.class, processor), department);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctorList;
    }

    @Override
    public PageUtil<Doctor> findAll(PageUtil<Doctor> pageUtil) {
        String querySql = "SELECT * FROM tb_doctor LIMIT ? OFFSET ?";
        String queryCountSql = "select COUNT(*) FROM tb_doctor";
        int offset = (pageUtil.getCurrentPage() - 1) * pageUtil.getSize();
        int limit = pageUtil.getSize();
        try {
            pageUtil.setData(queryRunner.query(querySql, new BeanListHandler<>(Doctor.class, processor), limit, offset));
            pageUtil.setTotal(((int) (long) queryRunner.query(queryCountSql, new ScalarHandler<>())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pageUtil;
    }

    @Override
    public PageUtil<Doctor> findByNameOrDepartment(PageUtil<Doctor> pageUtil, String nameKeyword, String departmentKeyword) {
        String querySql = "SELECT * FROM tb_doctor WHERE name LIKE ? AND department REGEXP ? LIMIT ? OFFSET ?";
        String queryCountSql = "SELECT COUNT(*) FROM tb_doctor WHERE name LIKE ? AND department REGEXP ?";
        int offset = (pageUtil.getCurrentPage() - 1) * pageUtil.getSize();
        int limit = pageUtil.getSize();
        String nameParameter = "%", departmentParameter = "[0-9]";
        if (nameKeyword != null && !nameKeyword.isBlank()) {
            nameParameter = "%" + nameKeyword + "%";
        } else if (departmentKeyword != null && !departmentKeyword.isBlank() && !departmentKeyword.equals("0")) {
            departmentParameter = "^" + departmentKeyword + "$";
        }
        try {
            pageUtil.setData(
                    queryRunner.query(querySql, new BeanListHandler<>(Doctor.class, processor), nameParameter, departmentParameter, limit, offset));
            pageUtil.setTotal(((int) (long) queryRunner.query(queryCountSql, new ScalarHandler<>(), nameParameter, departmentParameter)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pageUtil;
    }

    @Override
    public Doctor findByPhone(String phone) {
        String querySql = "SELECT * FROM tb_doctor WHERE phone=?";
        Doctor doctor = null;
        try {
            doctor = queryRunner.query(querySql, new BeanHandler<>(Doctor.class, processor), phone);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctor;
    }

    @Override
    public Doctor findByIdCard(String idCard) {
        String querySql = "SELECT * FROM tb_doctor WHERE id_card=?";
        Doctor doctor = null;
        try {
            doctor = queryRunner.query(querySql, new BeanHandler<>(Doctor.class, processor), idCard);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctor;
    }
}
