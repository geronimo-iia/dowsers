package com.intelligentsia.dowsers.entity;

import java.net.URI;
import java.util.Map;

import com.google.common.collect.Maps;
import com.intelligentsia.dowsers.entity.meta.MetaAttribute;
import com.intelligentsia.dowsers.entity.meta.MetaEntity;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProvider;
import com.intelligentsia.dowsers.entity.meta.MetaModel;
import com.intelligentsia.dowsers.entity.model.CustomizableSampleEntity;
import com.intelligentsia.dowsers.entity.model.SampleEntity;

public class MockMetaEntityContextRepository implements MetaEntityContextProvider {

	Map<String, MetaEntityContext> map = Maps.newHashMap();

	public MockMetaEntityContextRepository() {
		map.put(MetaAttribute.class.getName(), MetaModel.getMetaAttributModel());
		map.put(MetaEntity.class.getName(), MetaModel.getMetaEntityModel());
		map.put(EntityDynamic.class.getName(), MetaModel.getEntityDynamicModel());
				
		
		map.put(SampleEntity.class.getName(), MetaEntityContext.builder().definition( // definition
				MetaEntity.builder().name(SampleEntity.class.getName()).version(MetaModel.VERSION). // attributes
						addMetaAttribute("name", String.class).//
						addMetaAttribute("description", String.class).build())//
				.build());
		
		map.put(CustomizableSampleEntity.class.getName(), MetaEntityContext.builder().definition( // definition
				MetaEntity.builder().name(CustomizableSampleEntity.class.getName()).version(MetaModel.VERSION). // attributes
						addMetaAttribute("name", String.class).//
						addMetaAttribute("description", String.class).build())//
				.build());
	}

	@Override
	public MetaEntityContext find(URI reference) throws IllegalArgumentException, NullPointerException {
		MetaEntityContext context = map.get(Reference.getEntityPart(reference));
		if (context == null) {
			throw new IllegalArgumentException("no context found for " + reference);
		}
		return context;
	}
}
