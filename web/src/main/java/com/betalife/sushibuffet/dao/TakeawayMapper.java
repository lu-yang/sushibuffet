package com.betalife.sushibuffet.dao;

import java.util.List;

import com.betalife.sushibuffet.model.Takeaway;

public interface TakeawayMapper {
	void insert(Takeaway t);

	void update(Takeaway t);

	void delete(Takeaway t);

	void deleteAll();

	List<Takeaway> selectTodayUnTakeaways();

	Takeaway select(Takeaway t);
}
