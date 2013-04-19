package org.ossmeter.platform;

import java.util.List;

import org.ossmeter.repository.model.Project;

public interface IMetricProvider {
	
	/**
	 * Unique identifier of the metric provider. Usually the fully qualified name
	 * of the class.
	 * @return
	 */
	public String getIdentifier();

	/**
	 * Returns true if this metric provider is applicable to the given Project.
	 * @param project
	 * @return
	 */
	public boolean appliesTo(Project project);
	
	/**
	 * Presents the MP with the set of MPs that it depends on
	 * for its execution.
	 * 
	 * If this MP is *provided* information by other MPs then
	 * they will also be assigned here.
	 * @param uses
	 */
	public void setUses(List<IMetricProvider> uses);
	
	/**
	 * Specifies the list of MPs that this MP depends on for
	 * its execution.
	 * @return
	 */
	public List<String> getIdentifiersOfUses();
	
	/**
	 * Provides the MP with contextual information that it can choose
	 * to use or ignore for its execution.
	 */
	public void setMetricProviderContext(MetricProviderContext context);
}