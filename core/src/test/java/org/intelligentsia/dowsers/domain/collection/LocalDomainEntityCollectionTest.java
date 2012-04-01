/**
 * 
 */
package org.intelligentsia.dowsers.domain.collection;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.intelligentsia.dowsers.container.GenericDomainEntityFactory;
import org.intelligentsia.dowsers.domain.AggregateFactory;
import org.intelligentsia.dowsers.domain.DomainEntity;
import org.intelligentsia.dowsers.domain.DummyDomainEntity;
import org.intelligentsia.dowsers.domain.DummyLocalEntity;
import org.intelligentsia.dowsers.domain.collection.LocalDomainEntityList;
import org.intelligentsia.dowsers.domain.collection.LocalDomainEntityMap;
import org.intelligentsia.dowsers.domain.collection.LocalDomainEntitySet;
import org.intelligentsia.dowsers.event.GenericDomainAggregateFactory;
import org.intelligentsia.dowsers.event.processor.CacheEventProcessorProvider;

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
