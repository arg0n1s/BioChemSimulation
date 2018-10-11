package biochemsimulation.simulation.matching.viatra;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import biochemsimulation.simulation.matching.IMatch;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;

public class ViatraMatch implements IMatch {
	
	final private IPatternMatch match;
	
	public ViatraMatch(final IPatternMatch match) {
		this.match = match;
	}
	
	public IPatternMatch getIPatternMatch() {
		return match;
	}

	@Override
	public String patternName() {
		//return match.patternName();
		return match.patternName().replaceAll("^(.)*\\.", "");
	}

	@Override
	public List<String> parameterNames() {
		return match.parameterNames();
	}

	@Override
	public Object get(String parameterName) {
		return match.get(parameterName);
	}

	@Override
	public boolean contains(String parameterName) {
		return match.get(parameterName) != null;
	}
	
	@Override
	public int hashCode() {
		//return Objects.hashCode(match.toArray());
		return Arrays.hashCode(match.toArray());
	}
	
	@Override
	public boolean equals(Object other) {
		return this.hashCode()==other.hashCode();
	}

}
