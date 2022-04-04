package com.capgemini.dao;

import com.capgemini.entities.Presentacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPresentacionDao extends JpaRepository<Presentacion, Long> {

}
