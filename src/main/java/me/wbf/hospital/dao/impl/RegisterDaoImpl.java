package me.wbf.hospital.dao.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import me.wbf.hospital.dao.DoctorDao;
import me.wbf.hospital.dao.RegisterDao;
import me.wbf.hospital.entity.Doctor;
import me.wbf.hospital.entity.Medicine;
import me.wbf.hospital.entity.Register;
import me.wbf.hospital.util.PageUtil;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.Arrays;

public class RegisterDaoImpl implements RegisterDao {

    static QueryRunner queryRunner = new QueryRunner(new ComboPooledDataSource());
    static BeanProcessor bean = new GenerousBeanProcessor();
    static RowProcessor processor = new BasicRowProcessor(bean);

    private DoctorDao doctorDao = new DoctorDaoImpl();

    @Override
    public boolean save(Register register) {
        boolean result = false;
        String insertSql = "INSERT INTO tb_register(name,id_card,social_security_number," +
                "register_money,phone,is_pay,sex,age,consultation,department,doctor_id," +
                "register_time,status,remark) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            int affectedRows = queryRunner.update(insertSql, register.getName(), register.getIdCard(),
                    register.getSocialSecurityNumber(), register.getRegisterMoney(), register.getPhone(),
                    register.getIsPay(), register.getSex(), register.getAge(), register.getConsultation(),
                    register.getDepartment(), register.getDoctorId(), register.getRegisterTime(),
                    register.getStatus(), register.getRemark());
            result = affectedRows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Register register) {
        boolean result = false;
        String updateSql = "UPDATE tb_register SET name=?,id_card=?,social_security_number=?," +
                "register_money=?,phone=?,is_pay=?,sex=?,age=?,consultation=?,department=?," +
                "doctor_id=?,register_time=?,status=?,remark=? WHERE id=?";
        try {
            int affectedRows = queryRunner.update(updateSql, register.getName(), register.getIdCard(),
                    register.getSocialSecurityNumber(), register.getRegisterMoney(), register.getPhone(),
                    register.getIsPay(), register.getSex(), register.getAge(), register.getConsultation(),
                    register.getDepartment(), register.getDoctorId(), register.getRegisterTime(),
                    register.getStatus(), register.getRemark(),register.getId());
            result = affectedRows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean deleteById(int id) {
        boolean result = false;
        String deleteSql = "DELETE FROM tb_register WHERE id = ?";
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
        String deleteSql = "DELETE FROM tb_register WHERE id IN ";
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
    public Register findById(int id) {
        String querySql = "SELECT * FROM tb_register WHERE id=?";
        Register register = null;
        try {
            register = queryRunner.query(querySql, new BeanHandler<>(Register.class, processor), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        register.setDoctor(doctorDao.findById(register.getDoctorId()));
        return register;
    }

    @Override
    public PageUtil<Register> findAll(PageUtil<Register> pageUtil) {
        String querySql = "SELECT * FROM tb_register LIMIT ? OFFSET ?";
        String queryCountSql = "select COUNT(*) FROM tb_register";
        int offset = (pageUtil.getCurrentPage() - 1) * pageUtil.getSize();
        int limit = pageUtil.getSize();
        try {
            pageUtil.setData(queryRunner.query(querySql, new BeanListHandler<>(Register.class, processor), limit, offset));
            pageUtil.setTotal(((int) (long) queryRunner.query(queryCountSql, new ScalarHandler<>())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Register register : pageUtil.getData()) {
            register.setDoctor(doctorDao.findById(register.getDoctorId()));
        }
        return pageUtil;
    }

    @Override
    public PageUtil<Register> findByIdOrNameOrDepartment(PageUtil<Register> pageUtil, int idKeyword, String nameKeyword, int departmentKeyword) {
        String querySql = "SELECT * FROM tb_register WHERE 1=1 ";
        String pageSql = " LIMIT ? OFFSET ?";
        String queryCountSql = "SELECT COUNT(*) FROM tb_register WHERE 1=1 ";
        int offset = (pageUtil.getCurrentPage() - 1) * pageUtil.getSize();
        int limit = pageUtil.getSize();
        if (idKeyword != 0) {
            querySql += " AND id REGEXP " + idKeyword;
            queryCountSql += " AND id REGEXP " + idKeyword;
        }
        if (nameKeyword != null && !nameKeyword.isBlank()) {
            querySql += " AND name LIKE " + "'%" + nameKeyword + "%'";
            queryCountSql += " AND name LIKE " + "'%" + nameKeyword + "%'";
        }
        if (departmentKeyword != 0) {
            querySql += " AND department =" + departmentKeyword;
            queryCountSql += " AND department = " + departmentKeyword;
        }
        try {
            pageUtil.setData(
                    queryRunner.query(querySql + pageSql, new BeanListHandler<>(Register.class, processor), limit, offset));
            pageUtil.setTotal(((int) (long) queryRunner.query(queryCountSql, new ScalarHandler<>())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pageUtil;
    }
}
