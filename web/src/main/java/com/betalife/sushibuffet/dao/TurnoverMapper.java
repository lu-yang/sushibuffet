package com.betalife.sushibuffet.dao;

import com.betalife.sushibuffet.model.Turnover;

public interface TurnoverMapper {
	void insertTurnover(Turnover t);

	void checkout(int id);

	void changeTable(Turnover t);
}
