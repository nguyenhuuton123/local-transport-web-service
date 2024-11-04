package com.vtb.seatransportservice.repository;

import com.vtb.seatransportservice.domain.entity.TablePrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITablePriceRepository extends JpaRepository<TablePrice,Long> {
}
