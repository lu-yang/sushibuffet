package com.betalife.sushibuffet.asynctask;

import java.util.List;

import com.betalife.sushibuffet.model.BaseModel;

public interface AsyncTaskCallback<T extends BaseModel> {
	void callback(List<T> list);
}
