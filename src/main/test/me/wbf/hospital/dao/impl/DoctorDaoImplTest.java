package me.wbf.hospital.dao.impl;

import me.wbf.hospital.dao.DoctorDao;
import me.wbf.hospital.entity.Doctor;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class DoctorDaoImplTest {
    DoctorDao doctorDao = new DoctorDaoImpl();

    @Test
    void save() {
        Doctor doctor = new Doctor(null, "终南山", "410328196003020345", "1514321423", 1, 60, Instant.now().toEpochMilli(), "test@qq.com", 1, 4, null);
        assertEquals(true, doctorDao.save(doctor));
    }


}