package com.intelligentsia.dowsers.entity.model;

/**
 * PersonDto.
 * 
 */
public class PersonDto {
	private final String name;
	private final Integer age;

	public PersonDto(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "PersonDto [name=" + name + ", age=" + age + "]";
	}

}