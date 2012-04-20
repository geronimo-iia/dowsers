/**
 * 
 */
package org.intelligentsia.dowsers.repository.eventsource.snapshot;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface SnapshotProcessor {
	void handle(SnapshotEvent snapshotEvent);
}
