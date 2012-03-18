/**
 * 
 */
package com.iia.cqrs.events;

import java.util.UUID;

/**
 *
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface SnapShotStorage {
	  SnapShot GetSnapShot(UUID entityId);
      void saveShapShot(EventProvider  entity);
}
