package fr.nouas.pojo.utils;

import javax.servlet.http.HttpServletRequest;

public abstract class Action {

	public abstract boolean executeAction(HttpServletRequest request);
}
