package org.simsg.core.pm.match;

import java.util.List;

public interface IMatch {
	
	public String patternName();
	
	public List<String> parameterNames();
	
	public Object get(String parameterName);
	
	public boolean contains(String parameterName);
	
}
