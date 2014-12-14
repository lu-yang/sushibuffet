package com.betalife.sushibuffet.util;

import java.util.List;

public interface Printer {

	public void addCutPaper(List<String> lines);

	public void print(List<String> lines) throws Exception;

	public void setLogo(String logo);
}
