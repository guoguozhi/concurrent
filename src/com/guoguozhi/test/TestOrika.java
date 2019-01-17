package com.guoguozhi.test;

import ma.glasnost.orika.*;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestOrika {

    public static class Person {

        private Date birthday;

        public Person() {
        }

        public Person(Date birthday) {
            this.birthday = birthday;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }
    }

    public static class PersonDto {

        private String birthdayStr;

        public PersonDto() {
        }

        public PersonDto(String birthdayStr) {
            this.birthdayStr = birthdayStr;
        }

        public String getBirthdayStr() {
            return birthdayStr;
        }

        public void setBirthdayStr(String birthdayStr) {
            this.birthdayStr = birthdayStr;
        }

    }

    public static class StringDateCustomConverter extends CustomConverter<String, Date> {

        private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");

        @Override
        public Date convert(String s, Type<? extends Date> type) {
            Date date = null;
            try {
                date = sdf.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }

    }

    public static void main(String args[]) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter(new StringDateCustomConverter());

        /* mapperFactory.classMap(Person.class, PersonDto.class)
                .fieldMap("birthday","birthdayStr")
                .converter("myStringDateCustomConverterID").add().byDefault().register();*/

        mapperFactory.classMap(PersonDto.class, Person.class)
                .field("birthdayStr", "birthday").byDefault().register();

        BoundMapperFacade<PersonDto, Person> boundMapper = mapperFactory.getMapperFacade(PersonDto.class, Person.class);
        PersonDto personDto = new PersonDto();
        personDto.setBirthdayStr("2019-01-01 01:01:01");
        Person person = boundMapper.map(personDto);

        System.out.println(person.birthday);
    }

}
