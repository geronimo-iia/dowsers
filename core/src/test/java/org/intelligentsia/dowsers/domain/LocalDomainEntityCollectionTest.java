/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.intelligentsia.dowsers.container.GenericDomainEntityFactory;
import org.intelligentsia.dowsers.domain.collections.LocalDomainEntityList;
import org.intelligentsia.dowsers.domain.collections.LocalDomainEntityMap;
import org.intelligentsia.dowsers.domain.collections.LocalDomainEntitySet;
import org.intelligentsia.dowsers.eventprocessor.CacheEventProcessorProvider;
import org.intelligentsia.dowsers.events.GenericDomainAggregateFactory;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Maps;

/**
 * LocalDomainEntityCollection.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class LocalDomainEntityCollectionTest {

	private AggregateFactory aggregateFactory;

	@Before
	public void initialize() {
		aggregateFactory = new GenericDomainAggregateFactory(new CacheEventProcessorProvider());
	}

	public DomainEntity getDomainEntity() {
		GenericDomainEntityFactory defaultDomainEntityFactory = new GenericDomainEntityFactory(aggregateFactory);
		return defaultDomainEntityFactory.create(DummyDomainEntity.class);
	}

	public DummyLocalEntity getDummyLocalEntity() {
		return new DummyLocalEntity();
	}

	@Test
	public void checkOnRegisterCallEntityList() {

		LocalDomainEntityList<DummyLocalEntity> entities = new LocalDomainEntityList<DummyLocalEntity>(getDomainEntity());

		DummyLocalEntity dummyLocalEntity = getDummyLocalEntity();
		assertTrue(!dummyLocalEntity.isOnRegisterCalled());
		entities.add(dummyLocalEntity);
		assertTrue(dummyLocalEntity.isOnRegisterCalled());

		List<DummyLocalEntity> list = new ArrayList<DummyLocalEntity>();
		for (int i = 0; i < 10; i++) {
			list.add(getDummyLocalEntity());
		}

		for (DummyLocalEntity entity : list) {
			assertTrue(!entity.isOnRegisterCalled());
		}
		entities.addAll(list);
		for (DummyLocalEntity entity : list) {
			assertTrue(entity.isOnRegisterCalled());
		}
	}

	@Test
	public void checkOnRegisterCallEntitySet() {

		LocalDomainEntitySet<DummyLocalEntity> entities = new LocalDomainEntitySet<DummyLocalEntity>(getDomainEntity());

		DummyLocalEntity dummyLocalEntity = getDummyLocalEntity();
		assertTrue(!dummyLocalEntity.isOnRegisterCalled());
		entities.add(dummyLocalEntity);
		assertTrue(dummyLocalEntity.isOnRegisterCalled());

		List<DummyLocalEntity> list = new ArrayList<DummyLocalEntity>();
		for (int i = 0; i < 10; i++) {
			list.add(getDummyLocalEntity());
		}

		for (DummyLocalEntity entity : list) {
			assertTrue(!entity.isOnRegisterCalled());
		}
		entities.addAll(list);
		for (DummyLocalEntity entity : list) {
			assertTrue(entity.isOnRegisterCalled());
		}
	}

	@Test
	public void checkOnRegisterCallEntityMap() {

		LocalDomainEntityMap<String, DummyLocalEntity> entities = new LocalDomainEntityMap<String, DummyLocalEntity>(getDomainEntity());

		DummyLocalEntity dummyLocalEntity = getDummyLocalEntity();
		assertTrue(!dummyLocalEntity.isOnRegisterCalled());
		entities.put(dummyLocalEntity.getIdentity(), dummyLocalEntity);
		assertTrue(dummyLocalEntity.isOnRegisterCalled());

		Map<String, DummyLocalEntity> map = Maps.newHashMap();
		for (int i = 0; i < 10; i++) {
			DummyLocalEntity e = getDummyLocalEntity();
			map.put(e.getIdentity(), e);
		}

		for (DummyLocalEntity entity : map.values()) {
			assertTrue(!entity.isOnRegisterCalled());
		}
		entities.putAll(map);
		for (DummyLocalEntity entity : map.values()) {
			assertTrue(entity.isOnRegisterCalled());
		}
	}
}
