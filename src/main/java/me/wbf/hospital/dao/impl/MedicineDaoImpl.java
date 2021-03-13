package me.wbf.hospital.dao.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import me.wbf.hospital.dao.MedicineDao;
import me.wbf.hospital.entity.Doctor;
import me.wbf.hospital.entity.Medicine;
import me.wbf.hospital.util.PageUtil;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author Baofeng.Wu
 */
public class MedicineDaoImpl implements MedicineDao {
    static QueryRunner queryRunner = new QueryRunner(new ComboPooledDataSource());
    static BeanProcessor bean = new GenerousBeanProcessor();
    static RowProcessor processor = new BasicRowProcessor(bean);

    @Override
    public boolean save(Medicine medicine) {
        boolean result = false;
        String insertSql = "INSERT INTO tb_medicine(picture,in_price,sal_price,name,type,simple_description,quality_date,detailed_description,product_firm,readme,remark)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            int affectedRows = queryRunner.update(insertSql, medicine.getPicture(), medicine.getInPrice(), medicine.getSalPrice(), medicine.getName(), medicine.getType(),
                    medicine.getSimpleDescription(), medicine.getQualityDate(), medicine.getDetailedDescription(), medicine.getProductFirm(), medicine.getReadme(), medicine.getRemark());
            result = affectedRows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Medicine medicine) {
        boolean result = false;
        String updateSql = "UPDATE tb_medicine SET picture=?, in_price=?, sal_price=?, name=?, type=?, simple_description=?, quality_date=?, detailed_description=?, product_firm=?, readme=?, remark=? WHERE id = ?";
        try {
            int affectedRows = queryRunner.update(updateSql, medicine.getPicture(), medicine.getInPrice(), medicine.getSalPrice(), medicine.getName(), medicine.getType(),
                    medicine.getSimpleDescription(), medicine.getQualityDate(), medicine.getDetailedDescription(), medicine.getProductFirm(), medicine.getReadme(), medicine.getRemark(), medicine.getId());
            result = affectedRows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean deleteById(int id) {
        boolean result = false;
        String deleteSql = "DELETE FROM tb_medicine WHERE id = ?";
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
        String deleteSql = "DELETE FROM tb_medicine WHERE id IN ";
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
    public Medicine findById(int id) {
        String querySql = "SELECT * FROM tb_medicine WHERE id=?";
        Medicine medicine = null;
        try {
            medicine = queryRunner.query(querySql, new BeanHandler<>(Medicine.class, processor), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicine;
    }

    @Override
    public PageUtil<Medicine> findAll(PageUtil<Medicine> pageUtil) {
        String querySql = "SELECT * FROM tb_medicine LIMIT ? OFFSET ?";
        String queryCountSql = "select COUNT(*) FROM tb_medicine";
        int offset = (pageUtil.getCurrentPage() - 1) * pageUtil.getSize();
        int limit = pageUtil.getSize();
        try {
            pageUtil.setData(queryRunner.query(querySql, new BeanListHandler<>(Medicine.class, processor), limit, offset));
            pageUtil.setTotal(((int) (long) queryRunner.query(queryCountSql, new ScalarHandler<>())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pageUtil;
    }

    @Override
    public PageUtil<Medicine> findByNameOrType(PageUtil<Medicine> pageUtil, String nameKeyword, String typeKeyword) {
        String querySql = "SELECT * FROM tb_medicine WHERE 1=1 ";
        String pageSql = " LIMIT ? OFFSET ?";
        String queryCountSql = "SELECT COUNT(*) FROM tb_medicine WHERE 1=1 ";
        int offset = (pageUtil.getCurrentPage() - 1) * pageUtil.getSize();
        int limit = pageUtil.getSize();
        if (nameKeyword != null && !nameKeyword.isBlank()) {
            querySql = querySql + " AND name LIKE " + "'%" + nameKeyword + "%'";
            queryCountSql = queryCountSql + " AND name LIKE " + "'%" + nameKeyword + "%'";
        }
        if (typeKeyword != null && !typeKeyword.isBlank() && !typeKeyword.equals("0")) {
            querySql = querySql + " AND type=" + typeKeyword;
            queryCountSql = queryCountSql + " AND type=" + typeKeyword;
        }
        try {
            pageUtil.setData(
                    queryRunner.query(querySql + pageSql, new BeanListHandler<>(Medicine.class, processor), limit, offset));
            pageUtil.setTotal(((int) (long) queryRunner.query(queryCountSql, new ScalarHandler<>())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pageUtil;
    }

    @Override
    public Medicine findByName(String name) {
        String querySql = "SELECT * FROM tb_medicine WHERE name=?";
        Medicine medicine = null;
        try {
            medicine = queryRunner.query(querySql, new BeanHandler<>(Medicine.class, processor), name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicine;
    }
}
