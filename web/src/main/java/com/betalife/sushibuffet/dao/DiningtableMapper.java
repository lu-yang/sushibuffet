package com.betalife.sushibuffet.dao;

import java.util.List;

import com.betalife.sushibuffet.model.Diningtable;

public interface DiningtableMapper {
	void updateDiningtable(Diningtable table);

	List<Diningtable> selectAvailableTables();
}
